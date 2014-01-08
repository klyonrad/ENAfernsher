import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import javax.swing.*;


public class MainWindow {
  
  private JFrame frame;
  private ImageIcon ViewAreaImage;
  private JPanel viewArea;
  private JPanel pipPanel;
  private TvElectronics myTvElectronics;
  private ArrayList<kanal> channellist;
  
  /**
  * Launch the application.
  */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          MainWindow window = new MainWindow();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  
  /**
  * Create the application.
  */
  public MainWindow() {
    initialize();
  }
  
  static private String selectedString(ItemSelectable is) {
    Object selected[] = is.getSelectedObjects();
    return ((selected.length == 0) ? "null" : (String) selected[0]);
  }
  
  /**
  * Initialize the contents of the frame.
  */
  
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 1320, 760);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    frame.setResizable(false);
    //  JSeparator separator = new JSeparator();
    //  separator.setBounds(10, 500, 1244, 10);
    //  frame.getContentPane().add(separator);
    
    
    //ViewAreaImage = new ImageIcon(ImageIO.read(MainWindow.class.getResourceAsStream("images/testImage.jpg") ) );
    //ViewAreaImage = new ImageIcon(ImageIO.read(new File("images/downformaintenance.png")));
    //  ViewAreaImage = new ImageIcon("testImage.jpg");
    
    
    
    final Panel pbtoverlay = new Panel();
    /*pbtoverlay.addMouseMotionListener(new MouseMotionAdapter() {
    @Override
    public void mouseMoved(MouseEvent e) {
    pbtoverlay.setVisible(true);
    }
    });
    pbtoverlay.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseExited(MouseEvent e) {
    pbtoverlay.setVisible(false);
    }
    });
    */
    pbtoverlay.setBounds(495, 509, 256, 183);
    frame.getContentPane().add(pbtoverlay);
    pbtoverlay.setBackground(Color.green);
    pbtoverlay.setLayout(null);
    
    JPanel pnlViewArea = new JPanel();
    pnlViewArea.setBounds(10, 11, 1294, 720);
    frame.getContentPane().add(pnlViewArea);
    pnlViewArea.setLayout(null);
    
    JLabel lblViewArea = new JLabel("", ViewAreaImage, JLabel.CENTER);
    lblViewArea.setBounds(0, 0, 1294, 720);
    pnlViewArea.add(lblViewArea);
    
    
    lblViewArea.setIcon(new ImageIcon(MainWindow.class.getResource("/images/downformaintenance.png")));
    
    
    myTvElectronics = new TvElectronics(pnlViewArea, pnlViewArea, lblViewArea);
    channellist = myTvElectronics.scanChannels();
    JButton btchannelup = new JButton("▲");
    
    btchannelup.setBounds(10, 81, 89, 23);
    pbtoverlay.add(btchannelup);
    
    JButton btchanneldown = new JButton("▼");
    btchanneldown.setBounds(10, 149, 89, 23);
    pbtoverlay.add(btchanneldown);
    
    final JComboBox cbchannels = new JComboBox();
    cbchannels.setBounds(10, 115, 89, 23);
    for (int i = 0; i < channellist.size(); i++) {
      cbchannels.addItem(channellist.get(i).getProgramm());
    } // end of for
    pbtoverlay.add(cbchannels);
    
    JSlider slider = new JSlider(JSlider.VERTICAL,0,100,20);
    slider.setBounds(109, 81, 32, 91);
    pbtoverlay.add(slider);
    
    final JButton btoption = new JButton("Settings");
    btoption.setBounds(81, 47, 89, 23);
    pbtoverlay.add(btoption);
    
    final JButton btpip = new JButton("PiP");
    btpip.setBounds(151, 81, 89, 23);
    pbtoverlay.add(btpip);
    
    final JButton btplaypause = new JButton("||");
    btplaypause.setBounds(151, 149, 89, 23);
    pbtoverlay.add(btplaypause);
    
    final JComboBox cbframebounds = new JComboBox();
    cbframebounds.addItemListener(new ItemListener() {    	
    	public void itemStateChanged(ItemEvent arg0) {
    		if (arg0.getStateChange() == ItemEvent.SELECTED) {
    			int number = cbframebounds.getSelectedIndex();
    			try {
    			if (number == -1)
    				return;
    			if (number == 1)
    				myTvElectronics.setZoom(false, ViewAreaImage);
    			else
    				myTvElectronics.setZoom(true, ViewAreaImage);
    			} catch (Exception ex) {
    				ex.printStackTrace();
    			}
    		} // end of if
    	} 
    });
    cbframebounds.setBounds(151, 116, 86, 20);
    cbframebounds.addItem("16:9");
    cbframebounds.addItem("4:3");
    cbframebounds.addItem("2,35:1");
    pbtoverlay.add(cbframebounds);
    
    final JPanel pipPanel = new JPanel();
    pipPanel.setBounds(953, 11, 351, 241);
    pipPanel.setVisible(false);
    frame.getContentPane().add(pipPanel);
    
    JLabel lblPip = new JLabel("pip");
    lblPip.setIcon(new ImageIcon(MainWindow.class.getResource("/images/testImage.jpg")));
    pipPanel.add(lblPip);
    
    /*Eventhandler*/
    
    btchannelup.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent arg0) {
        try {
          if (cbchannels.getItemCount() > 0 && cbchannels.getSelectedIndex() > 0) {
            cbchannels.setSelectedIndex(cbchannels.getSelectedIndex() - 1);
          } // end of if
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }                                                                                                                                               
    });
    
    btchanneldown.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent argv1) {
        try {
          if (cbchannels.getItemCount() > 0 && cbchannels.getSelectedIndex() < cbchannels.getItemCount() - 1) {
            cbchannels.setSelectedIndex(cbchannels.getSelectedIndex() + 1);
          } // end of if
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }                                                                                                                                               
    });
    
    cbchannels.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent e){
          {
          int number = cbchannels.getSelectedIndex();
          try {
            myTvElectronics.setChannel(channellist.get(number).getKanal(), false);
          } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
          }
        } // end of if
      }
    });
    
    btpip.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (pipPanel.isVisible() == false)
        myTvElectronics.setPictureInPicture(true);
        else if (pipPanel.isVisible() == true)
        myTvElectronics.setPictureInPicture(false);
      }
    });
    
    btplaypause.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if(btplaypause.getText() == "►"){
          btplaypause.setText("||");
        }
        else{
          btplaypause.setText("►");
        }
        
      }
    });
    
    /*JPanel pnlViewArea = new JPanel ();
    pnlViewArea.setLocation(92, 5);
    pnlViewArea.setSize(500, 300);
    pnlViewArea.setBounds(92, 0, 1, 1);
    
    pnlViewArea.setLocation(92, 0);
    pnlViewArea.setSize(500, 500);
    pnlViewArea.setLayout(null); */
    
    
    
    
    
  }
}
