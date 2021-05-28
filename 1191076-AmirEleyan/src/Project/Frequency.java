/**
 * @autor: Amir Eleyan
 * ID: 1191076
 * At: 2/5/2021  5:04 PM
 */
package Project;

public class Frequency implements Comparable<Frequency> {
    /**
     * Attribute
     */
    private int year;
    private int frequency;

    // no arg constructor
    public Frequency() {
    }

    // constructor with specific data
    public Frequency(int year, int frequency) {
        this.year = year;
        this.frequency = frequency;
    }


    // constructor with year as argument
    public Frequency(int year) {
        this.year = year;
    }

    // return the year for this obj
    public int getYear() {
        return this.year;
    }

    // set a new year of this obj
    public void setYear(int year) {
        this.year = year;
    }

    // get frequency for this obj
    public int getFrequency() {
        return this.frequency;
    }

    // set a new frequency of this obj
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    // return the year and frequency as a sting
    @Override
    public String toString() {
        return year + ", " + frequency;
    }

    // compare two obj based on the year
    @Override
    public int compareTo(Frequency o) {
        return Integer.compare(this.frequency, o.frequency);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Frequency)
            return (this.year == ((Frequency) obj).year);
        return false;
    }
}
