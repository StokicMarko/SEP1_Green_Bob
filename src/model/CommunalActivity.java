package model;

import java.util.ArrayList;
import java.util.List;
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

  public void setParticipants(List<Resident> participants) {
    this.participants = new ArrayList<>(participants);
  }
  public List<Resident> getParticipants() {
    return participants;
  }

  public void addParticipant(Resident r) {
    if (!participants.contains(r)) {
      participants.add(r);
    }
  }

}
