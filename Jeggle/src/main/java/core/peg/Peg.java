package main.core.peg;

import framework.nodes.PhysicsCircle;

public class Peg {

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

    private PhysicsCircle model;
    private boolean visible = false;
    private Type type = Type.BLUE;
    private Cycle cycle = Cycle.CREATED;

    private Peg(PhysicsCircle model) {
        this.model = model;
    }

    public void show(double posX, double posY) {
        model.setLayoutX(posX);
        model.setLayoutY(posY);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
