package main.java.core.loader.Resources;

import javafx.scene.Node;
import main.java.controller.Controller;
import main.java.controller.UiController;
import main.java.core.util.Path;

/*
Jeden Resource loader do wszytskiego, każdy resource dziedziczy po Resource<Type>

 */

public class Resource {
    public enum Type {
        FXML,
        SOUND,
        GRAPHIC,
        MUSIC
    }

    public enum Fxml {
        MainMenu("", Node.class, null),
        Ui(Path.Ui.UI, Node.class, UiController.class);

        String path;
        Class<?> nodeClass;
        Class<? extends Controller> controllerClass;

        Fxml(String path, Class<?> nodeClass, Class<? extends Controller> controllerClass) {
            this.path = path;
            this.nodeClass = nodeClass;
            this.controllerClass = controllerClass;
        }

        public String getPath() {
            return path;
        }

        public Class<?> getNodeClass() {
            return nodeClass;
        }

        public Class<? extends Controller> getControllerClass() {
            return controllerClass;
        }
    }

}
