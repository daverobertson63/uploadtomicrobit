package dhr.uploadtomicrobit;
/**
 * 
 */

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author daver
 *
 */
public class HexlifyPython {

	/**
	 * 
	 */
	public HexlifyPython() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int[] ar={1,2,3,66,77,8,89,123};
		
		StringBuilder output = new StringBuilder();
		
		String simple="# Add your Python code here. E.g.\n";
		simple = simple + "from microbit import *\n";
		simple = simple + "while True:\n";
		simple = simple + "\tdisplay.scroll('Fuck You!')\n";
		simple = simple + "\tdisplay.show(Image.HEART)\n";
		simple = simple + "\tsleep(2000)\n";
		
		String script=simple;
		
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
	    
	    output.append(MicrobitFirmware.getFirmwareTrailer());
	    File file = new File("firmware.hex");
	    FileUtils.writeStringToFile(file,output.toString());

	    System.out.println(output);

	}
	
	public static String hexlify(int[] ar) {
		
        StringBuffer result= new StringBuffer();;
        int l = ar.length;
        
        for (int i = 0; i < ar.length; ++i) {
            
            result.append(String.format("%02X", ar[i]));
        }
        return result.toString();
    }

}
