package net.wforbes.omnia.overworld.world.area.effect;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class ClickCircle {
    private EffectController effectController;
    private Timeline circleFadeTimeline;

    private Color circleColor;
    private double circleLineWidth;
    private double circleRadius = 6;
    private Circle c;

    private double circleFadeDelay = 250;
    private double circleFadeDuration = 250;
    private DoubleProperty circleOpacity = new SimpleDoubleProperty();
    private double xMapDiff;
    private double yMapDiff;

    private Point2D initialMapPosition;

    public ClickCircle(EffectController ec) {
        this.effectController = ec;
    }

    public void init() {
        circleOpacity.set(1.0);
        circleLineWidth = 5.0;
        circleFadeTimeline = new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(circleOpacity, 1)
                ),
                new KeyFrame(Duration.millis(circleFadeDelay),
                        new KeyValue(circleOpacity, 1)
                ),
                new KeyFrame(Duration.millis(circleFadeDelay + circleFadeDuration),
                        new KeyValue(circleOpacity, 0)
                )
        );
        circleColor = Color.YELLOW;
        c = new Circle(0, 0, circleRadius, circleColor);
    }

    public double getCircleRadius() {
        return this.circleRadius;
    }

    public void set(double x, double y) {
        initialMapPosition = new Point2D(
                effectController.getArea().getTileMap().getX(),
                effectController.getArea().getTileMap().getY());
        c.setCenterX(x);
        c.setCenterY(y);
        circleFadeTimeline.stop();
        circleFadeTimeline.play();
    }

    public void render(GraphicsContext gc) {
        if(circleFadeTimeline.getStatus() == Animation.Status.RUNNING) {
            gc.setStroke(c.getFill());
            gc.setLineWidth(circleLineWidth);
            double currentTime = circleFadeTimeline.getCurrentTime().toMillis();
            if (currentTime > circleFadeDelay-circleFadeDuration) {
                gc.setGlobalAlpha(circleOpacity.get());
            } else {
                gc.setGlobalAlpha(1.0);
            }
            xMapDiff = effectController.getArea().getTileMap().getX() - initialMapPosition.getX();
            yMapDiff = effectController.getArea().getTileMap().getY() - initialMapPosition.getY();

            gc.strokeOval(
                    (c.getCenterX() + xMapDiff*getScale()) - (c.getRadius()*getScale())/2.0,
                    (c.getCenterY() + yMapDiff*getScale()) - ((c.getRadius() - c.getRadius()/2)*getScale())/2.0,
                    c.getRadius()*getScale(),
                    (c.getRadius() - c.getRadius()/3)*getScale()
            );
            gc.setGlobalAlpha(1.0);
        }
    }
}
