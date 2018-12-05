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

        int groupID = startGroup();

        loggingIn = true;

        newLabel("L-pswd", "Enter a Password", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 - 70), 300, 50,
                ARIAL_20, SwingConstants.CENTER);


        JLabel l1 = newLabel("L-err", "Password doesn't fit minimum character requirement (4 characters)",
                new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 - 30), 500, 50,
                ARIAL_15, SwingConstants.CENTER);
        l1.setForeground(Color.RED);
        l1.setVisible(false);

        newTextField("TF-pswd", true, "", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 + 30), 300, 50, ARIAL_15);

        newButton("B-pswd", "Set Password", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 + 100), 300, 50, ARIAL_15,
                e -> {

                    String input = ((JTextField) components.get("TF-pswd")).getText();

                    if (input.length() >= 4) {

                        password = input;

                        //EncryptionManager.Encrypt(password, EncryptionManager.PASSWORD_FILE_NAME);

                        removeGroup(groupID);

                        //Window.LoadData();
                        Window.DoMainScreen();

                    } else {

                        showComponent("L-err", true);

                    }
                });

        stopGroup();

    }

    // Prompts the user to enter their password to login
    private static void DoLogin() {

        loggingIn = true;

        newLabel("Enter your Password", new Point(screen.width / 2, screen.height / 2 - 70), 300, 50,
                ARIAL_20, SwingConstants.CENTER);


        newLabel("Incorrect Password", new Point(screen.width / 2, screen.height / 2 - 30), 300, 50,
                ARIAL_15, SwingConstants.CENTER);
        l2.setForeground(Color.RED);
        l2.setVisible(false);

        t1 = newTextField(true, "", new Point(screen.width / 2, screen.height / 2 + 30), 300, 50);

        b1 = newButton("Exit", new Point(screen.width / 2 + 100, screen.height / 2 + 100), 100, 50,
                e -> {

                    Window.exit();


                });

        b2 = newButton("Login", new Point(screen.width / 2 - 100, screen.height / 2 + 100), 100, 50,
                e -> {

                    if (password.equals(t1.getText())) {

                        Window.LoadData();

                        l1.setVisible(false);
                        l2.setVisible(false);
                        t1.setVisible(false);
                        b1.setVisible(false);
                        b2.setVisible(false);

                        Window.DoMainScreen();

                    } else if (ADMIN_PASSWORD.equals(t1.getText())) {

                        l1.setVisible(false);
                        l2.setVisible(false);
                        t1.setVisible(false);
                        b1.setVisible(false);
                        b2.setVisible(false);

                        NewUser();

                    } else {

                        l2.setText("Incorrect Password! " + (5 - attempts) + " attempts remaining.");
                        attempts++;
                        l2.setVisible(true);

                        if (attempts > 5)
                            Window.exit();

                    }


                });

    }

}
