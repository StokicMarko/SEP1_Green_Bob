package logic;

import model.ActivityList;
import model.Resident;
import model.ResidentList;
import model.TradeOffer;
import model.TradeOfferList;
import utils.JsonFileHandler;

import java.util.ArrayList;
import java.util.UUID;

public class TownManager
{
  private int greenPointsPool = 0;

  private ResidentList residentList = new ResidentList();
  private TradeOfferList tradeOfferList = new TradeOfferList();
  private ActivityList greenActivityList = new ActivityList();
  private ActivityList communalActivityList = new ActivityList();

  private static final String RESIDENTS_JSON = "residents.json";
  private static final String TRADE_OFFER_JSON = "tradeOffer.json";
  private static final String GREEN_ACTIVITIES_JSON = "greenActivities.json";
  private static final String COMMUNAL_ACTIVITIES_JSON = "communalActivities.json";

  public TownManager()
  {
    loadResidents();
  }

  public void loadResidents()
  {
    try
    {
      ArrayList<Resident> loaded = JsonFileHandler.readResidentsFromJson(
          RESIDENTS_JSON);
      residentList.setAll(loaded);
    }
    catch (Exception e)
    {
      residentList.setAll(new ArrayList<>());
    }
  }

  public ArrayList<Resident> getResidents()
  {
    return residentList.getAllResidents();
  }

  public void createResident(String name, String lastname, int points, String address)
  {
    residentList.add(
        new Resident(
            name,
            lastname,
            points,
            address
        )
    );

    saveResidentsToFile();
  }

  public void updateResident(String id, Resident newData)
  {
    residentList.updateByID(id, newData);
    saveResidentsToFile();
  }

  private void saveResidentsToFile()
  {
    try
    {
      JsonFileHandler.saveResidentsToJson(RESIDENTS_JSON,
          residentList.getAllResidents());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void removeResidentByID(String id)
  {
    residentList.removeByID(id);
    saveResidentsToFile();
  }
}
