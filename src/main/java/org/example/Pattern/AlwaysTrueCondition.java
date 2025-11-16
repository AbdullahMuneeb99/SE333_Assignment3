package org.example.Pattern;

import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtWhile;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

/**
 * Detects control-flow statements with conditions that always evaluate to true.
 * Examples:
 *   if (true) { ... }
 *   while (true) { ... }
 */
public class AlwaysTrueCondition extends AbstractPattern {

    String logMessage = "Always True Condition";
    CtModel model;

    // Constructor: initialized this rule with a reference to the parsed model
    public AlwaysTrueCondition(ModelUtil model) {
        this.model = model.getModel();
    }

    @Override
    public void process() {

        // Getting a list of all if-statements in the model
        List<CtIf> ifs = model.getElements(new TypeFilter<>(CtIf.class));

        // Iterating through the list and checking if condition exists and is true
        for (CtIf ifStmt : ifs) {
            if (ifStmt.getCondition() != null &&
                    "true".equals(ifStmt.getCondition().toString().trim())) {
                elements.add(new elementSchema(
                        logMessage,
                        ifStmt.getPosition().getFile().getName(),
                        ifStmt.getPosition().getLine(),
                        ifStmt.getPosition().getEndLine(),
                        ifStmt.toString()
                ));
            }
        }

        // Iterating through while loops and Checking if they exist and are true
        List<CtWhile> whiles = model.getElements(new TypeFilter<>(CtWhile.class));
        for (CtWhile w : whiles) {
            if (w.getLoopingExpression() != null &&
                    "true".equals(w.getLoopingExpression().toString().trim())) {
                elements.add(new elementSchema(
                        logMessage,
                        w.getPosition().getFile().getName(),
                        "WhileLoop",
                        w.getPosition().getLine(),
                        w.getPosition().getEndLine(),
                        w.toString()
                ));
            }
        }
    }
}

