package main.java.core.state;

import javafx.scene.layout.Region;
import main.java.controller.LevelController;
import main.java.core.fsm.StateContext;
import main.java.core.fsm.StateMachine;
import main.java.core.fsm.annotation.OnEntry;
import main.java.core.fsm.annotation.OnExit;
import main.java.core.fsm.annotation.State;
import main.java.core.fsm.annotation.TransitionSource;
import main.java.core.util.ViewController;

@State("score")
@TransitionSource(target = "aim", event = "reaimEvent")
public class ScoreState extends AbstractState {

    public ScoreState(StateMachine machine, StateContext data) {
        super(machine, data);
    }

    @OnEntry
    public void onEntry() {

        ViewController<Region, LevelController> level = (ViewController<Region, LevelController>) context.get(StateContext.LEVEL_CONTEXT_KEY);
        level.getController().removeHighlighted();

        machine.triggerEvent("reaimEvent");
    }

    @OnExit
    public void onExtit() {

    }
}
