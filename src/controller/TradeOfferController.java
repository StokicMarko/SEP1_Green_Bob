package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.TownManager;
import model.*;
import myEnum.*;

import java.time.LocalDate;

public class TradeOfferController {

  @FXML private TableView<TradeOffer> tableOffers;
  @FXML private TableColumn<TradeOffer, String> colTitle;
  @FXML private TableColumn<TradeOffer, OfferType> colType;
  @FXML private TableColumn<TradeOffer, String> colDescription;
  @FXML private TableColumn<TradeOffer, Number> colPointCost;
  @FXML private TableColumn<TradeOffer, OfferStatus> colStatus;
  @FXML private TableColumn<TradeOffer, String> colOfferBy;
  @FXML private TableColumn<TradeOffer, String> colAssignedTo;
  @FXML private TableColumn<TradeOffer, Date> colDate;

  @FXML private TextField txtTitle;
  @FXML private TextField txtDescription;
  @FXML private TextField txtPointCost;
  @FXML private DatePicker datePicker;

  @FXML private Button btnSave;
  @FXML private Button btnNew;
  @FXML private Button btnDelete;
  @FXML private Button btnClear;

  @FXML private ComboBox<OfferType> comboType;
  @FXML private ComboBox<OfferStatus> comboStatus;
  @FXML private ComboBox<Resident> comboOfferBy;
  @FXML private ComboBox<Resident> comboAssignedTo;

  private TownManager townManager;

  @FXML
  public void init(TownManager townManager) {
    this.townManager = townManager;

    setupTable();
    setupComboBoxes();
    setupDatePicker();
    setupButtonActions();
    setupSelectionListener();

    refreshTable();
    clearForm();
  }

  private void setupTable() {
    colTitle.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitle()));
    colType.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getType()));
    colDescription.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescription()));
    colPointCost.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty((int) c.getValue().getPointCost()));
    colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getStatus()));
    colOfferBy.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
        c.getValue().getOfferBy() != null ? c.getValue().getOfferBy().getName() : "Null"));
    colAssignedTo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
        c.getValue().getAssignedTo() != null ? c.getValue().getAssignedTo().getName() : "Nobody"));
    colDate.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getCreateDate()));

    tableOffers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }

  private void setupComboBoxes() {
    comboType.getItems().setAll(OfferType.values());
    comboStatus.getItems().setAll(OfferStatus.values());
    updateResidentBox(comboOfferBy);
    updateResidentBox(comboAssignedTo);

    comboAssignedTo.getItems().add(0, null);
    comboAssignedTo.setPromptText("Nobody");

    comboStatus.setDisable(true);
  }

  private void setupDatePicker() {
    datePicker.setValue(LocalDate.now());
    datePicker.setEditable(false);
  }


  private void setupButtonActions() {
    btnSave.setOnAction(e -> onSave());
    btnNew.setOnAction(e -> onNewTradeOffer());
    btnDelete.setOnAction(e -> onDelete());
    btnClear.setOnAction(e -> onClearSelection());
  }

  private void setupSelectionListener() {
    tableOffers.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
      boolean hasSelection = newSel != null;
      btnSave.setDisable(!hasSelection);
      btnDelete.setDisable(!hasSelection);
      showTradeOffer(newSel);
    });
  }

  private void refreshTable() {
    tableOffers.getItems().setAll(townManager.getTradeoffers());
  }

  private void showTradeOffer(TradeOffer offer) {
    if (offer == null) return;

    txtTitle.setText(offer.getTitle());
    txtDescription.setText(offer.getDescription());
    txtPointCost.setText(String.valueOf(offer.getPointCost()));
    datePicker.setValue(offer.getCreateDate() != null
        ? LocalDate.of(offer.getCreateDate().getYear(), offer.getCreateDate().getMonth(), offer.getCreateDate().getDay())
        : LocalDate.now());

    comboType.setValue(offer.getType());
    comboStatus.setValue(offer.getStatus());
    comboOfferBy.setValue(offer.getOfferBy());
    comboAssignedTo.setValue(offer.getAssignedTo());
    comboStatus.setDisable(false);
  }

  private void onSave() {
    TradeOffer selected = tableOffers.getSelectionModel().getSelectedItem();
    if (selected == null || !validateRequiredFields()) return;

    selected.setTitle(txtTitle.getText());
    selected.setType(comboType.getValue());
    selected.setDescription(txtDescription.getText() == null ? "" : txtDescription.getText());
    selected.setPointCost(Integer.parseInt(txtPointCost.getText()));
    selected.setOfferBy(comboOfferBy.getValue());
    selected.setAssignedTo(comboAssignedTo.getValue());

    LocalDate pickedDate = datePicker.getValue();
    if (pickedDate == null) {
      showDateError();
      return;
    }
    selected.setCreateDate(new Date(pickedDate));

    selected.setGeneralStatus(selected, comboStatus.getValue(), comboAssignedTo.getValue());

    townManager.updateTradeOffer(selected.getID(), selected);
    refreshTable();
    clearForm();
  }

  private void onNewTradeOffer() {
    if (!validateRequiredFields()) return;

    TradeOffer offer = new TradeOffer(
        txtTitle.getText(),
        comboType.getValue(),
        txtDescription.getText(),
        Integer.parseInt(txtPointCost.getText()),
        comboOfferBy.getValue(),
        new Date(datePicker.getValue())
    );
    offer.setAssignedTo(comboAssignedTo.getValue());
    offer.setGeneralStatus(offer, OfferStatus.AVAILABLE, comboAssignedTo.getValue());

    townManager.addTradeOffer(offer);
    comboStatus.setDisable(true);
    refreshTable();
    clearForm();
  }

  private boolean validateRequiredFields() {
    if (txtTitle.getText().isEmpty()
        || comboType.getValue() == null
        || txtPointCost.getText().isEmpty()
        || comboOfferBy.getValue() == null
        || datePicker.getValue() == null) {
      new Alert(Alert.AlertType.ERROR, "Please fill all required fields").show();
      return false;
    }
    return true;
  }

  private void clearForm() {
    txtTitle.clear();
    txtDescription.clear();
    txtPointCost.clear();
    datePicker.setValue(LocalDate.now());

    if (!comboType.getItems().isEmpty()) comboType.getSelectionModel().select(0);
    if (!comboOfferBy.getItems().isEmpty()) comboOfferBy.getSelectionModel().select(0);

    comboStatus.getSelectionModel().select(OfferStatus.AVAILABLE);
    comboStatus.setDisable(true);

    comboAssignedTo.getSelectionModel().select(null);
    tableOffers.getSelectionModel().clearSelection();

    btnSave.setDisable(true);
    btnDelete.setDisable(true);
  }

  private void onDelete() {
    TradeOffer selectedOffer = tableOffers.getSelectionModel().getSelectedItem();
    if (selectedOffer == null) return;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete offer");
    alert.setHeaderText("Are you sure you want to delete " + selectedOffer.getTitle() + " " + selectedOffer.getDescription() + "?");

    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        townManager.removeTradeOffer(selectedOffer.getID());
        refreshTable();
        clearForm();
      }
    });
  }

  @FXML
  private void onClearSelection() {
    tableOffers.getSelectionModel().clearSelection();
    comboStatus.setDisable(true);
    clearForm();
  }

  private void updateResidentBox(ComboBox<Resident> comboBox) {
    int currentIndex = comboBox.getSelectionModel().getSelectedIndex();
    comboBox.getItems().clear();
    comboBox.getItems().addAll(townManager.getResidents());

    if (currentIndex >= 0 && currentIndex < comboBox.getItems().size()) {
      comboBox.getSelectionModel().select(currentIndex);
    } else {
      comboBox.getSelectionModel().clearSelection();
    }
  }

  private void showDateError() {
    new Alert(Alert.AlertType.ERROR, "Please select a valid date from the calendar.").show();
  }
}