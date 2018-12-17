
//import java.util.*;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

//import java.io.File;
//import javax.swing.filechooser.*;

/**
 * @author Thomas
 */
@SuppressWarnings("serial")
public class Window extends JComponent {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy");

    private static final int SAVE_CONSTANT = 15000;

    public static ClientManager clientManager;

    private static JFrame frame;
    private static UpdateTimer timer;

    private static boolean autoSave;
    private static int saveCountDown;

    /**
     * Main method of the program
     *
     * @param args Expected main input
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

        if (clientManager.getClients().length != 0)
            MainScreenManager.DoMainScreen(clientManager.getClients());
        else
            MainScreenManager.DoMainScreen(new Client[0]);

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

        System.out.println(clientManager);
        System.out.println(clientManager.getClients());

        EncryptionManager.Encrypt(clientManager.toFile(), EncryptionManager.SAVE_FILE_NAME);

    }

    /**
     * Loads and decrypts the list of clients from the save file
     */
    public static void LoadData() {

        ArrayList<Client> clients = (ArrayList<Client>) EncryptionManager.Decrypt(EncryptionManager.SAVE_FILE_NAME);

        if (clients == null)
            clientManager = new ClientManager();
        else
            clientManager = new ClientManager(clients);

        System.out.println(clientManager.toString());
        System.out.println("====================RAN SUCCESSFULLY=====================");


    }


    public static File ChooseFile() {

        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files",
                "txt");

        fc.setFileFilter(filter);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.showOpenDialog(null);

        if (fc.getSelectedFile() != null) return fc.getSelectedFile();

        return null;

    }

    public static String GetFilePath() {

        JFileChooser fc = new JFileChooser();

        fc.setDialogTitle("Select a save folder");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.showOpenDialog(null);

        return fc.getSelectedFile().getAbsolutePath();

    }

    public static boolean ExportClients() {

        return ClientManager.ExportClients(GetFilePath(), clientManager);

    }

    public static boolean GetImportFormat() {

        int num = Integer.parseInt(JOptionPane.showInputDialog(frame, "Number of clients to import:"));

        return ClientManager.getImportFormat(GetFilePath(), num);

    }

    public static boolean ImportClients() {

        return ClientManager.ImportClients(ChooseFile());

    }


    // Initializes the Window class
    private static void DoSetup() {

        autoSave = false;
        saveCountDown = SAVE_CONSTANT;
        WindowSetup();
        EncryptionManager.InitCrypt();
        PasswordManager.Init();
        MainScreenManager.Init();
        timer = new UpdateTimer();
        clientManager = new ClientManager();

    }

    /**
     * Adds the component passed to be displayed on the JFrame
     *
     * @param comp Component to be added to the JFrame
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

    public static JFrame getJFrame() {
        return frame;
    }

}
