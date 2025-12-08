package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.TownManager;
import model.Resident;
import utils.JsonFileHandler;

import java.util.ArrayList;
import java.util.List;

public class ResidentsController
{
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

  private TownManager townManager;
  private List<Resident> residentData = new ArrayList<>();


  @FXML
  public void init(TownManager townManager)
  {
    this.townManager = townManager;

    setupTable();
    loadResidents();

    tableResidents.setOnMouseClicked(e -> {
      Resident r = tableResidents.getSelectionModel().getSelectedItem();
      showResidentDetails(r);
    });


    btnSave.setOnAction(e -> saveResidentChanges());
    btnNew.setOnAction(e -> createNewResident());
    btnReload.setOnAction(e -> loadResidents());
  }

  private void setupTable()
  {
    colID.setCellValueFactory(cell ->
        new javafx.beans.property.SimpleStringProperty(cell.getValue().getID()));

    colName.setCellValueFactory(cell ->
        new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));

    colLastname.setCellValueFactory(cell ->
        new javafx.beans.property.SimpleStringProperty(cell.getValue().getLastname()));

    colPoints.setCellValueFactory(cell ->
        new javafx.beans.property.SimpleIntegerProperty((int) cell.getValue().getPersonalPoints()));

    colAddress.setCellValueFactory(cell ->
        new javafx.beans.property.SimpleStringProperty(cell.getValue().getAddress()));
  }

  private void refreshTable() {
    tableResidents.getItems().setAll(residentData);
  }


  private void loadResidents()
  {
    residentData = townManager.getResidentsFromFile();
    refreshTable();
  }

  private void showResidentDetails(Resident resident)
  {
    if (resident == null) return;

    txtName.setText(resident.getName());
    txtLastname.setText(resident.getLastname());
    txtPoints.setText(Integer.toString((int) resident.getPersonalPoints()));
    txtAddress.setText(resident.getAddress());
  }

  private void saveResidentChanges()
  {
    Resident selectedResident = tableResidents.getSelectionModel().getSelectedItem();
    if (selectedResident == null) return;

    selectedResident.setName(txtName.getText());
    selectedResident.setLastname(txtLastname.getText());
    selectedResident.setPersonalPoints(Integer.parseInt(txtPoints.getText()));
    selectedResident.setAddress(txtAddress.getText());

    saveToFile();
    loadResidents();
  }

  private void createNewResident()
  {
    Resident r = new Resident(
        java.util.UUID.randomUUID().toString(),
        txtName.getText(),
        txtLastname.getText(),
        Integer.parseInt(txtPoints.getText()),
        txtAddress.getText()
    );

    residentData.add(r);
    saveToFile();
    refreshTable();
  }

  private void saveToFile()
  {
    try
    {
      JsonFileHandler.saveResidentsToJson("residents.json", new ArrayList<>(residentData));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
