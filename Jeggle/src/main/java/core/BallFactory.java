package main.java.core;

import framework.SimulationType;
import framework.nodes.PhysicsCircle;
import javafx.scene.CacheHint;
import javafx.scene.paint.Color;

public class BallFactory {

    public static PhysicsCircle getBall(double Vx, double Vy, double x) {
        PhysicsCircle ball = new PhysicsCircle();
        ball.setRestitution(ConfigurationLoader.restitution);
        ball.setFriction(ConfigurationLoader.friction);
        ball.setLinearDamping(ConfigurationLoader.linearDamping);
        ball.setBullet(false);
        ball.setDensity(ConfigurationLoader.density);
        ball.setFixedRotation(true);
        ball.setGravityScale(ConfigurationLoader.gravityScale);

        ball.setCache(true);
        ball.setCacheHint(CacheHint.SPEED);
        ball.setSimulationType(SimulationType.Full);
        ball.setLayoutX(x);
        ball.setLayoutY(30);
        ball.setLinearVelocityX(Vx);
        ball.setLinearVelocityY(Vy);

        ball.setRadius(8);
        ball.setFill(Color.BLACK);

        return ball;
    }

}
