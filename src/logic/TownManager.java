package logic;

import model.ActivityList;
import model.Resident;
import model.ResidentList;
import model.TradeOfferList;
import utils.JsonFileHandler;

import java.util.ArrayList;

public class TownManager
{
  private int greenPointsPool = 0;
  private ResidentList residentList = new ResidentList();
  private ActivityList activityList = new ActivityList();
  private TradeOfferList tradeOfferList = new TradeOfferList();
  private static final String FILE_NAME = "residents.json";

  public TownManager()
  {
  }

  private void saveResidentToFile(Resident resident)
  {
    residentList.add(resident);
    try
    {
      JsonFileHandler.saveResidentsToJson(FILE_NAME,
          residentList.getAllResidents());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public ArrayList<Resident> getResidentsFromFile()
  {
    try
    {
      return JsonFileHandler.readResidentsFromJson(FILE_NAME);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return new ArrayList<Resident>();
  }

  public ArrayList<Resident> getResidents()
  {
    return residentList.getAllResidents();
  }

  public void addResident(Resident resident)
  {
    residentList.add(resident);
    saveResidentToFile(resident);
  }
}
