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



  public int getPoints()
  {
    return points;
  }

  public void setPoints(int points)
  {
    this.points = points;
  }

  public String getID()
  {
    return ID;
  }

  public void addPoints(int pointsToAdd)
  {
    points +=  pointsToAdd;
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

  @Override public String toString()
  {
    return "Activity{" + "ID='" + ID + '\'' + ", title='" + title + '\''
        + ", description='" + description + '\'' + ", points=" + points
        + ", eventDate=" + eventDate + '}';
  }
}
