package main.java.core.fsm.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    String value();
    boolean initial() default false;
}
