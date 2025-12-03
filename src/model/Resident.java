package model;

import java.util.UUID;
import java.io.Serializable;

public class Resident implements Serializable
{
  private final String ID;
  private String name = "";
  private  String lastname = "";
  private int personalPoints = 0;
  private String address ="";

  public Resident(String name, String lastname, int personalPoints,
      String address)
  {
    ID = UUID.randomUUID().toString();
    this.name = name;
    this.lastname = lastname;
    this.personalPoints = personalPoints;
    this.address = address;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getLastname()
  {
    return lastname;
  }

  public void setLastname(String lastname)
  {
    this.lastname = lastname;
  }

  public int getPersonalPoints()
  {
    return personalPoints;
  }

  public void addPoints(int pointsToAdd)
  {
    personalPoints += pointsToAdd;
  }

  public void removePoints(int pointsToRemove)
  {
    personalPoints -= pointsToRemove;
  }

  public void setPersonalPoints(int personalPoints)
  {
    this.personalPoints = personalPoints;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getID() {
    return ID;
  }
}
