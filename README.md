# Upload to Microbit Tool

### Description

This tool adds an _Upload to Microbit_ menu item under _Tools_. Invoking it will compile the current python sketch, upload it to a connected Microbit, and execute it there. Any output of your sketch, such as from `println`, is displayed on the microbit

This uses the micropython firmware but this can be configured

### Install with the Contribution Manager

Add contributed tools by selecting the menu item _Tools_ → _Add Tool..._ This will open the Contribution Manager, where you can browse for Upload to Microbit, or any other tool you want to install.

Not all available tools have been converted to show up in this menu. If a tool isn't there, it will need to be installed manually by following the instructions below.

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

### Configuration

The following settings can be modified by editing Processing's `preferences.txt` file:

`dhr.uploadtomicrobit.drive` - The drive name that is required to copy the file to the microbit  

`dhr.uploadtomicrobit.drive` - firmware name you want to use - must have insertion point reference for micropython script 

### Troubleshooting

If you're having trouble, please file issues [here](https://github.com/daverobertson63/processing-uploadtomicrobit/issues/new).
