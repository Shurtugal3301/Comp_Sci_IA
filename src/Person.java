import java.io.Serializable;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

public class Person implements Serializable, Comparable {

    private String firstName;
    private char middleInitial;
    private String lastName;
    private String relation;
    private GregorianCalendar birthday;

    /**
     * Constructs a default person with a name of Steve Rogers, relation of client, and birthday
     * of January 1st, 2000
     */
    public Person() {

        firstName = "Steve";
        middleInitial = 'C';
        lastName = "Rogers";
        relation = "Client";
        birthday = new GregorianCalendar(2000, 0, 1);

    }

    /**
     * Constructs a person with a name
     *
     * @param personFirstName First name of the person
     */
    public Person(String personFirstName) {

        firstName = personFirstName;
        middleInitial = '\u0000';
        lastName = "";
        relation = "Client";
        birthday = new GregorianCalendar(2000, 0, 1);

    }

    /**
     * Constructs a person with a given name, relation, and birthday
     *
     * @param personFirstName     First name of the person
     * @param personMiddleInitial Middle initial of the person
     * @param personLastName      Last name of the person
     * @param personRelation      Relation of the person to the client
     * @param personBirthday      Birthday of the person
     */
    public Person(String personFirstName, char personMiddleInitial, String personLastName, String personRelation,
                  GregorianCalendar personBirthday) {

        firstName = personFirstName;
        middleInitial = personMiddleInitial;
        lastName = personLastName;
        relation = personRelation;
        birthday = personBirthday;

    }

    /**
     * Gets the first name of the person
     *
     * @return First name of the person
     */
    public String getFirstName() {

        return firstName;

    }

    /**
     * Gets the name of the person
     *
     * @return Name of the person
     */
    public String getName() {

        return firstName + " "
                + ((Character.isLetter(middleInitial) && middleInitial != '\u0000') ? (middleInitial + ". ") : "")
                + lastName;

    }

    /**
     * Gets the relation of the person to the client
     *
     * @return Relation of the person to the client
     */
    public String getRelation() {

        return relation;

    }

    /**
     * Gets the birthday of the person
     *
     * @return Birthday of the person
     */
    public GregorianCalendar getBirthday() {

        return birthday;

    }

    /**
     * Sets the name of the Person
     *
     * @param personFirstName     The person's first name
     * @param personMiddleInitial Middle initial of the person
     * @param personLastName      ast name of the person
     */
    public void setName(String personFirstName, char personMiddleInitial, String personLastName) {

        firstName = personFirstName;
        middleInitial = personMiddleInitial;
        lastName = personLastName;

    }

    /**
     * Sets the relation of the person to the client
     *
     * @param personRelation Relation of the person to the client
     */
    public void setRelation(String personRelation) {

        relation = personRelation;

    }

    /**
     * Sets the person's birthday
     *
     * @param personBirthday Birthday of the person
     */
    public void setBirthday(GregorianCalendar personBirthday) {

        birthday = personBirthday;

    }

    /**
     * Formats the person to a string
     *
     * @return The formated string
     */
    public String toString() {

        return "Name: " + getName() + "\nRelation: " + getRelation() + "\nBirthday: "
                + Window.DATE_FORMAT.format(birthday.getTime());

    }

    /**
     * Compares two people based on their first name
     *
     * @param o Object the person is being compared to
     * @return The difference between the names of the people
     */
    public int compareTo(Object o) {

        Person p = (Person) o;
        return getFirstName().toLowerCase().compareTo(p.getFirstName().toLowerCase());

    }

    /**
     * Checks if two people are identical based on their name
     *
     * @param o Object the person is being compared to
     * @return Are the people identical based on their name
     */
    public boolean equals(Object o) {

        return (compareTo(o) == 0);

    }

    /**
     * Formats the person for saving
     *
     * @return The formatted string
     */
    public String saveToFile() {

        return String.format("PRSN ~%s~ ~%s~ ~%d~ /n/", getName(), getRelation(),
                birthday.getTime().getTime());

    }

}
