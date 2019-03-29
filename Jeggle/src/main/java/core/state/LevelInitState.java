package main.java.core.state;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import main.java.controller.BallotronController;
import main.java.controller.LevelController;
import main.java.controller.UiController;
import main.java.core.GameManager;
import main.java.core.fsm.StateContext;
import main.java.core.fsm.StateMachine;
import main.java.core.fsm.annotation.OnEntry;
import main.java.core.fsm.annotation.OnExit;
import main.java.core.fsm.annotation.State;
import main.java.core.fsm.annotation.TransitionSource;
import main.java.core.level.Level;
import main.java.core.level.LevelBuilder;
import main.java.core.loader.FxmlLoader;
import main.java.core.util.Path;
import main.java.core.util.ViewController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@State(value = "init", initial = true)
@TransitionSource(target = "aim", event = "aimEvent")
public class LevelInitState extends AbstractState {
    private static final Logger LOG = LogManager.getLogger( LevelInitState.class );

    public LevelInitState(StateMachine machine, StateContext context) {
        super(machine, context);
    }

    @OnEntry
    public void onEntry() {

        LOG.debug("Loading gui viewController");
        ViewController<Region, UiController> ui = FxmlLoader.loadViewController(Region.class, UiController.class, Path.Ui.UI);

        context.put(StateContext.UI_CONTEXT_KEY, ui);

        ViewController<Region, LevelController> level = FxmlLoader.loadViewController(Region.class, LevelController.class, Path.Level.LEVEL);

        level.getController().initializeLevel( LevelBuilder.build( Level.getLevel1Layout() ) );

        level.getController().prepareOrangePegs();

        ViewController<Parent, BallotronController> ballotron = FxmlLoader.loadViewController(Parent.class, BallotronController.class, Path.Ui.BALLOTRON);
        context.put(StateContext.BALLOTRON_CONTEXT_KEY, ballotron);

        ui.getController().setLevel(level.getView());
        ui.getController().setBallotron(ballotron.getView());

        context.put(StateContext.LEVEL_CONTEXT_KEY, level);

        GameManager.getInstance().showStage(new Scene(ui.getView()));

        GameManager.getInstance().setLevel(level.getView());

        GameManager.getInstance().createGame();

        GameManager.getInstance().startGame();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        level.getController().showPegs();

        LOG.debug("Sending event to aim state");
        machine.triggerEvent("aimEvent");
    }

    @OnExit
    public void onExit() {

    }
}
