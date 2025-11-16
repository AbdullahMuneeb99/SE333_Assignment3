package org.example.Pattern;

import org.example.Util.ModelUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlwaysTrueConditionTest {

    @Test
    @DisplayName("TP: Detect Always True Condition in BuggyFile.java")
    public void testAlwaysTrueCondition_TP() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "BuggyFile.java");

        ModelUtil model = new ModelUtil(path);
        final AlwaysTrueCondition rule = new AlwaysTrueCondition(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertFalse(res.isEmpty(), "Expected at least one always-true condition to be detected");
        assertEquals("Always True Condition", res.get(0).message);
        assertTrue(res.get(0).code.contains("if (true)"), "Detected code should include 'if (true)'");
    }

    @Test
    @DisplayName("TN: No Always True Condition in CleanFile.java")
    public void testAlwaysTrueCondition_TN() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "CleanFile.java");

        ModelUtil model = new ModelUtil(path);
        final AlwaysTrueCondition rule = new AlwaysTrueCondition(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertTrue(res.isEmpty(), "Expected no always true conditions in CleanFile.java");
    }
}

