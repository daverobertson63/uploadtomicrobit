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
import javax.swing.border.EtchedBorder;
import javax.swing.JFileChooser;

import processing.app.Base;
import processing.app.Mode;
import processing.app.Preferences;
import processing.app.Sketch;
import processing.app.tools.Tool;
import processing.app.ui.Editor;
import processing.app.ui.EditorConsole;

public class GUI {

	private JFrame frmMicrobit;
	private DefaultListModel wbListNames = new  DefaultListModel();
	private JTextField microbitLocation;
	private JTextField firmwareLocation;
	private Editor editor;
	private String sketchName;
	private String sketchPath;
	
	Base processingBase;
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
	
		initialize();
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

		JButton btnNewButton_1 = new JButton("Upload");
		
		btnNewButton_1.putClientProperty( "sketchname", this.sketchName );
		btnNewButton_1.putClientProperty( "sketchpath", this.sketchPath );
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Update the index
				
				JOptionPane.showMessageDialog(null, "Now to upload to Microbit");
				
				String sketchName = (String)((JButton)arg0.getSource()).getClientProperty( "sketchname" );
				String sketchPath = (String)((JButton)arg0.getSource()).getClientProperty( "sketchpath" );

				System.out.println("In Upload Action Listener");
				
				System.out.println("Sketchname is: " + sketchName);
			    System.out.println("Sketchpath is: " + sketchPath);

			    System.out.println("Base Name is: " + processingBase.toString());
			    
				if (microbitLocation.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please select a location for the Microbit");
					return;
				}
				int option = JOptionPane.showConfirmDialog(null, "Ready to Run update ?", "Update", JOptionPane.YES_NO_OPTION);

				if (option == 0) { //The ISSUE is here
					System.out.print("Yes");
					// Copy the current sketch after hexlify


				} else {
					System.out.print("No");
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
		
		btnNewButton_4.setBounds(388, 84, 125, 42);
		frmMicrobit.getContentPane().add(btnNewButton_4);

		microbitLocation = new JTextField();
		microbitLocation.setText("Select");
		microbitLocation.setBounds(163, 12, 530, 20);
		frmMicrobit.getContentPane().add(microbitLocation);
		microbitLocation.setColumns(10);

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
		btnFirmwareLocation.setBounds(28, 45, 125, 23);
		frmMicrobit.getContentPane().add(btnFirmwareLocation);

		firmwareLocation = new JTextField();
		firmwareLocation.setText("Select");
		firmwareLocation.setColumns(10);
		firmwareLocation.setBounds(163, 46, 530, 20);
		frmMicrobit.getContentPane().add(firmwareLocation);
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
