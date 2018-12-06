import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PasswordManager extends GraphicsManager {

    private static final String ADMIN_PASSWORD = "~PSWDRST~";

    static boolean loggingIn;
    private static int attempts;
    private static String password;

    /**
     * Initializes the Password Manager
     */
    public static void Init() {

        password = "";
        loggingIn = false;
        attempts = 0;

        //password = EncryptionManager.Decrypt(EncryptionManager.PASSWORD_FILE_NAME);

    }

    /**
     * Prompts the user to login
     */
    public static void Login() {

        if (password != "")
            PasswordManager.DoLogin();
        else
            PasswordManager.NewUser();

    }

    /**
     * Prompts the user for a new password
     */
    public static void NewPassword() {

        PasswordManager.NewUser();

    }

    // Prompts the user to create a new password
    private static void NewUser() {

        loggingIn = true;

		int groupID = startGroup();

        newLabel("L-nwusr-pmt", "Enter a Password", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 - 70), 300, 50,
                ARIAL_20, SwingConstants.CENTER);


        JLabel l1 = newLabel("L-nwusr-err", "Password doesn't fit minimum character requirement (4 characters)",
                new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 - 30), 500, 50,
                ARIAL_15, SwingConstants.CENTER);
        l1.setForeground(Color.RED);
        l1.setVisible(false);

        newTextField("TF-nwusr-pswd", true, "", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 + 30), 300, 50, ARIAL_15);

        newButton("B-nwusr-cfm", "Set Password", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 + 100), 300, 50, ARIAL_15,
                e -> {

					String input = ((JTextField) components.get("TF-nwusr-pswd")).getText();

                    if (input.length() >= 4) {

                        password = input;

                        //EncryptionManager.Encrypt(password, EncryptionManager.PASSWORD_FILE_NAME);

                        removeGroup(groupID);

						//Window.LoadData();
                        Window.DoMainScreen();

                    } else {

                        showComponent("L-nwusr-err", true);

                    }

                });

        stopGroup();

    }

    // Prompts the user to enter their password to login
    private static void DoLogin() {

        loggingIn = true;
        
        int groupID = startGroup();

        newLabel("L-lgn-pmt", "Enter your Password", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 - 70), 300, 50,
                ARIAL_20, SwingConstants.CENTER);


        JLabel l1 = newLabel("L-lgn-err", "Incorrect Password", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 - 30), 300, 50,
                ARIAL_15, SwingConstants.CENTER);
        l1.setForeground(Color.RED);
        l1.setVisible(false);

        newTextField("TF-lgn-pswd", true, "", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 + 30), 300, 50, ARIAL_15);

        newButton("B-lgn-ext", "Exit", new Point(SCREEN_SIZE.width / 2 + 100, SCREEN_SIZE.height / 2 + 100), 100, 50, ARIAL_15,
                e -> {Window.exit();});

        newButton("B-lgn-cfm", "Login", new Point(SCREEN_SIZE.width / 2 - 100, SCREEN_SIZE.height / 2 + 100), 100, 50, ARIAL_15,
                e -> {

					String input = ((JTextField) components.get("TF-lgn-pswd")).getText();
					
					if (password.equals(input)) {

                        Window.LoadData();

                        showGroup(groupID, false);

                        Window.DoMainScreen();

                    } else if (ADMIN_PASSWORD.equals(input)) {

						showGroup(groupID, false);

                        NewUser();

                    } else {

                        ((JTextField) components.get("TF-lgn-pswd")).setText("Incorrect Password! " + (5 - attempts) + " attempts remaining.");
                        attempts++;
                        showComponent("L-lgn-err", true);

                        if (attempts > 5)
                            Window.exit();

                    }

                });
                
		stopGroup();
		
    }

}
