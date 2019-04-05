package main.java.core.debug;

import javafx.scene.Node;
import main.java.controller.ConsoleController;
import main.java.core.util.ViewController;

public class ConsoleManager {

    private static class ConsoleManagerHolder {
        private static final ConsoleManager INSTANCE = new ConsoleManager();
    }

    private ConsoleManager() { }

    public static ConsoleManager getInstance() {
        return ConsoleManager.ConsoleManagerHolder.INSTANCE;
    }

    private ViewController<Node, ConsoleController> console;

    public void init(ViewController<Node, ConsoleController> console) {
        this.console = console;
    }

    public void println(String message) {
        if (console != null) {
            console.getController().addText(message);
        }
    }


}
