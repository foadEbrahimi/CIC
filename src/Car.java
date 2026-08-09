public class Car {
    protected String model;
    protected CarType type;
    protected int ManufacturingYear;
    protected double price;

    public Car(String model, CarType type, int ManufacturingYear, double price) {
        this.model = model;
        this.type = type;
        this.ManufacturingYear = ManufacturingYear;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public int getManufacturingYear() {
        return this.ManufacturingYear;
    }

    public String getModel() {
        return this.model;
    }

    @Override
    public String toString() {
        return "Model: " + this.model + "Type: " + this.type + "ManufacturingYear: " + this.ManufacturingYear
                + "Price: "
                + this.price;
    }
}
