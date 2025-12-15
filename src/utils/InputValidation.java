package utils;

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
}
