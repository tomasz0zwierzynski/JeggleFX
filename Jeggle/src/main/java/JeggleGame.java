package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.core.GameManager;
import main.java.core.fsm.FiniteStateMachine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JeggleGame extends Application {

    private static final Logger LOG = LogManager.getLogger( JeggleGame.class );

    @Override
    public void start(Stage primaryStage) {

        GameManager.getInstance().initializeGame(primaryStage);

        FiniteStateMachine stateMachine = new FiniteStateMachine("main.java.core.state");
        stateMachine.init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
