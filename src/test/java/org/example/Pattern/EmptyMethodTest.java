package org.example.Pattern;

import org.example.Util.ModelUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmptyMethodTest {

    @Test
    @DisplayName("TP: Detect Empty Method Block in BuggyFile.java")
    public void testEmptyMethod_TP() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "BuggyFile.java");

        ModelUtil model = new ModelUtil(path);

        // Applying rule
        final EmptyMethod rule = new EmptyMethod(model);
        rule.process();

        // Getting results
        List<AbstractPattern.elementSchema> res = rule.getDetection();

        // Verifying detection
        assertFalse(res.isEmpty(), "Expected at least one empty method to be detected");
        assertEquals("Empty Method Block", res.get(0).message);
        assertTrue(res.get(0).code.contains("method0"), "The first detection should correspond to method0()");
    }

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

