package streamassist;

public class CharacterIcon {

	public static final String DEFAULT_NAME = "Other";
	
	private String name;
	private String filename;
	
	public CharacterIcon(String n, String f)
	{
		name = n;
		filename = f;
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
	
	// getIconFilename - return the icon's filename
	public String getIconFilename()
	{
		return filename;
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
