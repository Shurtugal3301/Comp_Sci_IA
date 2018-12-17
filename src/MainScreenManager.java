import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class MainScreenManager extends GraphicsManager {

    private static int mainScreenGroup;
    private static int findGroup;
    private static int selectGroup;
    private static int newClientGroup;
    private static int editClientGroup;

    private static int peopleIdx;
    private static int transactionsIdx;

    private static ArrayList<Client> selectedClients;
    private static ArrayList<Integer> selectedClientsIndex;
    private static Client clientToEdit;
    private static int clientToEditIndex;

    private static Set<String> currentPeople;
    private static Set<String> currentTransactions;

    /**
     * Initializes the different screens
     */
    public static void Init() {

        mainScreenGroup = -1;
        findGroup = -1;
        selectGroup = -1;
        peopleIdx = 0;
        transactionsIdx = 0;
        clientToEditIndex = -1;

        selectedClients = new ArrayList<>();
        selectedClientsIndex = new ArrayList<>();
        clientToEdit = new Client();

        currentPeople = new TreeSet<>();
        currentTransactions = new TreeSet<>();

        InitMainScreen();
        InitFindClient();
        InitSelectClient();
        InitNewClient();
        InitEditClient();

    }

    // Creates the JComponents for the Main Screen
    private static void InitMainScreen() {

        mainScreenGroup = startGroup();

        Font buttonFont = ARIAL_12;
        Point buttonOffset = new Point(150, 75);

        newLabel("L-mnscrn-hdr", "REAL ESTATE CLIENT MANAGER", new Point(SCREEN_SIZE.width / 2, buttonOffset.y + SCREEN_SIZE.height / 30 + 20), SCREEN_SIZE.width - buttonOffset.x * 2, 100,
                new Font("Arial", Font.PLAIN, SCREEN_SIZE.height / 30), SwingConstants.CENTER);

        newButton("B-mnscrn-nwclnt", "New Client", new Point(buttonOffset.x, buttonOffset.y), 100, 50, buttonFont,
                e -> {

                    showGroup(mainScreenGroup, false);
                    DoNewClient();

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

                    int result = JOptionPane.showConfirmDialog(Window.getJFrame(), "Save copy of Import Format?");

                    if (result == 0) {

                        if (Window.GetImportFormat())
                            JOptionPane.showMessageDialog(Window.getJFrame(), "Client Import Template Created");
                        else
                            JOptionPane.showMessageDialog(Window.getJFrame(), "Import Template Creation FAILED!");

                    }

                    if (result != 2) {

                        if (Window.ImportClients())
                            JOptionPane.showMessageDialog(Window.getJFrame(), "Client Import Complete");
                        else
                            JOptionPane.showMessageDialog(Window.getJFrame(), "Import FAILED!");

                        showGroup(mainScreenGroup, false);
                        Window.DoMainScreen();

                    }

                }

        );

        newButton("B-mnscrn-xptclnt", "Export Clients", new Point(buttonOffset.x + 395, buttonOffset.y), 125, 50, buttonFont,
                e -> {

                    if (Window.ExportClients())
                        JOptionPane.showMessageDialog(Window.getJFrame(), "Client Export Successful");
                    else
                        JOptionPane.showMessageDialog(Window.getJFrame(), "Export FAILED!");

                }

        );


        newButton("B-mnscrn-srtclnt", "Sort Clients", new Point(buttonOffset.x + 530, buttonOffset.y), 100, 50, buttonFont,
                e -> {

                    ClientManager.Sort();
                    showGroup(mainScreenGroup, false);
                    Window.DoMainScreen();
                    JOptionPane.showMessageDialog(Window.getJFrame(), "Clients Sorted");

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


        JTextField t1 = newTextField("TF-mnscrn-fnd-inpt", true, "", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2 + 30), 300, 50, ARIAL_15);

        newButton("B-mnscrn-fnd-ext", "Exit", new Point(SCREEN_SIZE.width / 2 + 100, SCREEN_SIZE.height / 2 + 100), 100, 50, ARIAL_15,
                e -> {

                    showGroup(findGroup, false);
                    Window.DoMainScreen();

                }

        );

        newButton("B-mnscrn-fnd-fnd", "Find", new Point(SCREEN_SIZE.width / 2 - 100, SCREEN_SIZE.height / 2 + 100), 100, 50, ARIAL_15,
                e -> {

                    Client[] clients = Window.clientManager.getClients();
                    selectedClients.clear();

                    for (int i = 0; i < clients.length; i++) {

                        if (clients[i].equals(new Client(new Person(t1.getText())))) {

                            selectedClients.add(clients[i]);
                            selectedClientsIndex.add(i);

                        }

                    }

                    if (selectedClients.size() > 1) {
                        showGroup(findGroup, false);
                        DoSelectClient();
                    } else if (selectedClients.size() == 1) {
                        showGroup(findGroup, false);
                        clientToEdit = selectedClients.get(0);
                        clientToEditIndex = selectedClientsIndex.get(0);
                        DoEditClient();
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

        JComboBox<String> c1 = new JComboBox<>();
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

                    clientToEdit = selectedClients.get(c1.getSelectedIndex());
                    clientToEditIndex = selectedClientsIndex.get(c1.getSelectedIndex());

                    showGroup(selectGroup, false);
                    DoEditClient();

                }

        );

        stopGroup();

        showGroup(selectGroup, false);

    }

    // Creates the JComponents for the New Client Screen
    private static void InitNewClient() {

        newClientGroup = startGroup();

        newTextField("TF-mnscrn-nwclnt-crrAdd", true, "", new Point(SCREEN_SIZE.width / 4 + 125, 100), 300, 50, ARIAL_15);
        newLabel("L-mnscrn-nwclnt-crrAddPmpt", "Current Address:", new Point(SCREEN_SIZE.width / 4 - 100, 100), 150, 50, ARIAL_15, SwingConstants.CENTER);

        newLabel("L-mnscrn-nwclnt-lcdPmpt", "Last Contact Date (YYYY-MM-DD):", new Point(SCREEN_SIZE.width / 4 * 3 - 160, 100), 250, 50, ARIAL_15, SwingConstants.CENTER);
        JTextField tf = newTextField("TF-mnscrn-nwclnt-lcd", true, "2000-01-01", new Point(SCREEN_SIZE.width / 4 * 3, 100), 80, 40, ARIAL_15);

        newButton("B-mnscrn-nwclnt-lcdStTdy", "Today", new Point(SCREEN_SIZE.width / 4 * 3 + 110, 100), 75, 40, ARIAL_15,
                e -> {

                    LocalDateTime now = LocalDateTime.now();

                    tf.setText(now.getYear() + "-" + now.getMonth().getValue() + "-" + now.getDayOfMonth());

                }

        );

        newLabel("L-mnscrn-nwclnt-ntsPmpt", "Notes:", new Point(SCREEN_SIZE.width / 4 - 100, SCREEN_SIZE.height / 4 * 3 - 40), 150, 50, ARIAL_15, SwingConstants.CENTER);
        JTextArea ar = new JTextArea();
        newJComponent(ar, "TF-mnscrn-nwclnt-nts", new Point(SCREEN_SIZE.width / 4 + 130, SCREEN_SIZE.height / 4 * 3), 400, 100, ARIAL_15);
        ar.setEnabled(true);
        ar.setBackground(Color.LIGHT_GRAY);

        JPanel personPanel = new JPanel();
        personPanel.setLayout(new BoxLayout(personPanel, BoxLayout.Y_AXIS));
        personPanel.setPreferredSize(new Dimension(400, 200 * currentPeople.size()));

        JScrollPane jpp = new JScrollPane(personPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        newJComponent(jpp, "SP-mnscrn-nwclnt-psn", new Point(SCREEN_SIZE.width / 4 + 100, SCREEN_SIZE.height / 2 - 25), 400, SCREEN_SIZE.height / 2 - 100, ARIAL_15);

        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));
        transactionPanel.setPreferredSize(new Dimension(440, 150 * currentTransactions.size()));

        JScrollPane jpt = new JScrollPane(transactionPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        newJComponent(jpt, "SP-mnscrn-nwclnt-trsn", new Point(SCREEN_SIZE.width / 4 * 3 - 100, SCREEN_SIZE.height / 2 - 25), 440, SCREEN_SIZE.height / 2 - 100, ARIAL_15);

        newButton("B-mnscrn-nwclnt-sv", "Save", new Point(200, SCREEN_SIZE.height * 6 / 7), 100, 50, ARIAL_15,
                e -> {

                    Window.clientManager.addClient(getClient());
                    showGroup(newClientGroup, false);
                    Window.DoMainScreen();

                }

        );

        newButton("B-mnscrn-nwclnt-dcd", "Discard", new Point(SCREEN_SIZE.width - 200, SCREEN_SIZE.height * 6 / 7), 100, 50, ARIAL_15,
                e -> {

                    showGroup(newClientGroup, false);
                    getClient();
                    Window.DoMainScreen();

                }

        );

        newButton("B-mnscrn-nwclnt-nwpsn", "New Person", new Point(SCREEN_SIZE.width / 4 + 100, SCREEN_SIZE.height / 7 + 75), 175, 50, ARIAL_15,
                e -> {

                    personPanel.add(newPersonPanel("P-mnscrn-nwclnt-psn" + peopleIdx, new Point(0, 0)));
                    personPanel.setPreferredSize(new Dimension(440, 200 * currentPeople.size()));

                    showComponent("SP-mnscrn-nwclnt-psn", false);
                    showComponent("SP-mnscrn-nwclnt-psn", true);

                }

        );

        newButton("B-mnscrn-nwclnt-nwtrsn", "New Transaction", new Point(SCREEN_SIZE.width / 4 * 3 - 100, SCREEN_SIZE.height / 7 + 75), 175, 50, ARIAL_15,
                e -> {

                    transactionPanel.add(newTransactionPanel("P-mnscrn-nwclnt-trsn" + transactionsIdx, new Point(0, 0)));
                    transactionPanel.setPreferredSize(new Dimension(420, 150 * currentTransactions.size()));

                    showComponent("SP-mnscrn-nwclnt-trsn", false);
                    showComponent("SP-mnscrn-nwclnt-trsn", true);

                }

        );

        stopGroup();

        showGroup(newClientGroup, false);

    }

    // Creates the JComponents for the Edit Client Screen
    private static void InitEditClient() {

        editClientGroup = startGroup();

        newButton("B-mnscrn-edclnt-sv", "Save", new Point(200, SCREEN_SIZE.height * 6 / 7), 100, 50, ARIAL_15,
                e -> {

                    setClient(clientToEditIndex, getClient());

                    showGroup(editClientGroup, false);
                    showGroup(newClientGroup, false);
                    Window.DoMainScreen();

                }

        );

        newButton("B-mnscrn-edclnt-rm", "Remove", new Point(SCREEN_SIZE.width / 2, SCREEN_SIZE.height * 6 / 7), 100, 50, ARIAL_15,
                e -> {

                    removeClient(clientToEditIndex);

                    showGroup(editClientGroup, false);
                    showGroup(newClientGroup, false);
                    Window.DoMainScreen();

                }

        );

        newButton("B-mnscrn-edclnt-ext", "Exit", new Point(SCREEN_SIZE.width - 200, SCREEN_SIZE.height * 6 / 7), 100, 50, ARIAL_15,
                e -> {

                    showGroup(editClientGroup, false);
                    showGroup(newClientGroup, false);
                    getClient();
                    Window.DoMainScreen();

                }

        );

        stopGroup();

        showGroup(editClientGroup, false);

    }

    // Replaces the current client (client1) with the new client (client2)
    private static void setClient(int index, Client client) {

        removeClient(index);
        Window.clientManager.addClient(client);
        Window.SaveData();

    }

    // Removes the client that was being viewed
    private static void removeClient(int index) {

        Window.clientManager.removeClient(index);
        Window.SaveData();

    }

    // Gets the client object from the information passed by the user
    private static Client getClient() {

        TreeMap<String, Person> persons = new TreeMap<>();
        TreeMap<String, Transaction> transactions = new TreeMap<>();

        for (Object o : currentPeople.toArray()) {

            String s = (String) o;

            String frstNm = ((JTextField) components.get(s + "-fnm")).getText();
            String mdInt = ((JTextField) components.get(s + "-mint")).getText();
            String lstNm = ((JTextField) components.get(s + "-lnm")).getText();
            String rltn = ((JTextField) components.get(s + "-rltn")).getText();
            String brthd = ((JTextField) components.get(s + "-brthdy")).getText();

            char mInt;

            if (mdInt.length() != 0)
                mInt = mdInt.charAt(0);
            else
                mInt = '\u0000';

            String[] brthdNums = brthd.split("-");

            persons.put(rltn, new Person(frstNm, mInt, lstNm, rltn,
                    new GregorianCalendar(Integer.parseInt(brthdNums[0]),
                            Integer.parseInt(brthdNums[1]) - 1,
                            Integer.parseInt(brthdNums[2]))));

            removeFromGroup(s, newClientGroup);
            removeComponent(s);
            currentPeople.remove(s);

        }


        String currAdd = ((JTextField) components.get("TF-mnscrn-nwclnt-crrAdd")).getText();
        ((JTextField) components.get("TF-mnscrn-nwclnt-crrAdd")).setText("");

        for (Object o : currentTransactions.toArray()) {

            String s = (String) o;

            ClientType clntStts = (ClientType) ((JComboBox<ClientType>) components.get(s + "-clnttyp")).getSelectedItem();
            String trnAdd = ((JTextField) components.get(s + "-curadd")).getText();
            StatusType sttsTyp = (StatusType) ((JComboBox<StatusType>) components.get(s + "-sttstyp")).getSelectedItem();

            transactions.put(trnAdd, new Transaction(clntStts, trnAdd, sttsTyp));

            removeFromGroup(s, newClientGroup);
            removeComponent(s);
            currentTransactions.remove(s);

        }

        String lastContactDate = ((JTextField) components.get("TF-mnscrn-nwclnt-lcd")).getText();
        String[] lcdNums = lastContactDate.split("-");
        ((JTextField) components.get("TF-mnscrn-nwclnt-lcd")).setText("2000-01-01");

        String notes = ((JTextArea) components.get("TF-mnscrn-nwclnt-nts")).getText();
        ((JTextArea) components.get("TF-mnscrn-nwclnt-nts")).setText("");

        return new Client(persons, currAdd, transactions,
                new GregorianCalendar(Integer.parseInt(lcdNums[0]),
                        Integer.parseInt(lcdNums[1]) - 1,
                        Integer.parseInt(lcdNums[2])),
                notes);

    }

    /**
     * Runs the Main Screen GUI and updates client table
     *
     * @param clients Reference to all of the current clients
     */
    public static void DoMainScreen(Client[] clients) {

        JTable jt = (JTable) components.get("TB-mnscrn-dttbl");
        DefaultTableModel tm = (DefaultTableModel) jt.getModel();

        tm.setRowCount(0);

        for (int i = 0; i < clients.length; i++) {

            Transaction trans = clients[i].getTransactions().first();

            tm.addRow(new String[]{clients[i].getClient().getName(),
                    clients[i].getCurrentAddress(),
                    trans.getClientType().toString(),
                    trans.getTransactionAddress(),
                    trans.getCurrentStatus().toString(),
                    clients[i].getLastContactDateString()
            });

        }

        tm.fireTableDataChanged();

        showGroup(mainScreenGroup, true);

    }

    // Shows the Find Client Interface
    private static void DoFindClient() {

        ((JTextField) components.get("TF-mnscrn-fnd-inpt")).setText("");
        showGroup(findGroup, true);
        showComponent("L-mnscrn-fnd-err", false);

    }

    // Shows the Select Client Interface
    private static void DoSelectClient() {

        JComboBox<String> c1 = (JComboBox<String>) components.get("CB-mnscrn-slct-clnts");

        c1.removeAllItems();

        for (Client c : selectedClients) {

            c1.addItem(c.getClient().getName() + ": " + c.getCurrentAddress());

        }

        showGroup(selectGroup, true);

    }

    // Shows the New Client Interface
    private static void DoNewClient() {

        ((JButton) components.get("B-mnscrn-nwclnt-nwpsn")).doClick();
        ((JButton) components.get("B-mnscrn-nwclnt-nwtrsn")).doClick();
        showGroup(newClientGroup, true);

    }

    // Prompts the user to edit an existing client
    private static void DoEditClient() {

        JButton nwPsnBttn = (JButton) components.get("B-mnscrn-nwclnt-nwpsn");

        for (int i = 0; i < clientToEdit.getPeople().size(); i++)
            nwPsnBttn.doClick();

        int idx = 0;

        Object[] peopleObj = clientToEdit.getPeople().toArray();
        Person[] people = new Person[peopleObj.length];

        for (int i = 0; i < peopleObj.length; i++)
            people[i] = (Person) peopleObj[i];


        for (String s : currentPeople) {

            ((JTextField) components.get(s + "-fnm")).setText(people[idx].getFirstName());
            ((JTextField) components.get(s + "-mint")).setText("" + people[idx].getMiddleInitial());
            ((JTextField) components.get(s + "-lnm")).setText(people[idx].getLastName());
            ((JTextField) components.get(s + "-rltn")).setText(people[idx].getRelation());
            Date brthd = people[idx].getBirthday().getTime();
            ((JTextField) components.get(s + "-brthdy")).setText((brthd.getYear() + 1900) + "-" + (brthd.getMonth() + 1) + "-" + brthd.getDate());

            idx++;

        }

        JButton nwTrsnBttn = (JButton) components.get("B-mnscrn-nwclnt-nwtrsn");

        for (int i = 0; i < clientToEdit.getTransactions().size(); i++)
            nwTrsnBttn.doClick();

        idx = 0;

        Object[] transactionObj = clientToEdit.getTransactions().toArray();
        Transaction[] transactions = new Transaction[peopleObj.length];

        for (int i = 0; i < transactionObj.length; i++)
            transactions[i] = (Transaction) transactionObj[i];

        for (String s : currentTransactions) {

            ((JComboBox<ClientType>) components.get(s + "-clnttyp")).setSelectedItem(transactions[idx].getClientType());
            ((JTextField) components.get(s + "-curadd")).setText(transactions[idx].getTransactionAddress());
            ((JComboBox<StatusType>) components.get(s + "-sttstyp")).setSelectedItem(transactions[idx].getCurrentStatus());

            idx++;

        }

        Date lctDate = clientToEdit.getLastContactDate().getTime();
        ((JTextField) components.get("TF-mnscrn-nwclnt-lcd")).setText((lctDate.getYear() + 1900) + "-" + (lctDate.getMonth() + 1) + "-" + lctDate.getDate());

        ((JTextField) components.get("TF-mnscrn-nwclnt-crrAdd")).setText(clientToEdit.getCurrentAddress());
        ((JTextArea) components.get("TF-mnscrn-nwclnt-nts")).setText(clientToEdit.getNotes());

        showGroup(newClientGroup, true);
        showComponent("B-mnscrn-nwclnt-sv", false);
        showComponent("B-mnscrn-nwclnt-dcd", false);

        showGroup(editClientGroup, true);

    }

    // Creates a new JPanel for a person object
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

    // Creates a new JPanel for a transaction object
    private static JPanel newTransactionPanel(String id, Point location) {

        JPanel panel = startPanel(id, location, 440, 150);

        newLabel(id + "-clnttyppmpt", "Client Type:", new Point(85, 35), 150, 50, ARIAL_15, SwingConstants.LEFT);

        JComboBox<ClientType> c1 = new JComboBox<>(ClientType.values());
        newJComponent(c1, id + "-clnttyp", new Point(285, 35), 200, 30, ARIAL_15);
        c1.setSelectedItem(ClientType.UNSPECIFIED);
        c1.setEditable(false);
        c1.addActionListener(c1);

        newTextField(id + "-curadd", true, "", new Point(285, 85), 200, 30, ARIAL_15);
        newLabel(id + "-curaddpmpt", "Transaction Address:", new Point(85, 85), 150, 50, ARIAL_15, SwingConstants.LEFT);

        newLabel(id + "-sttstyppmpt", "Current Status:", new Point(85, 135), 150, 50, ARIAL_15, SwingConstants.LEFT);

        JComboBox<StatusType> c2 = new JComboBox<>(StatusType.values());
        newJComponent(c2, id + "-sttstyp", new Point(285, 135), 200, 30, ARIAL_15);
        c2.setSelectedItem(StatusType.UNKNOWN);
        c2.setEditable(false);
        c2.addActionListener(c2);

        JButton b1 = newButton(id + "-rmv", "X", new Point(410, 20), 40, 25, ARIAL_9,
                e -> {

                    currentTransactions.remove(id);
                    panel.getParent().setPreferredSize(new Dimension(400, 200 * currentTransactions.size()));

                    removeFromGroup(id, newClientGroup);
                    removeComponent(id);

                }

        );

        b1.setForeground(Color.WHITE);
        b1.setBackground(Color.RED);

        stopPanel();

        currentTransactions.add(id);
        transactionsIdx++;

        return panel;

    }

}
