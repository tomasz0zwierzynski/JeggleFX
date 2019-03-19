package main.java.core.state;

import main.java.core.fsm.StateContext;
import main.java.core.fsm.StateMachine;
import main.java.core.fsm.annotation.OnEntry;
import main.java.core.fsm.annotation.State;
import main.java.core.fsm.annotation.TransitionSource;

@State("score")
@TransitionSource(target = "aim", event = "reaimEvent")
public class ScoreState extends AbstractState {

    public ScoreState(StateMachine machine, StateContext data) {
        super(machine, data);
    }

    @OnEntry public void enter() {
        machine.triggerEvent("reaimEvent");
    }

}
