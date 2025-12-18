package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.TownManager;
import model.Resident;
import utils.InAppToast;
import utils.InputValidation;
import utils.NotificationService;

public class ResidentsController {

  @FXML private TableView<Resident> tableResidents;
  @FXML private TableColumn<Resident, String> colName;
  @FXML private TableColumn<Resident, String> colLastname;
  @FXML private TableColumn<Resident, Number> colPoints;
  @FXML private TableColumn<Resident, String> colAddress;

  @FXML private TextField txtName;
  @FXML private TextField txtLastname;
  @FXML private TextField txtPoints;
  @FXML private TextField txtAddress;

  @FXML private Button btnSave;
  @FXML private Button btnNew;
  @FXML private Button btnClear;
  @FXML private Button btnDelete;
  @FXML private Button btnReset;

  private TownManager townManager;

  @FXML
  public void init(TownManager townManager) {
    this.townManager = townManager;

    setupTable();
    setupButtonActions();
    setupSelectionListener();

    refreshTable();
    clearForm();
  }

  private void setupTable() {
    colName.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
    colLastname.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getLastname()));
    colPoints.setCellValueFactory(c ->
        new javafx.beans.property.SimpleIntegerProperty((int) c.getValue().getPersonalPoints()));
    colAddress.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getAddress()));

    tableResidents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }

  private void setupButtonActions() {
    btnSave.setOnAction(e -> onSave());
    btnNew.setOnAction(e -> onNewResident());
    btnDelete.setOnAction(e -> onDelete());
    btnClear.setOnAction(e -> onClearSelection());
    btnReset.setOnAction(e -> onReset());
  }

  private void setupSelectionListener() {
    tableResidents.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldSel, newSel) -> {
          boolean hasSelection = newSel != null;
          btnSave.setDisable(!hasSelection);
          btnDelete.setDisable(!hasSelection);
          showResident(newSel);
        });
  }

  public void refreshTable() {
    tableResidents.getItems().setAll(townManager.getResidents());
  }

  private void showResident(Resident resident) {
    if (resident == null) return;

    txtName.setText(resident.getName());
    txtLastname.setText(resident.getLastname());
    txtPoints.setText(String.valueOf((int) resident.getPersonalPoints()));
    txtAddress.setText(resident.getAddress());
  }

  private void onSave() {
    Resident selected = tableResidents.getSelectionModel().getSelectedItem();
    if (selected == null) return;

    if (!InputValidation.validateResidentInput(
        txtName.getText(),
        txtLastname.getText(),
        txtPoints.getText(),
        txtAddress.getText()
    )) {
      return;
    }

    townManager.updateResident(
        selected.getID(),
        new Resident(
            txtName.getText().trim(),
            txtLastname.getText().trim(),
            Integer.parseInt(txtPoints.getText()),
            txtAddress.getText().trim()
        )
    );

    refreshTable();
    clearForm();
    NotificationService.success("Resident updated successfully");
  }

  private void onNewResident() {
    if (!InputValidation.validateResidentInput(
        txtName.getText(),
        txtLastname.getText(),
        txtPoints.getText(),
        txtAddress.getText()
    )) {
      return;
    }

    townManager.createResident(
        txtName.getText().trim(),
        txtLastname.getText().trim(),
        Integer.parseInt(txtPoints.getText()),
        txtAddress.getText().trim()
    );

    refreshTable();
    clearForm();
    NotificationService.success("Resident added successfully");

  }

  private void onDelete() {
    Resident selected = tableResidents.getSelectionModel().getSelectedItem();
    if (selected == null) return;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete Resident");
    alert.setHeaderText(
        "Are you sure you want to delete " +
            selected.getName() + " " + selected.getLastname() + "?"
    );

    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        townManager.removeResidentByID(selected.getID());
        refreshTable();
        clearForm();
        NotificationService.success("Resident deleted successfully");

      }
    });
  }

  private void onReset() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Reset Personal Points");
    alert.setHeaderText("Are you sure you want to reset ALL personal points?");

    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        townManager.resetPersonalPoints();
        refreshTable();
        clearForm();
        NotificationService.success("All personal points have been reset successfully");

      }
    });
  }

  private void clearForm() {
    txtName.clear();
    txtLastname.clear();
    txtPoints.clear();
    txtAddress.clear();

    tableResidents.getSelectionModel().clearSelection();

    btnSave.setDisable(true);
    btnDelete.setDisable(true);
  }

  @FXML
  private void onClearSelection() {
    clearForm();
  }
}
