package main;

import javafx.application.Application;
import model.Resident;
import model.TradeOffer;
import parser.ParserException;
import utils.JsonFileHandler;
import view.StartGUI;

import java.util.ArrayList;

public class LoadTradeOffersData
{

    public static void main(String[] args)
    {

      try {

        ArrayList<TradeOffer> tradeoffers = JsonFileHandler.readTradeoffersFromJson("tradeoffers.json");
        for (TradeOffer t : tradeoffers)
        {
          System.out.println(t.toString());
        }
      }
      catch (ParserException e)
      {
        System.out.println("Error on loading residents file");
      }

      Application.launch(StartGUI.class);
    }
  }


