import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

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

		password = EncryptionManager.Decrypt(EncryptionManager.PASSWORD_FILE_NAME);

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

		JLabel l1, l2;

		loggingIn = true;

		l1 = newLabel("L-pswd", "Enter a Password", new Point(screen.width / 2, screen.height / 2 - 70), 300, 50, new Font("Arial", Font.PLAIN, 20));
		l1.setHorizontalAlignment(SwingConstants.CENTER);

		l2 = newLabel("", "Password doesn't fit minimum character requirement (4 characters)",
			new Point(screen.width / 2, screen.height / 2 - 30), 500, 50, new Font("Arial", Font.PLAIN, 15));
		l2.setForeground(Color.RED);
		l2.setVisible(false);
		l2.setHorizontalAlignment(SwingConstants.CENTER);

		newTextField("TF-pswd", true, "", new Point(screen.width / 2, screen.height / 2 + 30), 300, 50, new Font("Arial", Font.PLAIN, 15));

		b1 = newButton("Set Password", new Point(screen.width / 2, screen.height / 2 + 100), 300, 50,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if (t1.getText().length() >= 4) {
							password = t1.getText();

							EncryptionManager.Encrypt(password, EncryptionManager.PASSWORD_FILE_NAME);

							l1.setVisible(false);
							t1.setVisible(false);
							b1.setVisible(false);
							l2.setVisible(false);

							Window.LoadData();
							Window.DoMainScreen();

						} else {

							l2.setVisible(true);

						}

					}

				});

		Component[] comps = { t1, l1, b1, l2 };

		Window.AddComponents(comps);

	}

	// Prompts the user to enter their password to login
	private static void DoLogin() {

		loggingIn = true;

		l1 = newLabel("Enter your Password", new Point(screen.width / 2, screen.height / 2 - 70), 300, 50);
		l1.setFont(new Font("Arial", Font.PLAIN, 20));
		l1.setHorizontalAlignment(SwingConstants.CENTER);

		l2 = newLabel("Incorrect Password", new Point(screen.width / 2, screen.height / 2 - 30), 300, 50);
		l2.setFont(new Font("Arial", Font.PLAIN, 15));
		l2.setForeground(Color.RED);
		l2.setVisible(false);
		l2.setHorizontalAlignment(SwingConstants.CENTER);

		t1 = newTextField(true, "", new Point(screen.width / 2, screen.height / 2 + 30), 300, 50);

		b1 = newButton("Exit", new Point(screen.width / 2 + 100, screen.height / 2 + 100), 100, 50,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						Window.exit();

					}

				});

		b2 = newButton("Login", new Point(screen.width / 2 - 100, screen.height / 2 + 100), 100, 50,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

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

					}

				});

		Component[] comps = { t1, l1, b1, l2, b2 };

		Window.AddComponents(comps);

	}

}
