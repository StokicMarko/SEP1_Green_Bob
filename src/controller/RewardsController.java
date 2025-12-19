package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.TownManager;
import model.Reward;
import model.Date;
import utils.InputValidation;
import utils.NotificationService;

import java.time.LocalDate;

public class RewardsController {

  @FXML private TableView<Reward> tableRewards;
  @FXML private TableColumn<Reward,String> colTitle;
  @FXML private TableColumn<Reward,Number> colThreshold;
  @FXML private TableColumn<Reward,String> colStatus;
  @FXML private TableColumn<Reward,Date> colAwardedAt;

  @FXML private TextField txtTitle;
  @FXML private TextField txtDescription;
  @FXML private TextField txtThreshold;

  @FXML private Label lblTotalPoints;

  @FXML private Button btnSave;
  @FXML private Button btnNew;
  @FXML private Button btnDelete;

  @FXML private TownManager townManager;

  @FXML public void init(TownManager townManager) {
    this.townManager = townManager;
    setupTable();
    setupButtonActions();
    refreshTable();
    clearForm();
  }

  private void setupTable() {
    colTitle.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitle()));
    colThreshold.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getThreshold()));
    colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus().name()));
    colAwardedAt.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getAwardedAt()));
    tableRewards.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    tableRewards.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
      boolean hasSelection = newSel != null;
      btnSave.setDisable(!hasSelection);
      btnDelete.setDisable(!hasSelection);
      showReward(newSel);
    });
  }

  public void refreshTable() {
    tableRewards.getItems().setAll(townManager.getRewards());
    updateTotalPoints();
  }

  private void showReward(Reward r) {
    if (r == null) return;
    txtTitle.setText(r.getTitle());
    txtDescription.setText(r.getDescription());
    txtThreshold.setText(String.valueOf(r.getThreshold()));
  }

  private void setupButtonActions() {
    btnSave.setOnAction(e -> onSave());
    btnNew.setOnAction(e -> onNewReward());
    btnDelete.setOnAction(e -> onDelete());
  }

  private void onSave() {
    Reward selected = tableRewards.getSelectionModel().getSelectedItem();
    if (selected == null) return;



    selected.setTitle(txtTitle.getText().trim());
    selected.setDescription(txtDescription.getText().trim());
    selected.setThreshold(Integer.parseInt(txtThreshold.getText().trim()));

    townManager.updateReward(selected.getID(), selected);
    townManager.checkAndAutoAwardRewards();
    refreshTable();
    clearForm();
    NotificationService.success("Reward updated successfully");
  }

  private void onNewReward() {


    Reward r = new Reward(txtTitle.getText().trim(), txtDescription.getText().trim(), Integer.parseInt(txtThreshold.getText().trim()), new Date(LocalDate.now()));
    townManager.addReward(r);
    townManager.checkAndAutoAwardRewards();
    refreshTable();
    clearForm();
    NotificationService.success("Reward added");
  }

  private void onDelete() {
    Reward selected = tableRewards.getSelectionModel().getSelectedItem();
    if (selected == null) return;
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete Reward");
    alert.setHeaderText("Are you sure you want to delete " + selected.getTitle() + "?");
    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        townManager.removeReward(selected.getID());
        refreshTable();
        clearForm();
        NotificationService.success("Reward deleted");
      }
    });
  }

  private void clearForm() {
    txtTitle.clear();
    txtDescription.clear();
    txtThreshold.clear();

    tableRewards.getSelectionModel().clearSelection();

    btnSave.setDisable(true);
    btnDelete.setDisable(true);
  }

  private void updateTotalPoints() {
    lblTotalPoints.setText(
        "Total Points: " + townManager.getTotalGreenPoints()
    );
  }
}
