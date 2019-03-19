package main.java.controller;

import framework.SimulationType;
import framework.events.CollisionEvent;
import framework.nodes.PhysicsCircle;
import framework.nodes.PhysicsRectangle;
import framework.nodes.PhysicsWorld;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.paint.Color;
import main.core.ConfigurationLoader;
import main.core.level.Level;
import main.java.core.level.LevelEvent;
import main.java.core.level.LevelEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JeggleController extends Controller {
    private static final Logger LOG = LogManager.getLogger( JeggleController.class );

    PhysicsCircle ball;

    @FXML private PhysicsWorld world;

    @FXML private PhysicsRectangle floor;

    @FXML private PhysicsRectangle sensor;

    @FXML private void initialize() {

        world.setPhysicsScale(ConfigurationLoader.physicsScale);
        world.setGravityX(ConfigurationLoader.gravityX);
        world.setGravityY(ConfigurationLoader.gravityY);

        Level.getPegs().stream().forEach( peg -> {
            world.getChildren().add(peg);
        });
    }

    private double prevVelocity = 0.0;

    private List<LevelEventListener> levelListeners = new CopyOnWriteArrayList<>();

    public void addLevelEventListener(LevelEventListener listener) {
        levelListeners.add(listener);
    }

    public void removeLevelEventListener(LevelEventListener listener) {
        levelListeners.remove(listener);
    }

    public void fireLevelEvent(LevelEvent event) {
        levelListeners.forEach(l -> l.eventOccured(event));
    }

    @FXML private void handlePhysicsStep() {

    }

    public void shootBall(double velX, double velY) {
        ball = new PhysicsCircle();

        ball.setCache(true);
        ball.setCacheHint(CacheHint.SPEED);

        ball.setSimulationType(SimulationType.Full);
        ball.setLayoutX(160);
        ball.setLayoutY(40);
        ball.setRadius(8);
        ball.setFill(Color.BLACK);

        ball.setLinearVelocityX(velX);
        ball.setLinearVelocityY(velY);


        ball.setRestitution(ConfigurationLoader.restitution);
        ball.setFriction(ConfigurationLoader.friction);
        ball.setLinearDamping(ConfigurationLoader.linearDamping);
        ball.setBullet(false);
        ball.setDensity(ConfigurationLoader.density);
        ball.setFixedRotation(true);
        ball.setGravityScale(ConfigurationLoader.gravityScale);

        world.getChildren().add(ball);
    }

    @FXML
    private void handleCollision(CollisionEvent event) {


        if (event.getObject1() == ball || event.getObject2() == ball ) {
            if (event.getObject1() == sensor || event.getObject2() == sensor) {
                LOG.info("Collision!");
                double velX = ball.getLinearVelocityX();
                double velY = ball.getLinearVelocityY();
                double posX = ball.getLayoutX();

                Platform.runLater(()->{
                    world.getChildren().remove(ball);
                });

                fireLevelEvent(null);

//                Platform.runLater(()->{
//                    world.getChildren().remove(ball);
//                    ball = BallFactory.getBall(velX, velY, posX);
//                    world.getChildren().add(ball);
//                });

            }
        }

    }
}
