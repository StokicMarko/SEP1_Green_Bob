package model;
import java.util.UUID;

  public class GreenActivity extends Activity
  {
    private String ID;
    private String title;
    private String description;
    private double greenPoints;


    public GreenActivity(String title, String description, double points)
    {
      super(title,description,(int)points);
      this.ID=ID;
      this.title=title;
      this.description=description;
      this.greenPoints=greenPoints;
    }

    public double getGreenPoints()
    {
      return greenPoints;
    }
    public String getID()
    {
    return ID;
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

    public void addPoints(double pointsToAdd)
    {
      super.addPoints(pointsToAdd);
    }

    public void removePoints(int pointsToRemove)
    {
      super.removePoints(pointsToRemove);
    }
  }





