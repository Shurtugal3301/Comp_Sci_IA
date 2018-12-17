import java.io.Serializable;

public class Transaction implements Serializable, Comparable {

    private ClientType clientType;
    private String transactionAddress;
    private StatusType currentStatus;

    /**
     * Creates a transaction with an unspecified client type and status and an empty
     * address
     */
    public Transaction() {

        clientType = ClientType.UNSPECIFIED;
        transactionAddress = "";
        currentStatus = StatusType.UNKNOWN;

    }

    /**
     * Creates a transaction with a transaction address
     *
     * @param addressOfTransaction Address of this transaction
     */
    public Transaction(String addressOfTransaction) {

        clientType = ClientType.UNSPECIFIED;
        transactionAddress = addressOfTransaction;
        currentStatus = StatusType.UNKNOWN;

    }

    /**
     * Creates a transaction with a client type, transaction address, and status
     *
     * @param transactionClientType    Type of client for this transaction
     * @param addressOfTransaction     Address of this transaction
     * @param transactionCurrentStatus Current Status of this transaction
     */
    public Transaction(ClientType transactionClientType, String addressOfTransaction,
                       StatusType transactionCurrentStatus) {

        clientType = transactionClientType;
        transactionAddress = addressOfTransaction;
        currentStatus = transactionCurrentStatus;

    }

    /**
     * Gets the client type of the transaction
     *
     * @return Client type of the transaction
     */
    public ClientType getClientType() {

        return clientType;

    }

    /**
     * Gets the address of the transaction
     *
     * @return Address of the transaction
     */
    public String getTransactionAddress() {

        return transactionAddress;

    }

    /**
     * Gets the current status of this transaction
     *
     * @return Current status of this transaction
     */
    public StatusType getCurrentStatus() {

        return currentStatus;

    }

    /**
     * Sets the client type of the transaction
     *
     * @param typeOfClient Client Type of the transaction
     */
    public void setClientType(ClientType typeOfClient) {

        clientType = typeOfClient;

    }

    /**
     * Sets the address of the transaction
     *
     * @param addressOfTransaction Address of the transaction
     */
    public void setTransactionAddress(String addressOfTransaction) {

        transactionAddress = addressOfTransaction;

    }

    /**
     * Sets the current status of this transaction
     *
     * @param statusType Current status of this transaction
     */
    public void setCurrentStatus(StatusType statusType) {

        currentStatus = statusType;

    }

    /**
     * Formats the transaction to a string
     *
     * @return The formated string
     */
    public String toString() {

        return "Client Type: " + getClientType().toString() + "\r\nTransaction Address: " + getTransactionAddress()
                + "\r\nCurrent Status: " + getCurrentStatus().toString();

    }

    /**
     * Compares two transactions based on their address
     *
     * @param o Object the transaction is being compared to
     * @return The difference between the addresses of the transactions
     */
    public int compareTo(Object o) {

        Transaction p = (Transaction) o;
        return getTransactionAddress().compareTo(p.getTransactionAddress());

    }

    /**
     * Checks if two transactions are identical based on their address
     *
     * @param o Object the transaction is being compared to
     * @return Are the transactions identical based on their addresses
     */
    public boolean equals(Object o) {

        return (compareTo(o) == 0);

    }

}