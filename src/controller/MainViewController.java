package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import logic.TownManager;
import utils.NotificationService;

public class MainViewController {

  @FXML private ResidentsController residentsViewController;
  @FXML private TradeOfferController tradeOffersViewController;
  @FXML private GreenActivityController greenActivityViewController;
  @FXML private CommunalActivityController communalActivityViewController;

  @FXML private TabPane mainTabs;
  @FXML private Tab communalTab;
  @FXML private Tab residentsTab;

  @FXML private VBox toastContainer;


  private TownManager townManager;

  public void initialize() {
    NotificationService.init(toastContainer);
    townManager = new TownManager();

    residentsViewController.init(townManager);
    tradeOffersViewController.init(townManager);
    greenActivityViewController.init(townManager);
    communalActivityViewController.init(townManager);

    mainTabs.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
      if (newTab == residentsTab) {
        residentsViewController.refreshTable();
      }
      else if (newTab == communalTab) {
        communalActivityViewController.refresh();
      }
    });
  }
}
