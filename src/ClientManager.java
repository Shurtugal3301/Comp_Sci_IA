import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
//import java.io.*;

public class ClientManager implements Serializable {

    // private static final String FILE_NAME = "ClientData.txt";
    // private static final String END_STRING = "-------------------------";

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

/*
    public static boolean getImportFormat(int clients, int[] people) {

        if (clients == 0) return true;

        boolean success = false;

        PrintWriter fwtr = null;

        try {

            File directory = Window.GetFilePath();

            fwtr = new PrintWriter(directory.getAbsolutePath() +
                    "\\Client_Import_File.txt");

            fwtr.println("PLEASE ENTER CLIENT INFORMATION BENEATH THE BOLDED LABEL");
            fwtr.println("DO NOT ADD LINES UNLESS OTHERWISE SPECIFIED");
            fwtr.println();
            fwtr.
                    println("Client Types: BUYER, SELLER, LANDLORD, TENNAT, PROSPECT, UNSPECIFIED"
                    );
            fwtr.
                    println("Status Types: STATUS1, STATUS2, STATUS3, STATUS4, STATUS5, UNKNOWN"
                    );
            fwtr.println();
            fwtr.println("NUMBER OF CLIENTS:");
            fwtr.println(clients);
            fwtr.println(END_STRING);
            fwtr.println();

            for (int i = 0; i < clients; i++) {

                fwtr.println("NUMBER OF PEOPLE:");
                fwtr.println(people[i]);
                fwtr.println(END_STRING);
                fwtr.println();
                fwtr.println("PERSONAL INFORMATION:");
                fwtr.println();

                for (int j = 0; j < people[i]; j++) {

                    fwtr.println("FIRST NAME:");
                    fwtr.println();
                    fwtr.println(END_STRING);
                    fwtr.println("LAST NAME:");
                    fwtr.println();
                    fwtr.println(END_STRING);
                    fwtr.println("RELATION:");
                    fwtr.println();
                    fwtr.println(END_STRING);
                    fwtr.println("BIRTHDAY (YYYY MM DD):");
                    fwtr.println();
                    fwtr.println(END_STRING);
                    fwtr.println();

                }

                fwtr.println("TRANSACTION INFORMATION:");
                fwtr.println();

                fwtr.println("CLIENT TYPE:");
                fwtr.println();
                fwtr.println(END_STRING);
                fwtr.println("TRANSACTION ADDRESS:");
                fwtr.println();
                fwtr.println(END_STRING);
                fwtr.println("STATUS:");
                fwtr.println();
                fwtr.println(END_STRING);
                fwtr.println();

                fwtr.println("CLIENT INFORMATION:");
                fwtr.println();
                fwtr.println("CURRENT ADDRESS:");
                fwtr.println();
                fwtr.println(END_STRING);
                fwtr.println("LAST CONTACT DATE (YYYY MM DD):");
                fwtr.println();
                fwtr.println(END_STRING);
                fwtr.println("NOTES (You may add lines as necessary):");
                fwtr.println();
                fwtr.println();
                fwtr.println();
                fwtr.println(END_STRING);
                fwtr.println();

            }

            fwtr.flush();

            success = true;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (fwtr != null) {

                try {

                    fwtr.close();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        }

        return success;

    }
*/

    /**
     * Returns a String for saving the current list of clients to a file
     *
     * @return List of clients for saving to file
     */
    public static String Save() {

        String returnString = "";

        //returnString += String.format("/s/ /d/ ~%d~ /d/ ", clients.size());

        //for (int i = 0; i < clients.size(); i++) {

        //	returnString += clients.get(i).saveToFile() + " ";

        //}

        //returnString += "/n/";

        return returnString;

    }

    /**
     * Creates clients based off of the string it is passed
     *
     * @param loadString String used to create clients
     * @return ArrayList of the clients created
     */
	/*public static ArrayList<Client> Load(String loadString) {

		if (!loadString.contains("/s/"))
			return new ArrayList<Client>();

		Client[] clnts;

		int currentIndex = 0, index1 = 0, index2 = 0;

		currentIndex = loadString.indexOf("/s/");

		currentIndex = loadString.indexOf("/d/", currentIndex + 1);

		currentIndex = loadString.indexOf("~", currentIndex + 1);

		index1 = currentIndex + 1;

		currentIndex = loadString.indexOf("~", currentIndex + 1);

		index2 = currentIndex;

		int numClients = Integer.parseInt(loadString.substring(index1, index2));

		clnts = new Client[numClients];

		currentIndex = loadString.indexOf("/d/", currentIndex + 1);

		for (int i = 0; i < numClients; i++) {

			currentIndex = loadString.indexOf("CLNT", currentIndex + 1);

			currentIndex = loadString.indexOf("/p/", currentIndex + 1);

			currentIndex = loadString.indexOf("/d/", currentIndex + 1);

			currentIndex = loadString.indexOf("~", currentIndex + 1);

			index1 = currentIndex + 1;

			currentIndex = loadString.indexOf("~", currentIndex + 1);

			index2 = currentIndex;

			int numPeople = Integer.parseInt(loadString.substring(index1, index2));

			currentIndex = loadString.indexOf("/d/", currentIndex + 1);

			Person[] people = new Person[numPeople];

			for (int j = 0; j < numPeople; j++) {

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				currentIndex = loadString.indexOf("PRSN", currentIndex + 1);

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index1 = currentIndex + 1;

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index2 = currentIndex;

				boolean isClient = Boolean.parseBoolean(loadString.substring(index1, index2).toUpperCase());

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index1 = currentIndex + 1;

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index2 = currentIndex;

				String fullName = loadString.substring(index1, index2);

				index1 = 0;
				index2 = fullName.indexOf(" ");

				String firstName = fullName.substring(index1, index2);

				index1 = fullName.lastIndexOf(" ") + 1;
				index2 = fullName.length();

				String lastName = fullName.substring(index1, index2);

				char midInt = '\u0000';

				if (fullName.contains(".")) {

					midInt = fullName.charAt(fullName.indexOf(".") - 1);

				}

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index1 = currentIndex + 1;

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index2 = currentIndex;

				String relation = loadString.substring(index1, index2);

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index1 = currentIndex + 1;

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index2 = currentIndex;

				long birthdayLong = Long.parseLong(loadString.substring(index1, index2));

				Date birthdayDate = new Date(birthdayLong);

				GregorianCalendar birthday = new GregorianCalendar();

				birthday.setTime(birthdayDate);

				currentIndex = loadString.indexOf("/n/", currentIndex + 1);

				people[j] = new Person(firstName, midInt, lastName, relation, birthday, isClient);

				currentIndex = loadString.indexOf("~", currentIndex + 1);

			}

			currentIndex = loadString.indexOf("/p/", currentIndex + 1);

			currentIndex = loadString.indexOf("~", currentIndex + 1);

			index1 = currentIndex + 1;

			currentIndex = loadString.indexOf("~", currentIndex + 1);

			index2 = currentIndex;

			String currentAddress = loadString.substring(index1, index2);

			currentIndex = loadString.indexOf("/t/", currentIndex + 1);

			currentIndex = loadString.indexOf("/d/", currentIndex + 1);

			currentIndex = loadString.indexOf("~", currentIndex + 1);

			index1 = currentIndex + 1;

			currentIndex = loadString.indexOf("~", currentIndex + 1);

			index2 = currentIndex;

			int numTrans = Integer.parseInt(loadString.substring(index1, index2));

			currentIndex = loadString.indexOf("/d/", currentIndex + 1);

			Transaction[] transactions = new Transaction[numTrans];

			for (int j = 0; j < numTrans; j++) {

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				currentIndex = loadString.indexOf("TSACTN", currentIndex + 1);

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index1 = currentIndex + 1;

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index2 = currentIndex;

				int clientOrdinal = Integer.parseInt(loadString.substring(index1, index2));

				ClientType clientType = ClientType.values()[clientOrdinal];

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index1 = currentIndex + 1;

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index2 = currentIndex;

				String transactionAddress = loadString.substring(index1, index2);

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index1 = currentIndex + 1;

				currentIndex = loadString.indexOf("~", currentIndex + 1);

				index2 = currentIndex;

				int statusOrdinal = Integer.parseInt(loadString.substring(index1, index2));

				StatusType statusType = StatusType.values()[statusOrdinal];

				currentIndex = loadString.indexOf("/n/", currentIndex + 1);

				transactions[j] = new Transaction(clientType, transactionAddress, statusType);

				currentIndex = loadString.indexOf("~", currentIndex + 1);

			}

			currentIndex = loadString.indexOf("/t/", currentIndex + 1);

			currentIndex = loadString.indexOf("~", currentIndex + 1);

			index1 = currentIndex + 1;

			currentIndex = loadString.indexOf("~", currentIndex + 1);

			index2 = currentIndex;

			long lastContactLong = Long.parseLong(loadString.substring(index1, index2));

			Date lastContactDate = new Date(lastContactLong);

			GregorianCalendar lastContact = new GregorianCalendar();

			lastContact.setTime(lastContactDate);

			currentIndex = loadString.indexOf("~", currentIndex + 1);

			index1 = currentIndex + 1;

			currentIndex = loadString.indexOf("~", currentIndex + 1);

			index2 = currentIndex;

			String notes = loadString.substring(index1, index2);

			currentIndex = loadString.indexOf("/n/", currentIndex + 1);

			clnts[i] = new Client(true, new ArrayList<Person>(Arrays.asList(people)), currentAddress, transactions[0],
					lastContact, notes);

		}

		currentIndex = loadString.indexOf("/n/", currentIndex + 1);

		return new ArrayList<Client>(Arrays.asList(clnts));

	}
	*/


    /*
     * public static void ExportClients(Client[] clients) { }
     *
     * public static Client[] ImportClients(File importFile) {
     *
     * return null;
     *
     * }
     */

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

        for (int i = 0; i < clients.length; i++) {

            if (clients[i].getClient().getFirstName().equals(clientToFind))
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

        for (Client c : getClients()) {

            if (c.getClient().getFirstName().equals(clientToFind))
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

        String returnString = "";

        for (Client c : getClients()) {

            returnString += c + "\n\n";

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
