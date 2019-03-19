package main.java.core.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import main.java.JeggleGame;
import main.java.controller.Controller;
import main.java.core.util.ViewController;

import java.io.IOException;

public class FxmlLoader {

    public static ViewController<Region, Controller> loadViewController(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(JeggleGame.class.getResource(path));
            Region region = loader.load();
            Controller controller = loader.getController();
            return new ViewController<>(region, controller);
        } catch (IOException e) {
            throw new LoaderException("Could not load viewController");
        }
    }

    public static <N, C extends Controller> ViewController<N,C>
    loadViewController(Class<N> nodeClass, Class<C> controllerClass, String path) {
        try {
            FXMLLoader loader = new FXMLLoader(JeggleGame.class.getResource(path));
            if (nodeClass != null && controllerClass != null) {
                N node = nodeClass.cast(loader.load());
                C controller = controllerClass.cast(loader.getController());
                return new ViewController<>(node, controller);
            } else {
                throw new LoaderException("nodeClass or controllerClass is null");
            }
        } catch (IOException exception) {
            throw new LoaderException("Could not load viewController");
        }
    }

}
