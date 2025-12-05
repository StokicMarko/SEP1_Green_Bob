package utils;


import model.Resident;
import model.ResidentList;
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
}
