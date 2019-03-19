package main.java.core.util;

import javafx.util.Pair;
import main.java.controller.Controller;

public class ViewController<N,C extends Controller> extends Pair<N,C> {

    public N getView() {
        return super.getKey();
    }

    public C getController() {
        return super.getValue();
    }

    public ViewController(N node, C controller) {
        super(node, controller);
    }
}