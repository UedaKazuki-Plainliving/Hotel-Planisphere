package com.hotel.planisphere.context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static final Map<String, Object> store = new HashMap<>();

    public static void set(String key, Object value) {
        store.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) store.get(key);
    }

    public static void clear() {
        store.clear();
    }
}
