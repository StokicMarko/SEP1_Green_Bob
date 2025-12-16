package model;

import java.util.ArrayList;

public class ActivityList
{
  private ArrayList<Activity> activities = new ArrayList<Activity>();

  public void add(Activity activity)
  {
    activities.add(activity);
  }

  public void remove(Activity activity)
  {
    activities.remove(activity);
  }

  public void removeById(String id)
  {
    for (int i = 0; i < activities.size(); i++)
    {
      if (activities.get(i).getID().equals(id)) // use i and equals
      {
        activities.remove(i);
        return; // stop after removing
      }
    }
  }

  public ArrayList<Activity> getAll()
  {
    return activities;
  }

  public void updateByID(String id, Activity newData)
  {
    Activity existing = findByID(id);
    if (existing == null) return;

    existing.setTitle(newData.getTitle());
    existing.setDescription(newData.getDescription());
    existing.setEventDate(newData.getEventDate());

    if (existing instanceof CommunalActivity && newData instanceof CommunalActivity) {
      ((CommunalActivity) existing).setParticipants(((CommunalActivity) newData).getParticipants());
    }
  }

  public Activity findByID(String id) {
    return activities.stream()
        .filter(activity -> activity.getID().equals(id))
        .findFirst()
        .orElse(null);
  }
}
