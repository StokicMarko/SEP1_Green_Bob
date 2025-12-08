package model;

import java.util.ArrayList;
import java.io.Serializable;

public class ResidentList
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

  public void setAll(ArrayList<Resident> list) {
    residents = list;
  }

  public void updateResidentByID(String id, Resident newData) {
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
