package streamassist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.*;
import java.util.Scanner;

public class TSAEngine implements ActionListener {
	
	// Constants
	private static final int DEFAULT_SCORE = 0;
	private static final String DEFAULT_NAME = "";
	
	private static final String MSG_ERR_FNF = "ERROR: Could not find a given file";
	private static final String MSG_ERR_NULLPOINT = "ERROR: Icon list lied about number of icons!";
	
	private static final String ICON_FILE_NAME = "iconlist";
	private static final String ICON_FILE_EXTENSION = ".png";
	
	// Data Members
	private TSAWindow parent;
	private File p1Name;
	private File p2Name;
	private File p1Score;
	private File p2Score;
	private Path p1Icon;
	private Path p2Icon;
	private File iconDir;
	private CharacterIcon[] iconList;
	
	// Constructor
	public TSAEngine(TSAWindow window, File p1n, File p2n, File p1s, File p2s, File p1i, File p2i, File id)
	{
		parent = window;
		p1Name = p1n;
		p2Name = p2n;
		p1Score = p1s;
		p2Score = p2s;
		p1Icon = p1i.toPath();
		p2Icon = p2i.toPath();
		iconDir = id;
		
		// Initialize icon list
		InputStream iconFile = TSAEngine.class.getResourceAsStream(ICON_FILE_NAME);
		Scanner fileScan = new Scanner(iconFile);
		int numIcons = fileScan.nextInt();
		iconList = new CharacterIcon[numIcons];
		int index = 0;
		
		fileScan.nextLine();
		
		try
		{
			while(fileScan.hasNextLine())
			{
				String name = fileScan.nextLine();
				String fileName = fileScan.nextLine();
				
				iconList[index] = new CharacterIcon(name, new File(iconDir, fileName + ICON_FILE_EXTENSION));
				index++;
			}
		}
		catch(NullPointerException npe)
		{
			System.err.println(MSG_ERR_NULLPOINT);
			System.exit(1);
		}
		
		fileScan.close();
		
		// Sort icon list
		iconSort(iconList);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == parent.buttonUpdate)
		{
			try
			{
				updateFiles();
			}
			catch(FileNotFoundException fnf)
			{
				System.err.println(MSG_ERR_FNF);
				System.exit(1);
			}
			catch(IOException ioe)
			{
				System.err.println(MSG_ERR_FNF);
				System.exit(1);
			}
		}
	}
	
	// updateFiles - update linked files
	private void updateFiles() throws FileNotFoundException, IOException
	{
		PrintWriter p1NameWriter = new PrintWriter(p1Name);
		p1NameWriter.println(parent.nameFields[0].getText());
		p1NameWriter.close();
		
		PrintWriter p2NameWriter = new PrintWriter(p2Name);
		p2NameWriter.println(parent.nameFields[1].getText());
		p2NameWriter.close();
		
		PrintWriter p1ScoreWriter = new PrintWriter(p1Score);
		p1ScoreWriter.println(parent.scoreFields[0].getText());
		p1ScoreWriter.close();
		
		PrintWriter p2ScoreWriter = new PrintWriter(p2Score);
		p2ScoreWriter.println(parent.scoreFields[1].getText());
		p2ScoreWriter.close();
		
		// Copy image icons
		int iconSelectionP1 = parent.iconBoxes[0].getSelectedIndex();
		int iconSelectionP2 = parent.iconBoxes[1].getSelectedIndex();
		Files.copy(iconList[iconSelectionP1].getIconPath(), p1Icon, REPLACE_EXISTING);
		Files.copy(iconList[iconSelectionP2].getIconPath(), p2Icon, REPLACE_EXISTING);
	}
	
	// initialize - set all fields to default values
	public void initialize()
	{
		for(int i = 0; i < parent.nameFields.length; i++)
			parent.nameFields[i].setText(DEFAULT_NAME);
		
		for(int i = 0; i < parent.scoreFields.length; i++)
			parent.scoreFields[i].setText(Integer.toString(DEFAULT_SCORE));
		
		try
		{
			updateFiles();
		}
		catch(FileNotFoundException e)
		{
			System.err.println(MSG_ERR_FNF);
			System.exit(1);
		}
		catch(IOException ioe)
		{
			System.err.println(MSG_ERR_FNF);
			System.exit(1);
		}
	}
	
	// getIconNames - return an array of strings for icon names
	public String[] getIconNames()
	{
		String[] list = new String[iconList.length];
		for(int i = 0; i < iconList.length; i++)
			list[i] = iconList[i].getName();
		
		return list;
	}
	
	
	
	// Sorting Functions
	private void iconSort(CharacterIcon[] iconList)
	{
		iconSort(iconList, 0, iconList.length - 1);
	}
	
	private void iconSort(CharacterIcon[] iconList, int start, int end)
	{
		if(start < end)
		{
			int pivot = partition(iconList, start, end);
			iconSort(iconList, start, pivot - 1);
			iconSort(iconList, pivot + 1, end);
		}
	}
	
	private static int partition(CharacterIcon[] iconList, int start, int end)
	{
		int pivot = pivot(iconList, start, end);
		CharacterIcon pivotIcon = iconList[pivot];
		
		iconList[pivot] = iconList[end];
		iconList[end] = pivotIcon;
		
		int curr = start;
		for(int i = start; i < end; i++)
			if(iconList[i].compareTo(pivotIcon) <= 0)
			{
				CharacterIcon temp = iconList[curr];
				iconList[curr] = iconList[i];
				iconList[i] = temp;
				curr++;
			}
		
		iconList[end] = iconList[curr];
		iconList[curr] = pivotIcon;
		
		return curr;
	}
	
	private static int pivot(CharacterIcon[] iconList, int start, int end)
	{
		// This is a lazy pivot but since this won't ever sort that many things it's passable
		return start;
	}
}
