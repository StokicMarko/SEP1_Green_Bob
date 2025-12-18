package utils;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public final class InAppToast {

  private InAppToast() {}

  public static void show(VBox toastContainer, String message) {
    Label toast = new Label(message);

    toast.setStyle(
        "-fx-background-color: #8aa883;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 10 20;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;"
    );

    DropShadow ds = new DropShadow();
    ds.setColor(Color.rgb(0, 0, 0, 0.25));
    ds.setRadius(8);
    toast.setEffect(ds);

    toast.setOpacity(0);
    toastContainer.getChildren().add(toast);

    FadeTransition fadeIn = new FadeTransition(Duration.millis(250), toast);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);

    PauseTransition stay = new PauseTransition(Duration.seconds(3));

    FadeTransition fadeOut = new FadeTransition(Duration.millis(400), toast);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);
    fadeOut.setOnFinished(e -> toastContainer.getChildren().remove(toast));

    new SequentialTransition(fadeIn, stay, fadeOut).play();
  }
}
