package utils;
import javafx.scene.control.Alert;
import model.*;

public class InputValidation
{
  public static String transferPoints(TradeOffer selected, Resident newOfferBy,int newPointCost){
    Resident oldOfferBy= selected.getOfferBy();
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

  public static boolean validateResidentInput(
      String name,
      String lastname,
      String points,
      String address
  ) {
    if (!isValidName(name)) {
      showError("Invalid name", "Name must be at least 2 letters long.");
      return false;
    }

    if (!isValidName(lastname)) {
      showError("Invalid lastname", "Lastname must be at least 2 letters long.");
      return false;
    }

    if (!isValidPoints(points)) {
      showError("Invalid points", "Points must be a positive whole number.");
      return false;
    }

    if (!isValidAddress(address)) {
      showError(
          "Invalid address",
          "Address must be at least 5 characters and contain both letters and numbers."
      );
      return false;
    }

    return true;
  }

  private static boolean isValidName(String value) {
    return value != null
        && value.trim().length() >= 2
        && value.matches("[A-Za-zÀ-ÿ\\-\\s]+");
  }

  private static boolean isValidPoints(String value) {
    try {
      return Integer.parseInt(value) >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private static boolean isValidAddress(String value) {
    return value != null
        && value.trim().length() >= 5
        && value.matches(".*[A-Za-z].*")
        && value.matches(".*\\d.*");
  }

  private static void showError(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(message);
    alert.showAndWait();
  }
}
