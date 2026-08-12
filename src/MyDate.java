public class MyDate {
    private int year;
    private int month;
    private int day;

    public MyDate(int year,
            int month,
            int day) {
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
}
