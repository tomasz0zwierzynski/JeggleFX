package main.java.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class ConsoleController extends Controller {

    @FXML private TextField textField;

    public void addText(String text) {
        Platform.runLater(()->{
            textField.setText(textField.getText() + text);
        });
    }

}
