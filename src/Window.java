
//import java.util.*;
import java.awt.*;
import java.awt.event.*;
//import java.io.File;

import javax.swing.*;
//import javax.swing.filechooser.*;

/**
 * 
 * @author Thomas
 *
 */
@SuppressWarnings("serial")
public class Window extends JComponent {

	public static ClientManager clientManager;

	private static final int SAVE_CONSTANT = 15000;

	private static JFrame frame;
	@SuppressWarnings("unused")
	private static UpdateTimer timer;

	private static boolean autoSave;
	private static int saveCountDown;

	/**
	 * Main method of the program
	 * 
	 * @param args
	 *            Expected main input
	 */
	public static void main(String[] args) {

		DoSetup();

		PasswordManager.Login();

	}

	/**
	 * Refreshes the JFrame
	 */
	public static void DoRepaint() {

		frame.repaint();

	}

	/**
	 * Passes the current list of clients to the main screen to be displayed
	 */
	public static void DoMainScreen() {

		MainScreenManager.MainScreen(clientManager.getClients());

	}

	/**
	 * Updates every time the timer runs, used for auto-save
	 */
	public static void DoWindowUpdates() {

		if (autoSave) {

			if (saveCountDown <= 0) {

				saveCountDown = SAVE_CONSTANT;
				SaveData();

			} else {

				saveCountDown--;

			}

		}

	}

	/**
	 * Saves an encrypted list of the current list of clients to the save file
	 */
	public static void SaveData() {

		EncryptionManager.Encrypt(ClientManager.Save(), EncryptionManager.SAVE_FILE_NAME);

	}

	/**
	 * Loads and decrypts the list of clients from the save file
	 */
	public static void LoadData() {

		clientManager = new ClientManager(
				ClientManager.Load(EncryptionManager.Decrypt(EncryptionManager.SAVE_FILE_NAME)));

	}

	/*
	 * public static File ChooseFile() {
	 * 
	 * JFileChooser fc = new JFileChooser();
	 * 
	 * FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files",
	 * "txt");
	 * 
	 * fc.setFileFilter(filter); fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	 * fc.showOpenDialog(null);
	 * 
	 * if (fc.getSelectedFile() != null) return fc.getSelectedFile();
	 * 
	 * return null;
	 * 
	 * }
	 * 
	 * public static File GetFilePath() {
	 * 
	 * JFileChooser fc = new JFileChooser();
	 * 
	 * fc.setDialogTitle("Select a save folder");
	 * fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	 * fc.showOpenDialog(null);
	 * 
	 * return fc.getSelectedFile();
	 * 
	 * }
	 */

	// Initializes the Window class
	private static void DoSetup() {

		autoSave = false;
		saveCountDown = SAVE_CONSTANT;
		WindowSetup();
		EncryptionManager.InitCrypt();
		PasswordManager.Init();
		timer = new UpdateTimer();
		clientManager = new ClientManager();

	}

	/**
	 * Adds the component passed to be displayed on the JFrame
	 * 
	 * @param comp
	 *            Component to be added to the JFrame
	 */
	public static void AddComponent(Component comp) {

		frame.add(comp);
		frame.repaint();

	}

	// Sets up the JFrame
	private static void WindowSetup() {

		frame = GraphicsManager.newFrame();
		frame.setTitle("Real Estate Client Manager Version 1.0");
		frame.repaint();
		frame.setVisible(true);

	}

	/**
	 * Saves and closes the program
	 */
	public static void exit() {

		SaveData();
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

	}

}
