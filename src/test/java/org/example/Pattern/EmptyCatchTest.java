package org.example.Pattern;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.Pattern.EmptyCatch;

public class EmptyCatchTest {

    @Test
    public void testConstructor() {
        // Test constructor initializes object correctly
        assertDoesNotThrow(() -> {
            EmptyCatch obj = new EmptyCatch();
            assertNotNull(obj);
        });
    }

    @Test
    public void testProcess() {
        // Test void method executes without error
        EmptyCatch obj = new EmptyCatch();
        assertDoesNotThrow(() -> {
            obj.process();
        });
    }
}
