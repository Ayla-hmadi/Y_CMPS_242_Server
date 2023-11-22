package com.yplatform;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Assertions; // Import the assertTrue method

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTest {

    @Test
    public void testApp() {
        assertTrue(true);
    }
}
