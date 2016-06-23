package streamassist;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	
	private static final String BUTTON_CHANGE_TEXT = "Change Icon Set";
	private static final String BUTTON_SWAP_TEXT = "Swap Players";
	private static final String BUTTON_UPDATE_TEXT = "UPDATE";
	private static final String BUTTON_PLUS_TEXT = "+";
	private static final String BUTTON_MINUS_TEXT = "-";
	
	private static final String WINDOW_NAME = "Tourney Stream Assist";
	private static final int WINDOW_WIDTH = 270;
	private static final int WINDOW_HEIGHT = 275;
	
	private static final String MSG_DESCRIPTION = "Description:";
	
	// Window Elements
	JPanel modePanel;
	JPanel mainPanel;
	JPanel textPanel;
	JComboBox<String> modeBox;
	JButton buttonChange;
	JCheckBox[] nameFieldCheckboxes;
	JTextField[] namePrefixFields;
	JTextField[] nameFields;
	JCheckBox[] scoreFieldCheckboxes;
	JTextField[] scorePrefixFields;
	JTextField[] scoreFields;
	JButton[] buttonMinus;
	JButton[] buttonPlus;
	JCheckBox[] iconCheckboxes;
	JTextField[] iconPrefixFields;
	JComboBox<String>[] iconBoxes;
	JCheckBox descriptionCheckbox;
	JTextField descriptionField;
	JButton buttonSwap;
	JButton buttonUpdate;

	
	public TSAWindow(File p1name, File p2name, File p1score, File p2score, File p1icon, File p2icon, File description, int mode)
	{
		// Initialize engine
		TSAEngine engine = new TSAEngine(this, p1name, p2name, p1score, p2score, p1icon, p2icon, description, mode);
		
		// Initialize main panel
		mainPanel = new JPanel(new BorderLayout());
		
		// Initialize icon set panel
		modePanel = new JPanel(new GridLayout(1, 2));
		modeBox = new JComboBox<String>(TSAStarter.MODE_OPTIONS);
		modeBox.setSelectedIndex(mode);
		modePanel.add(modeBox);
		buttonChange = new JButton(BUTTON_CHANGE_TEXT);
		buttonChange.addActionListener(engine);
		modePanel.add(buttonChange);
		mainPanel.add(modePanel, BorderLayout.NORTH);
		
		// Initialize panel of text fields
		textPanel = new JPanel(new GridLayout(NUM_GRID_ROWS, NUM_GRID_COLS));
		nameFieldCheckboxes = new JCheckBox[NUM_PLAYERS];
		namePrefixFields = new JTextField[NUM_PLAYERS];
		nameFields = new JTextField[NUM_PLAYERS];
		scoreFieldCheckboxes = new JCheckBox[NUM_PLAYERS];
		scorePrefixFields = new JTextField[NUM_PLAYERS];
		scoreFields = new JTextField[NUM_PLAYERS];
		buttonPlus = new JButton[NUM_PLAYERS];
		buttonMinus = new JButton[NUM_PLAYERS];
		iconCheckboxes = new JCheckBox[NUM_PLAYERS];
		iconPrefixFields = new JTextField[NUM_PLAYERS];
		iconBoxes = new JComboBox[NUM_PLAYERS];
		for(int i = 0; i < NUM_PLAYERS; i++)
		{
			JPanel namePrefixPanel = new JPanel(new BorderLayout());
			nameFieldCheckboxes[i] = new JCheckBox();
			nameFieldCheckboxes[i].addActionListener(engine);
			namePrefixFields[i] = new JTextField();
			namePrefixFields[i].setEditable(false);
			namePrefixFields[i].setText(playerNamePrefix(i + 1));
			namePrefixPanel.add(nameFieldCheckboxes[i], BorderLayout.WEST);
			namePrefixPanel.add(namePrefixFields[i], BorderLayout.CENTER);
			textPanel.add(namePrefixPanel);
			
			nameFields[i] = new JTextField();
			textPanel.add(nameFields[i]);
			
			JPanel scorePrefixPanel = new JPanel(new BorderLayout());
			scoreFieldCheckboxes[i] = new JCheckBox();
			scoreFieldCheckboxes[i].addActionListener(engine);
			scorePrefixFields[i] = new JTextField();
			scorePrefixFields[i].setEditable(false);
			scorePrefixFields[i].setText(playerScorePrefix(i + 1));
			scorePrefixPanel.add(scoreFieldCheckboxes[i], BorderLayout.WEST);
			scorePrefixPanel.add(scorePrefixFields[i], BorderLayout.CENTER);
			textPanel.add(scorePrefixPanel);
			
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
			
			JPanel iconPrefixPanel = new JPanel(new BorderLayout());
			iconCheckboxes[i] = new JCheckBox();
			iconCheckboxes[i].addActionListener(engine);
			iconPrefixFields[i] = new JTextField();
			iconPrefixFields[i].setEditable(false);
			iconPrefixFields[i].setText(playerIconPrefix(i + 1));
			iconPrefixPanel.add(iconCheckboxes[i], BorderLayout.WEST);
			iconPrefixPanel.add(iconPrefixFields[i], BorderLayout.CENTER);
			textPanel.add(iconPrefixPanel);
			
			iconBoxes[i] = new JComboBox<String>(engine.getIconNames());
			iconBoxes[i].setSelectedIndex(0);
			textPanel.add(iconBoxes[i]);
		}
		
		// Add description fields
		JPanel descriptionPrefixPanel = new JPanel(new BorderLayout());
		descriptionCheckbox = new JCheckBox();
		descriptionCheckbox.addActionListener(engine);
		JTextField descriptionPrefixField = new JTextField(MSG_DESCRIPTION);
		descriptionPrefixField.setEditable(false);
		descriptionPrefixPanel.add(descriptionCheckbox, BorderLayout.WEST);
		descriptionPrefixPanel.add(descriptionPrefixField, BorderLayout.CENTER);
		textPanel.add(descriptionPrefixPanel);
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
