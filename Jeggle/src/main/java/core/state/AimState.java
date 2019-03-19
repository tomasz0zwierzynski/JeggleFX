package main.java.core.state;

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
import main.java.core.util.ViewController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@State("aim")
@TransitionSource(target = "simulation", event = "simulationEvent")
public class AimState extends AbstractState {
    private static final Logger LOG = LogManager.getLogger( AimState.class );

    public AimState(StateMachine machine, StateContext context) {
        super(machine, context);
    }

    @OnEntry
    public void entering() {
        LOG.debug("aimState: entering()");

        ViewController<Region, UiController> ui = (ViewController<Region, UiController>) context.get("Ui");
        ui.getController().setRegionListener((event) -> {
            machine.triggerEvent("simulationEvent");
        });
    }

    @OnExit
    public void clearing() {
        LOG.debug("aimState: clearing()");

        ViewController<Region, JeggleController> level = (ViewController<Region, JeggleController>) context.get("Level");
        level.getController().shootBall(0.0, 0.0);
    }

}
