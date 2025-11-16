package org.example.Pattern;

import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * Detects string comparisons using '==' or '!=' instead of '.equals()'.
 */
public class IncorrectStringComparison extends AbstractPattern {

    // No change to message — matches test
    String logMessage = "Incorrect String Comparison using '=='";
    CtModel model;

    //Constructor
    public IncorrectStringComparison(ModelUtil model) {
        this.model = model.getModel();
    }

    @Override
    public void process() {
        // Finding all binary operators
        for (CtBinaryOperator<?> binOp : model.getElements(new TypeFilter<>(CtBinaryOperator.class))) {
            // Check if operator is == or !=
            if (binOp.getKind() == BinaryOperatorKind.EQ || binOp.getKind() == BinaryOperatorKind.NE) {
                String leftType = binOp.getLeftHandOperand().getType() != null
                        ? binOp.getLeftHandOperand().getType().getQualifiedName()
                        : "";
                String rightType = binOp.getRightHandOperand().getType() != null
                        ? binOp.getRightHandOperand().getType().getQualifiedName()
                        : "";

                //  Only flags when both sides are java.lang.String
                if (leftType.equals("java.lang.String") && rightType.equals("java.lang.String")) {
                    elements.add(new elementSchema(
                            logMessage,
                            binOp.getPosition().getFile().getName(),
                            binOp.getPosition().getLine(),
                            binOp.getPosition().getEndLine(),
                            binOp.toString()
                    ));
                }
            }
        }
    }
}
