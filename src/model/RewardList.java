package model;

import java.util.ArrayList;

public class RewardList {
  private ArrayList<Reward> rewards = new ArrayList<>();

  public void add(Reward reward) {
    rewards.add(reward);
  }

  public void removeByID(String id) {
    Reward toRemove = findByID(id);
    if (toRemove != null) rewards.remove(toRemove);
  }

  public ArrayList<Reward> getAll() {
    return rewards;
  }

  public void setAll(ArrayList<Reward> list) {
    this.rewards = list;
  }

  public void updateByID(String id, Reward newData) {
    Reward existing = findByID(id);
    if (existing == null) return;

    existing.setTitle(newData.getTitle());
    existing.setDescription(newData.getDescription());
    existing.setThreshold(newData.getThreshold());
    existing.setStatus(newData.getStatus());
    existing.setAwardedAt(newData.getAwardedAt());
  }

  public Reward findByID(String id) {
    return rewards.stream().filter(r -> r.getID().equals(id)).findFirst().orElse(null);
  }
}
