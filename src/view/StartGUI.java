package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartGUI extends Application
{
  public void start(Stage window) throws Exception
  {
    window.setTitle("Cloverville Manager");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("MainView.fxml"));
    Scene scene = new Scene(loader.load());

    scene.getStylesheets().add(getClass().getResource("../view/css/style.css").toExternalForm());
    scene.getStylesheets().add(getClass().getResource("../view/css/table.css").toExternalForm());
    scene.getStylesheets().add(getClass().getResource("../view/css/button.css").toExternalForm());
    scene.getStylesheets().add(getClass().getResource("../view/css/combo-box.css").toExternalForm());
    scene.getStylesheets().add(getClass().getResource("../view/css/date-picker.css").toExternalForm());


    window.setScene(scene);
    window.show();
  }
}
