package main.java.core.util;

import javafx.geometry.Point2D;
import main.java.core.ConfigurationLoader;

public class AimResolver {

    public static int X_SHOOTING_POINT = 320;
    public static int Y_SHOOTING_POINT = 40;

    public static int BOARD_ENGINE_HEIGHT = 480;
    public static int BOARD_ENGINE_WIDTH = 640;

    //For given x0, y0 (point of click - ball should fall through this point)
    public static double calculateShootingAngle(int x0, int y0) throws Exception{
        double v = ConfigurationLoader.initialVelocity * 50;
        double g = ConfigurationLoader.gravityY;

        Point2D p0 = new Point2D(x0,y0);
        Point2D p = toMathematicalFrame(p0);

        double x = p.getX();
        double y = p.getY();

        //Mathematical formulas can be found on wikipedia page describing throws and ballistics
        double root_arg = Math.pow(v, 4)-g*(g*x*x+2*y*v*v);
        if (root_arg<0)
            throw new Exception("Not reachable point!");
        double root = Math.sqrt(root_arg);

        double angle;
        try{
            //second_angle_solution = Math.atan((v*v+root)/(g*x));
            //This solution above corresponds to longer curve and is not needed, because shortest path should be chosen
            angle = Math.atan((v*v-root)/(g*x));
        }catch(Exception ex){
            throw ex;
        }

        //Preparing return value due to 90 degree rotation in ball velocity creator
        double value;
        if (x < 0){
            value = Math.toDegrees(angle) - 90;
        }else{
            value = Math.toDegrees(angle) + 90;
        }

        return value;
    }

    //Method calculates value of predicted ball parabole for given argument
    public static double calculateParabole(double angle_in_degrees, double arg){
        double v = ConfigurationLoader.initialVelocity * 50;
        double g = ConfigurationLoader.gravityY;
        double Q = Math.toRadians(angle_in_degrees);

        double value = Math.tan(Q)*arg - ((g*arg*arg)/(2*v*v*Math.cos(Q)*Math.cos(Q)));

        return value;
    }

    //Transforms point from Math frame to Graph frame
    public static Point2D toGraphicalFrame(Point2D p){

        int xC = (int) X_SHOOTING_POINT; //X point of shoot
        int yC = (int) Y_SHOOTING_POINT; //Y point of shoot

        yC = BOARD_ENGINE_HEIGHT - yC;

        //Shift
        double x = p.getX() + xC;
        double y = p.getY() + yC;

        //Flip
        y = BOARD_ENGINE_HEIGHT - y;

        return new Point2D(x,y);
    }

    //Transforms point from Graph frame to Math frame
    public static Point2D toMathematicalFrame(Point2D p){

        double xC = X_SHOOTING_POINT; //X point of shoot
        double yC = Y_SHOOTING_POINT; //Y point of shoot
        //Flipping Y
        double y = 480 - p.getY();
        yC = 480 - yC;
        double x = p.getX();
        //Shifting frame
        x = x - xC;
        y = y - yC;

        return new Point2D(x, y);
    }


}
