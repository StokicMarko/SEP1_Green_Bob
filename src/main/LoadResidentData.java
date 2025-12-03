package main;
import model.Resident;
import model.ResidentList;
import utils.MyFileHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadResidentData
{
  public static void main(String[] args)
  {
    ResidentList residents= new ResidentList();
    String[] residentlist= null;
    try{
      residentArray= MyFileHandler.readArrayFromTextFile("Residents.txt");
      for(int i=0;i<residentArray.length;i++){
        String residentdetails=residentArray[i];
        String[] residentSplit= residentdetails.split(",");

      }

    }

  }


}
