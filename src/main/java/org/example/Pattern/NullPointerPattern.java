package org.example.Pattern;

import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;

/**
 * Detects potential NullPointerExceptions in the source code.
 * Specifically, variables initialized as null and later dereferenced.
 */
public class NullPointerPattern extends AbstractPattern {

    // Log Message and Spoon AST model
    String logMessage = "Possible Null Pointer Dereference";
    CtModel model;

    // Constructor
    public NullPointerPattern(ModelUtil model) {
        this.model = model.getModel();
    }

    @Override
    public void process() {
        // Finding all local variables initialized with null
        List<CtLocalVariable<?>> localVars = model.getElements(new TypeFilter<>(CtLocalVariable.class));
        Set<String> nullVars = new HashSet<>();

        for (CtLocalVariable<?> var : localVars) {
            if (var.getDefaultExpression() != null &&
                    "null".equalsIgnoreCase(var.getDefaultExpression().toString())) {
                nullVars.add(var.getSimpleName());
            }
        }

        // Finding method calls or variable reads that use those null variables
        List<CtInvocation<?>> invocations = model.getElements(new TypeFilter<>(CtInvocation.class));
        List<CtVariableRead<?>> reads = model.getElements(new TypeFilter<>(CtVariableRead.class));

        for (CtInvocation<?> inv : invocations) {
            if (inv.getTarget() != null &&
                    inv.getTarget().toString() != null &&
                    nullVars.contains(inv.getTarget().toString())) {

                // prevent false positives inside null checks
                if (inv.getParent() != null && inv.getParent().toString().contains("!= null")) continue;

                elements.add(new elementSchema(
                        logMessage,
                        inv.getPosition().getFile().getName(),
                        inv.getPosition().getLine(),
                        inv.getPosition().getEndLine(),
                        inv.toString()
                ));
            }
        }

        for (CtVariableRead<?> read : reads) {
            if (nullVars.contains(read.getVariable().getSimpleName())) {

                // prevent false positives inside null checks
                if (read.getParent() != null && read.getParent().toString().contains("!= null")) continue;

                elements.add(new elementSchema(
                        logMessage,
                        read.getPosition().getFile().getName(),
                        read.getPosition().getLine(),
                        read.getPosition().getEndLine(),
                        read.toString()
                ));
            }
        }
    }
}


