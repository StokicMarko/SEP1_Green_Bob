package model;

import java.util.UUID;

public abstract class Activity
{
  private final String ID;
  private String title = "";
  private String description = "";
  private int points = 0;
  private Date eventDate;

  public Activity(String title, String description, int points, Date eventDate)
  {
    ID = UUID.randomUUID().toString();
    this.title = title;
    this.description = description;
    this.points = points;
    this.eventDate = eventDate;
  }

  public Activity(String title, String description, int points)
  {
    this.title=title;
    this.description=description;
    this.points=points;
  }

  public int getPoints()
  {
    return points;
  }

  public String getID()
  {
    return ID;
  }

  public void addPoints(double pointsToAdd)
  {
    points += pointsToAdd;
  }

  public void removePoints(int pointsToRemove)
  {
    points -= pointsToRemove;
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

  public void setEventDate(Date eventDate)
  {
    this.eventDate = eventDate;
  }

  public Date getEventDate()
  {
    return eventDate;
  }
}
