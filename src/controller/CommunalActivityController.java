package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.TownManager;
import model.CommunalActivity;
import model.Date;
import model.Resident;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommunalActivityController {

  @FXML private TableView<CommunalActivity> tableCommunalActivities;
  @FXML private TableColumn<CommunalActivity, String> colTitle;
  @FXML private TableColumn<CommunalActivity, String> colDescription;
  @FXML private TableColumn<CommunalActivity, Integer> colPersonalPoints;
  @FXML private TableColumn<CommunalActivity, String> colEventDate;
  @FXML private TableColumn<CommunalActivity, String> colParticipants;

  @FXML private TextField txtTitle;
  @FXML private TextField txtDescription;
  @FXML private TextField txtPersonalPoints;
  @FXML private TextField txtEventDate;

  @FXML private Button btnDelete;
  @FXML private Button btnSave;
  @FXML private Button btnNew;
  @FXML private Button btnClear;

  @FXML private ListView<String> listResidents;

  private final TownManager townManager = new TownManager();
  private CommunalActivity selectedActivity;

  @FXML
  public void initialize() {
    townManager.loadCommunalActivities();

    // Table columns
    colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    colPersonalPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
    colEventDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEventDate().toString()));

    // Table selection listener
    tableCommunalActivities.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldVal, newVal) -> {
          fillForm(newVal);

          boolean selected = newVal != null;
          btnSave.setDisable(!selected);
          btnDelete.setDisable(!selected);
        });

    colParticipants.setCellValueFactory(data -> {
      CommunalActivity activity = data.getValue();

      if (activity.getParticipants() == null || activity.getParticipants().isEmpty()) {
        return new SimpleStringProperty("");
      }

      String names = activity.getParticipants().stream()
          .map(r -> r.getName() + " " + r.getLastname())
          .reduce((a, b) -> a + ", " + b)
          .orElse("");

      return new SimpleStringProperty(names);
    });

    // Initialize buttons
    btnDelete.setOnAction(e -> onDelete());
    btnSave.setOnAction(e -> onSave());
    btnNew.setOnAction(e -> onCreateNew());
    btnClear.setOnAction(e -> onClear());

    // Load residents into list and allow multiple selection
    listResidents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    loadResidents();

    // Refresh table
    refreshTable();

    btnSave.setDisable(true); // initially disabled
    btnDelete.setDisable(true); // initially disabled
  }

  private void loadResidents() {
    listResidents.getItems().clear();
    for (Resident r : townManager.getResidents()) {
      listResidents.getItems().add(formatResident(r));
    }
  }

  private String formatResident(Resident r) {
    return r.getName() + " " + r.getLastname() + " | " + r.getAddress();
  }

  private void fillForm(CommunalActivity a) {
    if (a == null) {
      clearForm();
      return;
    }
    selectedActivity = a;
    txtTitle.setText(a.getTitle());
    txtDescription.setText(a.getDescription());
    txtPersonalPoints.setText(String.valueOf(a.getPoints()));
    txtEventDate.setText(a.getEventDate().toString());

    // Select participants in ListView
    listResidents.getSelectionModel().clearSelection();
    if (a.getParticipants() != null) {
      for (Resident r : a.getParticipants()) {
        String display = formatResident(r);
        int index = listResidents.getItems().indexOf(display);
        if (index >= 0) listResidents.getSelectionModel().select(index);
      }
    }
  }

  @FXML
  private void onSave() {
    if (selectedActivity == null) {
      showError("Select an activity to update");
      return;
    }

    try {
      String title = txtTitle.getText().trim();
      String desc = txtDescription.getText().trim();
      int points = Integer.parseInt(txtPersonalPoints.getText().trim());
      Date date = parseDate(txtEventDate.getText().trim());

      CommunalActivity updated = new CommunalActivity(title, desc, points, date);

      for (String display : listResidents.getSelectionModel().getSelectedItems()) {
        for (Resident r : townManager.getResidents()) {
          if (formatResident(r).equals(display)) {
            updated.addParticipant(r);
            break;
          }
        }
      }

      townManager.removeCommunalActivity(selectedActivity.getID());
      townManager.addCommunalActivity(updated);

      refreshTable();
      clearForm();
      btnSave.setDisable(true);

    } catch (Exception e) {
      showError("Invalid input. Use format DD-MM-YYYY");
    }
  }

  @FXML
  private void onDelete() {
    if (selectedActivity == null) return;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
        "Delete selected communal activity?", ButtonType.YES, ButtonType.NO);

    if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
      townManager.removeCommunalActivity(selectedActivity.getID());
      refreshTable();
      clearForm();
      btnSave.setDisable(true);
    }
  }

  @FXML
  private void onClear() { clearForm(); }

  @FXML
  private void onCreateNew() {
    try {
      String title = txtTitle.getText().trim();
      String desc = txtDescription.getText().trim();
      int points = Integer.parseInt(txtPersonalPoints.getText().trim());
      Date date = parseDate(txtEventDate.getText().trim());

      Date today = new Date(LocalDate.now());
      if (date.compareTo(today) < 0) {
        showError("Date cannot be in the past");
        return;
      }
      if (points < 0) {
        showError("Points cannot be negative");
        return;
      }

      CommunalActivity activity = new CommunalActivity(title, desc, points, date);

      // add selected participants
      for (String display : listResidents.getSelectionModel().getSelectedItems()) {
        for (Resident r : townManager.getResidents()) {
          if (formatResident(r).equals(display)) {
            activity.addParticipant(r);
            break;
          }
        }
      }

      townManager.addCommunalActivity(activity); // ✅ SAVE TO JSON
      refreshTable();
      clearForm();

    } catch (Exception e) {
      showError("Invalid input. Use format DD-MM-YYYY");
    }
  }


  private void refreshTable() {
    tableCommunalActivities.getItems().setAll(townManager.getCommunalActivities());
  }

  private void clearForm() {
    selectedActivity = null;
    txtTitle.clear();
    txtDescription.clear();
    txtPersonalPoints.clear();
    txtEventDate.clear();
    listResidents.getSelectionModel().clearSelection();
    tableCommunalActivities.getSelectionModel().clearSelection();
  }

  private Date parseDate(String text) {
    String[] parts = text.split("-");
    if (parts.length != 3) throw new IllegalArgumentException("Invalid date format");
    return new Date(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
  }

  private void showError(String msg) {
    new Alert(Alert.AlertType.ERROR, msg).showAndWait();
  }
}
