package main.java.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class UiController extends Controller {

    @FXML private Region region;
    @FXML private BorderPane border;

    public void setLevel(Region level) {
        region = level;
        border.setCenter(region);
    }

    public void setRegionListener(EventHandler<MouseEvent> handler) {

        region.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
    }
}
