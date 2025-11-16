package org.example.Pattern;

import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * Detects divisions by zero in Java code.
 * Includes direct divisions by numeric literals (e.g., x / 0)
 * and divisions by variables known to be initialized to zero.
 */
public class DivisionByZero extends AbstractPattern {

    String logMessage = "Division by Zero";
    CtModel model;

    public DivisionByZero(ModelUtil model) {
        this.model = model.getModel();
    }

    @Override
    public void process() {
        // Getting all binary operators in the AST
        for (CtBinaryOperator<?> binOp : model.getElements(new TypeFilter<>(CtBinaryOperator.class))) {

            // Focusing on division and remainder operators
            String opKind = binOp.getKind().toString();
            if (!(opKind.equals("DIV") || opKind.equals("MOD"))) continue;

            CtExpression<?> right = binOp.getRightHandOperand();

            // Case 1: Direct division by literal zero
            if (right instanceof CtLiteral<?>) {
                Object val = ((CtLiteral<?>) right).getValue();
                if (val instanceof Number && ((Number) val).doubleValue() == 0.0) {
                    addFinding(binOp);
                    continue;
                }
            }

            // Case 2: Division by variable that is initialized to zero
            if (right != null && right.getType() != null) {
                String varName = right.toString();

                // Checking if a variable of the same name was declared and initialized to 0
                for (CtVariable<?> var : model.getElements(new TypeFilter<>(CtVariable.class))) {
                    if (var.getSimpleName().equals(varName)
                            && var.getDefaultExpression() instanceof CtLiteral<?>) {
                        Object val = ((CtLiteral<?>) var.getDefaultExpression()).getValue();
                        if (val instanceof Number && ((Number) val).doubleValue() == 0.0) {
                            addFinding(binOp);
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds the finding to the element list with formatted data.
     */
    private void addFinding(CtBinaryOperator<?> binOp) {
        elements.add(new elementSchema(
                logMessage,
                binOp.getPosition().getFile().getName(),
                binOp.getPosition().getLine(),
                binOp.getPosition().getEndLine(),
                binOp.toString()
        ));
    }
}



