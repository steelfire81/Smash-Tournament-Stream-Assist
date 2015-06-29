package streamassist;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TSAWindow {

	// Constants	
	private static final int NUM_PLAYERS = 2;
	private static final int NUM_GRID_ROWS = 6;
	private static final int NUM_GRID_COLS = 2;
	
	private static final String BUTTON_UPDATE_TEXT = "Update";
	
	private static final String WINDOW_NAME = "Tourney Stream Assist";
	private static final int WINDOW_WIDTH = 230;
	private static final int WINDOW_HEIGHT = 185;
	
	// Window Elements
	JPanel mainPanel;
	JPanel textPanel;
	JTextField[] namePrefixFields;
	JTextField[] nameFields;
	JTextField[] scorePrefixFields;
	JTextField[] scoreFields;
	JTextField[] iconPrefixFields;
	JComboBox[] iconBoxes;
	JButton buttonUpdate;

	
	public TSAWindow(File p1name, File p2name, File p1score, File p2score, File p1icon, File p2icon, File id)
	{
		// Initialize engine
		TSAEngine engine = new TSAEngine(this, p1name, p2name, p1score, p2score, p1icon, p2icon, id);
		
		// Initialize main panel
		mainPanel = new JPanel(new BorderLayout());
		
		// Initialize panel of text fields
		textPanel = new JPanel(new GridLayout(NUM_GRID_ROWS, NUM_GRID_COLS));
		namePrefixFields = new JTextField[NUM_PLAYERS];
		nameFields = new JTextField[NUM_PLAYERS];
		scorePrefixFields = new JTextField[NUM_PLAYERS];
		scoreFields = new JTextField[NUM_PLAYERS];
		iconPrefixFields = new JTextField[NUM_PLAYERS];
		iconBoxes = new JComboBox[NUM_PLAYERS];
		for(int i = 0; i < NUM_PLAYERS; i++)
		{
			namePrefixFields[i] = new JTextField();
			namePrefixFields[i].setEditable(false);
			namePrefixFields[i].setText(playerNamePrefix(i + 1));
			textPanel.add(namePrefixFields[i]);
			
			nameFields[i] = new JTextField();
			textPanel.add(nameFields[i]);
			
			scorePrefixFields[i] = new JTextField();
			scorePrefixFields[i].setEditable(false);
			scorePrefixFields[i].setText(playerScorePrefix(i + 1));
			textPanel.add(scorePrefixFields[i]);
			
			scoreFields[i] = new JTextField();
			textPanel.add(scoreFields[i]);
			
			iconPrefixFields[i] = new JTextField();
			iconPrefixFields[i].setEditable(false);
			iconPrefixFields[i].setText(playerIconPrefix(i + 1));
			textPanel.add(iconPrefixFields[i]);
			
			iconBoxes[i] = new JComboBox(engine.getIconNames());
			iconBoxes[i].setSelectedIndex(0);
			textPanel.add(iconBoxes[i]);
		}
		mainPanel.add(textPanel, BorderLayout.CENTER);
		
		// Initialize update button
		buttonUpdate = new JButton(BUTTON_UPDATE_TEXT);
		buttonUpdate.addActionListener(engine);
		mainPanel.add(buttonUpdate, BorderLayout.SOUTH);
		
		// Initialize window, engine
		engine.initialize();
		
		// Make this visible
		JFrame frame = new JFrame(WINDOW_NAME);
		frame.setContentPane(mainPanel);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setName(WINDOW_NAME);
		frame.setVisible(true);
	}
	
	// playerNamePrefix - return a string containing an appropriate player name prefix
	private String playerNamePrefix(int n)
	{
		return "P" + n + " Name: ";
	}
	
	// playerScorePrefix - return a string containing an appropriate player score prefix
	private String playerScorePrefix(int n)
	{
		return "P" + n + " Score: ";
	}
	
	// playerIconPrefix - return a string containing an appropriate player icon prefix
	private String playerIconPrefix(int n)
	{
		return "P" + n + " Icon: ";
	}
	
}
