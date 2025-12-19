package model;

import myEnum.RewardStatus;
import java.util.UUID;

public class Reward {
  private final String ID;
  private String title = "";
  private String description = "";
  private int threshold = 0;
  private Date createdAt;
  private Date awardedAt;
  private RewardStatus status = RewardStatus.PENDING;

  public Reward(String title, String description, int threshold, Date createdAt) {
    ID = UUID.randomUUID().toString();
    this.title = title;
    this.description = description;
    this.threshold = threshold;
    this.createdAt = createdAt;
  }

  public String getID() {
    return ID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getThreshold() {
    return threshold;
  }

  public void setThreshold(int threshold) {
    this.threshold = threshold;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getAwardedAt() {
    return awardedAt;
  }

  public void setAwardedAt(Date awardedAt) {
    this.awardedAt = awardedAt;
  }

  public RewardStatus getStatus() {
    return status;
  }

  public void setStatus(RewardStatus status) {
    this.status = status;
  }

  @Override public String toString() {
    return "Reward{" + "ID='" + ID + '\'' + ", title='" + title + '\'' + ", threshold=" + threshold + ", status=" + status + '}';
  }
}
