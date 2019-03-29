package main.java.core.peg;

import framework.nodes.PhysicsCircle;
import javafx.scene.paint.Color;

public class Peg extends PhysicsCircle {

    public enum Cycle {
        CREATED,
        SHOWN,
        HIGHLIGHTED,
        HIDDEN
    }

    public enum Type {
        BLUE,
        ORANGE,
        GREEN,
        PINK
    }

    // private PhysicsPeg model;
    private boolean visible = false;
    private Type type = Type.BLUE;
    private Cycle cycle = Cycle.CREATED;

//    public Peg(PhysicsPeg model) {
//        this.model = model;
//    }

//    public PhysicsPeg getModel() {
//        return model;
//    }

//    public void relocate(double posX, double posY) {
//        model.setLayoutX(posX);
//        model.setLayoutY(posY);
//    }

    public void show() {
        setVisible(true);

        cycle = Cycle.SHOWN;
    }

    public void hide() {
        setVisible(false);

        cycle = Cycle.HIDDEN;
    }

//    public boolean isVisible() {
//        return visible;
//    }
//
//    public void setVisible(boolean visible) {
//        this.visible = visible;
//    }

    public Cycle getCycle() {
        return cycle;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;

        switch(type) {
            case BLUE:
                setFill(Color.DARKBLUE);
                break;
            case ORANGE:
                setFill(Color.CORAL);
                break;
            case GREEN:
                setFill(Color.DARKGREEN);
                break;
            case PINK:
                setFill(Color.DEEPPINK);
                break;
        }
    }

    public void highlight() {
        if (cycle == Cycle.SHOWN) {
            cycle = Cycle.HIGHLIGHTED;
            switch(type) {
                case BLUE:
                    setFill(Color.CORNFLOWERBLUE);
                    break;
                case ORANGE:
                    setFill(Color.LIGHTCORAL);
                    break;
                case GREEN:
                    setFill(Color.LAWNGREEN);
                    break;
                case PINK:
                    setFill(Color.PINK);
                    break;
            }
        }
    }
}
