package model;

import java.util.ArrayList;
import java.io.Serializable;

public class ResidentList implements Serializable
{
  private ArrayList<Resident> residents = new ArrayList<Resident>();

  public void add(Resident resident)
  {
    residents.add(resident);
  }

  public void remove(Resident resident)
  {
    residents.remove(resident);
  }

  public ArrayList<Resident> getAllResidents()
  {
    return residents;
  }

  public Resident findByID(String id)
  {
    return residents.stream()
        .filter(resident -> resident.getID().equals(id))
        .findFirst()
        .orElse(null);
  }
}
