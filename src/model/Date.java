package model;

public class Date
{
  private int day;
  private int month;
  private int year;

  @Override public String toString()
  {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(day)
        .append("-")
        .append(month)
        .append("-")
        .append(year);

    return stringBuilder.toString();
  }

  public int getDay()
  {
    return day;
  }

  public void setDay(int day)
  {
    this.day = day;
  }

  public int getMonth()
  {
    return month;
  }

  public void setMonth(int month)
  {
    this.month = month;
  }

  public int getYear()
  {
    return year;
  }

  public void setYear(int year)
  {
    this.year = year;
  }
}
