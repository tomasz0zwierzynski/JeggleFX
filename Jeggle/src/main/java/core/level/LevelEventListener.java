package main.java.core.level;

@FunctionalInterface
public interface LevelEventListener {
    void eventOccured(LevelEvent levelEvent);
}
