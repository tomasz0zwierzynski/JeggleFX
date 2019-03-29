package main.java.core.state;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import main.java.controller.LevelController;
import main.java.controller.UiController;
import main.java.core.ConfigurationLoader;
import main.java.core.fsm.StateContext;
import main.java.core.fsm.StateMachine;
import main.java.core.fsm.annotation.OnEntry;
import main.java.core.fsm.annotation.OnExit;
import main.java.core.fsm.annotation.State;
import main.java.core.fsm.annotation.TransitionSource;
import main.java.core.util.AimResolver;
import main.java.core.util.ViewController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@State("aim")
@TransitionSource(target = "simulation", event = "simulationEvent")
public class AimState extends AbstractState {
    private static final Logger LOG = LogManager.getLogger( AimState.class );

    private double velX;
    private double velY;

//    ViewController<Region, UiController> ui;
//    ViewController<Region, LevelController> level;

    public AimState(StateMachine machine, StateContext context) {
        super(machine, context);
    }

    @OnEntry
    public void onEntry() {

        // rozmieścić kolory pegów
        ViewController<Region, LevelController> level = (ViewController<Region, LevelController>) context.get(StateContext.LEVEL_CONTEXT_KEY);
        level.getController().preparePinkPegs();

        ViewController<Region, UiController> ui = (ViewController<Region, UiController>) context.get(StateContext.UI_CONTEXT_KEY);
        ui.getController().addRegionListener(MouseEvent.MOUSE_PRESSED, clickListener);

        ui.getController().addRegionListener(MouseEvent.MOUSE_MOVED, hoverListener);
    }

    @OnExit
    public void onExit() {

        ViewController<Region, LevelController> level = (ViewController<Region, LevelController>) context.get(StateContext.LEVEL_CONTEXT_KEY);

        ViewController<Region, UiController> ui = (ViewController<Region, UiController>) context.get(StateContext.UI_CONTEXT_KEY);
        ui.getController().removeRegionListener(MouseEvent.MOUSE_PRESSED, clickListener);
        ui.getController().removeRegionListener(MouseEvent.MOUSE_MOVED, hoverListener);

        LOG.debug("velX: " + velX + " velY: " + velY);
        level.getController().shootBall(velX, velY);
    }

    private EventHandler<MouseEvent> clickListener = (event) -> {
        LOG.debug(" ");
        LOG.debug("X: " + event.getX() + " Y: " + event.getY());
        LOG.debug("Screen X: " + event.getScreenX() + " Screen Y: " + event.getScreenY());
        LOG.debug("Scene X: " + event.getSceneX() + " Scene Y: " + event.getSceneY());
        // LOG.debug(event);
        // Tutaj przychodzi x y nacisniecia
        try {
            double angle = AimResolver.calculateShootingAngle((int)event.getX(), (int)event.getY());
            velX = ConfigurationLoader.initialVelocity * 50 * Math.sin(Math.toRadians(angle));
            velY =  - ConfigurationLoader.initialVelocity * 50 * Math.cos(Math.toRadians(angle));
            LOG.debug("angle: " + angle + " velX: " + velX + " velY: " + velY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        machine.triggerEvent("simulationEvent");
    };

    private EventHandler<MouseEvent> hoverListener = (event) -> {
        ViewController<Region, LevelController> level = (ViewController<Region, LevelController>) context.get(StateContext.LEVEL_CONTEXT_KEY);

//        try {
//            double angleDegrees = AimResolver.calculateShootingAngle((int) event.getX(), (int) event.getY());
//            for (int j=0; j<50; j++) {
//                double value = AimResolver.calculateParabole(angleDegrees, j * 3);
//                Point2D point = AimResolver.toGraphicalFrame(new Point2D(j * 3, value));
//                level.getController().drawPoint(point.getX(), - point.getY());
//                // LOG.debug(point.getX() + "," +  point.getY());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//            LOG.debug(" ");
//            LOG.debug("X: " + event.getX() + " Y: " + event.getY());
//            LOG.debug("Screen X: " + event.getScreenX() + " Screen Y: " + event.getScreenY());
//            LOG.debug("Scene X: " + event.getSceneX() + " Scene Y: " + event.getSceneY());
    };

}
