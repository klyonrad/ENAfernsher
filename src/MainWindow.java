import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.LineBorder;

public class MainWindow {

	private JFrame frame;
	private ImageIcon ViewAreaImage;
	private JPanel viewArea;
	private JPanel pipPanel;
	private JPanel pbtoverlay;
	private JPanel settingsPanel;

	private TvElectronics myTvElectronics;
	private ArrayList<kanal> channellist;
	private Fehrnseher_Daten fd;
	private boolean pip = false;
	private JComboBox cbchannels;
	private JComboBox cbframebounds;
	private JSlider slider;
	private String deselectedFramebound;
	private long timeStart;
	private boolean settingsButtonPressed;

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

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1320, 760);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		pbtoverlay = new JPanel();

		/* Event handlers for the overlay: */
		pbtoverlay.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				pbtoverlay.setVisible(true);
			}
		});
		pbtoverlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				pbtoverlay.setVisible(true);
			}
		});

		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				pbtoverlay.setVisible(true);
			}
		});
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent arg0) {
				pbtoverlay.setVisible(false);
			}
		});

		pbtoverlay.setBounds(495, 509, 256, 183);
		frame.getContentPane().add(pbtoverlay);
		pbtoverlay.setBackground(Color.green);
		pbtoverlay.setLayout(null);

		JPanel pnlViewArea = new JPanel();
		pnlViewArea.setBounds(10, 11, 1294, 720);
		frame.getContentPane().add(pnlViewArea);
		pnlViewArea.setLayout(null);

		settingsPanel = new JPanel();
		settingsPanel.setBorder(UIManager.getBorder("OptionPane.border"));
		settingsPanel.setBackground(Color.ORANGE);
		settingsPanel.setBounds(368, 244, 492, 253);
		pnlViewArea.add(settingsPanel);
		GridBagLayout gbl_settingsPanel = new GridBagLayout();
		gbl_settingsPanel.columnWidths = new int[] { 214, 63, 0 };
		gbl_settingsPanel.rowHeights = new int[] { 23, 0 };
		gbl_settingsPanel.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_settingsPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		settingsPanel.setLayout(gbl_settingsPanel);
		settingsPanel.setVisible(false);
		settingsButtonPressed = false;

		JButton btnBlaBla = new JButton("bla bla");
		GridBagConstraints gbc_btnBlaBla = new GridBagConstraints();
		gbc_btnBlaBla.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnBlaBla.gridx = 1;
		gbc_btnBlaBla.gridy = 0;
		settingsPanel.add(btnBlaBla, gbc_btnBlaBla);

		final JLabel lblViewArea = new JLabel("", ViewAreaImage, JLabel.CENTER);
		lblViewArea.setBounds(0, 0, 1294, 720);
		pnlViewArea.add(lblViewArea);
		lblViewArea.setIcon(new ImageIcon(MainWindow.class
				.getResource("/images/downformaintenance.png")));

		final JPanel pipPanel = new JPanel();
		pipPanel.setBounds(953, 11, 351, 241);
		pipPanel.setVisible(true);
		frame.getContentPane().add(pipPanel);

		final JLabel lblPip = new JLabel("");
		lblPip.setIcon(new ImageIcon(MainWindow.class
				.getResource("/images/testImage.jpg")));
		lblPip.setBorder(new LineBorder(Color.WHITE, 1));
		pipPanel.add(lblPip);
		lblPip.setVisible(true);

		myTvElectronics = new TvElectronics(pnlViewArea, pipPanel, lblViewArea);
		channellist = myTvElectronics.scanChannels();

		JButton btchannelup = new JButton("▲");
		btchannelup.setBounds(10, 81, 89, 23);
		pbtoverlay.add(btchannelup);

		JButton btchanneldown = new JButton("▼");
		btchanneldown.setBounds(10, 149, 89, 23);
		pbtoverlay.add(btchanneldown);

		cbchannels = new JComboBox();
		cbchannels.setBounds(10, 115, 89, 23);
		for (int i = 0; i < channellist.size(); i++) {
			cbchannels.addItem(channellist.get(i).getProgramm());
		} // end of for
		pbtoverlay.add(cbchannels);

		slider = new JSlider(JSlider.VERTICAL, 0, 100, 20);
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

		cbframebounds = new JComboBox();

		cbframebounds.setBounds(151, 116, 86, 20);
		cbframebounds.addItem("16:9");
		cbframebounds.addItem("4:3");
		cbframebounds.addItem("2,35:1");
		pbtoverlay.add(cbframebounds);

		/* Event handlers for the buttons: */

		btchannelup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					try {
						myTvElectronics.recordTimeShift(false);
						btplaypause.setText("||");
					} catch (Exception ignoreCase) {
					}
					cbframebounds.setSelectedIndex(0);
					if (cbchannels.getItemCount() > 0
							&& cbchannels.getSelectedIndex() > 0) {
						cbchannels.setSelectedIndex(cbchannels
								.getSelectedIndex() - 1);
					} else if (cbchannels.getItemCount() > 0
							&& cbchannels.getSelectedIndex() == 0) {
						cbchannels.setSelectedIndex(cbchannels.getItemCount() - 1);
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
					try {
						myTvElectronics.recordTimeShift(false);
						btplaypause.setText("||");
					} catch (Exception ignoreCase) {
					}
					cbframebounds.setSelectedIndex(0);
					if (cbchannels.getItemCount() > 0
							&& cbchannels.getSelectedIndex() < cbchannels
									.getItemCount() - 1) {
						cbchannels.setSelectedIndex(cbchannels
								.getSelectedIndex() + 1);
					} else if (cbchannels.getItemCount() > 0
							&& cbchannels.getSelectedIndex() == cbchannels
									.getItemCount() - 1) {
						cbchannels.setSelectedIndex(0);
					} // end of if
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		cbchannels.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int number = cbchannels.getSelectedIndex();
					try {
						myTvElectronics.setChannel(channellist.get(number)
								.getKanal(), false);
						try {
							myTvElectronics.recordTimeShift(false);
							btplaypause.setText("||");
						} catch (Exception ignoreCase) {
						}
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				} // end of if
			}
		});

		cbframebounds.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int number = cbframebounds.getSelectedIndex();
					try {
						if (cbframebounds.getSelectedIndex() != 0
								&& deselectedFramebound.equals("4:3") == false
								&& deselectedFramebound.equals("2,35:1") == false) {
							myTvElectronics.setZoom(true);
						} else if (cbframebounds.getSelectedIndex() == 0) {
							myTvElectronics.setZoom(false);
						} else {
							return;
						} // end of if
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				} else {
					deselectedFramebound = e.getItem().toString();
				}
			}
		});

		btpip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (pip == true) {
					myTvElectronics.setPictureInPicture(false);
					frame.getContentPane().validate();
					pip = false;
				} else {
					// lblPip.setIcon(lblViewArea.getIcon());
					myTvElectronics.setPictureInPicture(true);
					frame.getContentPane().validate();
					pip = true;
				}
			}
		});

		btplaypause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btplaypause.getText() == "►") {
					btplaypause.setText("||");
					try {
						myTvElectronics.playTimeShift(true,
								(int) (myTvElectronics.now() - timeStart));
						myTvElectronics.recordTimeShift(false);
					} catch (Exception ex) {
						System.err.println(ex);
					}
				} else {
					btplaypause.setText("►");
					try {
						myTvElectronics.recordTimeShift(true);
						timeStart = myTvElectronics.now();
					} catch (Exception ex) {
						System.err.println(ex);
					}
				}

			}
		});

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				try {
					myTvElectronics.setVolume(((JSlider) ce.getSource())
							.getValue());
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		});

		btoption.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (settingsButtonPressed == false)
					settingsPanel.setVisible(true);
				else if (settingsButtonPressed == true)
					settingsPanel.setVisible(false);
				settingsButtonPressed = !settingsButtonPressed;
			}
		});

		// load & save data:
		fd = new Fehrnseher_Daten();

		try {
			// System.out.println("blb blo");
			fd.readFromFile();
			System.out.println(fd.getVolume());

			if (cbchannels.getItemAt(fd.getKanalAktuell()).toString()
					.equals(fd.getKanalName())) {
				if (fd.getKanalAktuell() == 0) {
					myTvElectronics.setChannel(channellist.get(0).getKanal(),
							false);
				} else {
					cbchannels.setSelectedIndex(fd.getKanalAktuell());
				}
				System.out.println("Set channel to " + fd.getKanalAktuell());
			} else {
				int index = 0;
				while (index < cbchannels.getItemCount()
						|| cbchannels.getItemAt(index).toString()
								.equals(fd.getKanalName()) == true) {
					if (cbchannels.getItemAt(index).equals(fd.getKanalName())) {
						cbchannels.setSelectedIndex(index);
						System.out.println("Set channel to " + index);
					}
					index++;
					if (index == cbchannels.getItemCount() || index == 0) {
						myTvElectronics.setChannel(channellist.get(0)
								.getKanal(), false);
					}
				}
			}

			slider.setValue(fd.getVolume());
			cbframebounds.setSelectedIndex(fd.getSeitenverhaeltnis());
		} catch (Exception e) {
			System.err.println(e);
		}

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					saveData();
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		});

	}

	private void saveData() {
		fd.setKanalAktuell(cbchannels.getSelectedIndex());
		fd.setKanalAnzahl(cbchannels.getComponentCount());
		fd.setKanalName(cbchannels.getSelectedItem().toString());
		fd.setSeitenverhaeltnis(cbframebounds.getSelectedIndex());
		fd.setVolume(slider.getValue());
		fd.saveAsFile();
	}
}
