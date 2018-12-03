import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

public class MainScreenManager extends GraphicsManager {

	/**
	 * Displays the main screen
	 * 
	 * @param clients
	 *            Clients to be displayed
	 */
	public static void MainScreen(Client[] clients) {

		b1 = newButton("New Client", new Point(100, 75), 100, 50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				HideMainScreen();
				NewClient();

			}

		});

		b2 = newButton("View Client", new Point(220, 75), 100, 50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				HideMainScreen();

				FindClient();

			}

		});

		/*
		 * b3 = newButton("Import Clients", new Point(355, 75), 125, 50, new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * HideMainScreen(); NewClient();
		 * 
		 * }
		 * 
		 * });
		 * 
		 * b4 = newButton("Export Clients", new Point(495, 75), 125, 50, new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * HideMainScreen(); NewClient();
		 * 
		 * }
		 * 
		 * });
		 */

		b5 = newButton("Sort Clients", new Point(630, 75), 100, 50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ClientManager.Sort();
				HideMainScreen();
				Window.DoMainScreen();

			}

		});

		b6 = newButton("Change Password", new Point(800, 75), 200, 50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				HideMainScreen();
				PasswordManager.NewPassword();

			}

		});

		b7 = newButton("Exit", new Point(screen.width - 100, 75), 100, 50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Window.exit();

			}

		});

		String column[] = { "", "", "", "", "", "" };

		String[][] data = new String[clients.length + 1][6];

		data[0] = new String[] { "Name", "Current Address", "Client Type", "Transaction Address", "Current Status",
				"Last Contact Date" };

		for (int i = 0; i < clients.length; i++) {

			String transaction, type, status;

			Transaction trans = clients[i].getTransaction();

			transaction = trans.getTransactionAddress();
			type = trans.getClientType().toString();
			status = trans.getCurrentStatus().toString();

			data[i + 1] = new String[] {

					clients[i].getClient().getName(), clients[i].getCurrentAddress(), type, transaction, status,
					clients[i].getLastContactDateString() };

		}

		jt = new JTable(data, column);
		jt.setFont(new Font("Arial", Font.PLAIN, 12));
		jt.setRowHeight(30);
		jt.setBounds(50, 200, screen.width - 100, screen.height - 300);

		jtp = new JScrollPane(jt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jtp.setBounds(50, 200, screen.width - 100, screen.height - 300);

		Component[] comps = { b1, b2, /* b3, b4, */ b5, b6, b7, jtp };

		Window.AddComponents(comps);

		((AbstractTableModel) jt.getModel()).fireTableDataChanged();

	}

	// Prompts the user for a name in order to find a client
	private static void FindClient() {

		l1 = newLabel("Enter client first name", new Point(screen.width / 2, screen.height / 2 - 70), 300, 50);
		l1.setFont(new Font("Arial", Font.PLAIN, 20));
		l1.setHorizontalAlignment(SwingConstants.CENTER);

		l2 = newLabel("No client with the specified name", new Point(screen.width / 2, screen.height / 2 + 30), 500,
				50);
		l2.setFont(new Font("Arial", Font.PLAIN, 15));
		l2.setForeground(Color.RED);
		l2.setVisible(false);
		l2.setHorizontalAlignment(SwingConstants.CENTER);

		t1 = newTextField(true, "", new Point(screen.width / 2, screen.height / 2 - 30), 300, 50);

		b1 = newButton("Exit", new Point(screen.width / 2 + 100, screen.height / 2 + 100), 100, 50,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						HideFind();
						Window.DoMainScreen();

					}

				});

		b2 = newButton("Find", new Point(screen.width / 2 - 100, screen.height / 2 + 100), 100, 50,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						ArrayList<Client> foundClients = new ArrayList<Client>();

						Client[] clients = Window.clientManager.getClients();

						for (int i = 0; i < clients.length; i++) {

							if (clients[i].equals(new Client(new Person(t1.getText()))))
								foundClients.add(clients[i]);

						}

						if (foundClients.size() > 1) {
							HideFind();
							SelectClient(foundClients);
						} else if (foundClients.size() == 1) {
							HideFind();
							EditClient(foundClients.get(0));
						} else {
							l2.setVisible(true);
						}

					}

				});

		Component[] comps = { t1, l1, b1, l2, b2 };

		Window.AddComponents(comps);

	}

	// Prompts the user to select a client if the find results in more than one
	// client
	private static void SelectClient(ArrayList<Client> clients) {

		l1 = newLabel("Select client", new Point(screen.width / 2, screen.height / 2 - 70), 300, 50);
		l1.setFont(new Font("Arial", Font.PLAIN, 20));
		l1.setHorizontalAlignment(SwingConstants.CENTER);

		String[] names = new String[clients.size()];

		for (int i = 0; i < names.length; i++) {

			names[i] = clients.get(i).getClient().getName() + " " + clients.get(i).getCurrentAddress();

		}

		c1 = new JComboBox<String>(names);
		c1.setEditable(false);
		c1.addActionListener(c1);
		c1.setBounds(screen.width / 2 - 150, screen.height / 2 - 55, 300, 50);

		b1 = newButton("Exit", new Point(screen.width / 2 + 100, screen.height / 2 + 100), 100, 50,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						HideSelect();
						Window.DoMainScreen();

					}

				});

		b2 = newButton("Edit", new Point(screen.width / 2 - 100, screen.height / 2 + 100), 100, 50,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						HideSelect();
						EditClient(clients.get(c1.getSelectedIndex()));

					}

				});

		Component[] comps = { c1, l1, b1, l2, b2 };

		Window.AddComponents(comps);

	}

	// Prompts the user for new client information
	private static void NewClient() {

		people = 1;

		c1 = new JComboBox<ClientType>(ClientType.values());
		c1.setSelectedItem(ClientType.UNSPECIFIED);
		c1.setEditable(false);
		c1.addActionListener(c1);
		c1.setBounds(screen.width / 4 * 3 - 125, 150 - 15, 200, 30);

		c2 = new JComboBox<StatusType>(StatusType.values());
		c2.setSelectedItem(StatusType.UNKNOWN);
		c2.setEditable(false);
		c2.addActionListener(c2);
		c2.setBounds(screen.width / 4 * 3 - 125, 250 - 15, 200, 30);

		t1 = newTextField(true, "", new Point(screen.width / 4 * 3 + 25, 200), 300, 50);
		t2 = newTextField(true, "", new Point(screen.width / 4 * 3 + 25, 100), 300, 50);
		t3 = newTextField(true, "", new Point(screen.width / 4 * 3 + 25, 425), 300, 200);
		t4 = newTextField(true, "2000-01-01", new Point(screen.width / 4 * 3, 300), 80, 40);

		l1 = newLabel("Current Address:", new Point(screen.width / 4 * 3 - 200, 100), 150, 50);
		l1.setFont(new Font("Arial", Font.PLAIN, 15));

		l2 = newLabel("Client Type:", new Point(screen.width / 4 * 3 - 200, 150), 150, 50);
		l2.setFont(new Font("Arial", Font.PLAIN, 15));

		l3 = newLabel("Transaction Address:", new Point(screen.width / 4 * 3 - 200, 200), 150, 50);
		l3.setFont(new Font("Arial", Font.PLAIN, 15));

		l4 = newLabel("Current Status:", new Point(screen.width / 4 * 3 - 200, 250), 150, 50);
		l4.setFont(new Font("Arial", Font.PLAIN, 15));

		l5 = newLabel("Last Contact Date (YYYY-MM-DD):", new Point(screen.width / 4 * 3 - 150, 300), 250, 50);
		l5.setFont(new Font("Arial", Font.PLAIN, 15));

		l6 = newLabel("Notes:", new Point(screen.width / 4 * 3 - 200, 350), 150, 50);
		l6.setFont(new Font("Arial", Font.PLAIN, 15));

		b1 = newButton("Today", new Point(screen.width / 4 * 3 + 110, 300), 75, 40, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				LocalDateTime now = LocalDateTime.now();

				t4.setText(now.getYear() + "-" + now.getMonth().getValue() + "-" + now.getDayOfMonth());

			}

		});

		b2 = newButton("Save", new Point(200, screen.height * 6 / 7), 100, 50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Window.clientManager.addClient(getClient());

				HideClient();
				Window.DoMainScreen();

			}

		});

		b3 = newButton("Discard", new Point(screen.width - 200, screen.height * 6 / 7), 100, 50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				HideClient();
				Window.DoMainScreen();

			}

		});

		b4 = newButton("New Person", new Point(screen.width / 4, 300), 175, 50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				b4.setBounds(b4.getBounds().x, b4.getBounds().y + 150, 175, 50);

				switch (people) {

				case 1:
					l13.setVisible(true);
					l14.setVisible(true);
					l15.setVisible(true);
					l16.setVisible(true);
					l17.setVisible(true);
					t9.setVisible(true);
					t10.setVisible(true);
					t11.setVisible(true);
					t12.setVisible(true);
					t13.setVisible(true);
					b6.setVisible(true);
					break;

				case 2:
					l18.setVisible(true);
					l19.setVisible(true);
					l20.setVisible(true);
					l21.setVisible(true);
					l22.setVisible(true);
					t14.setVisible(true);
					t15.setVisible(true);
					t16.setVisible(true);
					t17.setVisible(true);
					t18.setVisible(true);
					b4.setEnabled(false);
					b4.setVisible(false);
					b7.setVisible(true);
					break;

				}

				people++;

			}

		});

		t5 = newTextField(true, "", new Point(screen.width / 4 - 100, 150), 100, 40);
		t6 = newTextField(true, "", new Point(screen.width / 4, 150), 40, 40);
		t7 = newTextField(true, "", new Point(screen.width / 4 + 100, 150), 100, 40);

		l7 = newLabel("First Name:", new Point(screen.width / 4 - 75, 100), 150, 50);
		l7.setFont(new Font("Arial", Font.PLAIN, 15));

		l8 = newLabel("Middle Initial:", new Point(screen.width / 4 + 25, 100), 150, 50);
		l8.setFont(new Font("Arial", Font.PLAIN, 15));

		l9 = newLabel("Last Name:", new Point(screen.width / 4 + 125, 100), 150, 50);
		l9.setFont(new Font("Arial", Font.PLAIN, 15));

		l10 = newLabel("Relation:", new Point(screen.width / 4 - 125, 200), 150, 50);
		l10.setFont(new Font("Arial", Font.PLAIN, 15));

		l11 = newLabel("Client", new Point(screen.width / 4 - 50, 200), 150, 50);
		l11.setFont(new Font("Arial", Font.PLAIN, 15));

		t8 = newTextField(true, "2000-01-01", new Point(screen.width / 4 + 170, 200), 80, 40);

		l12 = newLabel("Birthday (YYYY-MM-DD):", new Point(screen.width / 4 + 90, 200), 250, 50);
		l12.setFont(new Font("Arial", Font.PLAIN, 15));

		t9 = newTextField(true, "", new Point(screen.width / 4 - 100, 300), 100, 40);
		t10 = newTextField(true, "", new Point(screen.width / 4, 300), 40, 40);
		t11 = newTextField(true, "", new Point(screen.width / 4 + 100, 300), 100, 40);

		l13 = newLabel("First Name:", new Point(screen.width / 4 - 75, 250), 150, 50);
		l13.setFont(new Font("Arial", Font.PLAIN, 15));

		l14 = newLabel("Middle Initial:", new Point(screen.width / 4 + 25, 250), 150, 50);
		l14.setFont(new Font("Arial", Font.PLAIN, 15));

		l15 = newLabel("Last Name:", new Point(screen.width / 4 + 125, 250), 150, 50);
		l15.setFont(new Font("Arial", Font.PLAIN, 15));

		b6 = newButton("X", new Point(screen.width / 4 + 155, 250), 40, 25, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				b4.setBounds(b4.getBounds().x, b4.getBounds().y - 150, 175, 50);
				b4.setVisible(true);
				b4.setEnabled(true);

				switch (people) {

				case 2:
					b6.setVisible(false);
					l13.setVisible(false);
					l14.setVisible(false);
					l15.setVisible(false);
					l16.setVisible(false);
					l17.setVisible(false);
					t9.setVisible(false);
					t10.setVisible(false);
					t11.setVisible(false);
					t12.setVisible(false);
					t13.setVisible(false);
					t9.setText("");
					t10.setText("");
					t11.setText("");
					t12.setText("");
					t13.setText("2000-01-01");
					people--;
					break;

				case 3:
					l18.setVisible(false);
					l19.setVisible(false);
					l20.setVisible(false);
					l21.setVisible(false);
					l22.setVisible(false);
					t14.setVisible(false);
					t15.setVisible(false);
					t16.setVisible(false);
					t17.setVisible(false);
					t18.setVisible(false);
					b7.setVisible(false);
					t9.setText(t14.getText());
					t10.setText(t15.getText());
					t11.setText(t16.getText());
					t12.setText(t17.getText());
					t13.setText(t18.getText());
					t14.setText("");
					t15.setText("");
					t16.setText("");
					t17.setText("");
					t18.setText("2000-01-01");
					people--;
					break;

				}

			}

		});
		b6.setFont(new Font("Arial", Font.PLAIN, 9));
		b6.setForeground(Color.WHITE);
		b6.setBackground(Color.RED);

		l16 = newLabel("Relation:", new Point(screen.width / 4 - 150, 350), 150, 50);
		l16.setFont(new Font("Arial", Font.PLAIN, 15));

		t12 = newTextField(true, "", new Point(screen.width / 4 - 100, 350), 100, 40);

		t13 = newTextField(true, "2000-01-01", new Point(screen.width / 4 + 170, 350), 80, 40);

		l17 = newLabel("Birthday (YYYY-MM-DD):", new Point(screen.width / 4 + 90, 350), 250, 50);
		l17.setFont(new Font("Arial", Font.PLAIN, 15));

		t14 = newTextField(true, "", new Point(screen.width / 4 - 100, 450), 100, 40);
		t15 = newTextField(true, "", new Point(screen.width / 4, 450), 40, 40);
		t16 = newTextField(true, "", new Point(screen.width / 4 + 100, 450), 100, 40);

		l18 = newLabel("First Name:", new Point(screen.width / 4 - 75, 400), 150, 50);
		l18.setFont(new Font("Arial", Font.PLAIN, 15));

		l19 = newLabel("Middle Initial:", new Point(screen.width / 4 + 25, 400), 150, 50);
		l19.setFont(new Font("Arial", Font.PLAIN, 15));

		l20 = newLabel("Last Name:", new Point(screen.width / 4 + 125, 400), 150, 50);
		l20.setFont(new Font("Arial", Font.PLAIN, 15));

		b7 = newButton("X", new Point(screen.width / 4 + 155, 400), 40, 25, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				b4.setBounds(b4.getBounds().x, b4.getBounds().y - 150, 175, 50);
				b4.setVisible(true);
				b4.setEnabled(true);

				l18.setVisible(false);
				l19.setVisible(false);
				l20.setVisible(false);
				l21.setVisible(false);
				l22.setVisible(false);
				t14.setVisible(false);
				t15.setVisible(false);
				t16.setVisible(false);
				t17.setVisible(false);
				t18.setVisible(false);
				b7.setVisible(false);
				t14.setText("");
				t15.setText("");
				t16.setText("");
				t17.setText("");
				t18.setText("2000-01-01");
				people--;

			}

		});
		b7.setFont(new Font("Arial", Font.PLAIN, 9));
		b7.setForeground(Color.WHITE);
		b7.setBackground(Color.RED);

		l21 = newLabel("Relation:", new Point(screen.width / 4 - 150, 500), 150, 50);
		l21.setFont(new Font("Arial", Font.PLAIN, 15));

		t17 = newTextField(true, "", new Point(screen.width / 4 - 100, 500), 100, 40);

		t18 = newTextField(true, "2000-01-01", new Point(screen.width / 4 + 170, 500), 80, 40);

		l22 = newLabel("Birthday (YYYY-MM-DD):", new Point(screen.width / 4 + 90, 500), 250, 50);
		l22.setFont(new Font("Arial", Font.PLAIN, 15));

		l13.setVisible(false);
		l14.setVisible(false);
		l15.setVisible(false);
		l16.setVisible(false);
		l17.setVisible(false);
		l18.setVisible(false);
		l19.setVisible(false);
		l20.setVisible(false);
		l21.setVisible(false);
		l22.setVisible(false);

		t9.setVisible(false);
		t10.setVisible(false);
		t11.setVisible(false);
		t12.setVisible(false);
		t13.setVisible(false);
		t14.setVisible(false);
		t15.setVisible(false);
		t16.setVisible(false);
		t17.setVisible(false);
		t18.setVisible(false);

		b6.setVisible(false);
		b7.setVisible(false);

		Component[] comps = { c1, c2, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18,
				l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, b1,
				b2, b3, b4, b6, b7 };

		Window.AddComponents(comps);

	}

	// Prompts the user to edit an existing client
	@SuppressWarnings("deprecation")
	private static void EditClient(Client clientToEdit) {

		NewClient();

		b2.removeActionListener(b2.getActionListeners()[0]);
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setClient(clientToEdit, getClient());

				HideClient();
				Window.DoMainScreen();

			}

		});

		people = clientToEdit.getPeople().length;

		c1.setSelectedItem(clientToEdit.getTransaction().getClientType());

		c2.setSelectedItem(clientToEdit.getTransaction().getCurrentStatus());

		Date lctDate = clientToEdit.getLastContactDate().getTime();

		t1.setText(clientToEdit.getTransaction().getTransactionAddress());
		t2.setText(clientToEdit.getCurrentAddress());
		t3.setText(clientToEdit.getNotes());
		t4.setText((lctDate.getYear() + 1900) + "-" + (lctDate.getMonth() + 1) + "-" + lctDate.getDate());

		b3.setText("Exit");

		b5 = newButton("Remove", new Point(screen.width / 2, screen.height * 6 / 7), 100, 50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				removeClient(clientToEdit);

				HideClient();
				Window.DoMainScreen();

			}

		});

		String client1name = clientToEdit.getPeople()[0].getName();
		Date bthDate1 = clientToEdit.getPeople()[0].getBirthday().getTime();

		t5.setText(client1name.substring(0, client1name.indexOf(" ")));
		t6.setText((client1name.contains("."))
				? (client1name.substring(client1name.indexOf(".") - 1, client1name.indexOf(".")))
				: "");
		t7.setText(client1name.substring(client1name.lastIndexOf(" "), client1name.length()));
		t8.setText((bthDate1.getYear() + 1900) + "-" + (bthDate1.getMonth() + 1) + "-" + bthDate1.getDate());

		switch (people) {

		case 3:

			l18.setVisible(true);
			l19.setVisible(true);
			l20.setVisible(true);
			l21.setVisible(true);
			l22.setVisible(true);
			t14.setVisible(true);
			t15.setVisible(true);
			t16.setVisible(true);
			t17.setVisible(true);
			t18.setVisible(true);
			b7.setVisible(true);

			b4.setBounds(b4.getBounds().x, b4.getBounds().y + 150, 175, 50);
			b4.setVisible(false);
			String client3name = clientToEdit.getPeople()[2].getName();
			Date bthDate3 = clientToEdit.getPeople()[2].getBirthday().getTime();

			t14.setText(client3name.substring(0, client3name.indexOf(" ")));
			t15.setText((client3name.contains("."))
					? (client3name.substring(client3name.indexOf(".") - 1, client3name.indexOf(".")))
					: "");
			t16.setText(client3name.substring(client3name.lastIndexOf(" "), client3name.length()));
			t17.setText(clientToEdit.getPeople()[2].getRelation());
			t18.setText((bthDate3.getYear() + 1900) + "-" + (bthDate3.getMonth() + 1) + "-" + bthDate3.getDate());

		case 2:

			t9.setVisible(true);
			t10.setVisible(true);
			t11.setVisible(true);
			t12.setVisible(true);
			t13.setVisible(true);
			l13.setVisible(true);
			l14.setVisible(true);
			l15.setVisible(true);
			l16.setVisible(true);
			l17.setVisible(true);
			b6.setVisible(true);

			b4.setBounds(b4.getBounds().x, b4.getBounds().y + 150, 175, 50);
			String client2name = clientToEdit.getPeople()[1].getName();
			Date bthDate2 = clientToEdit.getPeople()[1].getBirthday().getTime();

			t9.setText(client2name.substring(0, client2name.indexOf(" ")));
			t10.setText((client2name.contains("."))
					? (client2name.substring(client2name.indexOf(".") - 1, client2name.indexOf(".")))
					: "");
			t11.setText(client2name.substring(client2name.lastIndexOf(" "), client2name.length()));
			t12.setText(clientToEdit.getPeople()[1].getRelation());
			t13.setText((bthDate2.getYear() + 1900) + "-" + (bthDate2.getMonth() + 1) + "-" + bthDate2.getDate());

		}

		Component[] comps = { c1, c2, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18,
				l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, b1,
				b2, b3, b4, b5, b6, b7 };

		Window.AddComponents(comps);

	}

	// Replaces the current client (client1) with the new client (client2)
	private static void setClient(Client client1, Client client2) {

		removeClient(client1);
		Window.clientManager.addClient(client2);
		Window.SaveData();

	}

	// Removes the client that was being viewed
	private static void removeClient(Client client1) {

		Window.clientManager.removeClient(Window.clientManager.getClientIndex(client1.getClient()));
		Window.SaveData();

	}

	// Gets the client object from the information passed by the user
	private static Client getClient() {

		ArrayList<Person> persons = new ArrayList<Person>();

		switch (people) {

		case 3:
			char initial3;

			if (t15.getText().length() < 1)
				initial3 = '\u0000';
			else
				initial3 = t15.getText().charAt(0);

			String birthdayDate1 = t18.getText();
			persons.add(0, new Person(t14.getText(), initial3, t16.getText(), t17.getText(), new GregorianCalendar(
					Integer.parseInt(birthdayDate1.substring(0, birthdayDate1.indexOf("-"))),
					Integer.parseInt(
							birthdayDate1.substring(birthdayDate1.indexOf("-") + 1, birthdayDate1.lastIndexOf("-")))
							- 1,
					Integer.parseInt(
							birthdayDate1.substring(birthdayDate1.lastIndexOf("-") + 1, birthdayDate1.length()))),
					false));

		case 2:
			char initial2;

			if (t10.getText().length() < 1)
				initial2 = '\u0000';
			else
				initial2 = t10.getText().charAt(0);

			String birthdayDate2 = t13.getText();
			persons.add(0, new Person(t9.getText(), initial2, t11.getText(), t12.getText(), new GregorianCalendar(
					Integer.parseInt(birthdayDate2.substring(0, birthdayDate2.indexOf("-"))),
					Integer.parseInt(
							birthdayDate2.substring(birthdayDate2.indexOf("-") + 1, birthdayDate2.lastIndexOf("-")))
							- 1,
					Integer.parseInt(
							birthdayDate2.substring(birthdayDate2.lastIndexOf("-") + 1, birthdayDate2.length()))),
					false));

		case 1:
			char initial1;

			if (t6.getText().length() < 1)
				initial1 = '\u0000';
			else
				initial1 = t6.getText().charAt(0);

			String birthdayDate3 = t8.getText();
			persons.add(0, new Person(t5.getText(), initial1, t7.getText(), "Client", new GregorianCalendar(
					Integer.parseInt(birthdayDate3.substring(0, birthdayDate3.indexOf("-"))),
					Integer.parseInt(
							birthdayDate3.substring(birthdayDate3.indexOf("-") + 1, birthdayDate3.lastIndexOf("-")))
							- 1,
					Integer.parseInt(
							birthdayDate3.substring(birthdayDate3.lastIndexOf("-") + 1, birthdayDate3.length()))),
					true));

		}

		String lastContactDate = t4.getText();

		return new Client(false, persons, t1.getText(),
				new Transaction((ClientType) c1.getSelectedItem(), t2.getText(), (StatusType) c2.getSelectedItem()),
				new GregorianCalendar(Integer.parseInt(lastContactDate.substring(0, lastContactDate.indexOf("-"))),
						Integer.parseInt(lastContactDate.substring(lastContactDate.indexOf("-") + 1,
								lastContactDate.lastIndexOf("-"))) - 1,
						Integer.parseInt(lastContactDate.substring(lastContactDate.lastIndexOf("-") + 1,
								lastContactDate.length()))),
				t3.getText());

	}

	// Hides the Find GUI
	private static void HideFind() {

		l1.setVisible(false);
		l2.setVisible(false);
		t1.setVisible(false);
		b1.setVisible(false);
		b2.setVisible(false);

	}

	// Hides the Select GUI
	private static void HideSelect() {

		l1.setVisible(false);
		t1.setVisible(false);
		b1.setVisible(false);
		b2.setVisible(false);
		c1.setVisible(false);

	}

	// Hides the New Client and Edit Client GUI
	private static void HideClient() {

		b1.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(false);
		b6.setVisible(false);
		b7.setVisible(false);

		l1.setVisible(false);
		l2.setVisible(false);
		l3.setVisible(false);
		l4.setVisible(false);
		l5.setVisible(false);
		l6.setVisible(false);
		l7.setVisible(false);
		l8.setVisible(false);
		l9.setVisible(false);
		l10.setVisible(false);
		l11.setVisible(false);
		l12.setVisible(false);
		l13.setVisible(false);
		l14.setVisible(false);
		l15.setVisible(false);
		l16.setVisible(false);
		l17.setVisible(false);
		l18.setVisible(false);
		l19.setVisible(false);
		l20.setVisible(false);
		l21.setVisible(false);
		l22.setVisible(false);

		t1.setVisible(false);
		t2.setVisible(false);
		t3.setVisible(false);
		t4.setVisible(false);
		t5.setVisible(false);
		t6.setVisible(false);
		t7.setVisible(false);
		t8.setVisible(false);
		t9.setVisible(false);
		t10.setVisible(false);
		t11.setVisible(false);
		t12.setVisible(false);
		t13.setVisible(false);
		t14.setVisible(false);
		t15.setVisible(false);
		t16.setVisible(false);
		t17.setVisible(false);
		t18.setVisible(false);

		c1.setVisible(false);
		c2.setVisible(false);

	}

	// Hides the Main Screen GUI
	private static void HideMainScreen() {

		b1.setVisible(false);
		b2.setVisible(false);
		// b3.setVisible(false);
		// b4.setVisible(false);
		b5.setVisible(false);
		b6.setVisible(false);
		b7.setVisible(false);

		jtp.setVisible(false);

	}

}
