package main.java.core;

import framework.PhysicsGame;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class GameManager {

    private static class GameManagerHolder {
        private static final GameManager INSTANCE = new GameManager();
    }

    public static GameManager getInstance() {
        return GameManagerHolder.INSTANCE;
    }

    private GameManager() {}

    private Stage stage;

    private PhysicsGame physicsGame;

    private Region region;

    public void initializeGame(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Jeggle");
    }

    public void showStage(Scene scene) {
        if (stage != null) {
            stage.setScene(scene);
            stage.show();
        }
    }

    public void setLevel(Region root) {
        region = root;
    }

    public void createGame() {
        physicsGame = new PhysicsGame();
    }

    public void startGame() {
        physicsGame.load(region);
        physicsGame.startGame();
    }

    public void pauseGame() {
        physicsGame.pauseGame();
    }

    public void stopGame() {
        physicsGame.stopGame();
    }
    // public void

}
