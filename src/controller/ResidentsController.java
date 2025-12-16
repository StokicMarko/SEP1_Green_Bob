package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.TownManager;
import model.Resident;

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
  @FXML private Button btnDelete;
  @FXML private Button btnClear;
  @FXML private Button btnReset;


  private TownManager townManager;

  @FXML
  public void init(TownManager townManager) {
    this.townManager = townManager;

    setupTable();
    refreshTable();

    btnSave.setDisable(true);
    btnDelete.setDisable(true);

    tableResidents.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldSel, newSel) -> {
      boolean hasSelection = newSel != null;

      btnSave.setDisable(!hasSelection);
      btnDelete.setDisable(!hasSelection);

      showResident(newSel);
    });

    tableResidents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    btnSave.setOnAction(e -> onSave());
    btnNew.setOnAction(e -> onNewResident());
    btnReset.setOnAction(e -> onReset());
    btnDelete.setOnAction(e -> onDelete());
    btnClear.setOnAction(e -> onClearSelection());

  }

  private void setupTable() {
    colName.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
    colLastname.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getLastname()));
    colPoints.setCellValueFactory(c ->
        new javafx.beans.property.SimpleIntegerProperty((int)c.getValue().getPersonalPoints()));
    colAddress.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getAddress()));
  }

  private void refreshTable() {
    tableResidents.getItems().setAll(townManager.getResidents());
  }

  private void showResident(Resident resident) {
    if (resident == null) return;

    txtName.setText(resident.getName());
    txtLastname.setText(resident.getLastname());
    txtPoints.setText("" + (int) resident.getPersonalPoints());
    txtAddress.setText(resident.getAddress());
  }

  @FXML
  private void onSave() {
    Resident selected = tableResidents.getSelectionModel().getSelectedItem();
    if (selected == null) return;

    townManager.updateResident(
        selected.getID(),
        new Resident(
            txtName.getText(),
            txtLastname.getText(),
            Integer.parseInt(txtPoints.getText()),
            txtAddress.getText()
        )
    );

    refreshTable();
    clearForm();
  }

  @FXML
  private void onNewResident() {
    townManager.createResident(
        txtName.getText(),
        txtLastname.getText(),
        Integer.parseInt(txtPoints.getText()),
        txtAddress.getText()
    );

    refreshTable();
    clearForm();
  }

  @FXML
  private void onReload() {
    townManager.loadResidents();
    refreshTable();
  }

  private void clearForm() {
    txtName.clear();
    txtLastname.clear();
    txtPoints.clear();
    txtAddress.clear();
    tableResidents.getSelectionModel().clearSelection();
  }

  private void onDelete()
  {
    Resident selectedResident = tableResidents.getSelectionModel().getSelectedItem();

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete Resident");
    alert.setHeaderText("Are you sure you want to delete " + selectedResident.getName() + " " + selectedResident.getLastname() + "?");

    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        townManager.removeResidentByID(selectedResident.getID());
        refreshTable();
        clearForm();
      }
    });
  }

  @FXML
  private void onClearSelection() {
    tableResidents.getSelectionModel().clearSelection();
    clearForm();
  }

  private void onReset()
  {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Reset personal points");
    alert.setHeaderText("Are you sure you want to reset ALL personal points?");

    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        townManager.resetPersonalPoints();
        refreshTable();
        clearForm();
      }
    });
  }
}
