package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import logic.TownManager;
import model.GreenActivity;

import javax.swing.*;
import java.time.LocalDate;

public class GreenActivityController
{
  @FXML public BorderPane greenActivityView;

  @FXML public TextField txtDescription;
  @FXML public TextField txtGreenPoints;
  @FXML public TextField txtEventDate;
  @FXML public TextField txtTitle;

  @FXML public Button btnSave;
  @FXML public Button btnNew;
  @FXML public Button btnDelete;
  @FXML public Button btnClear;

  @FXML public TableView<GreenActivity> tableGreenActivities;
  @FXML public TableColumn<GreenActivity, String> colTitle;
  @FXML public TableColumn<GreenActivity, String> colDescription;
  @FXML public TableColumn<GreenActivity, Integer> colGreenPoints;
  @FXML public TableColumn<GreenActivity, String> colEventDate;
  @FXML public TableColumn<GreenActivity, String> colID;

  @FXML
  private DatePicker datePicker;

  @FXML
  private ListView<String> greenActivityList;
  private TownManager townManager;
  private AbstractButton titleField;
  private AbstractButton pointsField;
  private AbstractButton descriptionField;

  @FXML public void initialize()
  {
    TownManager townManager = new TownManager();   // or receive injected instance
    townManager.loadGreenActivities();
    loadListView();
  }
  // --- LOAD LIST INTO UI ---
  private void loadListView()
  {
    greenActivityList.getItems().clear();

    for (GreenActivity g : townManager.getGreenActivities())
    {
      greenActivityList.getItems().add(
          g.getID() + " | " + g.getTitle() + " | " + g.getPoints() + " pts");
    }
  }

  // --- ADD GREEN ACTIVITY ---
  @FXML
  private void handleAddGreenActivity() {

    String title = titleField.getText();
    String description = descriptionField.getText();
    String pointsText = pointsField.getText();
    LocalDate date = datePicker.getValue();

    // Validation
    if (title.isEmpty() || description.isEmpty() || pointsText.isEmpty() || date == null) {
      showAlert("Please fill in all fields.");
      return;
    }

    int points;
    try {
      points = Integer.parseInt(pointsText);
    }
    catch (NumberFormatException e) {
      showAlert("Points must be an integer.");
      return;
    }


    // --- Create activity using YOUR EXACT CONSTRUCTOR ---
    GreenActivity activity = new GreenActivity(
        title,
        description,
        points,
        date
    );

    townManager.addGreenActivity(activity);
    loadListView();
    clearFields();
  }


  // --- REMOVE GREEN ACTIVITY ---
  @FXML
  private void handleRemoveGreenActivity() {

    String selected = greenActivityList.getSelectionModel().getSelectedItem();
    if (selected == null) {
      showAlert("Please select an activity to remove.");
      return;
    }

    // ID is before the "|" separator
    String id = selected.split("\\|")[0].trim();

    townManager.removeGreenActivity(id);
    loadListView();
  }


  // --- HELPER METHODS ---
  private void clearFields() {
    txtTitle.clear();
    txtDescription.clear();
    txtGreenPoints.clear();
    txtEventDate.clear();
  }

  private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Input Error");
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }


}

