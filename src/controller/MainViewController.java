package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import logic.TownManager;

public class MainViewController {

  @FXML private ResidentsController residentsViewController;
  @FXML private TradeOfferController tradeOffersViewController;
  @FXML private GreenActivityController greenActivityViewController;
  @FXML private CommunalActivityController communalActivityViewController;

  @FXML private TabPane mainTabs;
  @FXML private Tab communalTab;
  @FXML private Tab residentsTab;

  private TownManager townManager;

  public void initialize() {
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
