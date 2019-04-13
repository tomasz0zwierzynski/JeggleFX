package main.java.core.state.menu;

import main.java.core.fsm.StateContext;
import main.java.core.fsm.StateMachine;
import main.java.core.fsm.annotation.OnEntry;
import main.java.core.fsm.annotation.OnExit;
import main.java.core.fsm.annotation.State;
import main.java.core.fsm.annotation.TransitionSource;
import main.java.core.state.AbstractState;

@State( value = "Splash", initial = true)
@TransitionSource( target = "MainMenu", event = "Splash-MainMenu")
public class SplashState extends AbstractState {

    public SplashState(StateMachine machine, StateContext data) {
        super(machine, data);
    }

    @OnEntry
    void onEntry() {
        // Załaduj zasoby, uruchom serwisy itp.

    }

    @OnExit
    void onExit() {

    }


}
