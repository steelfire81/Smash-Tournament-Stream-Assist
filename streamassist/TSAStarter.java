package streamassist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class TSAStarter {
	
	private static final String MSG_LOAD = "Load an existing config?";
	private static final String MSG_P1_NAME = "Please select the player 1 name file";
	private static final String MSG_P2_NAME = "Please select the player 2 name file";
	private static final String MSG_P1_SCORE = "Please select the player 1 score file";
	private static final String MSG_P2_SCORE = "Please select the player 2 score file";
	private static final String MSG_P1_ICON = "Please select the player 1 icon file";
	private static final String MSG_P2_ICON = "Please select the player 2 icon file";
	private static final String MSG_ICON_DIR = "Please select the directory containing icons";
	private static final String MSG_SAVE = "Do you want to save this configuration?";
	private static final String MSG_ERR_FNF = "ERROR: Could not write config file";
	private static final String MSG_ERR_LOAD = "ERROR: Could not load config file";
	
	private static final String TITLE_SAVE = "Save Configuration?";
	
	public static void main(String[] args)
	{
		int result;
		File p1name;
		File p2name;
		File p1score;
		File p2score;
		File p1icon;
		File p2icon;
		File iconDir;
		
		// Ask user to load existing configuration
		result = JOptionPane.showConfirmDialog(null, MSG_LOAD);
		if(result == JOptionPane.CANCEL_OPTION)
			System.exit(0);
		else if(result == JOptionPane.YES_OPTION)
		{
			JFileChooser loader = new JFileChooser();
			loader.setCurrentDirectory(null);
			result = loader.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					launchFromFile(loader.getSelectedFile());
				}
				catch(FileNotFoundException fnf)
				{
					JOptionPane.showMessageDialog(null, MSG_ERR_LOAD);
					System.exit(1);
				}
			}
			else
				System.exit(0);
		}
		else // "no" selected - create new configuration
		{
			JOptionPane.showMessageDialog(null, MSG_P1_NAME);
			JFileChooser selector = new JFileChooser();
			selector.setCurrentDirectory(null);
			result = selector.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION)
			{
				p1name = selector.getSelectedFile();	
				JOptionPane.showMessageDialog(null, MSG_P2_NAME);
				result = selector.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					p2name = selector.getSelectedFile();
					JOptionPane.showMessageDialog(null, MSG_P1_SCORE);
					result = selector.showOpenDialog(null);
					if(result == JFileChooser.APPROVE_OPTION)
					{
						p1score = selector.getSelectedFile();
						JOptionPane.showMessageDialog(null, MSG_P2_SCORE);
						result = selector.showOpenDialog(null);
						if(result == JFileChooser.APPROVE_OPTION)
						{
							p2score = selector.getSelectedFile();
							JOptionPane.showMessageDialog(null, MSG_P1_ICON);
							result = selector.showOpenDialog(null);
							if(result == JFileChooser.APPROVE_OPTION)
							{
								p1icon = selector.getSelectedFile();
								JOptionPane.showMessageDialog(null, MSG_P2_ICON);
								result = selector.showOpenDialog(null);
								if(result == JFileChooser.APPROVE_OPTION)
								{
									p2icon = selector.getSelectedFile();
									JOptionPane.showMessageDialog(null, MSG_ICON_DIR);
									result = selector.showOpenDialog(null);
									if(result == JFileChooser.APPROVE_OPTION)
									{
										iconDir = selector.getCurrentDirectory();
										
										// Ask user to save configuration
										result = JOptionPane.showConfirmDialog(null, MSG_SAVE, TITLE_SAVE, JOptionPane.YES_NO_OPTION);
										if(result == JOptionPane.YES_OPTION)
										{
											result = selector.showSaveDialog(null);
											if(result == JFileChooser.APPROVE_OPTION)
											{
												try
												{
													writeConfig(selector.getSelectedFile(), p1name, p2name, p1score, p2score, p1icon, p2icon, iconDir);
												}
												catch(FileNotFoundException fnf)
												{
													System.err.println(MSG_ERR_FNF);
													System.exit(1);
												}
											}
										}
										
										new TSAWindow(p1name, p2name, p1score, p2score, p1icon, p2icon, iconDir);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	// writeConfig - write paths of filenames to a file
	private static void writeConfig(File destination, File p1n, File p2n, File p1s, File p2s, File p1i, File p2i, File id) throws FileNotFoundException
	{
		PrintWriter configWriter = new PrintWriter(destination);
		
		configWriter.println(p1n.getPath());
		configWriter.println(p2n.getPath());
		configWriter.println(p1s.getPath());
		configWriter.println(p2s.getPath());
		configWriter.println(p1i.getPath());
		configWriter.println(p2i.getPath());
		configWriter.println(id.getPath());
		
		configWriter.close();
	}
	
	// launchFromFile - launch a TSAWindow using a config file
	private static void launchFromFile(File config) throws FileNotFoundException
	{
		Scanner fileScan = new Scanner(config);
		
		File p1n = new File(fileScan.nextLine());
		File p2n = new File(fileScan.nextLine());
		File p1s = new File(fileScan.nextLine());
		File p2s = new File(fileScan.nextLine());
		File p1i = new File(fileScan.nextLine());
		File p2i = new File(fileScan.nextLine());
		File id = new File(fileScan.nextLine());
		
		fileScan.close();
		
		new TSAWindow(p1n, p2n, p1s, p2s, p1i, p2i, id);
	}
}
