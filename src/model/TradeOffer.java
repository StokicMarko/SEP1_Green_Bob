package model;

import myEnum.OfferStatus;
import myEnum.OfferType;
import java.util.UUID;


public class TradeOffer
{
  private final String ID;
  private String title = "";
  private OfferType type;
  private String description = "";
  private int pointCost = 0;
  private OfferStatus status;
  private Resident offerBy;
  private Resident assignedTo;
  private Date createDate;

  public TradeOffer(String title, OfferType type,String description,
      int pointCost, Resident offerBy,Date createDate )
  {
    if (offerBy.getPersonalPoints() < pointCost) {
      throw new IllegalArgumentException(
          "Resident does not have enough points to create this TradeOffer."
      );
    }
    ID = UUID.randomUUID().toString();
    this.title = title;
    this.description = description;
    this.type = type;
    this.pointCost = pointCost;
    this.offerBy = offerBy;
    status=OfferStatus.AVAILABLE;
    assignedTo=null;
    this.createDate = createDate;
    offerBy.removePoints(pointCost);
  }

  public String getID() {
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

  public OfferType getType()
  {
    return type;
  }

  public void setType(OfferType type)
  {
    this.type = type;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public int getPointCost()
  {
    return pointCost;
  }

  public void setPointCost(int pointCost)
  {
    this.pointCost = pointCost;
  }

  public OfferStatus getStatus()
  {
    return status;
  }

  public void setStatusToCancelled()
  {
    offerBy.addPoints(pointCost);
    status = OfferStatus.CANCELLED;
  }

  public void setCreateDate(Date createDate){
    this.createDate = createDate;
}

  public Date getCreateDate(){
     return createDate;
  }

  public void setStatusToAvailable()
  {
    status = OfferStatus.AVAILABLE;
  }

  public void setStatusToTaken(Resident assignedTo)
  {
    this.assignedTo = assignedTo;
    status = OfferStatus.TAKEN;
  }

  public void setStatusToCompleted()
  {
    if (assignedTo != null)
    {
      assignedTo.addPoints(pointCost);
      status = OfferStatus.COMPLETED;
    }
  }
  public void setGeneralStatus(TradeOffer newOffer, OfferStatus status,Resident r){
    switch(status){
      case AVAILABLE :
        newOffer.setStatusToAvailable();
        break;
      case TAKEN:
        newOffer.setStatusToTaken(r);
        break;
      case CANCELLED:
        newOffer.setStatusToCancelled();
        break;
      case COMPLETED:
        newOffer.setStatusToCompleted();
        break;
      default:
        System.out.println("Wrong status");
    }

  }

  public Resident getOfferBy()
  {
    return offerBy;
  }

  public void setOfferBy(Resident offerBy)
  {
    this.offerBy = offerBy;
  }

  public Resident getAssignedTo()
  {
    return assignedTo;
  }

  public void setAssignedTo(Resident assignedTo)
  {
    this.assignedTo = assignedTo;
  }
  @Override
  public String toString() {
    return "TradeOffer{" +
        "ID='" + ID + '\'' +
        ", title='" + title + '\'' +
        ", type=" + type +
        ", description='" + description + '\'' +
        ", pointCost=" + pointCost +
        ", status=" + status +
        ", offerBy=" + (offerBy != null ? offerBy : "null") +
        ", assignedTo=" + (assignedTo != null ? assignedTo : "null") +
        ", date=" + (createDate != null ? createDate : "null") +
        '}';
  }

}
