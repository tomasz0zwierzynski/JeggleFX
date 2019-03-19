package main.java.core.fsm.annotation;

import java.lang.annotation.*;

@Deprecated
@Target(ElementType.TYPE)
@Repeatable(TransitionTargets.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransitionTarget {
    String source();
}
