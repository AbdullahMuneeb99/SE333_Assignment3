package org.example.Util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.Util.ModelUtil;

public class ModelUtilTest {

    @Test
    public void testAddruletoanalyze() {
        // Test void method executes without error
        ModelUtil obj = new ModelUtil();
        assertDoesNotThrow(() -> {
            obj.addRuleToAnalyze(new AbstractPattern());
        });
    }

    @Test
    public void testRunanalysis() {
        // Test void method executes without error
        ModelUtil obj = new ModelUtil();
        assertDoesNotThrow(() -> {
            obj.runAnalysis();
        });
    }

    @Test
    public void testPrint() {
        // Test void method executes without error
        ModelUtil obj = new ModelUtil();
        assertDoesNotThrow(() -> {
            obj.print();
        });
    }
}
