import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MainScreenManager extends GraphicsManager {

    private static int mainScreenGroup;
    private static int findGroup;
    private static int selectGroup;
    private static int newClientGroup;

    private static int peopleIdx;
    private static int transactionsIdx;

    private static Set<Client> selectedClients;
    private static Client clientToEdit;

    private static Set<String> currentPeople;

    public static void Init() {

        mainScreenGroup = -1;
        findGroup = -1;
        selectGroup = -1;
        peopleIdx = 0;
        transactionsIdx = 0;

        selectedClients = new TreeSet<>();
        clientToEdit = new Client();

        currentPeople = new TreeSet<>();

        InitMainScreen();
        InitFindClient();
        InitSelectClient();
        InitNewClient();

    }

    // Creates the JComponents for the Main Screen
    private static void InitMainScreen() {

        mainScreenGroup = startGroup();

        Font buttonFont = ARIAL_12;
        Point buttonOffset = new Point(150, 75);

        newLabel("L-mnscrn-hdr", "REAL ESTATE CLIENT MANAGER", new Point(SCREEN_SIZE.width / 2, buttonOffset.y + 65), SCREEN_SIZE.width - buttonOffset.x * 2, 100,
                ARIAL_30, SwingConstants.CENTER);

        newButton("B-mnscrn-nwclnt", "New Client", new Point(buttonOffset.x, buttonOffset.y), 100, 50, buttonFont,
                e -> {

                    showGroup(mainScreenGroup, false);
                    showGroup(newClientGroup, true);
                    //NewClient();

                }

        );

        newButton("B-mnscrn-vwclnt", "View Client", new Point(buttonOffset.x + 120, buttonOffset.y), 100, 50, buttonFont,
                e -> {

                    showGroup(mainScreenGroup, false);
                    DoFindClient();

                }

        );

        newButton("B-mnscrn-imptclnt", "Import Clients", new Point(buttonOffset.x + 255, buttonOffset.y), 125, 50, buttonFont,
                e -> {

                    showGroup(mainScreenGroup, false);
                    //NewClient();

                }

        );

        newButton("B-mnscrn-xptclnt", "Export Clients", new Point(buttonOffset.x + 395, buttonOffset.y), 125, 50, buttonFont,
                e -> {

                    showGroup(mainScreenGroup, false);
                    //NewClient();

                }

        );


        newButton("B-mnscrn-srtclnt", "Sort Clients", new Point(buttonOffset.x + 530, buttonOffset.y), 100, 50, buttonFont,
                e -> {

                    //ClientManager.Sort();
                    showGroup(mainScreenGroup, false);
                    Window.DoMainScreen();

                }

        );

        newButton("B-mnscrn-chgpswd", "Change Password", new Point(buttonOffset.x + 700, buttonOffset.y), 200, 50, buttonFont,
                e -> {

                    showGroup(mainScreenGroup, false);
                    PasswordManager.NewPassword();

                }

        );

        newButton("B-mnscrn-ext", "Exit", new Point(SCREEN_SIZE.width - buttonOffset.x, buttonOffset.y), 100, 50, buttonFont,
                e -> {

                    Window.exit();

                }

        );

        String[] columns = {"Name", "Current Address", "Client Type", "Transaction Address", "Current Status",
                "Last Contact Date"};
        String[][] data = new String[0][6];

        JTable jt = new JTable(new DefaultTableModel(data, columns));
        newJComponent(jt, "TB-mnscrn-dttbl");
        jt.setRowHeight(30);

        JScrollPane jtp = new JScrollPane(jt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        newJComponent(jtp, "SP-mnscrn-dttbl", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 + 50),
                SCREEN_SIZE.width - 200, SCREEN_SIZE.height * 3 / 4, ARIAL_12);

        stopGroup();

        showGroup(mainScreenGroup, false);

    }

    // Creates the JComponents for the Find Client Screen
    private static void InitFindClient() {

        findGroup = startGroup();

        newLabel("L-mnscrn-fnd-pmpt", "Enter client first name", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 - 70), 300, 50,
                ARIAL_20, SwingConstants.CENTER);

        JLabel l1 = newLabel("L-mnscrn-fnd-err", "No client with the specified name!", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 - 30), 500, 50,
                ARIAL_15, SwingConstants.CENTER);
        l1.setForeground(Color.RED);


        newTextField("TF-mnscrn-fnd-inpt", true, "", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 + 30), 300, 50, ARIAL_15);

        newButton("B-mnscrn-fnd-ext", "Exit", new Point(SCREEN_SIZE.width / 2 + 100, SCREEN_SIZE.height / 2 + 100), 100, 50, ARIAL_15,
                e -> {

                    showGroup(findGroup, false);
                    Window.DoMainScreen();

                }

        );

        newButton("B-mnscrn-fnd-fnd", "Find", new Point(SCREEN_SIZE.width / 2 - 100, SCREEN_SIZE.height / 2 + 100), 100, 50, ARIAL_15,
                e -> {

                    ArrayList<Client> foundClients = new ArrayList<>();

                    //Client[] clients = Window.clientManager.getClients();

                    //for (int i = 0; i < clients.length; i++) {

                    //	if (clients[i].equals(new Client(new Person(((JTextField)components.get("TF-mnscrn-fnd-inpt")).getText()))))
                    //		foundClients.add(clients[i]);
                    //	}

                    if (foundClients.size() > 1) {
                        showGroup(findGroup, false);
                        //SelectClient(foundClients);
                    } else if (foundClients.size() == 1) {
                        showGroup(findGroup, false);
                        //EditClient(foundClients.get(0));
                    } else {
                        showComponent("L-mnscrn-fnd-err", true);
                    }

                }

        );

        stopGroup();

        showGroup(findGroup, false);

    }

    // Creates the JComponents for the Select Client Screen
    private static void InitSelectClient() {

        selectGroup = startGroup();

        newLabel("L-mnscrn-slct-pmpt", "Select client", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 - 70), 300, 50,
                ARIAL_20, SwingConstants.CENTER);

        JComboBox c1 = new JComboBox<String>();
        newJComponent(c1, "CB-mnscrn-slct-clnts", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 - 30), 300, 50, ARIAL_15);

        c1.setEditable(false);
        c1.addActionListener(c1);

        newButton("B-mnscrn-slct-ext", "Exit", new Point(SCREEN_SIZE.width / 2 + 100, SCREEN_SIZE.height / 2 + 100), 100, 50, ARIAL_15,
                e -> {

                    showGroup(selectGroup, false);
                    Window.DoMainScreen();

                }

        );

        newButton("B-mnscrn-slct-slct", "Edit", new Point(SCREEN_SIZE.width / 2 - 100, SCREEN_SIZE.height / 2 + 100), 100, 50, ARIAL_15,
                e -> {

                    showGroup(selectGroup, false);
                    //DoEditClient(clientToEdit);

                }

        );

        stopGroup();

        showGroup(selectGroup, false);

    }


    // Prompts the user for new client information
    private static void InitNewClient() {

        newClientGroup = startGroup();

        JComboBox<ClientType> c1 = new JComboBox<>(ClientType.values());
        newJComponent(c1, "CB-mnscrn-nwclnt-trnsn-clnttyp", new Point(SCREEN_SIZE.width / 4 * 3 - 25, 150), 200, 30, ARIAL_15);

        c1.setSelectedItem(ClientType.UNSPECIFIED);
        c1.setEditable(false);
        c1.addActionListener(c1);

        JComboBox<StatusType> c2 = new JComboBox<>(StatusType.values());
        newJComponent(c2, "CB-mnscrn-nwclnt-trnsn-sttstyp", new Point(SCREEN_SIZE.width / 4 * 3 - 25, 250), 200, 30, ARIAL_15);

        c2.setSelectedItem(StatusType.UNKNOWN);
        c2.setEditable(false);
        c2.addActionListener(c2);
/*
		JTextArea ar = new JTextArea();
        newJComponent(ar, "id", new Point(SCREEN_SIZE.width / 4 * 3 + 25, 425), 300, 200, ARIAL_15);
		ar.setEnabled(true);

        newTextField("", true, "2000-01-01", new Point(SCREEN_SIZE.width / 4 * 3, 300), 80, 40, ARIAL_15);

        newTextField("", true, "", new Point(SCREEN_SIZE.width / 4 * 3 + 25, 100), 300, 50, ARIAL_15);
        newLabel("", "Current Address:", new Point(SCREEN_SIZE.width / 4 * 3 - 200, 100), 150, 50, ARIAL_15, SwingConstants.CENTER);

        newLabel("", "Client Type:", new Point(SCREEN_SIZE.width / 4 * 3 - 200, 150), 150, 50, ARIAL_15, SwingConstants.CENTER);

        newTextField("", true, "", new Point(SCREEN_SIZE.width / 4 * 3 + 25, 200), 300, 50, ARIAL_15);
        newLabel("", "Transaction Address:", new Point(SCREEN_SIZE.width / 4 * 3 - 200, 200), 150, 50, ARIAL_15, SwingConstants.CENTER);

        newLabel("", "Current Status:", new Point(SCREEN_SIZE.width / 4 * 3 - 200, 250), 150, 50, ARIAL_15, SwingConstants.CENTER);

        newLabel("", "Last Contact Date (YYYY-MM-DD):", new Point(SCREEN_SIZE.width / 4 * 3 - 150, 300), 250, 50, ARIAL_15, SwingConstants.CENTER);

        newLabel("", "Notes:", new Point(SCREEN_SIZE.width / 4 * 3 - 200, 350), 150, 50, ARIAL_15, SwingConstants.CENTER);

        newButton("", "Today", new Point(SCREEN_SIZE.width / 4 * 3 + 110, 300), 75, 40, ARIAL_15,
                e -> {

				LocalDateTime now = LocalDateTime.now();

                    //t4.setText(now.getYear() + "-" + now.getMonth().getValue() + "-" + now.getDayOfMonth());

			}

        );
*/

        JPanel personPanel = new JPanel();
        personPanel.setLayout(new BoxLayout(personPanel, BoxLayout.Y_AXIS));

        personPanel.add(newPersonPanel("P-mnscrn-nwclnt-psn0", new Point(0, 0)));
        personPanel.add(newPersonPanel("P-mnscrn-nwclnt-psn1", new Point(0, 0)));
        personPanel.add(newPersonPanel("P-mnscrn-nwclnt-psn2", new Point(0, 0)));
        personPanel.add(newPersonPanel("P-mnscrn-nwclnt-psn3", new Point(0, 0)));
        personPanel.add(newPersonPanel("P-mnscrn-nwclnt-psn4", new Point(0, 0)));
        personPanel.add(newPersonPanel("P-mnscrn-nwclnt-psn5", new Point(0, 0)));
        personPanel.add(newPersonPanel("P-mnscrn-nwclnt-psn6", new Point(0, 0)));

        personPanel.setPreferredSize(new Dimension(400, 200 * currentPeople.size()));

        JScrollPane jpp = new JScrollPane(personPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        newJComponent(jpp, "SP-mnscrn-nwclnt-psn", new Point(SCREEN_SIZE.width / 4 + 100, SCREEN_SIZE.height / 2 - 50), 400, SCREEN_SIZE.height / 2 - 100, ARIAL_15);

        newButton("B-mnscrn-nwclnt-sv", "Save", new Point(200, SCREEN_SIZE.height * 6 / 7), 100, 50, ARIAL_15,
                e -> {

                    //Window.clientManager.addClient(getClient());
                    showGroup(newClientGroup, false);

                    Window.DoMainScreen();

                }

        );

        newButton("B-mnscrn-nwclnt-dcd", "Discard", new Point(SCREEN_SIZE.width - 200, SCREEN_SIZE.height * 6 / 7), 100, 50, ARIAL_15,
                e -> {

                    showGroup(newClientGroup, false);
                    Window.DoMainScreen();

                }

        );

        newButton("B-mnscrn-nwclnt-nwpsn", "New Person", new Point(SCREEN_SIZE.width / 4 + 100, SCREEN_SIZE.height * 5 / 7), 175, 50, ARIAL_15,
                e -> {

                    personPanel.add(newPersonPanel("P-mnscrn-nwclnt-psn" + peopleIdx, new Point(0, 0)));
                    personPanel.setPreferredSize(new Dimension(400, 200 * currentPeople.size()));

                }

        );

        stopGroup();

        showGroup(newClientGroup, false);

    }

/*
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

		b5 = newButton("Remove", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height * 6 / 7), 100, 50, new ActionListener() {

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

*/


    public static void DoMainScreen(Client[] clients) {

        showGroup(mainScreenGroup, true);

        String[] columns = {"Name", "Current Address", "Client Type", "Transaction Address", "Current Status",
                "Last Contact Date"};
        String[][] data = new String[clients.length][6];

        JTable jt = (JTable) components.get("TB-mnscrn-dttbl");
        DefaultTableModel tm = (DefaultTableModel) jt.getModel();

        tm.setRowCount(0);

        for (int i = 0; i < clients.length; i++) {

            Transaction trans = new Transaction()/*clients[i].getLastTransaction()*/;

            tm.addRow(new String[]{clients[i].getClient().getName(),
                    clients[i].getCurrentAddress(),
                    trans.getClientType().toString(),
                    trans.getTransactionAddress(),
                    trans.getCurrentStatus().toString(),
                    clients[i].getLastContactDateString()
            });

        }

        ((AbstractTableModel) tm).fireTableDataChanged();

    }

    // Shows the Find Client Interface
    private static void DoFindClient() {

        showGroup(findGroup, true);
        showComponent("L-mnscrn-fnd-err", false);
        ((JTextField) components.get("TF-mnscrn-fnd-inpt")).setText("");

    }

    // Shows the Select Client Interface
    private static void DoSelectClient(ArrayList<Client> clients) {

        JComboBox c1 = (JComboBox) components.get("CB-mnscrn-slct-clnts");

        c1.removeAllItems();

        for (int i = 0; i < clients.size(); i++) {

            c1.addItem(clients.get(i).getClient().getName() + ": " + clients.get(i).getCurrentAddress());

        }

    }

    private static JPanel newPersonPanel(String id, Point location) {

        JPanel panel = startPanel(id, location, 380, 200);

        newLabel(id + "-fnmpmpt", "First Name:", new Point(55, 25), 150, 50, ARIAL_15, SwingConstants.CENTER);
        newTextField(id + "-fnm", true, "", new Point(60, 70), 100, 40, ARIAL_15);

        newLabel(id + "-mintpmpt", "Middle Initial:", new Point(165, 25), 150, 50, ARIAL_15, SwingConstants.CENTER);
        newTextField(id + "-mint", true, "", new Point(160, 70), 40, 40, ARIAL_15);

        newLabel(id + "-lnmpmpt", "Last Name:", new Point(255, 25), 150, 50, ARIAL_15, SwingConstants.CENTER);
        newTextField(id + "-lnm", true, "", new Point(260, 70), 100, 40, ARIAL_15);

        newLabel(id + "-rltnpmpt", "Relation:", new Point(55, 115), 150, 50, ARIAL_15, SwingConstants.CENTER);
        newTextField(id + "-rltn", true, "", new Point(60, 160), 100, 40, ARIAL_15);

        newLabel(id + "-brthdypmpt", "Birthday (YYYY-MM-DD):", new Point(225, 115), 250, 50, ARIAL_15, SwingConstants.CENTER);
        JTextField tf1 = newTextField(id + "-brthdy", true, "2000-01-01", new Point(225, 160), 120, 40, ARIAL_15);
        tf1.setHorizontalAlignment(SwingConstants.CENTER);

        JButton b1 = newButton(id + "-rmv", "X", new Point(350, 20), 40, 25, ARIAL_9,
                e -> {
                    //Some action
                    currentPeople.remove(id);
                    panel.getParent().setPreferredSize(new Dimension(380, 200 * currentPeople.size()));

                    removeFromGroup(id, newClientGroup);
                    removeComponent(id);

                }

        );

        b1.setForeground(Color.WHITE);
        b1.setBackground(Color.RED);

        stopPanel();

        currentPeople.add(id);
        peopleIdx++;

        return panel;

    }

}
