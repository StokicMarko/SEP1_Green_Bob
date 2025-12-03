package logic;

import model.ActivityList;
import model.Resident;
import model.ResidentList;
import model.TradeOfferList;

import java.util.ArrayList;

public class TownManager
{
  private int greenPointsPool = 0;

  private ResidentList residentList = new ResidentList();

  private ActivityList activityList = new ActivityList();

  private TradeOfferList tradeOfferList = new TradeOfferList();

  public TownManager()
  {
  }
}
