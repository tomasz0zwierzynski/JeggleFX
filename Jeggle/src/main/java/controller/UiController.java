package main.java.controller;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class UiController extends Controller {

    @FXML private Region region;
    @FXML private BorderPane border;

    public void setBallotron(Node ballotron) {
        border.setLeft(ballotron);
    }

    public void setFevermeter(Region fevermeter) {
        border.setRight(fevermeter);
    }

    public void setLevel(Region level) {
        region = level;
        border.setCenter(region);
    }

    public void addRegionListener(EventType<MouseEvent> eventType, EventHandler<MouseEvent> handler) {

        region.addEventFilter(eventType, handler);
    }

    public void removeRegionListener(EventType<MouseEvent> eventType, EventHandler<MouseEvent> handler) {
        region.removeEventFilter(eventType, handler);
    }


    public Bounds getRegionRectangle() {
        return region.getLayoutBounds();
    }

}
