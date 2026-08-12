public class Car {
    protected String model;
    protected CarType type;
    protected int manuFacturingYear;
    protected double price;

    public Car(String model, CarType type, int manuFacturingYear, double price) {
        this.model = model;
        this.type = type;
        this.manuFacturingYear = manuFacturingYear;
        this.price = price;
    }

    // get methods
    public double getPrice() {
        return this.price;
    }

    public int getManufacturingYear() {
        return this.manuFacturingYear;
    }

    public String getModel() {
        return this.model;
    }

    public CarType getType() {
        return this.type;
    }

    // set methods
    public boolean setModel(String model) {
        if (!model.isEmpty()) {
            this.model = model;
            return true;
        }
        return false;
    }

    public boolean setPrice(double price) {
        if (price > 0) {
            this.price = price;
            return true;
        }
        return false;
    }

    public boolean setType(CarType type) {
        if (type != null) {
            this.type = type;
            return true;
        }
        return false;
    }

    public boolean setManufacturingYear(int year) {
        if (year > 0) {
            this.manuFacturingYear = year;
            return true;
        }
        return false;
    }

    public void priceRise(double risePercent) {
        setPrice(this.price * (1 + risePercent));
    }

    @Override
    public String toString() {
        return "Model: " + this.model + "Type: " + this.type + "ManufacturingYear: " + this.manuFacturingYear
                + "Price: "
                + this.price;
    }
}
