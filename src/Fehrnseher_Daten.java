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

  private int kanalAnzahl;
  private String kanalName;
  private String[] kanalReihenfolge; // not used
  private int kanalAktuell;
  private int seitenverhaeltnis;
  private int volume;
  private ArrayList<kanal> channellist;

  public Fehrnseher_Daten() {
    this.kanalAnzahl = 0;
    this.kanalName = "";
    this.kanalReihenfolge = null;
    this.kanalAktuell = 0;
    this.seitenverhaeltnis = 0;
    this.volume = 20;
    channellist = new ArrayList<kanal>(); // without this, there would be a null pointer exception in readfromFile
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
    } else {
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

  public ArrayList<kanal> getChannellist() {
    return channellist;
  }

  public void setChannellist(ArrayList<kanal> channellist) {
    this.channellist = channellist;
  }

  public void saveAsFile() {
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
      for (int i = 0; i < CONTENTCOUNT; i++) {
        byte[] contentInBytes = content[i].getBytes();
        fop.write(contentInBytes);
        fop.write(System.getProperty("line.separator").getBytes());
      } // end of for
      
      fop.flush();
      fop.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // write channellist
    try {
      File file = new File("channellist.csv");
      FileOutputStream fop = new FileOutputStream(file);
      // if file doesnt exists, then create it
      if (!file.exists()) {
        file.createNewFile();
      }

      for (int i = 0; i < channellist.size(); i++) {
        int KANALCONTENTCOUNT = 5;
        String[] kanalContent = new String[KANALCONTENTCOUNT];
        kanalContent[0] = ""
            + Integer.toString(channellist.get(i).getFrequenz())
            + ";";
        kanalContent[1] = "" + channellist.get(i).getKanal() + ";";
        kanalContent[2] = ""
            + Integer.toString(channellist.get(i).getQualitaet())
            + ";";
        kanalContent[3] = "" + channellist.get(i).getProgramm() + ";";
        kanalContent[4] = "" + channellist.get(i).getAnbieter();

        // get the content in bytes
        for (int j = 0; j < KANALCONTENTCOUNT; j++) { // write one
                                // channel
          byte[] contentInBytes = kanalContent[j].getBytes();
          fop.write(contentInBytes);
        } // end of for
        fop.write(System.getProperty("line.separator").getBytes());
      }
      fop.flush();
      fop.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void readFromFile() {
    ArrayList<String> lines = new ArrayList<String>();
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader(
        "FernseherDaten.csv"))) {
      while ((line = br.readLine()) != null) {
        lines.add(line);
      } // end of while

      if (lines.size() >= 5) {
        this.kanalAnzahl = Integer.parseInt(lines.get(0));
        this.kanalName = lines.get(1);
        this.kanalAktuell = Integer.parseInt(lines.get(2));
        this.seitenverhaeltnis = Integer.parseInt(lines.get(3)); 
        this.volume = Integer.parseInt(lines.get(4));
      }// end of if
    } catch (IOException e) {
      e.printStackTrace();
    }
    lines.clear(); /*
             * so that we can fill stat ArrayList again with the
             * channellist. omfg, took me a lot of time to catch
             * that problem
             */

    // read channellist:
    // copied from TvElectronics.scanChannels():
    ArrayList<String> lines2 = new ArrayList<String>();
    String line2;
    String[] Channel;
    try (BufferedReader br = new BufferedReader(new FileReader(
        "channellist.csv"))) {
      while ((line2 = br.readLine()) != null) {
        lines2.add(line2);
      } // end of while
    } catch (IOException e) {
      e.printStackTrace();
    }
    int frequenz, qualitaet;
    String kanal, programm, anbieter;
    for (int i = 0; i < lines2.size(); i++) {
      Channel = lines2.get(i).split(";");
      frequenz = Integer.parseInt(Channel[0]);
      kanal = Channel[1];
      qualitaet = Integer.parseInt(Channel[2]);
      programm = Channel[3];
      anbieter = Channel[4];
      channellist.add(new kanal(frequenz, kanal, qualitaet, programm, anbieter));
    } // end of for

  }
}
