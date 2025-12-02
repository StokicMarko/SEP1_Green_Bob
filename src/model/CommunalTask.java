package model;

import java.util.ArrayList;
import java.util.UUID;

public class CommunalTask
{
  private final String ID;
  private String title;
  private String description;
  private int personalPoints;
  private ArrayList<Resident> participants;
  private boolean isPointAssign;

  public CommunalTask(String title, String description, int personalPoints, ArrayList<Resident> participants)
  {
    ID = UUID.randomUUID().toString();
    this.title = title;
    this.description = description;
    this.personalPoints = personalPoints;
    this.participants = participants;
    this.isPointAssign = false;
  }

  public void assignPointsToParticipants() {
    if(!isPointAssign)
    {
      isPointAssign = true;
      for (Resident resident: participants)
      {
        resident.addPoints(personalPoints);
      }
    }
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

  public int getPersonalPoints()
  {
    return personalPoints;
  }

  public void setPersonalPoints(int personalPoints)
  {
    this.personalPoints = personalPoints;
  }
}
