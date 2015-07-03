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
	private static final int NUM_GRID_ROWS = 7;
	private static final int NUM_GRID_COLS = 2;
	private static final int NUM_BUTTONS = 2;
	private static final int NUM_SCORE_COLS = 3; // column for -/+ buttons and score field
	
	private static final String BUTTON_SWAP_TEXT = "Swap Players";
	private static final String BUTTON_UPDATE_TEXT = "UPDATE";
	private static final String BUTTON_PLUS_TEXT = "+";
	private static final String BUTTON_MINUS_TEXT = "-";
	
	private static final String WINDOW_NAME = "Tourney Stream Assist";
	private static final int WINDOW_WIDTH = 270;
	private static final int WINDOW_HEIGHT = 245;
	
	private static final String MSG_DESCRIPTION = "Description:";
	
	// Window Elements
	JPanel mainPanel;
	JPanel textPanel;
	JTextField[] namePrefixFields;
	JTextField[] nameFields;
	JTextField[] scorePrefixFields;
	JTextField[] scoreFields;
	JButton[] buttonMinus;
	JButton[] buttonPlus;
	JTextField[] iconPrefixFields;
	JComboBox[] iconBoxes;
	JTextField descriptionField;
	JButton buttonSwap;
	JButton buttonUpdate;

	
	public TSAWindow(File p1name, File p2name, File p1score, File p2score, File p1icon, File p2icon, File description, int mode)
	{
		// Initialize engine
		TSAEngine engine = new TSAEngine(this, p1name, p2name, p1score, p2score, p1icon, p2icon, description, mode);
		
		// Initialize main panel
		mainPanel = new JPanel(new BorderLayout());
		
		// Initialize panel of text fields
		textPanel = new JPanel(new GridLayout(NUM_GRID_ROWS, NUM_GRID_COLS));
		namePrefixFields = new JTextField[NUM_PLAYERS];
		nameFields = new JTextField[NUM_PLAYERS];
		scorePrefixFields = new JTextField[NUM_PLAYERS];
		scoreFields = new JTextField[NUM_PLAYERS];
		buttonPlus = new JButton[NUM_PLAYERS];
		buttonMinus = new JButton[NUM_PLAYERS];
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
			
			// For score, add - button, text field, and + button
			JPanel scorePanel = new JPanel(new GridLayout(1, NUM_SCORE_COLS));
			buttonMinus[i] = new JButton(BUTTON_MINUS_TEXT);
			buttonMinus[i].addActionListener(engine);
			scorePanel.add(buttonMinus[i]);
			scoreFields[i] = new JTextField();
			scorePanel.add(scoreFields[i]);
			buttonPlus[i] = new JButton(BUTTON_PLUS_TEXT);
			buttonPlus[i].addActionListener(engine);
			scorePanel.add(buttonPlus[i]);
			textPanel.add(scorePanel);
			
			iconPrefixFields[i] = new JTextField();
			iconPrefixFields[i].setEditable(false);
			iconPrefixFields[i].setText(playerIconPrefix(i + 1));
			textPanel.add(iconPrefixFields[i]);
			
			iconBoxes[i] = new JComboBox(engine.getIconNames());
			iconBoxes[i].setSelectedIndex(0);
			textPanel.add(iconBoxes[i]);
		}
		// Add description fields
		JTextField descriptionPrefixField = new JTextField(MSG_DESCRIPTION);
		descriptionPrefixField.setEditable(false);
		textPanel.add(descriptionPrefixField);
		descriptionField = new JTextField();
		textPanel.add(descriptionField);
		mainPanel.add(textPanel, BorderLayout.CENTER);
		
		// Add a panel to hold buttons
		JPanel buttonPanel = new JPanel(new GridLayout(NUM_BUTTONS, 1));
		
		// Initialize swap button
		buttonSwap = new JButton(BUTTON_SWAP_TEXT);
		buttonSwap.addActionListener(engine);
		buttonPanel.add(buttonSwap);
		
		// Initialize update button
		buttonUpdate = new JButton(BUTTON_UPDATE_TEXT);
		buttonUpdate.addActionListener(engine);
		buttonPanel.add(buttonUpdate);
		
		// Add button panel
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
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
