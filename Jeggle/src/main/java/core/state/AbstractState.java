package main.java.core.state;

import main.java.core.fsm.StateContext;
import main.java.core.fsm.StateMachine;

public abstract class AbstractState {

    /** Data in form of properties passed from state to state */
    StateContext context;

    StateMachine machine;

    public AbstractState() {
        this(null);
    }

    public AbstractState(StateMachine machine) {
        this(machine, null);
    }

    public AbstractState(StateMachine machine, StateContext data) {
        if (data != null) {
            this.context = data;
        } else {
            this.context = new StateContext();
        }
        this.machine = machine;
    }

    public StateContext getContext() {
        return context;
    }

}
