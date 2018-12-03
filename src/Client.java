import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Client {

	static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

	private ArrayList<Person> people;
	private String currentAddress;
	private Transaction transaction;
	private GregorianCalendar lastContactDate;
	private String notes;

	/**
	 * Creates a client with a default person, empty address, unspecified client
	 * type, empty transaction address, unknown status, last contact date of January
	 * 1st 2000, and empty notes
	 */
	public Client() {

		people = new ArrayList<Person>();
		people.add(new Person());
		setClient(0);
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

		people = new ArrayList<Person>();
		people.add(client);
		setClient(0);
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

		people = new ArrayList<Person>();
		people.add(client);
		setClient(0);
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
	 * @param clientTransactions
	 *            Transactions of the client
	 * @param clientLastContactDate
	 *            Last time this client has been contacted
	 * @param clientNotes
	 *            Notes about this client
	 */
	public Client(boolean isLoad, ArrayList<Person> clients, String clientCurrentAddress, Transaction clientTransaction,
			GregorianCalendar clientLastContactDate, String clientNotes) {

		people = clients;

		if (!isLoad)
			setClient(0);

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
	public Person[] getPeople() {

		Person[] returnArray = new Person[people.size()];

		for (int i = 0; i < people.size(); i++) {

			returnArray[i] = people.get(i);

		}

		return returnArray;

	}

	/**
	 * Gets the client's personal information
	 * 
	 * @return The client's personal information
	 */
	public Person getClient() {

		for (int i = 0; i < people.size(); i++) {

			if (people.get(i).getIsClient())
				return people.get(i);

		}

		return null;

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
	public boolean addPerson(Person newPerson) {

		return people.add(newPerson);

	}

	/**
	 * Removes a person from being associated with this client at the given index
	 * 
	 * @param index
	 *            Index of the person being removed
	 * @return The person that was removed
	 */
	public Person removePerson(int index) {

		return people.remove(index);

	}

	/**
	 * Gets the first index of the person being searched for or returns -1 if the
	 * person isn't part of the current list
	 * 
	 * @param personToFind
	 *            The person being searched for
	 * @return The current index of that person
	 */
	public int getPersonIndex(Person personToFind) {

		return people.indexOf(personToFind);

	}

	/**
	 * Sets the personal information for this client
	 * 
	 * @param index
	 *            Index of the client's personal information
	 */
	public void setClient(int index) {

		if (getClient() != null)
			getClient().setIsClient(false);

		people.get(index).setIsClient(true);

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

		for (Person p : people) {

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

		returnString += String.format("CLNT /p/ /d/ ~%d~ /d/ ", getPeople().length);

		for (Person p : people) {

			returnString += String.format("~%s~ ", p.saveToFile());

		}

		returnString += String.format("/p/ ~%s~ /t/ /d/ ~%d~ /d/ ", getCurrentAddress(), 1);

		returnString += String.format("~%s~ ", transaction.saveToFile());

		returnString += String.format("/t/ ~%d~ ~%s~ /n/", lastContactDate.getTime().getTime(), getNotes());

		return returnString;

	}

}
