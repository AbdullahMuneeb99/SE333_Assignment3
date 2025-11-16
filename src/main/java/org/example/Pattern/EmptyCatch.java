package org.example.Pattern;

// Importing necessary classes from the Spoon library and other utility classes.
import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtCatch;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

/**
 * A custom static analysis rule that checks for empty catch clauses in Java source code.
 * It extends Spoon's AbstractPattern to process CtCatch elements in the Abstract Syntax Tree (AST).
 */
public class EmptyCatch extends AbstractPattern {

    // Log message that will be used for the empty catch blocks.
    String logMessage = "Empty Catch Block";

    // The CtModel object
    CtModel model;

    // Constructor that takes a ModelUtil object and initializes the CtModel field.
    public EmptyCatch(ModelUtil model){
        this.model = model.getModel(); // Retrieves the CtModel from the ModelUtil.
    }

    /**
     * Processes the CtModel to find empty catch blocks and add them to the 'elements' list.
     */
    @Override
    public void process() {
        // Get all the catch blocks (CtCatch) in the CtModel.
        List<CtCatch> catches = model.getElements(new TypeFilter<>(CtCatch.class));

        // Iterating through each catch block found.
        for (CtCatch c : catches) {
            // Checking if the catch block body is empty (no statements inside).
            if (c.getBody().getStatements().isEmpty()) {
                // If empty, add it to the 'elements' list.
                elements.add(new elementSchema(
                        logMessage,
                        c.getPosition().getFile().getName(),
                        c.getPosition().getLine(),
                        c.getPosition().getEndLine(),
                        c.toString()
                ));
            }
        }
    }
}
