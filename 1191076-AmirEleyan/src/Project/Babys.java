/**
 * @autor: Amir Eleyan
 * ID: 1191076
 * At: 6/5/2021  5:02 AM
 */
package Project;

public class Babys implements Comparable<Babys> {

    /**
     * Attribute
     */
    private String name;
    private char gender;
    private Frequency frequency;

    // no arg constructor
    public Babys() {

    }

    // constructor with a specific data
    public Babys(String name, char gender, Frequency frequency) {
        this.name = name;
        this.gender = Character.toUpperCase(gender);
        this.frequency = frequency;

    }

    // constructor with a specific data
    public Babys(String name, char gender) {
        this.name = name;
        this.gender = Character.toUpperCase(gender);
    }

    // constructor with a specific data
    public Babys(String name) {
        this.name = name;
    }


    // constructor with line of data
    public Babys(String lineOfDate, int year) {
        int FirstQ = lineOfDate.indexOf(',');
        int LastQ = lineOfDate.lastIndexOf(',');
        this.name = lineOfDate.substring(0, FirstQ).trim();
        this.gender = Character.toUpperCase((lineOfDate.substring(FirstQ + 1, LastQ)).trim().charAt(0));
        this.frequency = new Frequency(year, Integer.parseInt(lineOfDate.substring(LastQ + 1).trim()));
    }

    // return the name of this baby
    public String getName() {
        return this.name;
    }

    // set a new name for this baby
    public void setName(String name) {
        this.name = name;
    }

    // return the gander of this baby
    public char getGender() {
        return this.gender;
    }

    // set a new gander for this baby
    public void setGender(char gender) {
        this.gender = Character.toUpperCase(gender);
    }

    // Return the frequency of this baby
    public Frequency getFrequency() {
        return this.frequency;
    }

    // set a new frequency for this baby
    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    // clear frequency for this baby
    public void clearFrequency() {
        this.frequency = null;
    }

    // check baby if is female
    public boolean isFemale() {
        return this.gender == 'F';
    }

    // check baby if is male
    public boolean isMale() {
        return this.gender == 'M';
    }

    // return the name and gender od this baby as string
    @Override
    public String toString() {
        return name + ", " + gender;
    }

    // compare two object based to the name and gender
    @Override
    public int compareTo(Babys o) {
        int compareName = this.name.compareToIgnoreCase(o.name);
        if (compareName == 0) return Character.compare(this.gender, o.gender);
        else return compareName;

    }

    // compare to baby based of the name and gander
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Babys) {
            return (this.name.equalsIgnoreCase(((Babys) obj).name) && this.gender == ((Babys) obj).gender);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.name.toLowerCase().hashCode() + this.gender;
    }
}
