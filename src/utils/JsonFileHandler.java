package utils;


import model.Resident;
import model.ResidentList;
import model.TradeOffer;
import model.TradeOfferList;
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
}
