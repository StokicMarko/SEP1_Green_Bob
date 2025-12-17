package model;

import java.util.ArrayList;

public class ResidentList
{
  private ArrayList<Resident> residents = new ArrayList<Resident>();

  public void add(Resident resident)
  {
    residents.add(resident);
  }

  public void removeByID(String id)
  {
    Resident toRemove = findByID(id);
    if (toRemove != null) {
      residents.remove(toRemove);
    }
  }

  public ArrayList<Resident> getAll()
  {
    return residents;
  }

  public void setAll(ArrayList<Resident> list) {
    residents = list;
  }

  public void resetPersonalPoints()
  {
    residents.forEach(resident -> resident.setPersonalPoints(0));
  }

  public void updateByID(String id, Resident newData) {
    Resident existing = findByID(id);
    if (existing == null) return;

    existing.setName(newData.getName());
    existing.setLastname(newData.getLastname());
    existing.setPersonalPoints(newData.getPersonalPoints());
    existing.setAddress(newData.getAddress());
  }

  public Resident findByID(String id) {
    return residents.stream()
        .filter(resident -> resident.getID().equals(id))
        .findFirst()
        .orElse(null);
  }
}