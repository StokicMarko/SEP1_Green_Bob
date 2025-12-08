package main;
import javafx.application.Application;
import model.Resident;
import parser.ParserException;
import utils.JsonFileHandler;
import view.StartGUI;
import java.util.ArrayList;

public class LoadResidentData
{
  public static void main(String[] args)
  {

    try {

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

    Application.launch(StartGUI.class);
  }
}

