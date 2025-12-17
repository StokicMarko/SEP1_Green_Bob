package logic;

import model.*;
import parser.ParserException;
import utils.JsonFileHandler;

import java.util.ArrayList;

public class TownManager
{
  private int greenPointsPool = 0;

  private ResidentList residentList = new ResidentList();
  private TradeOfferList tradeOfferList = new TradeOfferList();
  private ActivityList greenActivityList = new ActivityList();
  private ActivityList communalActivityList = new ActivityList();

  private final String RESIDENTS_JSON = "residents.json";
  private final String TRADE_OFFER_JSON = "tradeoffers.json";
  private final String GREEN_ACTIVITIES_JSON = "greenactivities.json";
  private final String COMMUNAL_ACTIVITIES_JSON = "communalactivities.json";

  public TownManager()
  {
    loadResidents();
    loadTradeOffers();
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
    return residentList.getAll();
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
          residentList.getAll());
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
  public void loadTradeOffers(){
    try{
      ArrayList<TradeOffer> offer= JsonFileHandler.readTradeoffersFromJson(TRADE_OFFER_JSON);
      for(TradeOffer t:offer){
        tradeOfferList.add(t);
      }
    }
    catch(ParserException e){
      System.out.println("Unable to read from json file");
    }

  }
  private void saveTradeOfferToFile(){
     try{
       JsonFileHandler.saveTradeOfferToJson(TRADE_OFFER_JSON, tradeOfferList.getAll());
     }
     catch(ParserException e){
       System.out.println("Could not save to given file");
     }
  }
  public ArrayList<TradeOffer> getTradeoffers(){
    return tradeOfferList.getAll();
  }
  public void addTradeOffer(TradeOffer o){
      tradeOfferList.add(o);
      saveTradeOfferToFile();
      saveResidentsToFile();
  }
  public void removeTradeOffer(String id){
     tradeOfferList.removeByID(id);
     saveTradeOfferToFile();
  }

  public void updateTradeOffer(String id, TradeOffer newoffer)
  {
    tradeOfferList.updateByID(id, newoffer);
    saveTradeOfferToFile();
    saveResidentsToFile();
  }

  public void loadGreenActivities()
  {
  try
  {
    ArrayList<GreenActivity> loaded = JsonFileHandler.readGreenActivitiesFromJson(GREEN_ACTIVITIES_JSON);
    for(GreenActivity g:loaded)
    {
      greenActivityList.add(g);
    }
  }
  catch (ParserException e)
  {
    System.out.println("Unable to read from json file");
  }
}

  private void saveGreenActivityToFile()
  {
  try
  {
    JsonFileHandler.saveActivityToJson("greenactivities.json", greenActivityList.getAll());
  }
  catch (ParserException e)
  {
    System.out.println("Could not save to given file");
  }
}

  public ArrayList<GreenActivity> getGreenActivities()
  {
    ArrayList<GreenActivity> greens = new ArrayList<>();

    for (Activity a : greenActivityList.getAll()) {
      if (a instanceof GreenActivity) {
        greens.add((GreenActivity) a);
      }
    }
    return greens;  }

  public void addGreenActivity(GreenActivity a)
  {
    greenActivityList.add(a);
    saveGreenActivityToFile();
  }

  public void updateGreenActivity(String id, GreenActivity newData)
  {
      greenActivityList.updateByID(id, newData);
      saveGreenActivityToFile();
  }

  public void removeGreenActivity(String id)
  {
    greenActivityList.removeById(id);
    saveGreenActivityToFile();
  }

  public ArrayList<CommunalActivity> getCommunalActivities()
  {
    ArrayList<CommunalActivity> list = new ArrayList<>();
    for(Activity a : communalActivityList.getAll()) {
      if(a instanceof CommunalActivity)
        list.add((CommunalActivity)a);
    }
    return list;
  }

  public void addCommunalActivity(CommunalActivity communalActivity)
  {
    communalActivityList.add(communalActivity);
    saveCommunalActivitiesToFile();
  }

  public void removeCommunalActivity(String id)
  {
    communalActivityList.removeById(id);
    saveCommunalActivitiesToFile();
  }

  public void loadCommunalActivities()
  {
    try
    {
      ArrayList<CommunalActivity> loaded =
          JsonFileHandler.readCommunalActivitiesFromJson(COMMUNAL_ACTIVITIES_JSON);
      communalActivityList.getAll().clear();
      communalActivityList.getAll().addAll(loaded);
    }
    catch(Exception e)
    {
      communalActivityList.getAll().clear();
    }
  }

  private void saveCommunalActivitiesToFile()
  {
    try
    {
      ArrayList<CommunalActivity> list = getCommunalActivities();
      JsonFileHandler.saveCommunalActivitiesToJson(COMMUNAL_ACTIVITIES_JSON, list);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public void resetPersonalPoints()
  {
    residentList.resetPersonalPoints();
    saveResidentsToFile();
  }

  public void updateCommunalActivity(String id, CommunalActivity selectedActivity)
  {
    communalActivityList.updateByID(id, selectedActivity);
    saveCommunalActivitiesToFile();
  }

  public void assignPointFromCommunal(CommunalActivity selectedActivity)
  {
    selectedActivity.getParticipants().forEach(participant -> {
      residentList.updateByID(participant.getID(), new Resident(
          participant.getName(),
          participant.getLastname(),
          participant.getPersonalPoints() + selectedActivity.getPoints(),
          participant.getAddress()
      ));
    });

    selectedActivity.setPointAssign(true);

    saveCommunalActivitiesToFile();
    saveResidentsToFile();
  }

}
