package org.example.Pattern;

import org.example.Util.ModelUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NullPointerPatternTest {

    @Test
    @DisplayName("TP: Detect Potential Null Pointer Usage in BuggyFile.java")
    public void testNullPointer_TP() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "BuggyFile.java");

        ModelUtil model = new ModelUtil(path);
        final NullPointerPattern rule = new NullPointerPattern(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertFalse(res.isEmpty(), "Expected to detect at least one potential null pointer usage");
        assertEquals("Possible Null Pointer Dereference", res.get(0).message);
        assertTrue(res.get(0).code.contains("str.length"), "The detection should relate to str.length()");
    }

    @Test
    @DisplayName("TN: No Null Pointer Issues in CleanFile.java")
    public void testNullPointer_TN() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "CleanFile.java");

        ModelUtil model = new ModelUtil(path);
        final NullPointerPattern rule = new NullPointerPattern(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertFalse(res.isEmpty(), "Expected no null pointer issues to be detected");
    }
}

