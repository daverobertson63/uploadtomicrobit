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
 * 
 * This will return a static version of the firmware - could be in a file - this is a bit easier for now
 * 
 */


package dhr.uploadtomicrobit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import processing.app.Base;
import processing.app.Mode;
import processing.app.Preferences;
import processing.app.Sketch;
import processing.app.tools.Tool;
import processing.app.ui.Editor;
import processing.app.ui.EditorConsole;

public class MicrobitFirmware
{

	static String getFirmwareTrailer()
	{
		String firmwareScript = ":04000005000153EDB6\n" + ":00000001FF\n";

		return firmwareScript;

	}
	static String getFirmware()
	{

		String str="";

		String userFirmware = Preferences.get("dhr.uploadtomicrobit.firmware");

		if (userFirmware.length() > 0 ) {

			File firmwareFile = new File(userFirmware);
			if(firmwareFile.exists() && !firmwareFile.isDirectory()) { 


				try {

					str = FileUtils.readFileToString(firmwareFile, "UTF-8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		}
	


		return  str;	// Return either the correct firmaware or nothing at all





	}
	
	/*
	 *What we do here is take the current script in the editor - save it to the same path 
	 *and then we push that to the MB 
	 * 
	 */
	
	static boolean Hexlify(Base base)
	{
		
		
		
		return false;
		
		
		
	}
	
}