package org.example.Pattern;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.Pattern.AbstractPattern;

public class AbstractPatternTest {

    @Test
    public void testPrint() {
        // Test void method executes without error
        AbstractPattern obj = new AbstractPattern();
        assertDoesNotThrow(() -> {
            obj.print();
        });
    }
}
