package org.example.Pattern;

import org.example.Util.ModelUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IncorrectStringComparisonTest {

    @Test
    @DisplayName("TP: Detect String Comparison using '==' in BuggyFile.java")
    public void testIncorrectStringComparison_TP() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "BuggyFile.java");

        ModelUtil model = new ModelUtil(path);
        final IncorrectStringComparison rule = new IncorrectStringComparison(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertFalse(res.isEmpty(), "Expected at least one incorrect string comparison to be detected");
        assertEquals("Incorrect String Comparison using '=='", res.get(0).message);
        assertTrue(res.get(0).code.contains("=="), "The detected code should include '==' operator");
    }

    @Test
    @DisplayName("TN: No Incorrect String Comparison in CleanFile.java")
    public void testIncorrectStringComparison_TN() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "CleanFile.java");

        ModelUtil model = new ModelUtil(path);
        final IncorrectStringComparison rule = new IncorrectStringComparison(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertTrue(res.isEmpty(), "Expected no incorrect string comparisons in CleanFile.java");
    }
}

