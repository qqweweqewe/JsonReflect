package net.qwew;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JsonReflectTest {
    
    @Test
    public void initialTest() {
        String expected = "test";

        String actual = JsonReflect.test();
    
        assertEquals(expected, actual);
    }
}
