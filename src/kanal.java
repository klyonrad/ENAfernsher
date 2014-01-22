/**
  *
  * description
  *
  * @version 1.0 from 07.01.2014
  * @author 
  */

public class kanal {

  // Anfang Attribute
  private int frequenz;
  private String kanal;
  private int qualitaet;
  private String programm;
  private String anbieter;
  private String adress;
  // Ende Attribute
  
  public kanal(int frequenz, String kanal, int qualitaet, String programm, String anbieter) {
    this.frequenz = frequenz;
    this.kanal = kanal;
    this.qualitaet = qualitaet;
    this.programm = programm;
    this.anbieter = anbieter;
    this.adress = "/channelimages/0011ff.png&text=" + kanal + ".png";
  }
  
  // Anfang Methoden
  public String getAdress() {
    return adress;
  }
  
  public int getFrequenz() {
    return frequenz;
  }
  
  public void setFrequenz(int frequenz) {
    this.frequenz = frequenz;
  }
  
  public String getKanal() {
    return kanal;
  }
  
  public void setKanal(String kanal) {
    this.kanal = kanal;
  }
  
  public int getQualitaet() {
    return qualitaet;
  }
  
  public void setQualitaet(int qualitaet) {
    this.qualitaet = qualitaet;
  }
  
  public String getProgramm() {
    return programm;
  }
  
  public void setProgramm(String programm) {
    this.programm = programm;
  }
  
  public String getAnbieter() {
    return anbieter;
  }
  
  public void setAnbieter(String anbieter) {
    this.anbieter = anbieter;
  }
  
  // Ende Methoden
  
}
