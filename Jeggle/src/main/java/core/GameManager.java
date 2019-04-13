package main.java.core;

import framework.PhysicsGame;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
        // stage.setTitle("Jeggle");

//        VBox vBox = new VBox();
//        vBox.getChildren().add(new Label("Loading..."));
//        Scene loadingScene = new Scene(vBox);
//        stage.setScene(loadingScene);
//        stage.setTitle("Loading...");
//        Platform.runLater(() -> {
//            stage.show();
//        });
    }

    @Deprecated
    public void showStage(Scene scene) {
        if (stage != null) {
            Platform.runLater(()->{
                stage.setScene(scene);
                stage.show();
            });
        }
    }

    @Deprecated
    public void setLevel(Region root) {
        region = root;
    }

    @Deprecated
    public void createGame() {
        Platform.runLater(()->{
            physicsGame = new PhysicsGame();
        });
    }

    @Deprecated
    public void startGame() {
        Platform.runLater(()->{
            physicsGame.load(region);
            physicsGame.startGame();
        });
    }

    @Deprecated
    public void pauseGame() {
        Platform.runLater(()->{
            physicsGame.pauseGame();
        });
    }

    @Deprecated
    public void stopGame() {
        Platform.runLater(()->{
            physicsGame.stopGame();
        });
    }
    // public void

}
