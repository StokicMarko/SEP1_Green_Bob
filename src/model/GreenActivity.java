package model;

import java.util.UUID;

public class GreenActivity
{
  private final String ID;
  private String title = "";
  private String description = "";
  private int greenPoint = 0;

  public GreenActivity(String title, String description, int greenPoint)
  {
    ID = UUID.randomUUID().toString();
    this.title = title;
    this.description = description;
    this.greenPoint = greenPoint;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public int getGreenPoint()
  {
    return greenPoint;
  }

  public void setGreenPoint(int greenPoint)
  {
    this.greenPoint = greenPoint;
  }
}
