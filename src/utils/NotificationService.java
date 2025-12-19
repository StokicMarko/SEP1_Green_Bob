package utils;

import javafx.scene.layout.VBox;

public final class NotificationService {

  private static VBox toastContainer;

  public static void init(VBox container) {
    toastContainer = container;
  }

  public static void success(String msg) {
    InAppToast.showSuccess(toastContainer, msg);
  }

  public static void warning(String msg) {
    InAppToast.showWarning(toastContainer, msg);
  }

  public static void info(String msg) {
    InAppToast.showInfo(toastContainer, msg);
  }
}
