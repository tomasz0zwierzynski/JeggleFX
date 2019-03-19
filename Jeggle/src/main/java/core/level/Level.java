package main.core.level;

import framework.SimulationType;
import framework.nodes.PhysicsCircle;
import javafx.scene.CacheHint;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Level {

    public static List<PhysicsCircle> getPegs() {
        List<PhysicsCircle> pegs = new ArrayList<>();


        IntStream.range(0,11).forEach(i -> {
            PhysicsCircle peg = new PhysicsCircle();
            peg.setCache(true);
            peg.setCacheHint(CacheHint.SPEED);
            peg.setSimulationType(SimulationType.NonMovable);
            peg.setLayoutX( (i + 1) * 50 );
            peg.setLayoutY( 300 );
            peg.setRadius(10);
            peg.setFill(Color.CORAL);
            pegs.add(peg);
        });

        IntStream.range(0,11).forEach(i -> {
            PhysicsCircle peg = new PhysicsCircle();
            peg.setCache(true);
            peg.setCacheHint(CacheHint.SPEED);
            peg.setSimulationType(SimulationType.NonMovable);
            peg.setLayoutX( ((i + 1) * 50) + 25 );
            peg.setLayoutY( 240 );
            peg.setRadius(10);
            peg.setFill(Color.CORAL);
            pegs.add(peg);
        });


        IntStream.range(0,11).forEach(i -> {
            PhysicsCircle peg = new PhysicsCircle();
            peg.setCache(true);
            peg.setCacheHint(CacheHint.SPEED);
            peg.setSimulationType(SimulationType.NonMovable);
            peg.setLayoutX(((i + 1) * 50));
            peg.setLayoutY( 180 );
            peg.setRadius(10);
            peg.setFill(Color.CORAL);
            pegs.add(peg);
        });


        IntStream.range(0,11).forEach(i -> {
            PhysicsCircle peg = new PhysicsCircle();
            peg.setCache(true);
            peg.setCacheHint(CacheHint.SPEED);
            peg.setSimulationType(SimulationType.NonMovable);
            peg.setLayoutX( ((i + 1) * 50) + 25 );
            peg.setLayoutY( 120 );
            peg.setRadius(10);
            peg.setFill(Color.CORAL);
            pegs.add(peg);
        });

        return pegs;
    }


}
