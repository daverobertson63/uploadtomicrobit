# ##tool.name## Tool

### Description

This tool adds an _Upload to BBC Microbit_ menu item under _Tools_. Invoking it will compile the current python sketch, upload it to a connected Microbit, and execute it there. Any output of your sketch, such as from `println`, is displayed on the microbit

This uses the micropython firmware but this can be configured by adding your own firmware.  The hexlification code is taken from the browser javascript app and converted to be written in Java. Hexlification is the way that you send the firmware code and the processing python sketch to the Microbit board.  This approach is used by the javascript browsers.  

When you run the tool it will display a simple dialog box and from this you can save simple settings and upload the current sketch as a Micropython MB app.  This is the same as the javascript browsers - except you can use processing!

### Install with the Contribution Manager

Add contributed tools by selecting the menu item _Tools_ → _Add Tool..._ This will open the Contribution Manager, where you can browse for ##tool.name##, or any other tool you want to install.

Not all available tools have been converted to show up in this menu. If a tool isn't there, it will need to be installed manually by following the instructions below.

This is yet to be implemented - but it will be.

### Manual Install

Contributed tools may be downloaded separately and manually placed within the `tools` folder of your Processing sketchbook. To find (and change) the Processing sketchbook location on your computer, open the Preferences window from the Processing application (PDE) and look for the "Sketchbook location" item at the top.

By default the following locations are used for your sketchbook folder: 
  * For Mac users, the sketchbook folder is located inside `~/Documents/Processing` 
  * For Windows users, the sketchbook folder is located inside `My Documents/Processing`

Download ##tool.name## from ##tool.url##

Unzip and copy the contributed tool's folder into the `tools` folder in the Processing sketchbook. You will need to create this `tools` folder if it does not exist.
    
The folder structure for tool ##tool.name## should be as follows:

```
Processing
  tools
    ##tool.name##
      examples
      tool
        ##tool.name##.jar
      reference
      src
```
                      
Some folders like `examples` or `src` might be missing. After tool ##tool.name## has been successfully installed, restart the Processing application.

Note that the `tool` folder will have the firware.hex file. This can be updated to reflect more up to date firmware files you may want to use 

### Configuration

The following settings can be modified by editing Processing's `preferences.txt` file:

`dhr.uploadtomicrobit.microbit` - The drive/file location name that is required to copy the file to the microbit e.g H:  

`dhr.uploadtomicrobit.firware` - firmware name you want to use - must have insertion point reference for micropython script - see the master version in tool. This is the same approach as the browser app.

### Troubleshooting

If you're having trouble, please file issues [here](https://github.com/daverobertson63/processing-uploadtomicrobit/issues/new).
