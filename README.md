# Smash-Tournament-Stream-Assist
A simple tool I plan to use alongside OBS to quickly update text and icons for a Smash tournament stream.

SETUP:
This is a bit of a workaround I use for streaming that beats going into OBS and manually editing text/images.  For this to work, you will need (preferably within the same folder) FOUR text files (player 1/2 name & score) and TWO image files (player 1/2 character icons).  Additionally, you should extract the icons to a folder within the overarching folder.  I'll upload images of my setup as an example.

ADDING ICONS:
The program is set up to read iconlist and build a library of character icons.  The file format is as follows:
[Number of Icons]
[Icon 1 Name]
[Icon 1 Filename]
[Icon 2 Name]
[Icon 2 Filename]
...
[Last Icon Name]
[Last Icon Filename]
If you want to add icons or make your own iconlist file, just stick to that format.  The program sorts files alphabetically by character name (with "Other" always coming last).  I may add a built-in Melee icon list eventually as well for the sake of avoiding constant editing.

RUNNING THE PROGRAM:
On startup, you can choose to load a config that contains the file locations of previously used files or manually select all the files.  From there it's pretty straightforward.  Icons take a second longer to update than the text.
