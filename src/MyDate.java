import java.io.Serializable;
import java.time.LocalDate;

public class MyDate implements Cloneable, Comparable<MyDate>, Serializable {
    private int year;
    private int month;
    private int day;

    public MyDate(int year, int month, int day) throws IllegalArgumentException {
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than 0");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day must be between 1 and 31");
        }
        this.year = year;
        this.month = month;
        this.day = day;
    }

    // get methods
    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    // set methods
    public boolean setYear(int year) {
        if (year > 0) {
            this.year = year;
            return true;
        }
        return false;
    }

    public boolean setMonth(int month) {
        if (month >= 1 && month <= 12) {
            this.month = month;
            return true;
        }
        return false;
    }

    public boolean setDay(int day) {
        if (day >= 1 && day <= 31) {
            this.day = day;
            return true;
        }
        return false;
    }

    boolean isExpired(MyDate expiryDate) {
        LocalDate currentDate = LocalDate.of(
                this.getYear(),
                this.getMonth(),
                this.getDay());

        LocalDate expiry = LocalDate.of(
                expiryDate.getYear(),
                expiryDate.getMonth(),
                expiryDate.getDay());
        return !currentDate.isAfter(expiry);
    }

    // lab-4
    public MyDate(MyDate date) {
        this.year = date.year;
        this.month = date.month;
        this.day = date.day;
    }

    @Override
    public MyDate clone() throws CloneNotSupportedException {
        return (MyDate) super.clone();
    }

    @Override
    public int compareTo(MyDate other) {
        LocalDate thisDate = LocalDate.of(this.year, this.month, this.day);
        LocalDate otherDate = LocalDate.of(other.year, other.month, other.day);
        return thisDate.compareTo(otherDate);
    }

    // lab-6
    public String toDilimatedString() {
        return "date," + year + "," + month + "," + day;
    }
}
