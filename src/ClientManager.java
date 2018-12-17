import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.TreeMap;

import sun.reflect.generics.tree.Tree;
//import java.io.*;

public class ClientManager implements Serializable {

    private static final String IMPORT_NAME = "ClientDataImport.txt";
    private static final String EXPORT_NAME = "ClientDataExport.txt";
    private static final String END_STRING = "-------------------------";

    private static ArrayList<Client> clients;

    /**
     * Creates an empty array list of clients
     */
    public ClientManager() {

        clients = new ArrayList<>();

    }

    /**
     * Creates an array list of clients with the given clients
     *
     * @param clientList List of the clients
     */
    public ClientManager(ArrayList<Client> clientList) {

        clients = clientList;

    }

    /**
     * Sorts the current clients alphabetically by first name
     */
    public static void Sort() {

        Client[] clientsToSort = clients.toArray(new Client[clients.size()]);

        DPQuickSort(clientsToSort, clientsToSort, 0, clientsToSort.length - 1);

        clients = new ArrayList<>(Arrays.asList(clientsToSort));

    }

    // Used recursively to sort the clients into three sections of decreasing size
    private static void DPQuickSort(Client[] clientList, Client[] clients, Integer low, Integer high) {

        if (low < high) {

            int lp, rp;
            int[] p = partition(clientList, clients, low, high);
            lp = p[0];
            rp = p[1];
            DPQuickSort(clientList, clients, low, lp - 1);
            DPQuickSort(clientList, clients, lp + 1, rp - 1);
            DPQuickSort(clientList, clients, rp + 1, high);

        }

    }

    // Sorts the clients passed into three different sections
    private static int[] partition(Client[] clientList, Client[] clnts, int low, int high) {

        if (clientList[high].compareTo(clientList[low]) < 0)
            swap(clnts, low, high);

        int j = low + 1;
        int g = high - 1, k = low + 1;
        Client p = clientList[low], q = clientList[high];

        while (k <= g) {

            if (clientList[k].compareTo(p) < 0) {

                swap(clnts, k, j);
                j++;

            } else if (clientList[k].compareTo(q) >= 0) {

                while (clientList[g].compareTo(q) > 0 && k < g)
                    g--;

                swap(clnts, k, g);
                g--;

                if (clientList[k].compareTo(p) < 0) {

                    swap(clnts, k, j);
                    j++;

                }
            }

            k++;

        }

        j--;
        g++;

        swap(clnts, low, j);
        swap(clnts, high, g);

        int[] rtrn = {j, g};

        return rtrn;

    }

    // Swaps the two array positions
    private static void swap(Client[] clnts, int a, int b) {

        Client temp = clnts[a];
        clnts[a] = clnts[b];
        clnts[b] = temp;

    }


    public static boolean getImportFormat(String filePath, int clients, int[] people, int[] transaction) {

        if (clients == 0) return true;

        boolean success = false;

        FileWriter fileWriter = null;
        BufferedWriter bwrtr = null;

        try {

            fileWriter = new FileWriter(new File(filePath + "/" + IMPORT_NAME));
            bwrtr = new BufferedWriter(fileWriter);

            bwrtr.write(

                    "PLEASE ENTER CLIENT INFORMATION BENEATH THE LABEL\r\n" +
                            "DO NOT ADD LINES UNLESS OTHERWISE SPECIFIED\r\n" +
                            "\r\n" +
                            "Client Types: BUYER, SELLER, LANDLORD, TENNAT, PROSPECT, UNSPECIFIED\r\n" +
                            "Status Types: STATUS1, STATUS2, STATUS3, STATUS4, STATUS5, UNKNOWN\r\n" +
                            "\r\n" +
                            "NUMBER OF CLIENTS:\r\n" +
                            clients + "\r\n" +
                            END_STRING + "\r\n" +
                            "\r\n"
            );

            for (int i = 0; i < clients; i++) {

                bwrtr.write(

                        "CLIENT " + i + "\r\n" +
                                "Number of People:\r\n" +
                                people[i] + "\r\n" +
                                END_STRING + "\r\n" +
                                "\r\n" +
                                "PERSON INFORMATION:\r\n" +
                                "\r\n"
                );

                for (int j = 0; j < people[i]; j++) {

                    bwrtr.write(

                            "PERSON " + j + "\r\n" +
                                    "First Name:\r\n" +
                                    "\r\n" +
                                    END_STRING + "\r\n" +
                                    "Last Name:\r\n" +
                                    "\r\n" +
                                    END_STRING + "\r\n" +
                                    "Relation:\r\n" +
                                    "\r\n" +
                                    END_STRING + "\r\n" +
                                    "Birthday (yyyy mm dd):\r\n" +
                                    "\r\n" +
                                    END_STRING + "\r\n" +
                                    "END PERSON\r\n" +
                                    "\r\n"
                    );

                }

                bwrtr.write(

                        "Number of Transactions:\r\n" +
                                transaction[i] + "\r\n" +
                                END_STRING + "\r\n" +
                                "\r\n" +
                                "TRANSACTION INFORMATION:\r\n" +
                                "\r\n"

                );

                for (int j = 0; j < transaction[i]; j++) {

                    bwrtr.write(

                            "TRANSACTION " + j + "\r\n" +
                                    "Client Type:\r\n" +
                                    "\r\n" +
                                    END_STRING + "\r\n" +
                                    "Transaction Address:\r\n" +
                                    "\r\n" +
                                    END_STRING + "\r\n" +
                                    "Status:\r\n" +
                                    "\r\n" +
                                    END_STRING + "\r\n" +
                                    "END TRANSACTION\r\n" +
                                    "\r\n"

                    );

                }

                bwrtr.write(

                        "CLIENT INFORMATION:\r\n" +
                                "\r\n" +
                                "Current Address:\r\n" +
                                "\r\n" +
                                END_STRING + "\r\n" +
                                "Last Contact Date (yyyy mm dd):\r\n" +
                                "\r\n" +
                                END_STRING + "\r\n" +
                                "Notes (You may add lines as necessary):\r\n" +
                                "\r\n" +
                                "\r\n" +
                                "\r\n" +
                                END_STRING + "\r\n" +
                                "END CLIENT\r\n" +
                                "\r\n");

            }

            bwrtr.flush();

            success = true;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (bwrtr != null) {

                try {

                    bwrtr.close();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        }

        return success;

    }

    public static boolean ExportClients(String filePath, ClientManager clients) {

        boolean success = false;

        FileWriter fileWriter = null;
        BufferedWriter bwrtr = null;

        try {

            fileWriter = new FileWriter(filePath + "/" + EXPORT_NAME);
            bwrtr = new BufferedWriter(fileWriter);

            bwrtr.write(clients.toString());

            bwrtr.flush();

            success = true;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (bwrtr != null) {

                try {

                    bwrtr.close();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        }

        return success;

    }

    public static boolean ImportClients(File importFile) {

        boolean success = false;
        int numClient, numPeople, numTransactions;

        FileReader fileReader;
        BufferedReader brdr;
        Scanner scan = null;

        String fstNm, lstNm, rltn;
        int brthY, brthM, brthD;

        String clntTyp, trnsAdd, sttsTyp;

        String curAdd, notes;
        int lcdY, lcdM, lcdD;

        TreeMap<String, Person> people = new TreeMap<>();
        TreeMap<String, Transaction> transactions = new TreeMap<>();

        try {

            fileReader = new FileReader(importFile);
            brdr = new BufferedReader(fileReader);
            scan = new Scanner(brdr);

            while (scan.nextLine() != "NUMBER OF CLIENTS:") ;

            numClient = scan.nextInt();

            for (int i = 0; i < numClient; i++) {

                people.clear();
                transactions.clear();

                while (scan.nextLine() != "CLIENT " + i) ;

                while (scan.nextLine() != "Number of People:") ;

                numPeople = scan.nextInt();

                for (int j = 0; j < numPeople; j++) {

                    while (scan.nextLine() != "PERSON " + j) ;

                    while (scan.nextLine() != "First Name:") ;
                    fstNm = scan.next();

                    while (scan.nextLine() != "Last Name:") ;
                    lstNm = scan.next();

                    while (scan.nextLine() != "Relation:") ;
                    rltn = scan.nextLine();

                    while (scan.nextLine() != "Birthday (yyyy mm dd):") ;
                    brthY = scan.nextInt();
                    brthM = scan.nextInt();
                    brthD = scan.nextInt();

                    people.put(rltn, new Person(fstNm, '\u0000', lstNm, rltn,
                            new GregorianCalendar(brthY, brthM - 1, brthD)));

                }

                while (scan.nextLine() != "Number of Transactions:") ;

                numTransactions = scan.nextInt();

                for (int j = 0; j < numTransactions; j++) {

                    while (scan.nextLine() != "TRANSACTION " + j) ;

                    while (scan.nextLine() != "Client Type:") ;
                    clntTyp = scan.next();

                    while (scan.nextLine() != "Transaction Address:") ;
                    trnsAdd = scan.nextLine();

                    while (scan.nextLine() != "Status:") ;
                    sttsTyp = scan.next();

                    transactions.put(trnsAdd, new Transaction(ClientType.valueOf(clntTyp), trnsAdd, StatusType.valueOf(sttsTyp)));

                }

                while (scan.nextLine() != "Current Address:") ;
                curAdd = scan.nextLine();

                while (scan.nextLine() != "Last Contact Date (yyyy mm dd):") ;
                lcdY = scan.nextInt();
                lcdM = scan.nextInt();
                lcdD = scan.nextInt();

                while (scan.nextLine() != "Notes (You may add lines as necessary):") ;

                String s = scan.nextLine();
                notes = "";

                while (s != END_STRING) {

                    notes += s + "\r\n";
                    s = scan.nextLine();

                }

                while (scan.nextLine() != "END CLIENT") ;

                clients.add(new Client(people, curAdd, transactions, new GregorianCalendar(lcdY, lcdM - 1, lcdD), notes));

            }

            success = true;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (scan != null) {

                try {

                    scan.close();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        }

        return success;

    }

    /**
     * Returns an array of all the current clients
     *
     * @return The array of clients
     */
    public Client[] getClients() {

        return clients.toArray(new Client[clients.size()]);

    }

    /**
     * Adds a new client
     *
     * @param newClient The client being added
     * @return Returns whether or not the addition was successful
     */
    public boolean addClient(Client newClient) {

        return clients.add(newClient);

    }

    /**
     * Removes a client at the given index
     *
     * @param clientToRemove Name of the client being removed
     * @return The client that was removed
     */
    public Client removeClient(String clientToRemove) {

        Client remove = getClient(clientToRemove);
        clients.remove(remove);
        return remove;

    }

    /**
     * Returns the index of the client object with the specified name or returns
     * -1 if the client isn't part of the current list
     *
     * @param clientToFind The client being searched for
     * @return Index of the client object with the input name
     */
    public int getClientIdx(String clientToFind) {

        Client[] clients = getClients();
        Client cToFind = new Client(new Person(clientToFind));

        for (int i = 0; i < clients.length; i++) {

            if (clients[i].equals(cToFind))
                return i;

        }

        return -1;

    }

    /**
     * Returns the client object with the specified name or returns
     * null if the client isn't part of the current list
     *
     * @param clientToFind The client being searched for
     * @return The client object with the input name
     */
    public Client getClient(String clientToFind) {

        Client cToFind = new Client(new Person(clientToFind));

        for (Client c : getClients()) {

            if (c.equals(cToFind))
                return c;

        }

        return null;

    }

    /**
     * Returns the current list of clients in a String format
     *
     * @return The clients as a String
     */
    public String toString() {

        String returnString = "Client List:\r\n\r\n";

        for (Client c : getClients()) {

            returnString += "Client\r\n\r\n" + c + "\r\nEnd Client\r\n\r\n";

        }

        return returnString;

    }

    /**
     * Returns the data for saving
     *
     * @return The clients to save
     */
    public ArrayList<Client> toFile() {

        return clients;

    }

}
