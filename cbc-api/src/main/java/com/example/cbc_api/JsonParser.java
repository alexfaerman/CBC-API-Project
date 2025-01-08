package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    public static Map<String, Map<String, Object>> parseJson(String json) {
        Map<String, Map<String, Object>> result = new HashMap<>();
        json = json.trim().substring(1, json.length() - 1); // remove the outer brackets

        String[] entries = json.split("},\\s*\""); // split by client blocks
        for (String entry : entries) {
            entry = entry.replace("{", "").replace("}", "");
            String[] keyValue = entry.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].replace("\"", "").trim();
                String value = "{" + keyValue[1].trim(); // add opening brace back
                result.put(key, parseJsonObject(value));
            }
        }

        return result;
    }

    public static Map<String, Object> parseJsonObject(String json) {
        Map<String, Object> map = new HashMap<>();
        json = json.trim().substring(1, json.length() - 1); // remove the outer brackets

        String[] pairs = json.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // split by comma outside quotes
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].replace("\"", "").trim();
                String value = keyValue[1].trim().replace("\"", "");
                map.put(key, value);
            }
        }

        return map;
    }

    public static String mapToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\", ");
        }
        if (json.length() > 1) {
            json.setLength(json.length() - 2); // remove trailing comma and space
        }
        json.append("}");
        return json.toString();
    }
}
