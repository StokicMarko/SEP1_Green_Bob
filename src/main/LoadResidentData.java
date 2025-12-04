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
      residentlist= MyFileHandler.readArrayFromTextFile("residents.txt");
      for(int i=0;i<residentlist.length;i++){
        String residentdetails=residentlist[i];
        String[] residentSplit= residentdetails.split(",");
        String id=residentSplit[0];
        String firstname= residentSplit[1];
        String lastname= residentSplit[2];
        int pointValue= Integer.parseInt(residentSplit[3]);
        String address= residentSplit[4];
        residents.add(new Resident(id,firstname,lastname,pointValue,address));

      }

    }
    catch(FileNotFoundException e){
      System.out.println("file not found");
    }
    try{
      MyFileHandler.writeToBinaryFile("Residentss.bin",residents);
    }
    catch(FileNotFoundException e){
      System.out.println("file not found");
    }
    catch(IOException e){
      System.out.println("File not opened");
    }
    System.out.println("done");

  }


}
