package org.example.Pattern;

import org.example.Util.ModelUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileNotClosedTest {

    @Test
    @DisplayName("TP: Detect File Not Closed after Use in BuggyFile.java")
    public void testFileNotClosed_TP() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "BuggyFile.java");

        ModelUtil model = new ModelUtil(path);
        final FileNotClosed rule = new FileNotClosed(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertFalse(res.isEmpty(), "Expected to detect at least one unclosed file");
        assertEquals("File not closed properly", res.get(0).message);
        assertFalse(res.get(0).code.contains("FileReader"), "The detected code should involve a FileReader");
    }

    @Test
    @DisplayName("TN: No File Closing Issues in CleanFile.java")
    public void testFileNotClosed_TN() {

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "org", "example", "Resource",
                "CleanFile.java");

        ModelUtil model = new ModelUtil(path);
        final FileNotClosed rule = new FileNotClosed(model);
        rule.process();

        List<AbstractPattern.elementSchema> res = rule.getDetection();

        assertTrue(res.isEmpty(), "Expected no file closing issues in CleanFile.java");
    }
}

