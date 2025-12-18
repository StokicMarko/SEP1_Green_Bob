package utils;

import javafx.scene.layout.VBox;

public final class NotificationService {

  private static VBox toastContainer;

  public static void init(VBox container) {
    toastContainer = container;
  }

  public static void success(String msg) {
    InAppToast.show(toastContainer, msg);
  }
}
