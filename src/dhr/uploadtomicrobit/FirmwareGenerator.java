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
 * Hexifly the python code and combine with the firmware to create a single exe
 * 
 */
package dhr.uploadtomicrobit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

/**
 * @author daver
 *
 */
public class FirmwareGenerator {

	/**
	 * 
	 */
	public FirmwareGenerator() {
		// TODO Auto-generated constructor stub
		// No constructor required
	}

	/**
	 * @param script to hexifly and combine 
	 *  
	 */
	
	public String createFirmware(String pythonScript)
	{
		
		return null;
		
		
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int[] ar={1,2,3,66,77,8,89,123};
		
		
		String simple="# Add your Python code here. E.g.\n";
		simple = simple + "from microbit import *\n";
		simple = simple + "while True:\n";
		simple = simple + "\tdisplay.scroll('Great It Works!')\n";
		simple = simple + "\tdisplay.show(Image.HEART)\n";
		simple = simple + "\tsleep(2000)\n";
		
		String script=simple;
		
		FirmwareGenerator fg = new FirmwareGenerator();
		
		String output = fg.generateFirmware(simple);
		
		File file = new File("firmware.hex");
	    FileUtils.writeStringToFile(file,output.toString(),Charset.forName("ISO-8859-1"));
	
	}
	
	public String generateFirmware(File file)
	{
		String script;
		try {
			
			script = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			return generateFirmware(script);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public String generateFirmware(String script)
		
	{
		
		StringBuilder output = new StringBuilder();
		
		output.append(MicrobitFirmware.getFirmware());
			
		// add header, pad to multiple of 16 bytes
	    int[] data = new int[4 + script.length() + (16 - (4 + script.length()) % 16)];
	    data[0] = 77; // 'M'
	    data[1] = 80; // 'P'
	    data[2] = script.length() & 0xff;
	    data[3] = (script.length() >> 8) & 0xff;
	    
	    for (int i = 0; i < script.length(); ++i) {
	    	int codePointAt0 = Character.codePointAt(script, i);
	        data[4 + i] = codePointAt0;
	    }
	    
	    // convert to .hex format
	    int addr = 0x3e000; // magic start address in flash
	    int[] chunk = new int[5 + 16];
	    
	    output.append(":020000040003F7\n");
	    
	    for (int i = 0; i < data.length; i += 16, addr += 16) {
	        chunk[0] = 16; // length of data section
	        chunk[1] = (addr >> 8) & 0xff; // high byte of 16-bit addr
	        chunk[2] = addr & 0xff; // low byte of 16-bit addr
	        chunk[3] = 0; // type (data)
	        for (int j = 0; j < 16; ++j) {
	            chunk[4 + j] = data[i + j];
	        }
	        int checksum = 0;
	        for (int j = 0; j < 4 + 16; ++j) {
	            checksum += chunk[j];
	        }
	        chunk[4 + 16] = (-checksum) & 0xff;
	        output.append(':' + hexlify(chunk));
	        output.append("\n");
	    }
	    
	    // Append the trailer records
	    output.append(MicrobitFirmware.getFirmwareTrailer());
	    
	    // Return a full string - ready to copy
	    return output.toString();
	   
	    

	}
	/*
	 * Create a HEX representaion of the data... HEXLIFY!
	 * 
	 */
	public  String hexlify(int[] ar) {
		
        StringBuffer result= new StringBuffer();;
        int l = ar.length;
        
        for (int i = 0; i < ar.length; ++i) {
            
            result.append(String.format("%02X", ar[i]));
        }
        return result.toString();
    }

}
