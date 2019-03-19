package main.java.core.state;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import main.java.controller.JeggleController;
import main.java.controller.UiController;
import main.java.core.GameManager;
import main.java.core.fsm.StateContext;
import main.java.core.fsm.StateMachine;
import main.java.core.fsm.annotation.OnEntry;
import main.java.core.fsm.annotation.OnExit;
import main.java.core.fsm.annotation.State;
import main.java.core.fsm.annotation.TransitionSource;
import main.java.core.loader.FxmlLoader;
import main.java.core.util.Path;
import main.java.core.util.ViewController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@State(value = "init", initial = true)
@TransitionSource(target = "aim", event = "aimEvent")
public class InitialState extends AbstractState {
    private static final Logger LOG = LogManager.getLogger( InitialState.class );

    public InitialState(StateMachine machine, StateContext context) {
        super(machine, context);
    }

    @OnEntry
    public void initialize() {
        LOG.debug("initState: initialize()");

        LOG.debug("Loading gui viewController");
        ViewController<Region, UiController> ui = FxmlLoader.loadViewController(Region.class, UiController.class, Path.Ui.UI);

        context.put("Ui", ui);


        ViewController<Region, JeggleController> level = FxmlLoader.loadViewController(Region.class, JeggleController.class, Path.Level.JEGGLE);
        ui.getController().setLevel(level.getView());

        context.put("Level", level);

        GameManager.getInstance().showStage(new Scene(ui.getView()));

        GameManager.getInstance().setLevel(level.getView());

        GameManager.getInstance().createGame();

        GameManager.getInstance().startGame();

        LOG.debug("Sending event to aim state");
        machine.triggerEvent("aimEvent");
    }

    @OnExit
    public void clean() {
        LOG.debug("initState: clean()");

    }
}
