package net.qwew;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class JsonReflectTest {

    @Test
    public void fromJsonTest() {
        // given
        String json = "{\"name\": \"John\", \"age\": 30}";
        JsonReflect<Person> jsonReflect = new JsonReflect<>(new Person("John", 30));

        // when
        Map<String, Object> fields = jsonReflect.jsonToMap(json);

        // then
        assertEquals("John", fields.get("name"));
        assertEquals(30, Integer.parseInt((String)fields.get("age")));
    }

    private static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}