package utils;
import logic.TownManager;
import model.*;

public class InputValidation
{
  public static boolean isPositive(int value)
  {
    return value > 0;
  }

  public static boolean isValidString(String value)
  {
    return value.length() >= 2;
  }

  public static boolean isValidDate(String value)
  {
    String[] dateFromTxt = value.split("[-/]");
    if (dateFromTxt.length != 3)
      return false;

    return true;
  }
  public static String transferPoints(TradeOffer selected, Resident newOfferBy,
      int newPointCost, TownManager manager)
  {
    Resident oldOfferBy= manager.findResidentById(selected.getOfferBy().getID());
    Resident realnewOfferBy= manager.findResidentById(newOfferBy.getID());

    int oldPointCost= selected.getPointCost();

    if(!(oldOfferBy.getID().equals(newOfferBy.getID()))){
      oldOfferBy.addPoints(oldPointCost);

      if(newOfferBy.getPersonalPoints()<newPointCost){
        return newOfferBy.getFullName()+" "+"doesnot have enough points for trade offer";
      }
      newOfferBy.removePoints(newPointCost);
    }

    else if(oldPointCost!=newPointCost){
      int difference= newPointCost- oldPointCost;
      if(difference<0){
        oldOfferBy.addPoints(-difference);
      }
      else{
        if(oldOfferBy.getPersonalPoints()<difference){
          return oldOfferBy.getFullName()+" "+"couldnot exceed the original points";
        }
        oldOfferBy.removePoints(difference);
      }
    }
    return null;
  }
}
