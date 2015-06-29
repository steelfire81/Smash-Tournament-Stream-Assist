package streamassist;

import java.io.File;
import java.nio.file.Path;

public class CharacterIcon {

	public static final String DEFAULT_NAME = "Other";
	
	private String name;
	private File icon;
	
	public CharacterIcon(String n, File i)
	{
		name = n;
		icon = i;
	}
	
	// getIconPath - return the file path to the icon
	public Path getIconPath()
	{
		return icon.toPath();
	}
	
	// getName - return name
	public String getName()
	{
		return name;
	}
	
	// toString - return name
	public String toString()
	{
		return name;
	}
	
	// getIcon - return the icon file
	public File getIcon()
	{
		return icon;
	}
	
	// compareTo
	public int compareTo(CharacterIcon other)
	{
		// If this object has default name, it should come after other icons
		if(name.equals(DEFAULT_NAME))
		{
			if(other.getName().equals(DEFAULT_NAME))
				return 0; // shouldn't happen, but just in case...
			else
				return 1;
		}
		
		return name.compareTo(other.getName());
	}
}
