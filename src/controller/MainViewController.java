package controller;

import javafx.fxml.FXML;
import logic.TownManager;

public class MainViewController
{
  @FXML private ResidentsController residentsViewController;
  @FXML private TradeOfferController tradeoffersViewController;

  private TownManager townManager;

  public void initialize()
  {
    townManager = new TownManager();

    residentsViewController.init(townManager);
    tradeoffersViewController.init(townManager);
  }
}
