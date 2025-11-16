package org.example.Pattern;
import org.example.Util.ModelUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spoon.reflect.declaration.CtElement;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmptyCatchTest {

    @Test
    @DisplayName("TN: No Empty Methods in CleanFile.java")
    public void testEmptyMethod_TN() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "CleanFile.java");

        ModelUtil model = new ModelUtil(path);
        final EmptyMethod rule = new EmptyMethod(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertTrue(res.isEmpty(), "Expected no empty methods to be detected");
    }
}