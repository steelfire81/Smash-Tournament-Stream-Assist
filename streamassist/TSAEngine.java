package streamassist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class TSAEngine implements ActionListener {
	
	// Constants
	private static final int DEFAULT_SCORE = 0;
	private static final String DEFAULT_NAME = "";
	
	private static final String MSG_FILE_P1NAME = "Player 1 Name";
	private static final String MSG_FILE_P2NAME = "Player 2 Name";
	private static final String MSG_FILE_P1SCORE = "Player 1 Score";
	private static final String MSG_FILE_P2SCORE = "Player 2 Score";
	private static final String MSG_FILE_DESCRIPTION = "Description";
	private static final String MSG_ERR_FNF = "ERROR: Could not find file for ";
	
	private static final String ICON_FILE_NAME = "iconlist";
	private static final String ICON_FILE_EXTENSION = ".png";
	
	private static final String FOLDER_RESOURCES = "resources/";
	
	private static final String PREFIX_64 = "64_";
	private static final String PREFIX_MELEE = "m_";
	private static final String PREFIX_PM = "pm_";
	private static final String PREFIX_SMASH4 = "4_";
	private static final String PREFIX_TEAMS = "t_";
	
	private static final int MIN_SCORE = 0;
	
	private static final String TITLE_FNF = "File Not Found";
	
	
	// Data Members
	private TSAWindow parent;
	private File p1Name;
	private File p2Name;
	private File p1Score;
	private File p2Score;
	private File p1Icon;
	private File p2Icon;
	private File description;
	private int mode;
	private int icon1curr;
	private int icon2curr;
	private CharacterIcon[] iconList;
	
	// Constructor
	public TSAEngine(TSAWindow window, File p1n, File p2n, File p1s, File p2s, File p1i, File p2i, File dsc, int m)
	{
		parent = window;
		p1Name = p1n;
		p2Name = p2n;
		p1Score = p1s;
		p2Score = p2s;
		p1Icon = p1i;
		p2Icon = p2i;
		description = dsc;
		mode = m;
		
		// Initialize icon list
		setupIcons();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		// Change button
		if(e.getSource() == parent.buttonChange)
		{
			changeIconSet(parent.modeBox.getSelectedIndex());
		}
		
		// Plus buttons
		for(int i = 0; i < parent.buttonPlus.length; i++)
			if(e.getSource() == parent.buttonPlus[i])
				plusScore(i);
		
		// Minus buttons
		for(int i = 0; i < parent.buttonMinus.length; i++)
			if(e.getSource() == parent.buttonMinus[i])
				minusScore(i);
		
		// Swap Button
		if(e.getSource() == parent.buttonSwap)
		{
			try
			{
				swapPlayers();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
				System.exit(1);
			}
		}	
		
		// Update Button
		if(e.getSource() == parent.buttonUpdate)
		{
			try
			{
				updateFiles();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	// updateText - only update text files (not images)
	private void updateText()
	{
		try
		{
			PrintWriter p1NameWriter = new PrintWriter(p1Name);
			p1NameWriter.print(parent.nameFields[0].getText());
			p1NameWriter.close();
		}
		catch(IOException e)
		{
			errorFNF(MSG_FILE_P1NAME);
		}
		
		try
		{
			PrintWriter p2NameWriter = new PrintWriter(p2Name);
			p2NameWriter.print(parent.nameFields[1].getText());
			p2NameWriter.close();
		}
		catch(IOException e)
		{
			errorFNF(MSG_FILE_P2NAME);
		}
		
		try
		{
			PrintWriter p1ScoreWriter = new PrintWriter(p1Score);
			p1ScoreWriter.print(parent.scoreFields[0].getText());
			p1ScoreWriter.close();
		}
		catch(IOException e)
		{
			errorFNF(MSG_FILE_P1SCORE);
		}
		
		try
		{
			PrintWriter p2ScoreWriter = new PrintWriter(p2Score);
			p2ScoreWriter.print(parent.scoreFields[1].getText());
			p2ScoreWriter.close();
		}
		catch(IOException e)
		{
			errorFNF(MSG_FILE_P2SCORE);
		}
		
		try
		{
			PrintWriter descriptionWriter = new PrintWriter(description);
			descriptionWriter.print(parent.descriptionField.getText());
			descriptionWriter.close();
		}
		catch(IOException e)
		{
			errorFNF(MSG_FILE_DESCRIPTION);
		}
	}
	
	// updateFiles - update linked files
	private void updateFiles() throws IOException
	{
		// Update text files
		updateText();
		
		// Write p1 file
		int icon1new = parent.iconBoxes[0].getSelectedIndex();
		if(icon1new != icon1curr)
		{
			String p1filename = iconList[icon1new].getIconFilename();
			InputStream p1input = TSAEngine.class.getResourceAsStream(FOLDER_RESOURCES + getPrefixForMode() + p1filename + ICON_FILE_EXTENSION);
			FileOutputStream p1output = new FileOutputStream(p1Icon, false);
			p1output.getChannel().truncate(0);
			int in = p1input.read();
			while(in != -1)
			{
				p1output.write(in);
				in = p1input.read();
			}
			p1input.close();
			p1output.close();
		}
		
		// Write p2 file
		int icon2new = parent.iconBoxes[1].getSelectedIndex();
		if(icon2new != icon2curr)
		{
			String p2filename = iconList[icon2new].getIconFilename();
			InputStream p2input = TSAEngine.class.getResourceAsStream(FOLDER_RESOURCES + getPrefixForMode() + p2filename + ICON_FILE_EXTENSION);
			FileOutputStream p2output = new FileOutputStream(p2Icon, false);
			p2output.getChannel().truncate(0);
			int in = p2input.read();
			while(in != -1)
			{
				p2output.write(in);
				in = p2input.read();
			}
			p2input.close();
			p2output.close();
		}
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
			e.printStackTrace();
			System.exit(1);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			System.exit(1);
		}

		// Set current icons to -1 to ensure they get updated the first time
		icon1curr = -1;
		icon2curr = -1;
	}
	
	// getIconNames - return an array of strings for icon names
	public String[] getIconNames()
	{
		String[] list = new String[iconList.length];
		for(int i = 0; i < iconList.length; i++)
			list[i] = iconList[i].getName();
		
		return list;
	}
	
	// setupIcons - initialize icon list from files
	private void setupIcons()
	{
		InputStream iconFile;
		iconFile = TSAEngine.class.getResourceAsStream(FOLDER_RESOURCES + getPrefixForMode() + ICON_FILE_NAME);
		
		Scanner fileScan = new Scanner(iconFile);
		int numIcons = fileScan.nextInt();
		iconList = new CharacterIcon[numIcons];
		
		fileScan.nextLine();
		
		for(int i = 0; i < numIcons; i++)
		{
			String name = fileScan.nextLine();
			String fileName = fileScan.nextLine();
			
			iconList[i] = new CharacterIcon(name, fileName);
		}
		
		fileScan.close();
		
		// Sort icon list
		iconSort(iconList);
	}
	
	// getPrefixForMode - return the appropriate prefix for icon set
	private String getPrefixForMode()
	{
		if(mode == TSAStarter.MODE_64)
			return PREFIX_64;
		else if(mode == TSAStarter.MODE_MELEE)
			return PREFIX_MELEE;
		else if(mode == TSAStarter.MODE_PM)
			return PREFIX_PM;
		else if(mode == TSAStarter.MODE_SMASH4)
			return PREFIX_SMASH4;
		else if(mode == TSAStarter.MODE_TEAMS)
			return PREFIX_TEAMS;
		else
			return PREFIX_SMASH4;
	}
	
	// swapPlayers - swap name, score, and icon for both players
	private void swapPlayers() throws IOException
	{
		// Store player 1's information
		String tmpName = parent.nameFields[0].getText();
		String tmpScore = parent.scoreFields[0].getText();
		int tmpIcon = parent.iconBoxes[0].getSelectedIndex();
		
		// Copy player 2's information to player 1
		parent.nameFields[0].setText(parent.nameFields[1].getText());
		parent.scoreFields[0].setText(parent.scoreFields[1].getText());
		parent.iconBoxes[0].setSelectedIndex(parent.iconBoxes[1].getSelectedIndex());
		
		// Copy player 1's old information to player 2
		parent.nameFields[1].setText(tmpName);
		parent.scoreFields[1].setText(tmpScore);
		parent.iconBoxes[1].setSelectedIndex(tmpIcon);
		
		// Update files
		updateFiles();
	}
	
	// plusScore - increment given score field by one
	private void plusScore(int player)
	{
		int score;
		try
		{
			score = Integer.parseInt(parent.scoreFields[player].getText());
		}
		catch(NumberFormatException e) // If score field doesn't contain number, set score to -1 (so it increments to 0)
		{
			score = -1;
		}
		
		parent.scoreFields[player].setText(Integer.toString(score + 1));
		updateText();
	}
	
	// minusScore - decrement given score field by one
	private void minusScore(int player)
	{
		int score;
		try
		{
			score = Integer.parseInt(parent.scoreFields[player].getText());
		}
		catch(NumberFormatException e) // If score field doesn't contain number, set score to 1 (so it decrements to 0)
		{
			score = 1;
		}
		
		if(score <= MIN_SCORE)
			score = MIN_SCORE;
		else
			score--;
		
		parent.scoreFields[player].setText(Integer.toString(score));
		updateText();
	}
	
	// changeIconSet - change to different icon set (if different set is selected)
	private void changeIconSet(int set)
	{
		// Only change icon set if mode has been changed
		if(set != mode)
		{
			mode = set;
			setupIcons();
			for(int i = 0; i < parent.iconBoxes.length; i++)
			{
				parent.iconBoxes[i].removeAllItems();
				for(int j = 0; j < iconList.length; j++)
					parent.iconBoxes[i].addItem(iconList[j]);
				
				parent.iconBoxes[i].setSelectedIndex(0);
			}
		}
	}
	
	// errorFNF - display an error message and quit when a file isn't found
	private void errorFNF(String filename)
	{
		JOptionPane.showMessageDialog(parent.buttonUpdate, MSG_ERR_FNF + filename, TITLE_FNF, JOptionPane.ERROR_MESSAGE);
		System.exit(1);
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
