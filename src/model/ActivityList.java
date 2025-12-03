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
      if (activities.get(0).getID() == id)
        activities.remove(i);
    }
  }

  public ArrayList<Activity> getAll()
  {
    return activities;
  }

}
