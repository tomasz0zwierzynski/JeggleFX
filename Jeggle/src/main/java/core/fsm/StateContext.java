package main.java.core.fsm;

import java.util.HashMap;
import java.util.Map;

public class StateContext {

    public static final String LEVEL_CONTEXT_KEY = "LEVEL_CONTEXT_KEY";
    public static final String UI_CONTEXT_KEY = "UI_CONTEXT_KEY";
    public static final String BALLOTRON_CONTEXT_KEY = "BALLOTRON_CONTEXT_KEY";
    public static final String FEVERMETER_CONTEXT_KEY = "FEVERMETER_CONTEXT_KEY";

    private Map<String, Object> objects = new HashMap<>();

    public void put(String key, Object object) {
        objects.put(key, object);
    }

    public Object get(String key) {
        return objects.get(key);
    }

}
