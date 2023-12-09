package com.stickhero.stickhero;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.Group;
import javafx.animation.KeyValue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;

public class Stick {

    private int x0;
    private int y0;
    private int x1;
    private int y1;
    private Line Stick;
    private boolean isPressed = false;
    private Timeline increasetimeline;
    private int willPass = 0;

    public Stick(){
        increasetimeline = new Timeline(
                new KeyFrame(Duration.millis(16), event -> {
                    if (isPressed) {
                        //System.out.println("increasing....");
                        y1 -= 2;  // Adjust the speed at which the line increases
                        redrawLine();
                    }
                })
        );
        increasetimeline.setCycleCount(Timeline.INDEFINITE);
    }

    public int getLength()
    {
        return y0-y1;
    }

    public void createStick(int x, int y, Group setGroup){
        setGroup.getChildren().remove(Stick);
        Stick = new Line();

        Stick.setStartX((double) x );
        Stick.setStartY((double) y );
        Stick.setEndX( (double) x );
        Stick.setEndY( (double) y );

        x0 = x1 = x;
        y0 = y1 = y;

        //root0.getChildren().clear();
        setGroup.getChildren().add(Stick);
        Stick.setStrokeWidth(3.0);
    }
    public void increaseHeight(Group setGroup) {

        //System.out.println("Starting AnimationTimer");
        isPressed = true;
        increasetimeline.play();
    }

    public void stopIncreaseHeight() {
        isPressed = false;
        increasetimeline.stop();
    }

    private void redrawLine() {
        // Update the y-coordinate of the line
        //System.out.println("kar redraw fir\n");
        Stick.setStartX(x0);
        Stick.setStartY(y0);
        Stick.setEndX(x1);
        Stick.setEndY(y1);
    }

    public void removeLine(Group setGroup){
        setGroup.getChildren().remove(Stick);
    }
    public Timeline StickFall(){
        //System.out.println("Stick falling??\n");
        // Create a RotateTransition
        Rotate rotate = new Rotate();

        rotate.pivotXProperty().bind(Stick.startXProperty());
        rotate.pivotYProperty().bind(Stick.startYProperty());

        Stick.getTransforms().add(rotate);

        Timeline timeline;

            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(rotate.angleProperty(), 90)));

        return timeline;
    }
}