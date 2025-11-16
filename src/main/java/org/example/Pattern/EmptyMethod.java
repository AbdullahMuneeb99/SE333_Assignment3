package org.example.Pattern;

// Importing necessary classes from the Spoon library and other utility classes.
import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

/**
 * A custom static analysis rule that checks for empty method clauses in Java source code.
 * It extends Spoon's AbstractPattern to process CtMethod elements in the Abstract Syntax Tree (AST).
 */
public class EmptyMethod extends AbstractPattern {

    // Log message that will be used for the empty method blocks.
    String logMessage = "Empty Method Block";

    // The CtModel object
    CtModel model;

    // Constructor that takes a ModelUtil object and initializes the CtModel field.
    public EmptyMethod(ModelUtil model){
        this.model = model.getModel(); // Retrieves the CtModel from the ModelUtil.
    }

    /**
     * Processes the CtModel to find empty method blocks and add them to the 'elements' list.
     */
    @Override
    public void process() {
        // Getting all the method blocks (CtMethod) in the CtModel.

        List<CtMethod<?>> methods = model.getElements(new TypeFilter<>(CtMethod.class));
        for (CtMethod<?> m : methods) {

            if (m.getBody() == null) continue;

            // If method body exists but has no statements
            if (m.getBody().getStatements().isEmpty()) {
                elements.add(new elementSchema(
                        logMessage,
                        m.getPosition().getFile().getName(),
                        m.getPosition().getLine(),
                        m.getPosition().getEndLine(),
                        m.toString()
                ));
            }
        }
    }
}
