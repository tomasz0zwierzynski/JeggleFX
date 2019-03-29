package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BallotronController extends Controller {

    @FXML private Label ballsLabel;
    @FXML private Label pointsLabel;

    public void setBalls(Integer balls) {
        ballsLabel.setText(String.valueOf(balls));
    }

    public void setPoints(Integer points) {
        pointsLabel.setText(String.valueOf(points));
    }

}
