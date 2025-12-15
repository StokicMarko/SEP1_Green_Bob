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
    participants = new ArrayList<Resident>();
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

  public void addParticipant(Resident resident) {
    if (!participants.contains(resident)) {
      participants.add(resident);
    }
  }

  @Override public String toString()
  {
    return "CommunalActivity{" + "participants=" + participants + '}';
  }
}
