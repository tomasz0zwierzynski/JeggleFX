package main.java.core.level;

import framework.SimulationType;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import main.java.core.peg.Peg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelBuilder {


    // TODO: tutaj tylko przypisanie pozycji, a wybieranie jaki kolor mają mieć poszczególne pegi gdzieś indziej
    public static List<Peg> build(List<Point2D> places) {
        List<Peg> result = new ArrayList<>();

        places.forEach( p -> {
            Peg peg = getNewPeg(p.getX(), p.getY());
            peg.setType(Peg.Type.BLUE);
            result.add(peg);
        });

        return result;
    }

    private static Peg getNewPeg(double x, double y) {
        Peg peg = new Peg();

        peg.setCache(true);
        peg.setCacheHint(CacheHint.SPEED);
        peg.setSimulationType(SimulationType.NonMovable);
        peg.setLayoutX(x);
        peg.setLayoutY(y);
        peg.setRadius(10);

        peg.setVisible(false);

        return peg;
    }
}

