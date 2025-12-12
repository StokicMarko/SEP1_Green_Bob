package model;


public class GreenActivity extends Activity
  {
    private int greenPoints;
    private Date eventDate;


    public GreenActivity( String title, String description,int greenPoints,
        Date eventDate)
    {
      super(title,description,greenPoints, eventDate);
      this.greenPoints=greenPoints;
    }


    public Date getEventDate()
    {
      return eventDate;
    }

    public void setEventDate(Date eventDate)
    {
      this.eventDate = eventDate;
    }

    public double getGreenPoints()
    {
      return greenPoints;
    }
    public String getID()
    {
    return super.getID();
    }

    public String getTitle()
    {
      return super.getTitle();
    }

    public void setTitle(String title)
    {
      super.setTitle(title);
    }

    public String getDescription()
    {
      return super.getDescription();
    }

    public void setDescription(String description)
    {
      super.setDescription(description);
    }

    public void addPoints(int pointsToAdd)
    {
      greenPoints += pointsToAdd;
    }

    public void removePoints(int pointsToRemove)
    {
      greenPoints -= pointsToRemove;
      if(greenPoints < 0 )
      {
        greenPoints=0;
      }
    }
  }





