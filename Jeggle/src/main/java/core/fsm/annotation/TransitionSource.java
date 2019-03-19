package main.java.core.fsm.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Repeatable(TransitionSources.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransitionSource {
    String target();
    String event();
}
