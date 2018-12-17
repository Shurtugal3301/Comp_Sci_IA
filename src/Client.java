import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Client implements Serializable, Comparable {

    private Map<String, Person> people;
    private String currentAddress;
    private Map<String, Transaction> transactions;
    private GregorianCalendar lastContactDate;
    private String notes;

    private static final String CLIENT_ID = "Client";

    /**
     * Creates a client with a default person, empty address, unspecified client
     * type, empty transaction address, unknown status, last contact date of January
     * 1st 2000, and empty notes
     */
    public Client() {

        people = new TreeMap<>();
        people.put(CLIENT_ID, new Person());
        currentAddress = "";
        transactions = new TreeMap<>();
        transactions.put("", new Transaction());
        lastContactDate = new GregorianCalendar(2000, 0, 1);
        notes = "";

    }

    /**
     * Creates a client with a defined person but otherwise default values
     *
     * @param client The person who is the client
     */
    public Client(Person client) {

        people = new TreeMap<>();
        people.put(CLIENT_ID, client);
        currentAddress = "";
        transactions = new TreeMap<>();
        transactions.put("", new Transaction());
        lastContactDate = new GregorianCalendar(2000, 0, 1);
        notes = "";

    }

    /**
     * Creates a client with a person, address, client type, transaction address,
     * status, last contact date, and notes
     *
     * @param client The person who is the client
     * @param clientCurrentAddress Current address of the client
     * @param clientTransaction Transaction of the client
     * @param clientLastContactDate Last time this client has been contacted
     * @param clientNotes Notes about this client
     */
    public Client(Person client, String clientCurrentAddress, Transaction clientTransaction,
                  GregorianCalendar clientLastContactDate, String clientNotes) {

        people = new TreeMap<>();
        people.put(CLIENT_ID, client);
        currentAddress = clientCurrentAddress;
        transactions = new TreeMap<>();
        transactions.put(clientTransaction.getTransactionAddress(), clientTransaction);
        lastContactDate = clientLastContactDate;
        notes = clientNotes;

    }

    /**
     * Creates a client with multiple people and an address, client type,
     * transaction address, status, last contact date, and notes
     *
     * @param clients The client and people related to him
     * @param clientCurrentAddress Current address of the client
     * @param clientTransaction Transactions of the client
     * @param clientLastContactDate Last time this client has been contacted
     * @param clientNotes Notes about this client
     */
    public Client(TreeMap<String, Person> clients, String clientCurrentAddress, TreeMap<String, Transaction> clientTransaction,
                  GregorianCalendar clientLastContactDate, String clientNotes) {

        people = clients;
        currentAddress = clientCurrentAddress;
        transactions = clientTransaction;
        lastContactDate = clientLastContactDate;
        notes = clientNotes;

    }

    /**
     * Gets an array of the people currently associated with this client
     *
     * @return People currently associated with this client
     */
    public TreeSet<Person> getPeople() {

        return new TreeSet<>(people.values());

    }

    /**
     * Gets the person with the specified relation
     *
     * @param relationToClient Relationship between person and client
     * @return Person with the specified relation
     */
    public Person getPerson(String relationToClient) {

        return people.get(relationToClient);

    }

    /**
     * Gets the client's personal information
     *
     * @return The client's personal information
     */
    public Person getClient() {

        return people.get(CLIENT_ID);

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
     * Gets the transactions currently associated with this client
     *
     * @return Transactions associated with this client
     */
    public TreeSet<Transaction> getTransactions() {

        return new TreeSet<>(transactions.values());

    }

    /**
     * Gets the transaction with the specified address
     *
     * @param transactionAddress Address of the transaction
     * @return Transaction with the specified address
     */
    public Transaction getTransaction(String transactionAddress) {

        return transactions.get(transactionAddress);

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

        return Window.DATE_FORMAT.format(lastContactDate.getTime());

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
     * @param newPerson The person being added
     */
    public void addPerson(Person newPerson) {

        people.put(newPerson.getRelation(), newPerson);

    }

    /**
     * Removes a person from being associated with this client with a given relation
     *
     * @param relationToClient The relationship between the client and person
     * @return The person that was removed
     */
    public Person removePerson(String relationToClient) {

        return people.remove(relationToClient);

    }

    /**
     * Sets the personal information for this client
     *
     * @param client The person who is the client
     * @return The old client, if any
     */
    public Person setClient(Person client) {

        return people.put(CLIENT_ID, client);

    }

    /**
     * Sets the current address of the client
     *
     * @param clientAddress Current address of the client
     */
    public void setCurrentAddress(String clientAddress) {

        currentAddress = clientAddress;

    }

    /**
     * Sets transaction to be associated with this client
     *
     * @param newTransaction The transaction being added
     */
    public void addTransaction(Transaction newTransaction) {

        transactions.put(newTransaction.getTransactionAddress(), newTransaction);

    }

    /**
     * Removes a transaction from being associated with this client with a given address
     *
     * @param transactionAddress The address of the transaction
     * @return The person that was removed
     */
    public Transaction removeTransaction(String transactionAddress) {

        return transactions.remove(transactionAddress);

    }

    /**
     * Sets the date that the client has last been contacted
     *
     * @param contactDate Date that the client has last been contacted
     */
    public void setLastContactDate(GregorianCalendar contactDate) {

        lastContactDate = contactDate;

    }

    /**
     * Sets the notes about this client
     *
     * @param clientNotes Notes about this client
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

        for (Object p : getPeople()) {

            returnString += "Person\r\n" + p + "\r\nEnd Person\r\n\r\n";

        }

        returnString += "Current Address: " + currentAddress + "\r\n";


        for (Object t : getTransactions()) {

            returnString += "Transaction\r\n" + t + "\r\nEnd Transaction\r\n\r\n";

        }

        returnString += "Last Contact Date: " + Window.DATE_FORMAT.format(lastContactDate.getTime()) + "\r\nNotes: " + notes;

        return returnString;

    }

    /**
     * Compares two clients based on their name
     *
     * @param o Object the client is being compared to
     * @return The difference between the names of the clients
     */
    public int compareTo(Object o) {

        Client p = (Client) o;
        return getClient().compareTo(p.getClient());

    }

    /**
     * Checks if two clients are identical based on their names
     *
     * @param o Object the client is being compared to
     * @return Are the clients identical based on their names
     */
    public boolean equals(Object o) {

        return (compareTo(o) == 0);

    }

}
