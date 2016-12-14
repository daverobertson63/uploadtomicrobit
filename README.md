# Upload to BBC Microbit Tool

### Description

This tool adds an _Upload to BBC Microbit_ menu item under _Tools_. Invoking it will compile the current python sketch, upload it to a connected Microbit, and execute it there. Any output of your sketch, such as from `display`, is displayed on the microbit

This uses the micropython firmware but this can be configured by adding your own firmware.  The hexlification code is taken from the browser javascript app and converted to Java. Hexlification is the way that you send the firmware code and the processing python sketch to the Microbit board.  This approach is used by the javascript browsers.  

When you run the tool it will display a simple dialog box and from this you can save simple settings and upload the current sketch as a Micropython MB app.  This is the same as the javascript browsers - except you can use processing!

### Install with the Contribution Manager

Add contributed tools by selecting the menu item _Tools_ → _Add Tool..._ This will open the Contribution Manager, where you can browse for Upload to Microbit, or any other tool you want to install.

Not all available tools have been converted to show up in this menu. If a tool isn't there, it will need to be installed manually by following the instructions below.

This is yet to be implemented - but it will be.

### Manual Install

Contributed tools may be downloaded separately and manually placed within the `tools` folder of your Processing sketchbook. To find (and change) the Processing sketchbook location on your computer, open the Preferences window from the Processing application (PDE) and look for the "Sketchbook location" item at the top.

By default the following locations are used for your sketchbook folder: 
  * For Mac users, the sketchbook folder is located inside `~/Documents/Processing` 
  * For Windows users, the sketchbook folder is located inside `My Documents/Processing`

Download Upload to Microbit from https://github.com/daverobertson63/processing-uploadtomicrobit

Unzip and copy the contributed tool's folder into the `tools` folder in the Processing sketchbook. You will need to create this `tools` folder if it does not exist.
    
The folder structure for tool Upload to Microbit should be as follows:

```
Processing
  tools
    Upload to Microbit
      examples
      tool
        Upload to Microbit.jar
      reference
      src
```
                      
Some folders like `examples` or `src` might be missing. After tool Upload to Microbit has been successfully installed, restart the Processing application.

Note that the `tool` folder will have the firware.hex file. This can be updated to reflect more up to date firmware files you may want to use 

### Configuration

The following settings can be modified by editing Processing's `preferences.txt` file:

`dhr.uploadtomicrobit.microbit` - The drive/file location name that is required to copy the file to the microbit e.g H: for Windows.  

`dhr.uploadtomicrobit.firware` - firmware name you want to use - must have insertion point reference for micropython script - see the master version in tool. This is the same approach as the browser app.

### Using it

Make sure the mode in processing is Python.  The best approach is to create a new sketch and save it in the default location.  So that would normally be in the Processing  sketches folder.

Then you write your BBC Microbit compatible micropython sketch.  Save it ( when you upload a save will be started for you ).  Then go to Tools and click on Upload to BBC microbit

This gives you a dialog box with some options.  Lets assume you have plugged the MB into the computer being used.

If this is the first time - then you will see a blank location in both locations.  The first is the location of the Microbit itself - in Windows this could be a drive in Linux a path like /dev/mb

So you need to know where your Microbit is - for example in Windows it might be H:

So select that location.

The next is where the default firmware file is located.  We have a supplied firmware file which is located in the Tools folder - so go to there and select the folders until you see the folder tool.  In this folder you will find a file called firmware.hex - select that or if you have your own - select that.  See the section on firmware.hex for more info.

If you are ok with that - click 'Save Settings'


### Firmware Stuff

The idea beind this app is that it delivers a fully ready firmware file to the microbit by copying a file called firmware.hex to the top level of the microbit folder which is exposed when plugged into the USB.

Essentially the microbit opens this file - and implements the contents of the file as micropython scripted runtime.  For this to work - you write your own micropython program, it gets converted to a hex format and then combined with the prewritten firmware file.

You can do all this manually or use the python javascript editors that are available.  What this Processing tool does is to automate some of that process.  We have a predefined firmware file - firmware.hex.  In this file is a line near the end with 32 : characters - :::::::::::::::::::::::

When you are ready to upload your sketch to the microbit - the tool will use this firmware.hex file - remove the line with the : characters and replace it with a hexlified version of your python program.  Hexlified means converting the basic ASCII text of the program into a series of hex characters.

A final file - also called firmware.hex is saved in the main sketch folder and then copied up to the microbit.

The latest versions of the firmware can be added as needed - or your own can also be added.  

The following example shows how hexlification works

####The original sketch is this:

	# Simple micropython script
	from microbit import *
	while True:
     display.scroll('Great It Works!')
     display.show(Image.HEART)
     sleep(2000)

####The hexlified version 

:10E000004D509B00232041646420796F757220502D

:10E010007974686F6E20636F646520686572652E21

:10E020000D0A66726F6D206D6963726F626974208C

:10E03000696D706F7274202A0D0A7768696C6520AB

:10E04000547275650D0A20202020646973706C611C

:10E05000792E7363726F6C6C282747726561742028

:10E06000497420576F726B732127290D0A202020D5

:10E0700020646973706C61792E73686F7728496DBD

:10E080006167652E4845415254290D0A2020202001

:10E09000736C6565702832303030290D0A202000FD

so checkout the generated firmware.hex to see how it works.

### Troubleshooting

If you're having trouble, please file issues [here](https://github.com/daverobertson63/processing-uploadtomicrobit/issues/new).
