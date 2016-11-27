package dhr.uploadtomicrobit;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

import javax.swing.border.EtchedBorder;
import javax.swing.JFileChooser;

import processing.app.Base;
import processing.app.Mode;
import processing.app.Preferences;
import processing.app.Sketch;
import processing.app.tools.Tool;
import processing.app.ui.Editor;
import processing.app.ui.EditorConsole;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GUI {

	private JFrame frmMicrobit;
	private DefaultListModel wbListNames = new  DefaultListModel();
	private JTextField microbitLocation;
	private JTextField firmwareLocation;
	private Editor editor;
	private String sketchName;
	private String sketchPath;
	String userFirmware;
	String microbitpath;

	Base processingBase;
	private File firmwareFile;
	/**
	 * Launch the application.
	 */
	public static void GUIStart(Base base) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI(base);
					window.frmMicrobit.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI(Base base) {

		this.processingBase = base;

		editor = processingBase.getActiveEditor();

		sketchName = editor.getSketch().getName();
		sketchPath = editor.getSketch().getFolder().getAbsolutePath();

		// Now get preferences I hope




		/*
		try {
			//String str = FileUtils.readFileToString(firmwarefile, "UTF-8");
			//System.out.println(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Put this into the firmware location if it exists
		 */



		initialize();

		userFirmware = Preferences.get("dhr.uploadtomicrobit.firmware");
		microbitpath = Preferences.get("dhr.uploadtomicrobit.microbit");

		microbitLocation.setText(microbitpath);
		firmwareLocation.setText(userFirmware);

		//System.out.println(userFirmware);

		//File firmwarefile = new File(userFirmware);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMicrobit = new JFrame();
		frmMicrobit.setTitle("Microbit MicroPython Uploader");
		frmMicrobit.setBounds(100, 100, 731, 204);

		frmMicrobit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frmMicrobit.getContentPane().setLayout(null);
		//loadWBList(wbListNames );

		JButton btnNewButton_1 = new JButton("Upload Sketch");

		btnNewButton_1.putClientProperty( "sketchname", this.sketchName );
		btnNewButton_1.putClientProperty( "sketchpath", this.sketchPath );

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Update the index

				int option = JOptionPane.showConfirmDialog(null, "Ready to push to microbit ?", "Update", JOptionPane.YES_NO_OPTION);

				if (option != 0) { //The ISSUE is here
					return;
				}			

				String sketchName = (String)((JButton)arg0.getSource()).getClientProperty( "sketchname" );
				String sketchPath = (String)((JButton)arg0.getSource()).getClientProperty( "sketchpath" );

				System.out.println("In Upload Action Listener");

				System.out.println("Sketchname is: " + sketchName);
				System.out.println("Sketchpath is: " + sketchPath);

				System.out.println("Base Name is: " + processingBase.toString());
				editor = processingBase.getActiveEditor();

				sketchName = editor.getSketch().getName();
				sketchPath = editor.getSketch().getFolder().getAbsolutePath();
				String sketchMainPath = editor.getSketch().getMainFilePath();

				System.out.println("sketchMainPath is: " + sketchMainPath);

				File sketchFile = new File(sketchMainPath);
				FirmwareGenerator fg = new FirmwareGenerator();
				File firmware = new File(sketchPath + File.separator +"firmware.hex");
				File microbit = new File(microbitLocation.getText() + File.separator + "firmware.hex");

				String sketch;
				
				try {
					
					sketch = FileUtils.readFileToString(sketchFile,"UTF-8");
					
					String output = fg.generateFirmware(sketch);
					
					FileUtils.writeStringToFile(firmware,output.toString(),Charset.forName("ISO-8859-1"));
					
					// Copy the file to the location
					FileUtils.copyFile(firmware, microbit);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}













			}
		});
		btnNewButton_1.setBounds(234, 84, 125, 42);
		frmMicrobit.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_4 = new JButton("Cancel");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Cancel!");
				frmMicrobit.setVisible(false); //you can't see me!
				frmMicrobit.dispose(); //Destroy the JFrame object

			}
		});

		btnNewButton_4.setBounds(568, 84, 125, 42);
		frmMicrobit.getContentPane().add(btnNewButton_4);

		microbitLocation = new JTextField();
		microbitLocation.setText("Select");
		microbitLocation.setBounds(163, 12, 530, 20);
		frmMicrobit.getContentPane().add(microbitLocation);
		microbitLocation.setColumns(10);

		/*
		 * Microbit Location
		 * 
		 */

		JButton btnNewButton_6 = new JButton("Microbit Location");
		btnNewButton_6.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int returnVal;

				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Excel files", "xlsx");
				fileChooser.setFileFilter(filter);

				File workingDirectory = new File(System.getProperty("user.dir"));
				fileChooser.setCurrentDirectory(workingDirectory);

				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);


				returnVal = fileChooser.showOpenDialog(frmMicrobit);


				if(returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this location: " +
							fileChooser.getSelectedFile().getName());
					microbitLocation.setText(new String(fileChooser.getSelectedFile().getName()));

				}
				fileChooser = null;


			}
		});
		btnNewButton_6.setBounds(28, 11, 125, 23);
		frmMicrobit.getContentPane().add(btnNewButton_6);

		JButton btnFirmwareLocation = new JButton("Firmware location");
		btnFirmwareLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// This is the firmware location - look for a Hex file ( .hex)

				int returnVal;

				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"HEX files", "hex");
				fileChooser.setFileFilter(filter);

				File workingDirectory = new File(System.getProperty("user.dir"));
				fileChooser.setCurrentDirectory(workingDirectory);

				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				returnVal = fileChooser.showOpenDialog(frmMicrobit);


				if(returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose this firmware file: " +
							fileChooser.getSelectedFile().getName());

					firmwareLocation.setText(new String(fileChooser.getSelectedFile().getAbsolutePath()));
				}
				fileChooser = null;




			}
		});
		btnFirmwareLocation.setBounds(28, 45, 125, 23);
		frmMicrobit.getContentPane().add(btnFirmwareLocation);

		firmwareLocation = new JTextField();
		firmwareLocation.setText("Select");
		firmwareLocation.setColumns(10);
		firmwareLocation.setBounds(163, 46, 530, 20);
		frmMicrobit.getContentPane().add(firmwareLocation);

		JButton btnSaveSettings = new JButton("Save Settings");
		btnSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Save Setttings

				// Save to prefernces folder
				Preferences.set("dhr.uploadtomicrobit.microbit", microbitLocation.getText());
				Preferences.set("dhr.uploadtomicrobit.firmware", firmwareLocation.getText());


			}
		});
		btnSaveSettings.setBounds(369, 84, 125, 42);
		frmMicrobit.getContentPane().add(btnSaveSettings);
	}

	private void loadWBList(DefaultListModel wbListNames )
	{
		List<Integer> list = new ArrayList<Integer>();
		File file = new File("MasterWB.txt");
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			while ((text = reader.readLine()) != null) {
				wbListNames.addElement(text);
				//list.add(Integer.parseInt(text));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}

		//print out the list
		System.out.println(list);


	}
}
