/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 28.11.2013
  * @author 
  */
import java.io.*;
import java.util.*;

public class Fehrnseher_Daten {
  
  // Anfang Attribute
  private int kanalAnzahl;
  private String kanalName;
  private String[] kanalReihenfolge;
  private int kanalAktuell;
  private int seitenverhaeltnis;
  private int volume;
  // Ende Attribute
  
  public Fehrnseher_Daten() {
    this.kanalAnzahl = 0;
    this.kanalName = "";
    this.kanalReihenfolge = null;
    this.kanalAktuell = 0;
    this.seitenverhaeltnis = 0;
    this.volume = 20;
    this.readFromFile();
  }

  // Anfang Methoden
  public String getKanalName() {
    return kanalName;
  }
  
  public void setKanalName(String kanalName) {
    this.kanalName = kanalName;
  }
  
  public int getKanalAnzahl() {
    return kanalAnzahl;
  }
  
  public void setKanalAnzahl(int kanalAnzahl) {
    this.kanalAnzahl = kanalAnzahl;
  }
  
  public String getKanalbyNumber(int kanalNr) {
    if (kanalNr >= 0 && kanalNr < this.kanalAnzahl) {
      return kanalReihenfolge[kanalNr];
    }else{
      return "NA";
    }
  }
  
  public void setKanalReihenfolge(String[] kanalReihenfolge) {
    this.kanalReihenfolge = kanalReihenfolge;
  }
  
  public int getKanalAktuell() {
    return kanalAktuell;
  }
  
  public void setKanalAktuell(int kanalAktuell) {
    this.kanalAktuell = kanalAktuell;
  }
  
  public int getSeitenverhaeltnis() {
    return seitenverhaeltnis;
  }
  
  public void setSeitenverhaeltnis(int seitenverhaeltnis) {
    this.seitenverhaeltnis = seitenverhaeltnis;
  }
  
  public int getVolume() {
    return volume;
  }
  
  public void setVolume(int volume) {
    this.volume = volume;
  }
  
  public void saveAsFile() {
    //TO DO hier müssen wir noch was machen ########################################################################
    int CONTENTCOUNT = 5;
    String[] content = new String[CONTENTCOUNT];
    content[0] = "" + this.kanalAnzahl;
    content[1] = "" + this.kanalName;
    content[2] = "" + this.kanalAktuell;
    content[3] = "" + this.seitenverhaeltnis;
    content[4] = "" + this.volume;
    
    try {
      File file = new File("FernseherDaten.csv");
      FileOutputStream fop = new FileOutputStream(file);
      
      // if file doesnt exists, then create it
      if (!file.exists()) {
        file.createNewFile();
      }
      
      // get the content in bytes
      for (int i = 0; i < CONTENTCOUNT ; i++) {
        byte[] contentInBytes = content[i].getBytes();
        fop.write(contentInBytes);
        fop.write(System.getProperty("line.separator").getBytes());
      } // end of for
      /*
      for (int i = 0; i < this.kanalAnzahl - 1; i++) {
        if (kanalReihenfolge[i] != "") {
          this.kanalReihenfolge[i] += ";";
          byte[] contentInBytes = this.kanalReihenfolge[i].getBytes();
          fop.write(contentInBytes);
        } // end of if
      } // end of for
      byte[] contentInBytes = this.kanalReihenfolge[this.kanalAnzahl - 1].getBytes();                               //verhindern das der Letzte Eintrag auch mit ; endet.
      fop.write(contentInBytes);
      */
      fop.flush();
      fop.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void readFromFile() {
    //TO DO hier müssen wir noch was machen ########################################################################
    ArrayList<String> lines = new ArrayList<String>();
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader("FernseherDaten.csv"))){
      while ((line = br.readLine()) != null) {
        lines.add(line);
      } // end of while
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    if (lines.size() >= 5) {
      this.kanalAnzahl = Integer.parseInt(lines.get(0));
      this.kanalName = lines.get(1);
      this.kanalAktuell = Integer.parseInt(lines.get(2));
      this.seitenverhaeltnis = Integer.parseInt(lines.get(3));
      this.volume = Integer.parseInt(lines.get(4));
      //this.kanalReihenfolge = lines.get(5).split(";");
    }// end of if   
  } 
}
  
  // Ende Methoden
 // end of Fehrnseher_Daten
