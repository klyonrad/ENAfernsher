/**
  *
  * description
  *
  * @version 1.0 from 07.01.2014
  * @author
  */
import java.io.*;
import java.util.*;

public class filetest {
  public static void main(String[] args) {
    TvElectronics tn = new TvElectronics();
    ArrayList<kanal> tmp;
    tmp = tn.scanChannels();
    System.out.println(tmp.get(2).getProgramm());
  }
}
