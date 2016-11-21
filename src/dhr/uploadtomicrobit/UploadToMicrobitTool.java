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

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.UserAuthException;


// XXX: there doesn't seem to be a way to handle the use pressing the stop button
// XXX: implement method to retrieve Pi's serial number
// XXX: implement method to retrieve Pi's network IPs & MAC addresses


public class UploadToMicrobitTool implements Tool {
  Base base;
  Thread t;

  String hostname;
  String username;
  String password;
  boolean persistent;
  boolean autostart;
  boolean logging;


  public String getMenuTitle() {
    return "Upload to Microbit";
  }


  public void init(Base base) {
    this.base = base;
    loadPreferences();
    // saving the preferences adds them to the txt file for the user to edit
    savePreferences();
  }


  public void run() {
	  
    final Editor editor = base.getActiveEditor();
    final String sketchName = editor.getSketch().getName();
    final String sketchPath = editor.getSketch().getFolder().getAbsolutePath();
    
    Mode mode = editor.getMode();
    
    System.out.println("Mode is: " + mode.toString());
    System.out.println("Mode is: " + mode.getTitle());
    Sketch sketch = editor.getSketch();

    JOptionPane.showMessageDialog(null, "Mode is:" +mode.getTitle() );
    
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
    	  
    	  // Do something in here

    
      }
    }, "Upload to Microbit");

    t.start();
  }


  public void addAutostart(String dest, String sketchName) throws IOException {
	  return;
	  
  }


  public static SSHClient connect(String host, String username, String password) throws IOException, TransportException, UserAuthException {
        return null;
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


  private void loadPreferences() {
    hostname = Preferences.get("gohai.uploadtopi.hostname");
    if (hostname == null) {
      hostname = "raspberrypi.local";
    }
    username = Preferences.get("gohai.uploadtopi.username");
    if (username == null) {
      username = "pi";
    }
    password = Preferences.get("gohai.uploadtopi.password");
    if (password == null) {
      password = "raspberry";
    }
    String tmp = Preferences.get("gohai.uploadtopi.persistent");
    if (tmp == null) {
      persistent = true;
    } else {
      persistent = Boolean.parseBoolean(tmp);
    }
    tmp = Preferences.get("gohai.uploadtopi.autostart");
    if (tmp == null) {
      autostart = true;
    } else {
      autostart = Boolean.parseBoolean(tmp);
    }
    tmp = Preferences.get("gohai.uploadtopi.logging");
    if (tmp == null) {
      logging = true;
    } else {
      logging = Boolean.parseBoolean(tmp);
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
    Preferences.set("gohai.uploadtopi.hostname", hostname);
    Preferences.set("gohai.uploadtopi.username", username);
    Preferences.set("gohai.uploadtopi.password", password);
    Preferences.setBoolean("gohai.uploadtopi.persistent", persistent);
    Preferences.setBoolean("gohai.uploadtopi.autostart", autostart);
    Preferences.setBoolean("gohai.uploadtopi.logging", logging);
  }


  public void stopSketches() throws IOException {
  
  }


  public void syncDisks() throws IOException {
  
  }


  public void uploadSketch(String localDir, String dest, String sketchName) throws IOException {
  
  }
}