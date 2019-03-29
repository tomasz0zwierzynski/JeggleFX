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
import javafx.scene.shape.Circle;
import main.java.core.ConfigurationLoader;
import main.java.core.level.LevelEvent;
import main.java.core.level.LevelEventListener;
import main.java.core.peg.Peg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static main.java.core.util.AimResolver.X_SHOOTING_POINT;
import static main.java.core.util.AimResolver.Y_SHOOTING_POINT;

public class LevelController extends Controller {
    private static final Logger LOG = LogManager.getLogger( LevelController.class );

    PhysicsCircle ball;

    @FXML private PhysicsWorld world;

    @FXML private PhysicsRectangle floor;

    @FXML private PhysicsRectangle sensor;

    @FXML private void initialize() {

        world.setPhysicsScale(ConfigurationLoader.physicsScale);
        world.setGravityX(ConfigurationLoader.gravityX);
        world.setGravityY(ConfigurationLoader.gravityY);

    }

    private double prevVelocity = 0.0;

    private List<Peg> pegs;

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

    public void initializeLevel(List<Peg> pegs) {
        this.pegs = pegs;

        Platform.runLater(()->{
            pegs.forEach( p -> {
                world.getChildren().add(p);
            });
        });
    }

    public void prepareOrangePegs() {
        Collections.shuffle(pegs);

        Platform.runLater(()->{
            pegs.stream().limit(10).forEach( p -> {
                p.setType(Peg.Type.ORANGE);
            });
        });
    }

    public void preparePinkPegs() {
        Collections.shuffle(pegs);

        Platform.runLater(()->{
            pegs.stream().filter(p -> p.getType().equals(Peg.Type.PINK)).findFirst().ifPresent(p -> p.setType(Peg.Type.BLUE));

            pegs.stream().filter(p -> p.getType().equals(Peg.Type.BLUE) && p.getCycle().equals(Peg.Cycle.SHOWN)).findFirst().ifPresent(p -> p.setType(Peg.Type.PINK));
        });
    }

    public void showPegs() {
        Platform.runLater(() -> {
            pegs.forEach( p -> {
                p.show();
            });
        });
    }

    public void removeHighlighted() {
        Platform.runLater(() -> {
            pegs.stream().filter(p -> p.getCycle().equals(Peg.Cycle.HIGHLIGHTED)).forEach(p -> {
                p.hide();
                world.getChildren().remove(p);
            });
        });
    }

    public void shootBall(double velX, double velY) {
        ball = new PhysicsCircle();

        ball.setCache(true);
        ball.setCacheHint(CacheHint.SPEED);

        ball.setSimulationType(SimulationType.Full);
        ball.setLayoutX(X_SHOOTING_POINT);
        ball.setLayoutY(Y_SHOOTING_POINT);
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

        Platform.runLater(()->{
            world.getChildren().add(ball);
        });
    }

    public void drawPoint(double x, double y) {
        Circle point = new Circle(x + X_SHOOTING_POINT, y + Y_SHOOTING_POINT, 3);
        point.setFill(Color.GREEN);

        Platform.runLater(()->{
            world.getChildren().add(point);
        });
    }

    @FXML
    private void handleCollision(CollisionEvent event) {

        if (event.getObject1() == ball || event.getObject2() == ball) {
            if (event.getObject1() instanceof Peg) {
                Platform.runLater(()->{
                    ((Peg) event.getObject1()).highlight();
                });
            } else if (event.getObject2() instanceof Peg) {
                Platform.runLater(()->{
                    ((Peg) event.getObject2()).highlight();
                });
            }
        }

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
