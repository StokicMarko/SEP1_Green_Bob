package utils;


import model.*;
import parser.ParserException;
import parser.XmlJsonParser;

import java.util.ArrayList;
import java.util.Arrays;

public class JsonFileHandler
{
  private static final XmlJsonParser parser = new XmlJsonParser();

  public static void saveResidentsToJson(String fileName,ArrayList<Resident> residents)
      throws ParserException
  {
    parser.toJsonFile(residents, fileName);
  }

  public static ArrayList<Resident> readResidentsFromJson(String fileName)
      throws ParserException
  {
    Resident[] residentArray = parser.fromJsonFile(fileName, Resident[].class);
    return new ArrayList<>(Arrays.asList(residentArray));
  }
  public static void saveTradeOfferToJson(String filename, ArrayList<TradeOffer> tradeoffers)
    throws ParserException
  {
    parser.toJsonFile(tradeoffers,filename);
  }
  public static ArrayList<TradeOffer> readTradeoffersFromJson(String filename)
    throws ParserException
  {
    TradeOffer [] tradeofferArray= parser.fromJsonFile(filename,TradeOffer[].class);
    return new ArrayList<>(Arrays.asList(tradeofferArray));
  }

  public static ArrayList<GreenActivity> readGreenActivitiesFromJson(String filename)
      throws ParserException
  {
    GreenActivity [] greenActivityArray = parser.fromJsonFile(filename, GreenActivity[].class);
    return new ArrayList<>(Arrays.asList(greenActivityArray));
  }

  public static void saveGreenActivityToJson(String filename, ArrayList<GreenActivity> greenActivities)
      throws ParserException
  {
    parser.toJsonFile(greenActivities, filename);
  }

  // Add to JsonFileHandler.java

  public static ArrayList<CommunalActivity> readCommunalActivitiesFromJson(String filename) throws ParserException
  {
    CommunalActivity[] array = parser.fromJsonFile(filename, CommunalActivity[].class);
    return new ArrayList<>(Arrays.asList(array));
  }

  public static void saveCommunalActivitiesToJson(String filename, ArrayList<CommunalActivity> activities) throws ParserException
  {
    parser.toJsonFile(activities, filename);
  }

}
