package main;
import model.Resident;
import model.ResidentList;
import parser.ParserException;
import utils.JsonFileHandler;
import utils.MyFileHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LoadResidentData
{
  public static void main(String[] args)
  {

    //EXAMPLE DATA
    ArrayList<Resident> list = new ArrayList<Resident>();
    list.add(new Resident("abc1", "Alice", "Blue", 10, "Fantasy"));
    list.add(new Resident("abc2", "Bob", "White", 15, "NoExist"));


    try {
      //Create example file
      JsonFileHandler.saveResidentsToJson("residents.json", list);

      ArrayList<Resident> residents = JsonFileHandler.readResidentsFromJson("residents.json");
      for (Resident resident : residents)
      {
        System.out.println(resident.toString());
      }
    }
    catch (ParserException e)
    {
      System.out.println("Error on loading residents file");
    }
  }
}

