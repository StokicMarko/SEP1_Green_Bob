package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import logic.TownManager;

public class MainViewController
{
  @FXML private ResidentsController residentsViewController;
  @FXML private TradeOfferController tradeoffersViewController;
  @FXML private GreenActivityController greenActivityViewController;
  @FXML private CommunalActivityController communalActivityViewController;

  @FXML private Tab communalTab;

  private TownManager townManager;

  public void initialize()
  {
    townManager = new TownManager();

    residentsViewController.init(townManager);
    tradeoffersViewController.init(townManager);
    // greenActivityViewController.init(townManager);
    communalActivityViewController.init(townManager);

    communalTab.setOnSelectionChanged(event -> {
      if (communalTab.isSelected()) {
        System.out.print("AA");
        communalActivityViewController.loadResidents();
      }
    });
  }
}
