import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class InsurancePolicy implements Cloneable, Comparable<InsurancePolicy>, Serializable {
    protected int id;
    protected String policyHolderName;
    protected Car car;
    protected int numberOfClaims;
    protected MyDate expiryDate;

    public InsurancePolicy(String policyHolderName, int id,
            Car car,
            int numberOfClaims, MyDate expiryDate) throws PolicyException {
        this.policyHolderName = policyHolderName;
        this.id = id;
        if (!isValidID(id)) {
            int newID = Helper.generateRandomID(399999, 300000);
            // this.id = newID;
            throw new PolicyException(newID);
        }
        this.car = car;
        this.numberOfClaims = numberOfClaims;
        this.expiryDate = expiryDate;
    }

    private boolean isValidID(int id) {
        String idString = String.valueOf(id);
        return idString.length() == 6 && idString.charAt(0) == '3';
    }

    public void print() {
        System.out.println("  Policy #" + this.id);
        System.out.println("    Car:      " + this.car.getModel());
        System.out.println("    Car-Price:" + String.format("%.2f", this.car.getPrice()));
        System.out.println("    Holder:   " + this.policyHolderName);
        System.out.println("    Claims:   " + this.numberOfClaims);
        System.out.println("    ExpiryDate:" + this.expiryDate.getYear() + "/"
                + this.expiryDate.getMonth() + "/" + this.expiryDate.getDay());
    }

    // get methods
    public int getNumberOfClaims() {
        return this.numberOfClaims;
    }

    public String getPolicyHolderName() {
        return this.policyHolderName;
    }

    public int getId() {
        return this.id;
    }

    public Car getCar() {
        return this.car;
    }

    public MyDate getExpiryDate() {
        return this.expiryDate;
    }

    // set methods
    public boolean setNumberOfClaims(int numberOfClaims) {
        if (numberOfClaims >= 0) {
            this.numberOfClaims = numberOfClaims;
            return true;
        }
        return false;
    }

    public boolean setPolicyHolderName(String policyHolderName) {
        if (policyHolderName != null && !policyHolderName.trim().isEmpty()) {
            this.policyHolderName = policyHolderName;
            return true;
        }
        return false;
    }

    public boolean setCar(Car car) {
        if (car != null) {
            this.car = car;
            return true;
        }
        return false;
    }

    public boolean setExpiryDate(MyDate expiryDate) {
        if (expiryDate != null) {
            this.expiryDate = expiryDate;
            return true;
        }
        return false;
    }

    public boolean setCarModel(String model) {
        if (car.setModel(model)) {
            return true;
        }
        return false;
    }

    // public methods
    static void printPolicies(ArrayList<InsurancePolicy> policies) {
        for (InsurancePolicy policy : policies) {
            policy.print();
        }
    }

    static double calcTotalPayments(ArrayList<InsurancePolicy> policies, int flatRate) {
        double total = 0;
        for (InsurancePolicy policy : policies) {
            total += policy.calcPayment(flatRate);
        }
        return total;
    }

    public void carPriceRise(double risePercent) {
        this.car.priceRise(risePercent);
    }

    static void carPriceRiseAll(ArrayList<InsurancePolicy> policies, double risePercent) {
        for (InsurancePolicy policy : policies) {
            policy.carPriceRise(risePercent);
        }
    }

    static ArrayList<InsurancePolicy> filterByCarModel(ArrayList<InsurancePolicy> policies, String carModel) {
        ArrayList<InsurancePolicy> filteredList = new ArrayList<>();
        for (InsurancePolicy policy : policies) {
            if (policy.getCar().getModel().toLowerCase().contains(carModel.toLowerCase())) {
                filteredList.add(policy);
            }
        }
        return filteredList;
    }

    static boolean existsPolicy(ArrayList<InsurancePolicy> policies, int id) {
        for (InsurancePolicy policy : policies) {
            if (policy.getId() == id) {
                return true;
            }
        }
        return false;
    }

    static ArrayList<InsurancePolicy> filterByExpiryDate(ArrayList<InsurancePolicy> policies, MyDate date) {
        ArrayList<InsurancePolicy> filteredList = new ArrayList<>();
        for (InsurancePolicy policy : policies) {
            if (policy.getExpiryDate().isExpired(date)) {
                filteredList.add(policy);
            }
        }
        return filteredList;
    }

    public abstract double calcPayment(double flatRate);

    @Override
    public String toString() {
        return "Policy #" + this.id + "\n"
                + "  Holder:   " + this.policyHolderName + "\n"
                + "  Car:      " + this.car.model + "\n"
                + "  Claims:   " + this.numberOfClaims + "\n";
    }

    // lab-4
    public InsurancePolicy(InsurancePolicy policy) throws CloneNotSupportedException {
        this.policyHolderName = policy.policyHolderName;
        this.id = policy.id;
        this.car = (Car) policy.car.clone();
        this.numberOfClaims = policy.numberOfClaims;
        this.expiryDate = (MyDate) policy.expiryDate.clone();
    }

    @Override
    public InsurancePolicy clone() throws CloneNotSupportedException {
        InsurancePolicy clonedPolicy = (InsurancePolicy) super.clone();
        if (this.car != null)
            clonedPolicy.car = (Car) this.car.clone();
        if (this.expiryDate != null)
            clonedPolicy.expiryDate = (MyDate) this.expiryDate.clone();
        clonedPolicy.id = this.id;
        return clonedPolicy;
    }

    public static ArrayList<InsurancePolicy> shallowCopy(ArrayList<InsurancePolicy> policies) {
        ArrayList<InsurancePolicy> shallowCopyList = new ArrayList<>();
        for (InsurancePolicy policy : policies) {
            shallowCopyList.add(policy);
        }
        return shallowCopyList;
    }

    public static ArrayList<InsurancePolicy> deepCopy(ArrayList<InsurancePolicy> policies)
            throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> deepCopyList = new ArrayList<>();
        for (InsurancePolicy policy : policies) {
            deepCopyList.add(policy.clone());
        }
        return deepCopyList;
    }

    public int compareTo(InsurancePolicy other) {
        return this.expiryDate.compareTo(other.expiryDate);
    }

    // lab-5
    static void printPolicies(HashMap<Integer, InsurancePolicy> policies) {
        for (InsurancePolicy policy : policies.values()) {
            policy.print();
        }
    }

    static double calcTotalPayments(HashMap<Integer, InsurancePolicy> policies, int flatRate) {
        double total = 0;
        for (InsurancePolicy policy : policies.values()) {
            total += policy.calcPayment(flatRate);
        }
        return total;
    }

    static void carPriceRiseAll(HashMap<Integer, InsurancePolicy> policies, double risePercent) {
        for (InsurancePolicy policy : policies.values()) {
            policy.carPriceRise(risePercent);
        }
    }

    static HashMap<Integer, InsurancePolicy> filterByCarModel(HashMap<Integer, InsurancePolicy> policies,
            String carModel) {
        HashMap<Integer, InsurancePolicy> filteredList = new HashMap<Integer, InsurancePolicy>();
        for (InsurancePolicy policy : policies.values()) {
            if (policy.getCar().getModel().toLowerCase().contains(carModel.toLowerCase())) {
                filteredList.put(policy.getId(), policy);
            }
        }
        return filteredList;
    }

    static boolean existsPolicy(HashMap<Integer, InsurancePolicy> policies, int id) {
        for (InsurancePolicy policy : policies.values()) {
            if (policy.getId() == id) {
                return true;
            }
        }
        return false;
    }

    static ArrayList<InsurancePolicy> filterByExpiryDate(HashMap<Integer, InsurancePolicy> policies, MyDate date) {
        ArrayList<InsurancePolicy> filteredList = new ArrayList<>();
        for (InsurancePolicy policy : policies.values()) {
            if (policy.getExpiryDate().isExpired(date)) {
                filteredList.add(policy);
            }
        }
        return filteredList;
    }

    ArrayList<InsurancePolicy> shallowCopy(HashMap<Integer, InsurancePolicy> policies) {
        ArrayList<InsurancePolicy> shallowCopyList = new ArrayList<>();
        for (InsurancePolicy policy : policies.values()) {
            shallowCopyList.add(policy);
        }
        return shallowCopyList;
    }

    ArrayList<InsurancePolicy> deepCopy(HashMap<Integer, InsurancePolicy> policies) throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> deepCopyList = new ArrayList<>();
        for (InsurancePolicy policy : policies.values()) {
            deepCopyList.add(policy.clone());
        }
        return deepCopyList;
    }

    HashMap<Integer, InsurancePolicy> shallowCopyHashMap(HashMap<Integer, InsurancePolicy> policies) {
        HashMap<Integer, InsurancePolicy> shallowCopyListHashMap = new HashMap<Integer, InsurancePolicy>();
        for (InsurancePolicy policy : policies.values()) {
            shallowCopyListHashMap.put(policy.getId(), policy);
        }
        return shallowCopyListHashMap;
    }

    HashMap<Integer, InsurancePolicy> deepCopyHashMap(HashMap<Integer, InsurancePolicy> policies)
            throws CloneNotSupportedException {
        HashMap<Integer, InsurancePolicy> shallowCopyListHashMap = new HashMap<Integer, InsurancePolicy>();
        for (InsurancePolicy policy : policies.values()) {
            shallowCopyListHashMap.put(policy.getId(), policy.clone());
        }
        return shallowCopyListHashMap;
    }

    // lab-6
    static HashMap<Integer, InsurancePolicy> load(String fileName)
            throws IOException, ClassNotFoundException {
        HashMap<Integer, InsurancePolicy> loadedPolicies = new HashMap<>();
        ObjectInputStream inputFile = null;
        try {
            inputFile = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
            while (true) {
                try {
                    InsurancePolicy policy = (InsurancePolicy) inputFile.readObject();
                    loadedPolicies.put(policy.getId(), policy);
                } catch (EOFException e) {
                    break;
                }
            }
        } finally {
            if (inputFile != null) {
                inputFile.close();
            }
        }
        return loadedPolicies;
    }

    static Boolean save(HashMap<Integer, InsurancePolicy> list, String fileName)
            throws IOException {
        ObjectOutputStream outputFile = null;
        try {
            outputFile = new ObjectOutputStream(
                    Files.newOutputStream(Paths.get(fileName)));
            for (InsurancePolicy policy : list.values()) {
                outputFile.writeObject(policy);
            }
        } finally {
            if (outputFile != null) {
                outputFile.close();
            }
        }
        return true;
    }

    public String toDilimatedString() {
        return "policy," + id + "," + policyHolderName + "," + numberOfClaims;
    }

    static HashMap<Integer, InsurancePolicy> loadTextFile(String fileName)
            throws IOException, PolicyException {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        HashMap<Integer, InsurancePolicy> loadedPolicies = new HashMap<>();
        String currentLine;
        while ((currentLine = fileReader.readLine()) != null) {
            currentLine = currentLine.trim();
            if (currentLine.isEmpty())
                continue;
            String[] fields = currentLine.split(",", -1);

            int id = Integer.parseInt(fields[1]);
            String name = fields[2];
            int numberOfClaims = Integer.parseInt(fields[3]);

            String carModel = fields[5];
            CarType carType = CarType.valueOf(fields[6]);
            int carYear = Integer.parseInt(fields[7]);
            double carPrice = Double.parseDouble(fields[8]);
            Car car = new Car(carModel, carType, carYear, carPrice);

            int year = Integer.parseInt(fields[10]);
            int month = Integer.parseInt(fields[11]);
            int day = Integer.parseInt(fields[12]);
            MyDate date = new MyDate(year, month, day);

            String type = fields[13];
            InsurancePolicy policy;
            if (type.equals("Third")) {
                String comments = fields[14];
                policy = new ThirdPartyPolicy(name, id, car, numberOfClaims, comments, date);
            } else {
                int driverAge = Integer.parseInt(fields[14]);
                int level = Integer.parseInt(fields[15]);
                policy = new ComprehensivePolicy(name, id, car, numberOfClaims, driverAge, level, date);
            }
            loadedPolicies.put(id, policy);
        }
        fileReader.close();
        return loadedPolicies;
    }

    static Boolean saveTextFile(HashMap<Integer, InsurancePolicy> policies, String fileName)
            throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName));
        for (InsurancePolicy policy : policies.values()) {
            fileWriter.write(policy.toDilimatedString());
            fileWriter.write("\n");
        }
        fileWriter.close();
        return true;
    }
}
