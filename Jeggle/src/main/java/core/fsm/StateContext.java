package main.java.core.fsm;

import java.util.HashMap;
import java.util.Map;

public class StateContext {

    private Map<String, Object> objects = new HashMap<>();

    public void put(String key, Object object) {
        objects.put(key, object);
    }

    public Object get(String key) {
        return objects.get(key);
    }

}
