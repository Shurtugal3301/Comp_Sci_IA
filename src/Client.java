import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Client {

	static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

	private Map<String, Person> people;
	private String currentAddress;
	private Transaction transaction;
	private GregorianCalendar lastContactDate;
	private String notes;

	private static final String CLIENT_ID = "~Client~";

	/**
	 * Creates a client with a default person, empty address, unspecified client
	 * type, empty transaction address, unknown status, last contact date of January
	 * 1st 2000, and empty notes
	 */
	public Client() {

		people = new TreeMap<String, Person>();
		people.put(CLIENT_ID, new Person());
		
		currentAddress = "";
		transaction = new Transaction();
		lastContactDate = new GregorianCalendar(2000, 0, 1);
		notes = "";

	}

	/**
	 * Creates a client with a defined person but otherwise default values
	 * 
	 * @param client
	 *            The person who is the client
	 */
	public Client(Person client) {

		people = new TreeMap<String, Person>();
		people.put(CLIENT_ID, client);

		
		currentAddress = "";
		transaction = new Transaction();
		lastContactDate = new GregorianCalendar(2000, 0, 1);
		notes = "";

	}

	/**
	 * Creates a client with a person, address, client type, transaction address,
	 * status, last contact date, and notes
	 * 
	 * @param client
	 *            The person who is the client
	 * @param clientCurrentAddress
	 *            Current address of the client
	 * @param clientTransaction
	 *            Transaction of the client
	 * @param clientLastContactDate
	 *            Last time this client has been contacted
	 * @param clientNotes
	 *            Notes about this client
	 */
	public Client(Person client, String clientCurrentAddress, Transaction clientTransaction,
			GregorianCalendar clientLastContactDate, String clientNotes) {

		people = new TreeMap<String, Person>();
		people.put(CLIENT_ID, client);

		
		currentAddress = clientCurrentAddress;
		transaction = clientTransaction;
		lastContactDate = clientLastContactDate;
		notes = clientNotes;

	}

	/**
	 * Creates a client with multiple people and an address, client type,
	 * transaction address, status, last contact date, and notes
	 * 
	 * @param isLoad
	 *            Changes whether the client is set based on whether or not the
	 *            program is loading
	 * @param clients
	 *            The client and people related to him
	 * @param clientCurrentAddress
	 *            Current address of the client
	 * @param clientTransaction
	 *            Transactions of the client
	 * @param clientLastContactDate
	 *            Last time this client has been contacted
	 * @param clientNotes
	 *            Notes about this client
	 */
	public Client(TreeMap<String, Person> clients, String clientCurrentAddress, Transaction clientTransaction,
			GregorianCalendar clientLastContactDate, String clientNotes) {

		people = clients;
		currentAddress = clientCurrentAddress;
		transaction = clientTransaction;
		lastContactDate = clientLastContactDate;
		notes = clientNotes;

	}

	/**
	 * Gets an array of the people currently associated with this client
	 * 
	 * @return People currently associated with this client
	 */
	public TreeSet<Person> getPeople() {

		return new TreeSet<Person>(people.values());

	}

	/**
	 * Gets the client's personal information
	 * 
	 * @return The client's personal information
	 */
	public Person getClient() {

		return people.get("Client");

	}

	/**
	 * Gets the current address of the client
	 * 
	 * @return Current address of the client
	 */
	public String getCurrentAddress() {

		return currentAddress;

	}

	/**
	 * Gets the transaction currently associated with this client
	 * 
	 * @return Transaction associated with this client
	 */
	public Transaction getTransaction() {

		return transaction;

	}

	/**
	 * Gets the last date that this client has been contacted
	 * 
	 * @return Last date that this client has been contacted
	 */
	public GregorianCalendar getLastContactDate() {

		return lastContactDate;

	}

	/**
	 * Gets the last date that this client has been contacted in String format
	 * 
	 * @return Last date that this client has been contacted in String format
	 */
	public String getLastContactDateString() {

		return dateFormat.format(lastContactDate.getTime());

	}

	/**
	 * Gets the notes about this client
	 * 
	 * @return Notes about this client
	 */
	public String getNotes() {

		return notes;

	}

	/**
	 * Adds a new person to be associated with this client
	 * 
	 * @param newPerson
	 *            The person being added
	 * @return Returns whether or not the addition was successful
	 */
	public Person addPerson(Person newPerson) {

		return people.put(newPerson.getRelation(), newPerson);

	}

	/**
	 * Removes a person from being associated with this client at the given index
	 * 
	 * @param index
	 *            Index of the person being removed
	 * @return The person that was removed
	 */
	public Person removePerson(String relationToClient) {

		return people.remove(relationToClient);

	}

	/**
	 * Sets the personal information for this client
	 * 
	 * @param client
	 *            The person who is the client
	 *
	 * @return The old client, if any
	 */
	public Person setClient(Person client) {

		return people.put(CLIENT_ID, client);

	}

	/**
	 * Sets the current address of the client
	 * 
	 * @param clientAddress
	 *            Current address of the client
	 */
	public void setCurrentAddress(String clientAddress) {

		currentAddress = clientAddress;

	}

	/**
	 * Sets transaction to be associated with this client
	 * 
	 * @param newTransaction
	 *            The transaction being added
	 */
	public void setTransaction(Transaction newTransaction) {

		transaction = newTransaction;

	}

	/**
	 * Sets the date that the client has last been contacted
	 * 
	 * @param contactDate
	 *            Date that the client has last been contacted
	 */
	public void setLastContactDate(GregorianCalendar contactDate) {

		lastContactDate = contactDate;

	}

	/**
	 * Sets the notes about this client
	 * 
	 * @param clientNotes
	 *            Notes about this client
	 */
	public void setNotes(String clientNotes) {

		notes = clientNotes;

	}

	/**
	 * Formats the client to a string
	 * 
	 * @return The formated string
	 */
	public String toString() {

		String returnString = "";

		for (Object p : people.values()) {

			returnString += p + "\n";

		}

		returnString += "Current Address: " + currentAddress + "\n";

		returnString += transaction + "\n";

		returnString += "Last Contact Date: " + dateFormat.format(lastContactDate.getTime()) + "\nNotes: " + notes;

		return returnString;

	}

	/**
	 * Compares two clients based on their name
	 * 
	 * @param o
	 *            Object the client is being compared to
	 * 
	 * @return The difference between the names of the clients
	 */
	public int compareTo(Object o) {

		Client p = (Client) o;
		return getClient().compareTo(p.getClient());

	}

	/**
	 * Checks if two clients are identical based on their names
	 * 
	 * @param o
	 *            Object the client is being compared to
	 * 
	 * @return Are the clients identical based on their names
	 */
	public boolean equals(Object o) {

		return (compareTo(o) == 0);

	}

	/**
	 * Formats the client for saving
	 * 
	 * @return The formated string
	 */
	public String saveToFile() {

		String returnString = "";

		returnString += String.format("CLNT /p/ /d/ ~%d~ /d/ ", people.size());

		for (Object p : people.values()) {

			returnString += String.format("~%s~ ", ((Person) p).saveToFile());

		}

		returnString += String.format("/p/ ~%s~ /t/ /d/ ~%d~ /d/ ", getCurrentAddress(), 1);

		returnString += String.format("~%s~ ", transaction.saveToFile());

		returnString += String.format("/t/ ~%d~ ~%s~ /n/", lastContactDate.getTime().getTime(), getNotes());

		return returnString;

	}

}
