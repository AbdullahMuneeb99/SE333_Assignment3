package org.example.Pattern;

import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;

/**
 * Detects file resources opened but not closed properly in the same method.
 */
public class FileNotClosed extends AbstractPattern {

    // Log Message and Spoon AST CtModel
    String logMessage = "File not closed properly";
    CtModel model;

    //Constructor initializes with spoon model
    public FileNotClosed(ModelUtil model) {
        this.model = model.getModel();
    }

    @Override
    public void process() {
        // Target methods for scoping (to match opens and closes within the same method)
        List<CtMethod<?>> methods = model.getElements(new TypeFilter<>(CtMethod.class));

        for (CtMethod<?> m : methods) {
            // Gathering file-opening variables
            List<CtLocalVariable<?>> localVars = m.getElements(new TypeFilter<>(CtLocalVariable.class));
            Set<String> fileVars = new HashSet<>();

            for (CtLocalVariable<?> var : localVars) {
                if (var.getType() != null) {
                    String typeName = var.getType().getSimpleName();
                    if (typeName.contains("File") || typeName.contains("Reader") || typeName.contains("Stream")) {
                        fileVars.add(var.getSimpleName());
                    }
                }
            }

            // Gathering close() invocations
            List<CtInvocation<?>> invocations = m.getElements(new TypeFilter<>(CtInvocation.class));
            Set<String> closedVars = new HashSet<>();

            for (CtInvocation<?> inv : invocations) {
                if (inv.getExecutable().getSimpleName().equals("close") && inv.getTarget() != null) {
                    closedVars.add(inv.getTarget().toString());
                }
            }

            // Variables opened but never closed
            for (String v : fileVars) {
                if (!closedVars.contains(v)) {
                    elements.add(new elementSchema(
                            logMessage,
                            m.getPosition().getFile().getName(),
                            m.getPosition().getLine(),
                            m.getPosition().getEndLine(),
                            "File resource '" + v + "' opened but not closed"
                    ));
                }
            }
        }
    }
}

