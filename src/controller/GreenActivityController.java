package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.TownManager;
import model.Date;
import model.GreenActivity;
import java.time.LocalDate;

public class GreenActivityController
{

  @FXML private TableView<GreenActivity> tableGreenActivities;
  @FXML private TableColumn<GreenActivity, String> colTitle;
  @FXML private TableColumn<GreenActivity, String> colDescription;
  @FXML private TableColumn<GreenActivity, Number> colGreenPoints;
  @FXML private TableColumn<GreenActivity, Date> colEventDate;

  @FXML private TextField txtDescription;
  @FXML private TextField txtGreenPoints;
  @FXML private TextField txtTitle;
  @FXML private DatePicker datePicker;

  @FXML private Label lblTotalPoints;

  @FXML private Button btnSave;
  @FXML private Button btnNew;
  @FXML private Button btnDelete;
  @FXML private Button btnClear;


  @FXML
  private TownManager townManager;

  @FXML public void init(TownManager townManager)
  {
    this.townManager = townManager;
    townManager.loadGreenActivities();

    setupTable();
    setupDatePicker();
    setupButtonActions();
    setupSelectionListener();

    refreshTable();
    clearForm();
  }

  private void refreshTable()
  {
    tableGreenActivities.getItems().setAll(townManager.getGreenActivities());
    updateTotalPoints();
  }

  private void setupDatePicker()
  {
    datePicker.setValue(LocalDate.now());
    datePicker.setEditable(false);
  }

  private void setupSelectionListener() {
    tableGreenActivities.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
      boolean hasSelection = newSel != null;
      btnSave.setDisable(!hasSelection);
      btnDelete.setDisable(!hasSelection);
      showGreenActivity(newSel);
    });
  }

  private void showGreenActivity(GreenActivity greenActivity)
  {
    if (greenActivity == null) return;

    txtTitle.setText(greenActivity.getTitle());
    txtDescription.setText(greenActivity.getDescription());
    txtGreenPoints.setText(String.valueOf(greenActivity.getPoints()));
    datePicker.setValue(greenActivity.getEventDate() != null
        ? LocalDate.of(greenActivity.getEventDate().getYear(), greenActivity.getEventDate().getMonth(), greenActivity.getEventDate().getDay())
        : LocalDate.now());
  }

  private void setupButtonActions() {
    btnSave.setOnAction(e -> onSave());
    btnNew.setOnAction(e -> onNewGreenActivity());
    btnDelete.setOnAction(e -> onDelete());
    btnClear.setOnAction(e -> onClearSelection());
  }

  private void onSave()
  {
    GreenActivity selected = tableGreenActivities.getSelectionModel().getSelectedItem();
    if (selected == null || !validateRequiredFields()) return;

    selected.setTitle(txtTitle.getText());
    selected.setPoints(Integer.parseInt(txtGreenPoints.getText()));
    selected.setDescription(txtDescription.getText() == null ? "" : txtDescription.getText());
    selected.setEventDate(new Date(datePicker.getValue()));

    townManager.updateActivity(selected.getID(), selected);
    refreshTable();
    clearForm();
  }

  private boolean validateRequiredFields() {
    if (txtTitle.getText().isEmpty()
        || Integer.parseInt(txtGreenPoints.getText()) < 0
        || txtGreenPoints.getText().isEmpty()
        || datePicker.getValue() == null) {
      new Alert(Alert.AlertType.ERROR, "Please fill all required fields").show();
      return false;
    }
    return true;
  }

  @FXML
  private void onNewGreenActivity() {

    GreenActivity greenActivity =
        new GreenActivity(
          txtTitle.getText(),
          txtDescription.getText(),
          Integer.parseInt(txtGreenPoints.getText()),
          new Date(datePicker.getValue()
        )
    );

    if (greenActivity.getTitle().isEmpty() || greenActivity.getPoints() < 0 || greenActivity.getEventDate() == null) {
      showAlert("Please fill in all fields.");
      return;
    }

    townManager.addGreenActivity(greenActivity);
    refreshTable();
    clearForm();
  }


  @FXML
  private void onDelete() {

    GreenActivity selected = tableGreenActivities.getSelectionModel().getSelectedItem();
    if (selected == null) {
      showAlert("Please select an activity to remove.");
      return;
    }

    townManager.removeGreenActivity(selected.getID());
    refreshTable();
    clearForm();
  }

  private void clearForm() {
    txtTitle.clear();
    txtDescription.clear();
    txtGreenPoints.clear();
    datePicker.setValue(LocalDate.now());

    tableGreenActivities.getSelectionModel().clearSelection();

    btnSave.setDisable(true);
    btnDelete.setDisable(true);
  }

  private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Input Error");
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }

  @FXML
  private void onClearSelection() {
    tableGreenActivities.getSelectionModel().clearSelection();
    clearForm();
  }


  private void setupTable() {
    colTitle.setCellValueFactory( c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getTitle()));
    colDescription.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getDescription()));
    colGreenPoints.setCellValueFactory(c->
        new javafx.beans.property.SimpleIntegerProperty( c.getValue().getPoints()));
    colEventDate.setCellValueFactory(c ->
        new javafx.beans.property.SimpleObjectProperty<Date>(c.getValue().getEventDate()));
    tableGreenActivities.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }

  private void updateTotalPoints() {
    int total = townManager.getGreenActivities()
        .stream()
        .mapToInt(GreenActivity::getPoints)
        .sum();

    lblTotalPoints.setText("Total Points: " + total);
  }

}

