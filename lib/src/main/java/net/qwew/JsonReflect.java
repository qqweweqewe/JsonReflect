package net.qwew;

import java.lang.reflect.*;

import java.util.HashMap;
import java.util.Map;

import java.io.FileWriter;
import java.io.IOException;

public class JsonReflect {

    private Object object;

    // constructors
    public JsonReflect(Object object) {
        this.object = object;
    }

    public JsonReflect() {
        this.object = null;
    }

    // object getter and setter
    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    //SERIALIZATION

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
    
    // method to write the json representation of an object
    public String toJson() {
        Map<String, Object> fields = collectFields();
        String jsonRepresentation = "{";
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            //check if the value of the field is a string and add quotes if it is
            if (entry.getValue() instanceof String) {
                jsonRepresentation += "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",";
            } else {
                jsonRepresentation += "\"" + entry.getKey() + "\":" + entry.getValue() + ",";
            }
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
    
    //DESERIALIZATION
    
    // a method to convert json string to a Map
    public Map<String, Object> jsonToMap(String json) {
        
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
