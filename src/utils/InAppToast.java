package utils;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public final class InAppToast {

  private InAppToast() {}

  public static void showSuccess(VBox container, String message) {
    show(container, message, "toast-success");
  }

  public static void showWarning(VBox container, String message) {
    show(container, message, "toast-warning");
  }

  public static void showInfo(VBox container, String message) {
    show(container, message, "toast-info");
  }

  private static void show(VBox toastContainer, String message, String styleClass) {
    Label toast = new Label(message);
    toast.getStyleClass().addAll("toast", styleClass);

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