package org.example.Pattern;

import org.example.Util.ModelUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DivisionByZeroTest {

    @Test
    @DisplayName("TP: Detect Division By Zero in BuggyFile.java")
    public void testDivisionByZero_TP() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "BuggyFile.java");

        ModelUtil model = new ModelUtil(path);
        final DivisionByZero rule = new DivisionByZero(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertFalse(res.isEmpty(), "Expected to detect at least one division by zero");
        assertEquals("Division by Zero", res.get(0).message);
        assertTrue(res.get(0).code.contains("/"), "Detected code should contain a division operator");
    }

    @Test
    @DisplayName("TN: No Division By Zero in CleanFile.java")
    public void testDivisionByZero_TN() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "CleanFile.java");

        ModelUtil model = new ModelUtil(path);
        final DivisionByZero rule = new DivisionByZero(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertTrue(res.isEmpty(), "Expected no division by zero to be detected");
    }
}

