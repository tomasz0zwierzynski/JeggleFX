package main.java.core.state;

import javafx.scene.layout.Region;
import main.java.controller.LevelController;
import main.java.core.fsm.StateContext;
import main.java.core.fsm.StateMachine;
import main.java.core.fsm.annotation.OnEntry;
import main.java.core.fsm.annotation.OnExit;
import main.java.core.fsm.annotation.State;
import main.java.core.fsm.annotation.TransitionSource;
import main.java.core.level.LevelEventListener;
import main.java.core.util.ViewController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@State("simulation")
@TransitionSource(target = "score", event = "scoreEvent")
public class SimulationState extends AbstractState {
    private static final Logger LOG = LogManager.getLogger( SimulationState.class );

    public SimulationState(StateMachine machine, StateContext data) {
        super(machine, data);
    }

    ViewController<Region, LevelController> level;

    @OnEntry
    public void onEntry() {

        level = (ViewController<Region, LevelController>) context.get(StateContext.LEVEL_CONTEXT_KEY);
        level.getController().addLevelEventListener(listener);

    }

    @OnExit
    public void onExit() {

        if (level != null) {
            level.getController().removeLevelEventListener(listener);
        }

    }

    private LevelEventListener listener = levelEvent -> machine.triggerEvent("scoreEvent");

}
