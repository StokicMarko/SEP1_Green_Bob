package logic;

import model.*;
import myEnum.OfferStatus;
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

  private static final String RESIDENTS_JSON = "residents.json";
  private static final String TRADE_OFFER_JSON = "tradeoffers.json";
  private static final String GREEN_ACTIVITIES_JSON = "greenactivities.json";
  private static final String COMMUNAL_ACTIVITIES_JSON = "communalactivities.json";

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
  public void loadTradeOffers(){
    try{
      ArrayList<TradeOffer> offer= JsonFileHandler.readTradeoffersFromJson("tradeoffers.json");
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
       JsonFileHandler.saveTradeOfferToJson("tradeoffers.json",tradeOfferList.getTradeOffers());
     }
     catch(ParserException e){
       System.out.println("Could not save to given file");
     }
  }
  public ArrayList<TradeOffer> getTradeoffers(){
    return tradeOfferList.getTradeOffers();
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
  public void changeTradeOfferStatus(String id, OfferStatus status,Resident r){
     TradeOffer t= tradeOfferList.findByID(id);
     if(t==null){
       System.out.println(" Trade offer with given id could not be found ");
     }
     switch (status)
     {
       case AVAILABLE :
         t.setStatusToAvailable();
         break;
       case TAKEN:
         t.setStatusToTaken(r);
         break;
       case CANCELLED:
         t.setStatusToCancelled();
         break;
       case COMPLETED:
         t.setStatusToCompleted();
         break;
       default:
         System.out.println("Wrong status");
      saveTradeOfferToFile();
     }
  }
  public void updateTradeOffer(String id, TradeOffer newoffer)
  {
    tradeOfferList.updateByID(id, newoffer);
    saveTradeOfferToFile();
  }

  public void loadGreenActivities()
  {
  try
  {
    ArrayList<GreenActivity> loaded = JsonFileHandler.readGreenActivitiesFromJson("greenactivities.json");
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
    System.out.println(communalActivity.toString());
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


}
