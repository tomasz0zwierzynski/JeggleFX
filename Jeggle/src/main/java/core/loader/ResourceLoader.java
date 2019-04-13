package main.java.core.loader;

import javafx.fxml.FXMLLoader;
import main.java.JeggleGame;
import main.java.controller.Controller;
import main.java.core.loader.Resources.Resource;
import main.java.core.util.ViewController;

import java.io.IOException;

public class ResourceLoader {

    private static class ResourceLoaderHolder {
        private static final ResourceLoader INSTANCE = new ResourceLoader();
    }

    private ResourceLoader() { }

    public ResourceLoader getInstance() {
        return ResourceLoaderHolder.INSTANCE;
    }

    public <N, C extends Controller> ViewController<N,C> loadFxmlResource(Resource.Fxml resource) {
        try {
            FXMLLoader loader = new FXMLLoader(JeggleGame.class.getResource(resource.getPath()));
            if (resource.getNodeClass() != null && resource.getControllerClass()!= null) {
                N node = (N) resource.getNodeClass().cast(loader.load());
                C controller = (C) resource.getControllerClass().cast(loader.getController());
                return new ViewController<>(node, controller);
            } else {
                throw new LoaderException("nodeClass or controllerClass is null");
            }
        } catch (IOException exception) {
            throw new LoaderException("Could not load viewController");
        }
    }

}
