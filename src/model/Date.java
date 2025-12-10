package model;
import java.lang.Comparable;

public class Date implements Comparable<Date>
{
  private int day;
  private int month;
  private int year;

  public Date(int day, int month, int year)
  {
    this.day = day;
    this.month = month;
    this.year = year;
  }
  public int compareTo(Date other){
    if(this.year!=other.year){
      return this.year-other.year;
    }
    else if(this.day!= other.day){
      return this.day-other.day;
    }
    return this.month-other.month;
  }

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
