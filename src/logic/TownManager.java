package logic;

import model.ActivityList;
import model.Resident;
import model.ResidentList;
import model.TradeOfferList;
import utils.JsonFileHandler;

import java.util.ArrayList;
import java.util.UUID;

public class TownManager
{
  private int greenPointsPool = 0;
  private ActivityList activityList = new ActivityList();
  private TradeOfferList tradeOfferList = new TradeOfferList();
  private ResidentList residentList = new ResidentList();

  private static final String RESIDENTS_JSON = "residents.json";


  public TownManager() {
    loadResidents();
  }

  public void loadResidents() {
    try {
      ArrayList<Resident> loaded = JsonFileHandler.readResidentsFromJson(RESIDENTS_JSON);
      residentList.setAll(loaded);
    } catch (Exception e) {
      residentList.setAll(new ArrayList<>());
    }
  }

  public ArrayList<Resident> getResidents() {
    return residentList.getAllResidents();
  }

  public Resident createResident(String name, String lastname, int points, String address) {
    Resident resident = new Resident(
        UUID.randomUUID().toString(),
        name,
        lastname,
        points,
        address
    );

    residentList.add(resident);
    saveToFile();
    return resident;
  }

  public void updateResident(Resident resident, String name, String lastname, int points, String address) {
    resident.setName(name);
    resident.setLastname(lastname);
    resident.setPersonalPoints(points);
    resident.setAddress(address);
    saveToFile();
  }

  private void saveToFile() {
    try {
      JsonFileHandler.saveResidentsToJson(
          RESIDENTS_JSON,
          residentList.getAllResidents()
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
