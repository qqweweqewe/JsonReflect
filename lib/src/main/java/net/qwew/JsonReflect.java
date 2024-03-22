package net.qwew;

import java.lang.reflect.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.io.FileWriter;
import java.io.IOException;

public class JsonReflect<T> {

    private Object object;

    public JsonReflect(Object object) {
        this.object = object;
    }

    // method to acquire all the fields of an object
    private Map<String, Object> collectFields() {  
        Map<String, Object> fields = new HashMap<>();
        Class<?> classVar = object.getClass();
        Field[] fieldsArray = classVar.getDeclaredFields();
        for (Field field : fieldsArray) {
            field.setAccessible(true);
            try {
                fields.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fields;
    }     
    
    //method to write the json representation of an object
    public String toJson() {
        Map<String, Object> fields = collectFields();
        String jsonRepresentation = "{";
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            jsonRepresentation += "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",";
        }
        jsonRepresentation = jsonRepresentation.substring(0, jsonRepresentation.length() - 1);
        jsonRepresentation += "}";
        return jsonRepresentation;
    }  
    
    // save json representation of an object to a file
    public void saveToFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName + ".json");
            writer.write(toJson());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Saved to file: " + fileName);
    }  
    

    // a method to convert json string to a Map
    public static Map<String, Object> jsonToMap(String json) {
        
        Map<String, Object> map = new HashMap<>();

        String[] keyValuePairs = json.substring(1, json.length() - 1).replace(" ", "").split(",");
        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split(":");
            String key = keyValue[0].substring(1, keyValue[0].length() - 1);
            Object value = keyValue[1].replace("\"", "");
            map.put(key, value);
        }
        
        return map;
    }

}
