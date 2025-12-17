package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.TownManager;
import model.CommunalActivity;
import model.Date;
import model.Resident;

import java.time.LocalDate;

public class CommunalActivityController {

  @FXML private TableView<CommunalActivity> tableCommunalActivities;
  @FXML private TableColumn<CommunalActivity, String> colTitle;
  @FXML private TableColumn<CommunalActivity, String> colDescription;
  @FXML private TableColumn<CommunalActivity, Number> colPersonalPoints;
  @FXML private TableColumn<CommunalActivity, Date> colEventDate;
  @FXML private TableColumn<CommunalActivity, String> colParticipants;
  @FXML private TableColumn<CommunalActivity, String> colPointsAssigned;


  @FXML private TextField txtTitle;
  @FXML private TextField txtDescription;
  @FXML private TextField txtPersonalPoints;
  @FXML private DatePicker datePicker;
  @FXML private ListView<Resident> listResidents;

  @FXML private Button btnDelete;
  @FXML private Button btnSave;
  @FXML private Button btnAssignPoints;
  @FXML private Button btnNew;
  @FXML private Button btnClear;

  private TownManager townManager;
  private CommunalActivity selectedActivity;

  @FXML
  public void init(TownManager townManager) {
    this.townManager = townManager;
    this.townManager.loadCommunalActivities();

    setupTable();
    setupDatePicker();
    setupButtonActions();
    setupSelectionListener();
    setupResidentList();

    refreshTable();
    clearForm();
  }

  private void setupDatePicker() {
    datePicker.setValue(LocalDate.now());
    datePicker.setEditable(false);
  }

  private void setupSelectionListener() {
    tableCommunalActivities.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldSel, newSel) -> {

          btnSave.setDisable(newSel == null);
          btnDelete.setDisable(newSel == null);

          if (newSel == null) {
            btnAssignPoints.setDisable(true);
            selectedActivity = null;
            return;
          }

          showCommunalActivity(newSel);
        });
  }

  private void setupButtonActions() {
    btnSave.setOnAction(e -> onSave());
    btnNew.setOnAction(e -> onCreateNew());
    btnDelete.setOnAction(e -> onDelete());
    btnClear.setOnAction(e -> onClearSelection());
    btnAssignPoints.setOnAction(e -> onAssignPoints());

    btnAssignPoints.setDisable(true);
  }

  private void showCommunalActivity(CommunalActivity activity) {
    if (activity == null) return;
    selectedActivity = activity;

    txtTitle.setText(activity.getTitle());
    txtDescription.setText(activity.getDescription());
    txtPersonalPoints.setText(String.valueOf(activity.getPoints()));
    datePicker.setValue(activity.getEventDate() != null
        ? LocalDate.of(activity.getEventDate().getYear(), activity.getEventDate().getMonth(), activity.getEventDate().getDay())
        : LocalDate.now());
    btnAssignPoints.setDisable(activity.isPointsAssigned());

  }

  private void onSave() {
    if (selectedActivity == null || !validateFields()) return;

    selectedActivity.setTitle(txtTitle.getText());
    selectedActivity.setDescription(txtDescription.getText());
    selectedActivity.setPoints(Integer.parseInt(txtPersonalPoints.getText()));
    selectedActivity.setEventDate(new Date(datePicker.getValue()));
    selectedActivity.setParticipants(listResidents.getSelectionModel().getSelectedItems());

    townManager.updateCommunalActivity(selectedActivity.getID(), selectedActivity);
    refreshTable();
    clearForm();
  }

  private void onCreateNew() {
    if (!validateFields()) return;

    CommunalActivity activity = new CommunalActivity(
        txtTitle.getText(),
        txtDescription.getText(),
        Integer.parseInt(txtPersonalPoints.getText()),
        new Date(datePicker.getValue())
    );

    activity.setParticipants(listResidents.getSelectionModel().getSelectedItems());

    townManager.addCommunalActivity(activity);
    refreshTable();
    clearForm();
  }

  private boolean validateFields() {
    if (txtTitle.getText().isEmpty() ||
        txtPersonalPoints.getText().isEmpty() ||
        Integer.parseInt(txtPersonalPoints.getText()) < 0 ||
        datePicker.getValue() == null) {
      new Alert(Alert.AlertType.ERROR, "Please fill all required fields").show();
      return false;
    }
    return true;
  }

  private void onAssignPoints()
  {
    if (selectedActivity != null && !selectedActivity.isPointsAssigned()) {
      townManager.assignPointFromCommunal(selectedActivity);
      refreshTable();
      btnAssignPoints.setDisable(true);
    }
  }

  private void onDelete() {
    CommunalActivity selected = tableCommunalActivities.getSelectionModel().getSelectedItem();
    if (selected == null) {
      new Alert(Alert.AlertType.WARNING, "Please select an activity to remove.").show();
      return;
    }
    townManager.removeCommunalActivity(selected.getID());
    refreshTable();
    clearForm();
  }

  private void onClearSelection() {
    tableCommunalActivities.getSelectionModel().clearSelection();
    clearForm();
  }

  private void clearForm() {
    txtTitle.clear();
    txtDescription.clear();
    txtPersonalPoints.clear();
    datePicker.setValue(LocalDate.now());
    tableCommunalActivities.getSelectionModel().clearSelection();

    btnSave.setDisable(true);
    btnDelete.setDisable(true);
    btnAssignPoints.setDisable(true);
    selectedActivity = null;
  }

  private void setupTable() {
    colTitle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTitle()));
    colDescription.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescription()));
    colPersonalPoints.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getPoints()));
    colEventDate.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getEventDate()));
    colParticipants.setCellValueFactory(c -> new SimpleStringProperty(
        c.getValue().getParticipants().stream()
            .map(r -> r.getName() + " " + r.getLastname())
            .reduce((a, b) -> a + ", " + b).orElse("")
    ));
    colPointsAssigned.setCellValueFactory(c ->
        new SimpleStringProperty(c.getValue().isPointsAssigned() ? "Yes" : "No")
    );

    tableCommunalActivities.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }

  private void refreshTable() {
    tableCommunalActivities.getItems().setAll(townManager.getCommunalActivities());
  }

  private void setupResidentList()
  {
    listResidents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    refresh();
  }
  public void refresh()
  {
    listResidents.getItems().setAll(townManager.getResidents());
  }
}
