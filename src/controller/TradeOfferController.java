package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.TownManager;
import model.*;
import myEnum.*;

import java.util.ArrayList;

public class TradeOfferController
{
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
  @FXML private TextField txtType;
  @FXML private TextField txtDescription;
  @FXML private TextField txtPointCost;
  @FXML private TextField txtStatus;
  @FXML private TextField txtOfferBy;
  @FXML private TextField txtAssignedTo;
  @FXML private TextField txtDate;

  @FXML private Button btnSave;
  @FXML private Button btnNew;
  @FXML private Button btnDelete;
  @FXML private Button btnClear;

  @FXML private ComboBox<OfferType> comboType;
  @FXML private ComboBox<OfferStatus> comboStatus;
  @FXML private ComboBox<Resident> comboOfferBy;
  @FXML private ComboBox<Resident> comboAssignedTo;

  private TownManager manager;

  @FXML public void init(TownManager manager)
  {
    this.manager = manager;

    setupTable();
    refreshTable();
    updateResidentBox(comboOfferBy);
    updateResidentBox(comboAssignedTo);
    StatusBox();
    TypeBox();
    txtDate.setPromptText("dd-mm-yyyy or dd/mm/yyyy");
    btnSave.setDisable(true);
    btnDelete.setDisable(true);
    txtAssignedTo.setDisable(true);
    txtStatus.setDisable(true);

    comboStatus.setDisable(true);
    comboAssignedTo.setDisable(true);

    tableOffers.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldSel, newSel) -> {
          boolean hasSelection = newSel != null;

          btnSave.setDisable(!hasSelection);
          btnDelete.setDisable(!hasSelection);

          showTradeOffer(newSel);

        });
    tableOffers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    btnSave.setOnAction(e -> onSave());
    btnNew.setOnAction(e -> onNewTradeOffer());
    btnDelete.setOnAction(e -> onDelete());
    btnClear.setOnAction(e -> onClearSelection());

  }

  private void setupTable(){
    colTitle.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getTitle()));
    colType.setCellValueFactory(c ->
        new javafx.beans.property.SimpleObjectProperty(c.getValue().getType()));
    colDescription.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getDescription()));
    colPointCost.setCellValueFactory(c ->
        new javafx.beans.property.SimpleIntegerProperty((int)c.getValue().getPointCost()));
    colStatus.setCellValueFactory(c ->
        new javafx.beans.property.SimpleObjectProperty(c.getValue().getStatus()));
    colOfferBy.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getOfferBy()!=null?
            c.getValue().getOfferBy().getName():"Null"));
    colAssignedTo.setCellValueFactory(c ->
        new javafx.beans.property.SimpleStringProperty(c.getValue().getAssignedTo()!=null?
            c.getValue().getAssignedTo().getName():"Null"));
    colDate.setCellValueFactory(c ->
        new javafx.beans.property.SimpleObjectProperty(c.getValue().getCreateDate()));
  }
  private void refreshTable() {
    tableOffers.getItems().setAll(manager.getTradeoffers());
  }
  private void showTradeOffer(TradeOffer offer) {
    if (offer == null) return;
    txtTitle.setText(offer.getTitle());
    txtDescription.setText(offer.getDescription());
    txtPointCost.setText(String.valueOf(offer.getPointCost()));
    txtDate.setText(offer.getCreateDate()!=null?offer.getCreateDate().toString():"Null");
    txtType.setText(offer.getType().toString());
    txtStatus.setText(offer.getStatus().toString());
    txtOfferBy.setText(offer.getOfferBy() != null ? offer.getOfferBy().getName() : "Null");
    txtAssignedTo.setText(offer.getAssignedTo() != null ? offer.getAssignedTo().getName() : "Null");

    comboType.getSelectionModel().select(offer.getType());
    comboStatus.getSelectionModel().select(offer.getStatus());
    comboOfferBy.getSelectionModel().select(offer.getOfferBy());
    comboAssignedTo.getSelectionModel().select(offer.getAssignedTo());

    // now we  select from those two boxes
    comboStatus.setDisable(false);
    comboAssignedTo.setDisable(false);
    txtStatus.setDisable(false);
    txtAssignedTo.setDisable(false);
  }

  @FXML
  private void onSave() {

    TradeOffer selected = tableOffers.getSelectionModel().getSelectedItem();
    if (selected == null) return;
    selected.setTitle(txtTitle.getText());
    selected.setType(comboType.getValue());
    selected.setDescription(txtDescription.getText());
    selected.setPointCost(Integer.parseInt(txtPointCost.getText()));
    selected.setOfferBy(comboOfferBy.getValue());
    selected.setAssignedTo(comboAssignedTo.getValue());
    selected.setCreateDate(parseDate(txtDate.getText()));
    selected.setGeneralStatus(selected, comboStatus.getValue(), comboAssignedTo.getValue());
    manager.updateTradeOffer(selected.getID(), selected);

    refreshTable();
    clearForm();
  }
  @FXML
  private void onNewTradeOffer() {

    if(!isallTextFilled()) return;
    manager.addTradeOffer( new TradeOffer(
        txtTitle.getText(),
        comboType.getValue(),
        txtDescription.getText(),
        Integer.parseInt(txtPointCost.getText()),
        comboOfferBy.getValue(),
        parseDate(txtDate.getText())
        )

    );
    comboStatus.setDisable(true);
    comboAssignedTo.setDisable(true);
    refreshTable();
    clearForm();
  }
  private boolean isallTextFilled(){
    if(txtTitle.getText().isEmpty() || comboType.getValue()==null||
        txtDescription.getText().isEmpty() || txtPointCost.getText().isEmpty() ||
        comboOfferBy.getValue()==null || txtDate.getText().isEmpty()
    )
    {
     Alert alert= new Alert(Alert.AlertType.ERROR,"Please fill all the fields");
     alert.show();
     return false;
    }
    return true;
  }
  @FXML
  private void onReload() {
    manager.loadTradeOffers();
    refreshTable();
  }

  private void clearForm() {
    txtTitle.clear();  txtType.clear();
    txtDescription.clear();   txtPointCost.clear();
    txtStatus.clear();    txtOfferBy.clear();
    txtAssignedTo.clear();     txtDate.clear();
    comboType.getSelectionModel().clearSelection();
    comboOfferBy.getSelectionModel().clearSelection();
    comboAssignedTo.setDisable(true);
    comboStatus.setDisable(true);
    txtStatus.setDisable(true);
    txtAssignedTo.setDisable(true);
    tableOffers.getSelectionModel().clearSelection();

  }
  private void onDelete()
  {
    TradeOffer selectedOffer = tableOffers.getSelectionModel().getSelectedItem();

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete offer");
    alert.setHeaderText("Are you sure you want to delete " + selectedOffer.getTitle() + " " + selectedOffer.getDescription() + "?");

    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        manager.removeTradeOffer(selectedOffer.getID());
        refreshTable();
        clearForm();
      }
    });
  }

  @FXML
  private void onClearSelection() {
    tableOffers.getSelectionModel().clearSelection();
    comboAssignedTo.setDisable(true);
    comboStatus.setDisable(true);
    clearForm();
  }



private void TypeBox()
  {
    int currentIndex= comboType.getSelectionModel().getSelectedIndex();
    comboType.getItems().clear();
    comboType.getItems().setAll(OfferType.values());
    if(currentIndex==-1 && comboType.getItems().size()>0){
      comboType.getSelectionModel().select(0);
    }
    else{
      comboType.getSelectionModel().select(currentIndex);
    }

  }
 private void StatusBox(){
    int currentIndex= comboStatus.getSelectionModel().getSelectedIndex();
    comboStatus.getItems().setAll(OfferStatus.values());
    if(currentIndex==-1 && comboStatus.getItems().size()>0){
      comboStatus.getSelectionModel().select(0);
    }
    else{
      comboStatus.getSelectionModel().select(currentIndex);
    }
 }
 private void updateResidentBox(ComboBox<Resident> comboBox){
   int currentIndex = comboBox.getSelectionModel().getSelectedIndex();

   comboBox.getItems().clear();
   ArrayList<Resident> residents = manager.getResidents();
   for (int i = 0; i < residents.size(); i++)
   {
     comboBox.getItems().add(residents.get(i));
   }

   if (currentIndex == -1 && comboBox.getItems().size() > 0)
   {
     comboBox.getSelectionModel().select(0);
   }
   else
   {
     comboBox.getSelectionModel().select(currentIndex);
   }
 }
  private Date parseDate(String textDate) {
    try {
      String[] dateFromTxt = textDate.split("[-/]");
      if(dateFromTxt.length!=3){
        throw new IllegalArgumentException();
      }
      int day = Integer.parseInt(dateFromTxt[0]);
      int month = Integer.parseInt(dateFromTxt[1]);
      int year = Integer.parseInt(dateFromTxt[2]);
      return new Date(day, month, year);
    } catch (Exception e) {
      Alert alert = new Alert(
          Alert.AlertType.ERROR,
          "Invalid date format.\nUse: dd-mm-yyyy or dd/mm/yyyy"
      );
      alert.show();
      return null;
    }
  }
  @FXML
  private void handleActions(ActionEvent e){
    if(e.getSource()==comboType){
      OfferType type= comboType.getSelectionModel().getSelectedItem();
      if(type!=null){
        txtType.setText(type.toString());
      }
    }
    else if(e.getSource()==comboStatus){
     OfferStatus status= comboStatus.getSelectionModel().getSelectedItem();
     if(status!=null){
       txtStatus.setText(status.toString());
     }
    }
    else if(e.getSource()==comboOfferBy){
      Resident resident= comboOfferBy.getSelectionModel().getSelectedItem();
      if(resident!=null){
        txtOfferBy.setText(resident.getName());
      }
    }
    else if(e.getSource()==comboAssignedTo){
      Resident resident= comboAssignedTo.getSelectionModel().getSelectedItem();
      if(resident!=null){
        txtAssignedTo.setText(resident.getName());
      }
    }
  }
 }


