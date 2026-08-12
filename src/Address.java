public class Address {
    private int streetNum;
    private String street;
    private String suburb;
    private String city;

    public Address(int streetNum, String street, String suburb, String city) {
        this.streetNum = streetNum;
        this.street = street;
        this.suburb = suburb;
        this.city = city;
    }

    // get methods
    public int getStreetNum() {
        return this.streetNum;
    }

    public String getStreet() {
        return this.street;
    }

    public String getSuburb() {
        return this.suburb;
    }

    public String getCity() {
        return this.city;
    }

    // set methods
    public boolean setStreetNum(int streetNum) {
        if (streetNum > 0) {
            this.streetNum = streetNum;
            return true;
        }
        return false;
    }

    public boolean setStreet(String street) {
        if (street != null && !street.isEmpty()) {
            this.street = street;
            return true;
        }
        return false;
    }

    public boolean setCity(String city) {
        if (city != null && !city.isEmpty()) {
            this.city = city;
            return true;
        }
        return false;
    }

    public boolean setSuburb(String suburb) {
        if (suburb != null && !suburb.isEmpty()) {
            this.suburb = suburb;
            return true;
        }
        return false;
    }

    public void print() {
        System.out.println("  Address: " + this.streetNum + " " + this.street + ", " + this.suburb + ", " + this.city);
    }

    @Override
    public String toString() {
        return this.streetNum + " " + this.street + ", " + this.suburb + ", " + this.city;
    }
}
