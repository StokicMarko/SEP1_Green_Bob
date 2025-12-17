package utils;

import javafx.scene.control.Alert;
import logic.TownManager;
import model.*;

import java.time.LocalDate;

public class InputValidation {

  private static final int NAME_MAX = 30;
  private static final int ADDRESS_MAX = 100;
  private static final int TITLE_MAX = 50;
  private static final int DESCRIPTION_MAX = 250;
  private static final int POINTS_MAX = 999_999;


  public static String transferPoints(TradeOffer selected, Resident newOfferBy, int newPointCost,
      TownManager manager)
  {
    Resident oldOfferBy = manager.findResidentById(selected.getOfferBy().getID());
    Resident realNewOfferBy= manager.findResidentById(newOfferBy.getID());

    int oldPointCost = selected.getPointCost();

    if (!oldOfferBy.getID().equals(newOfferBy.getID())) {
      oldOfferBy.addPoints(oldPointCost);

      if (newOfferBy.getPersonalPoints() < newPointCost) {
        return newOfferBy.getFullName() + " does not have enough points for trade offer";
      }
      newOfferBy.removePoints(newPointCost);
    }
    else if (oldPointCost != newPointCost) {
      int difference = newPointCost - oldPointCost;
      if (difference < 0) {
        oldOfferBy.addPoints(-difference);
      } else {
        if (oldOfferBy.getPersonalPoints() < difference) {
          return oldOfferBy.getFullName() + " could not exceed the original points";
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
      showError("Invalid name", "Name must be 2–30 letters long.");
      return false;
    }

    if (!isValidName(lastname)) {
      showError("Invalid lastname", "Lastname must be 2–30 letters long.");
      return false;
    }

    if (!isValidPoints(points)) {
      showError("Invalid points", "Points must be between 0 and " + POINTS_MAX + ".");
      return false;
    }

    if (!isValidAddress(address)) {
      showError(
          "Invalid address",
          "Address must be 5–100 characters and contain letters and numbers."
      );
      return false;
    }

    return true;
  }

  private static boolean isValidName(String value) {
    return value != null
        && value.trim().length() >= 2
        && value.trim().length() <= NAME_MAX
        && value.matches("[A-Za-zÀ-ÿ\\-\\s]+");
  }

  private static boolean isValidAddress(String value) {
    return value != null
        && value.trim().length() >= 5
        && value.trim().length() <= ADDRESS_MAX
        && value.matches(".*[A-Za-z].*")
        && value.matches(".*\\d.*");
  }


  public static boolean validateGreenActivityInput(
      String title,
      String points,
      LocalDate date,
      String description
  ) {
    if (title == null || title.trim().length() < 3 || title.length() > TITLE_MAX) {
      showError(
          "Invalid title",
          "Title must be between 3 and " + TITLE_MAX + " characters."
      );
      return false;
    }

    if (!isValidPoints(points) || Integer.parseInt(points) <= 0) {
      showError(
          "Invalid points",
          "Green points must be between 1 and " + POINTS_MAX + "."
      );
      return false;
    }

    if (description != null && description.length() > DESCRIPTION_MAX) {
      showError(
          "Invalid description",
          "Description cannot exceed " + DESCRIPTION_MAX + " characters."
      );
      return false;
    }

    if (date == null) {
      showError("Invalid date", "Please select a date.");
      return false;
    }

    if (date.isAfter(LocalDate.now())) {
      showError("Invalid date", "Event date cannot be in the future.");
      return false;
    }

    return true;
  }


  private static boolean isValidPoints(String value) {
    try {
      int points = Integer.parseInt(value);
      return points >= 0 && points <= POINTS_MAX;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private static void showError(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(message);
    alert.showAndWait();
  }
}
