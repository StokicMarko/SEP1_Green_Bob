package model;

import java.util.ArrayList;
import java.util.UUID;

public class CommunalActivity extends Activity
{
  private ArrayList<Resident> participants;
  private boolean isPointAssign;

  public CommunalActivity(String title, String description, int points, Date eventDate)
  {
    super(title, description, points, eventDate);
    isPointAssign = false;
  }

  public void assignPointsToParticipants() {
    if(!isPointAssign)
    {
      isPointAssign = true;
      for (int i = 0; i < participants.size(); i++)
      {
        participants.get(i).addPoints(getPoints());
      }
    }
  }

}
