package model;
import java.util.ArrayList;
import java.util.Comparator;

import myEnum.OfferStatus;

public class TradeOfferList
{
  private ArrayList<TradeOffer> tradeOffers = new ArrayList<>();

  public void add(TradeOffer tradeOffer)
  {
    tradeOffers.add(tradeOffer);
  }

  public void removeByID(String id)
  {
    TradeOffer remove = findByID(id);
    if (remove != null)
    {
      tradeOffers.remove(remove);
    }
  }

  public ArrayList<TradeOffer> getAvailable()
  {
    ArrayList<TradeOffer> Offers = new ArrayList<>();
    for (int i = 0; i < tradeOffers.size(); i++)
    {
      if (tradeOffers.get(i).getStatus() == OfferStatus.AVAILABLE)
      {
        Offers.add(tradeOffers.get(i));
      }
    }
    return Offers;
  }

  public ArrayList<TradeOffer> getSortedByDate()
  {
    tradeOffers.sort(Comparator.comparing(TradeOffer::getCreateDate));
    return tradeOffers;
  }

  public String toString() {
    String str = "";
    for (int i = 0; i < tradeOffers.size(); i++) {
      str += tradeOffers.get(i); // calls TradeOffer.toString() automatically
      str += "\n";               // new line after each TradeOffer
    }
    return str;
  }






  public TradeOffer findByID(String id) {
    return tradeOffers.stream()
        .filter(tradeOffer -> tradeOffer.getID().equals(id))
        .findFirst()
        .orElse(null);
  }
}
