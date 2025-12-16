package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import logic.TownManager;

public class MainViewController
{
  @FXML private ResidentsController residentsViewController;
  @FXML private TradeOfferController tradeOffersViewController;
  @FXML private GreenActivityController greenActivityViewController;
  @FXML private CommunalActivityController communalActivityViewController;

  @FXML private Tab communalTab;

  private TownManager townManager;

  public void initialize()
  {
    townManager = new TownManager();

    residentsViewController.init(townManager);
    tradeOffersViewController.init(townManager);
    // greenActivityViewController.init(townManager);
    communalActivityViewController.init(townManager);

    communalTab.setOnSelectionChanged(event -> {
      if (communalTab.isSelected()) {
        communalActivityViewController.loadResidents();
      }
    });
  }
}
