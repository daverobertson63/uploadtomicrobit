/**
 * Tool to upload and run sketches on BBC Microbits using MicroPython
 *
 * Copyright (c) The Processing Foundation 2016
 * Developed by Dave Robertson
 * Based on original upload to pi by Gottfried Haider
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 *
 * @author   Dave Robertson
 */

package dhr.uploadtomicrobit;

import processing.app.Base;
import processing.app.Mode;
import processing.app.Preferences;
import processing.app.Sketch;
import processing.app.tools.Tool;
import processing.app.ui.Editor;
import processing.app.ui.EditorConsole;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.*;

import javax.swing.JOptionPane;



// XXX: there doesn't seem to be a way to handle the use pressing the stop button
// XXX: implement method to retrieve Pi's serial number
// XXX: implement method to retrieve Pi's network IPs & MAC addresses


public class UploadToMicrobitTool implements Tool {

	Base base;
	Thread t;

	String microbitLocation;
	String firmwareLocation;
	String defaultFirmware;
	String username;
	String password;
	boolean persistent;
	boolean autostart;
	boolean logging;


	public String getMenuTitle() {
		return "Upload to BBC Microbit";
	}


	public void init(Base base) {
		this.base = base;

	}


	public void run() {

		final Editor editor = base.getActiveEditor();

		final String toolsFolder = base.getSketchbookToolsFolder().getAbsolutePath();
		final String sketchName = editor.getSketch().getName();
		final String sketchPath = editor.getSketch().getFolder().getAbsolutePath();

		Mode mode = editor.getMode();

		System.out.println("Mode is: " + mode.toString());
		
		if ( !mode.toString().equals("Python"))
		{
			
			JOptionPane.showMessageDialog(null, "This tool only works with Python Mode");
			return;
		}
		
		//System.out.println("Mode is: " + mode.getTitle());

		System.out.println("Sketchname is: " + sketchName);
		System.out.println("Sketchpath is: " + sketchPath);
		System.out.println("Tool Folder is: " + toolsFolder);

		defaultFirmware = toolsFolder + File.separator + "##project.name##" + File.separator + "tool" + File.separator + "firmware.hex";

		System.out.println("Default Microbit Firmware file: " + defaultFirmware);
		Sketch sketch = editor.getSketch();

		//JOptionPane.showMessageDialog(null, "Mode is:" +mode.getTitle() );
		loadPreferences();
		// saving the preferences adds them to the txt file for the user to edit
		savePreferences();


		// this assumes the working directory is home at the beginning of a ssh/sftp session
		// "~" didn't work (no such file)
		final String dest = (persistent) ? "." : "/tmp";

		// already running?
		if (t != null) {
			// terminate thread
			t.interrupt();
			try {
				// wait for it to finish
				t.join();
			} catch (InterruptedException e) {
				System.err.println("Error joining thread: " + e.getMessage());
			}
			t = null;
			// the thread should already have called this, but in case it didn't
			disconnect();
		}

		editor.getConsole().clear();
		//editor.statusError("Saving Sketch before uploading to MB");
		System.out.println("Welcome to Dave's Microbit Uploader");
		editor.statusNotice("Attempting to save the sketch before uploading");


		// this doesn't trigger the "Save as" dialog for unnamed sketches, but instead saves
		// them in the temporary location that is also used for compiling
		try {
			editor.getSketch().save();
		} catch (Exception e) {
			editor.statusError("Cannot save sketch");
			// DEBUG
			e.printStackTrace();
			System.err.println(e);
			return;
		}
		/*
    try {
      exportSketch();
    } catch (Exception e) {
      editor.statusError("Cannot export sketch");
      if (e instanceof InvocationTargetException) {
        System.err.println("Most likely caused by a syntax error. Press the Run button to get more information on where the problem lies.");
      } else {
        // DEBUG
        e.printStackTrace();
        System.err.println(e);
      }
      return;
    }
		 */
		t = new Thread(new Runnable() {
			public void run() {

				// Create new GUI and pass processing base

				GUI.GUIStart(base);
				// Do something in here


			}
		}, "Upload to Microbit");

		t.start();
	}


	public void addAutostart(String dest, String sketchName) throws IOException {
		return;

	}




	public void disconnect() {
		return;
	}


	public void exportSketch() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		Editor editor = base.getActiveEditor();
		Mode mode = editor.getMode();
		Sketch sketch = editor.getSketch();

		String oldSetting = Preferences.get("export.application.platform_linux");
		Preferences.set("export.application.platform_linux", "true");

		try {
			Method javaModeMethod = mode.getClass().getMethod("handleExportApplication", sketch.getClass());
			javaModeMethod.invoke(mode, sketch);
		} finally {
			Preferences.set("export.application.platform_linux", oldSetting);
		}
	}

	/*
	 * Load preferences - but make sure we have something in the locations to get started and populate the 
	 * preferences tab with something interesting
	 * 
	 * 
	 */

	private void loadPreferences() {

		// Start with location of microbit - use a Windows based mapping
		// TODO - add the Linux default location

		microbitLocation = Preferences.get("dhr.uploadtomicrobit.microbit");
		if (microbitLocation == null) {
			microbitLocation = "H:";
		}

		// This is where we ship the default firmware file
		firmwareLocation = Preferences.get("dhr.uploadtomicrobit.firmware");
		if (firmwareLocation == null) {
			firmwareLocation = defaultFirmware;
		}
	}


	public void removeAutostarts() throws IOException {

	}


	public void removeSketch(String dest, String sketchName) throws IOException {

	}


	public int runRemoteSketch(String dest, String sketchName) throws IOException {
		return 0;
	}


	public void savePreferences() {
		Preferences.set("dhr.uploadtomicrobit.microbit", microbitLocation);
		Preferences.set("dhr.uploadtomicrobit.firmware", firmwareLocation);

	}


	public void stopSketches() throws IOException {

	}


	public void syncDisks() throws IOException {

	}


	public void uploadSketch(String localDir, String dest, String sketchName) throws IOException {

	}
}