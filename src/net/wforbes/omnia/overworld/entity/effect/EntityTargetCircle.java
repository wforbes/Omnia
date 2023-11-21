package net.wforbes.omnia.overworld.entity.effect;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.Mob;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class EntityTargetCircle {
    private EntityEffectController entityEffectController;
    private Timeline circleFadeTimeline;
    private Color circleColor;
    private double circleLineWidth;
    private Circle c;
    private double circleFadeDelay = 250;
    private double circleFadeDuration = 250;
    private DoubleProperty circleOpacity = new SimpleDoubleProperty();
    private double xMapDiff;
    private double yMapDiff;
    public EntityTargetCircle(EntityEffectController eec) {
        this.entityEffectController = eec;
    }

    public void set() {
        circleOpacity.set(1.0);
        circleLineWidth = 5.0;
        circleColor = Color.LIGHTBLUE;
        circleFadeTimeline = new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(circleOpacity, 1)
                ),
                new KeyFrame(Duration.millis(circleFadeDelay),
                        new KeyValue(circleOpacity, 0)
                )
        );
        c = new Circle(0, 0, 16, circleColor);
        circleFadeTimeline.setAutoReverse(true);
        circleFadeTimeline.setCycleCount(Animation.INDEFINITE);
        /*
        c = new Circle(0, 0, 16, circleColor);
        circleFadeTimeline = new FadeTransition(
                Duration.millis(250), c
        );
        circleFadeTimeline.setFromValue(1);
        circleFadeTimeline.setToValue(0);
        */
        //circleFadeTimeline.stop();
        circleFadeTimeline.setCycleCount(Animation.INDEFINITE);
        circleFadeTimeline.play();
    }

    public void unset() {
        if (
            (circleFadeTimeline != null && circleFadeTimeline.getStatus() == Animation.Status.RUNNING )
            || ((Mob) this.entityEffectController.getEntity()).isTargeted()
        ) {
            circleFadeTimeline.stop();
        }
    }

    public void update() {

    }

    public void render(GraphicsContext gc) {
        if (!((Mob) this.entityEffectController.getEntity()).isTargeted()) return;

        if(circleFadeTimeline.getStatus() == Animation.Status.RUNNING) {
            gc.setStroke(c.getFill());
            gc.setLineWidth(circleLineWidth);
            double currentTime = circleFadeTimeline.getCurrentTime().toMillis();
            if (currentTime > circleFadeDelay - circleFadeDuration) {
                gc.setGlobalAlpha(circleOpacity.get());
            } else {
                gc.setGlobalAlpha(1.0);
            }
            Entity e = entityEffectController.getEntity();
            xMapDiff = (e.getX() + ((Mob) e).getXMap() - ((Mob) e).getWidth() / 2.0);
            yMapDiff = (e.getY() + ((Mob) e).getYMap() - ((Mob) e).getHeight() / 2.0) + (((Mob) e).getHeight() / 3.5);

            gc.strokeOval(
                    (c.getCenterX() + xMapDiff * getScale()) - (c.getRadius() * getScale()) / 2.0,
                    (c.getCenterY() + yMapDiff * getScale()) - ((c.getRadius() - c.getRadius() / 2) * getScale()) / 2.0,
                    c.getRadius() * getScale(),
                    (c.getRadius() - c.getRadius() / 3) * getScale()
            );
            gc.setGlobalAlpha(1.0);
        }
    }
}
