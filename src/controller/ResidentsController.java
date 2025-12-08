package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.TownManager;
import model.Resident;

public class ResidentsController {

  @FXML private TableView<Resident> tableResidents;
  @FXML private TableColumn<Resident, String> colID;
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
  @FXML private Button btnReload;
  @FXML private Button btnDelete;

  private TownManager townManager;

  @FXML
  public void init(TownManager townManager) {
    this.townManager = townManager;

    setupTable();
    refreshTable();

    tableResidents.setOnMouseClicked(e -> {
      Resident selectedResident = tableResidents.getSelectionModel().getSelectedItem();
      showResident(selectedResident);
    });

    btnSave.setOnAction(e -> onSave());
    btnNew.setOnAction(e -> onNewResident());
    btnReload.setOnAction(e -> onReload());
    btnDelete.setOnAction(e -> onDelete());
  }

  private void setupTable() {
    colID.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getID()));
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
            "",
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
    alert.setHeaderText("Are you sure you want to delete this resident?");
    alert.setContentText(selectedResident.getName() + " " + selectedResident.getLastname());

    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        townManager.removeResident(selectedResident);
        refreshTable();
        clearForm();
      }
    });
  }
}
