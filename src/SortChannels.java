import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.lang.*;

/**
  *
  * description
  *
  * @version 1.0 from 20.01.2014
  * @author 
  */

public class SortChannels {
  // Anfang Attribute
  private JFrame SortChannels = new JFrame();
  private JList<String> jList1 = new JList();
  private DefaultListModel jList1Model = new DefaultListModel();
  private JScrollPane jList1ScrollPane = new JScrollPane(jList1);
  private JButton btup = new JButton();
  private JButton btok = new JButton();
  private JButton btdown = new JButton();
  private Dimension buttonDimension = new Dimension();
  private ArrayList<kanal> channels;
  
  private  TvElectronics bla;
  // Ende Attribute
  
  public SortChannels(ArrayList<kanal> channels, /*mainwindow*/) {
    this.channels = channels;
    this.bla = bla;
    
    SortChannels.setTitle("Sendersortierung");
    SortChannels.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) { SortChannels.dispose(); }
    });
    int frameWidth = 200; 
    int frameHeight = 400;
    SortChannels.setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - SortChannels.getSize().width) / 2;
    int y = (d.height - SortChannels.getSize().height) / 2;
    SortChannels.setLocation(x, y);
    GridBagConstraints c = new GridBagConstraints();
    SortChannels.setLayout(new GridBagLayout());
    // Anfang Komponenten
    
    jList1.setModel(jList1Model);
    c.ipadx = 200;
    c.gridx = 0;
    c.gridy = 0;
    c.weighty = 1;
    c.weightx = 1;
    c.fill = GridBagConstraints.BOTH;
    c.gridheight = GridBagConstraints.RELATIVE;
    SortChannels.add(jList1ScrollPane, c);
    
    JPanel pane = new JPanel();
    pane.setLayout(new GridBagLayout());
    GridBagConstraints cp = new GridBagConstraints();
    btup.setText("Nach Oben");
    btup.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btup_ActionPerformed(evt);
      }
    });
    cp.gridy = 0;
    cp.gridx = 0;
    //cp.ipadx = 75;
    cp.weighty = 0;
    cp.insets = new Insets(10,0,0,0);  //top padding
    cp.fill = GridBagConstraints.NONE;
    pane.add(btup, cp);
    
    btok.setText("Liste Bestätigen");
    btok.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btok_ActionPerformed(evt);
      }
    });
    cp.gridy = 1;
    pane.add(btok, cp);
    
    btdown.setText("Nach Unten");
    btdown.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        btdown_ActionPerformed(evt);
      }
    });
    cp.gridy = 2;
    pane.add(btdown, cp);
    
    c.anchor = GridBagConstraints.PAGE_END;
    c.gridx = 0;
    c.gridy = 1;
    c.weighty = 0;
    c.weightx = 0;
    c.fill = GridBagConstraints.NONE;
    pane.setSize(100, 75);
    SortChannels.add(pane, c);
    insertInList();
    // Ende Komponenten
    SortChannels.setResizable(true);
    SortChannels.setAlwaysOnTop(true);
    SortChannels.setVisible(true);
  }
  
  // Anfang Methoden 
  public ArrayList<kanal> getChannels(){
    return channels;
  }
  
  public void insertInList(){
    DefaultListModel listModel = new DefaultListModel();
    for (int i = channels.size() - 1; i >= 0; i--) {
      listModel.addElement(channels.get(i).getProgramm());
    } // end of for
    jList1.setModel(listModel);
  }
  
  public void btup_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einfügen
    try{
      kanal oben;
      DefaultListModel listModel = (DefaultListModel) jList1.getModel();
      int toSwap = jList1.getSelectedIndex();
      String upperElement, myElement;
      myElement = (String) listModel.getElementAt(toSwap);
      if (toSwap >= 1) {
        upperElement = (String) listModel.get(toSwap - 1);
        listModel.set(toSwap - 1,myElement);
        listModel.set(toSwap, upperElement);
        jList1.setSelectedIndex(toSwap - 1);
        
        oben = channels.get(toSwap - 1);
        channels.set(toSwap - 1, channels.get(toSwap));
        channels.set(toSwap, oben);
      }else{
        return;
      }
    }catch(ArrayIndexOutOfBoundsException e){
      
    }
  } // end of btup_ActionPerformed
  
  public void btok_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einfügen
    bla.setChannels(channels);                                  //###################### das wird das MainWindow
    SortChannels.dispose();
  } // end of btok_ActionPerformed
  
  public void btdown_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einfügen
    try{
      kanal oben;
      DefaultListModel listModel = (DefaultListModel) jList1.getModel();
      int toSwap = jList1.getSelectedIndex();
      String underElement, myElement;
      myElement = (String) listModel.getElementAt(toSwap);
      if (toSwap <= listModel.size() - 2) {
        underElement = (String) listModel.get(toSwap + 1);
        listModel.set(toSwap + 1,myElement);
        listModel.set(toSwap, underElement);
        jList1.setSelectedIndex(toSwap + 1);
        
        oben = channels.get(toSwap);
        channels.set(toSwap, channels.get(toSwap + 1));
        channels.set(toSwap + 1, oben);
      }else{
        return;
      }
    }catch(ArrayIndexOutOfBoundsException e){
      
    }
  } // end of btdown_ActionPerformed
  
  // Ende Methoden
  
  public static void main(String[] args) {
    TvElectronics tv = new TvElectronics();
    new SortChannels(tv.scanChannels(), tv);
  }
}