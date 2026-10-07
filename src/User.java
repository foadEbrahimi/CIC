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
import java.util.Collections;
import java.util.HashMap;

public class User implements Cloneable, Comparable<User>, Serializable {
    private static int idCounter = 0;
    private String name;
    private int userID;
    private Address address;
    // private ArrayList<InsurancePolicy> policies;
    private HashMap<Integer, InsurancePolicy> policies = new HashMap<Integer, InsurancePolicy>();

    public User(String name, Address address) {
        this.name = name;
        this.userID = idCounter++;
        this.address = address;
        // this.policies = new ArrayList<>();
        this.policies = new HashMap<Integer, InsurancePolicy>();
    }

    // get methods
    public String getName() {
        return this.name;
    }

    public int getUserID() {
        return this.userID;
    }

    public Address getAddress() {
        return this.address;
    }

    // public ArrayList<InsurancePolicy> getPolicies() {
    // return this.policies;
    // }

    // set methods
    public boolean setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
            return true;
        }
        return false;
    }

    public boolean setAddress(Address address) {
        if (address != null && address.getStreetNum() > 0 && !address.getStreet().trim().isEmpty()
                && !address.getSuburb().trim().isEmpty()
                && !address.getCity().trim().isEmpty()) {
            this.address = address;
            return true;
        }
        return false;
    }

    public boolean setCity(String city) {
        if (address.setCity(city)) {
            return true;
        }
        return false;
    }

    // public boolean addPolicy(InsurancePolicy policy) {
    // if (!InsurancePolicy.existsPolicy(this.policies, policy.getId())) {
    // this.policies.add(policy);
    // return true;
    // }
    // return false;
    // }

    // InsurancePolicy findPolicy(int policyID) {
    // for (InsurancePolicy policy : policies) {
    // for (InsurancePolicy policy : policies) {
    // if (policy.getId() == policyID) {
    // return policy;
    // }
    // }
    // return null;
    // }

    // void printPolicies(int flatRate) {
    // for (InsurancePolicy policy : policies) {
    // // policy.print();
    // System.out.println(" Premium: " + String.format("%.2f",
    // policy.calcPayment(flatRate)));
    // }
    // }

    double calcTotalPremiums(int flatRate) {
        return InsurancePolicy.calcTotalPayments(this.policies, flatRate);
    }

    void carPriceRiseAll(double risePercent) {
        InsurancePolicy.carPriceRiseAll(this.policies, risePercent);
    }

    HashMap<Integer, InsurancePolicy> filterByCarModel(String carModel) {
        return InsurancePolicy.filterByCarModel(this.policies, carModel);
    }

    boolean createThirdPartyPolicy(String policyHolderName, int id, Car car, int numberOfClaims,
            String comments, MyDate expirDate) throws PolicyException {
        if (addPolicy(new ThirdPartyPolicy(policyHolderName, id, car, numberOfClaims,
                comments, expirDate))) {
            return true;
        }
        return false;
    }

    boolean createComprehensivePolicy(String policyHolderName, int id, Car car, int numberOfClaims,
            int driverAge, int level, MyDate expirDate) throws IllegalArgumentException, PolicyException {
        if (addPolicy(
                new ComprehensivePolicy(policyHolderName, id, car, numberOfClaims, driverAge, level, expirDate))) {
            return true;
        }
        return false;
    }

    ArrayList<InsurancePolicy> filterByExpiryDate(MyDate date) {
        return InsurancePolicy.filterByExpiryDate(policies, date);
    }

    // public boolean removePolicy(int policyID) {
    // for (InsurancePolicy policy : this.policies) {
    // if (policy.getId() == policyID) {
    // policies.remove(policy);
    // return true;
    // }
    // }
    // return false;
    // }

    // ArrayList<String> populateDistinctCarModels() {
    // ArrayList<String> cars = new ArrayList<>();
    // for (InsurancePolicy policy : this.policies) {
    // if (!cars.contains(policy.getCar().getModel().toLowerCase())) {
    // cars.add(policy.getCar().getModel().toLowerCase());
    // } else
    // continue;
    // }
    // return cars;
    // }

    // public double getTotalCountForCarModel(String carModel) {
    // int count = 0;
    // for (InsurancePolicy policy : this.policies) {
    // if (policy.getCar().getModel().equalsIgnoreCase(carModel)) {
    // count++;
    // }
    // }
    // return count;
    // }

    // public double getTotalPaymentForCarModel(String carModel, int flatRate) {
    // double total = 0;
    // for (InsurancePolicy policy : this.policies) {
    // if (policy.getCar().getModel().equalsIgnoreCase(carModel)) {
    // total += policy.calcPayment(flatRate);
    // }
    // }
    // return total;
    // }

    public ArrayList<Integer> getTotalCountPerCarModel(ArrayList<String> carModels) {
        ArrayList<Integer> counts = new ArrayList<>();
        for (String model : carModels) {
            counts.add((int) getTotalCountForCarModel(model));
        }
        return counts;
    }

    public ArrayList<Double> getTotalPaymentPerCarModel(ArrayList<String> carModels, int flatRate) {
        ArrayList<Double> payments = new ArrayList<>();
        for (String model : carModels) {
            payments.add(getTotalPaymentForCarModel(model, flatRate));
        }
        return payments;
    }

    public void reportPaymentsPerCarModel(ArrayList<String> carModels, ArrayList<Integer> counts,
            ArrayList<Double> premiumPayments) {
        System.out.printf("%-25s %-25s %-25s%n", "Car Model", "Total Premium Payment", "Average Premium Payment");

        for (int i = 0; i < carModels.size(); i++) {
            String model = carModels.get(i);
            int count = counts.get(i);
            double totalPayment = premiumPayments.get(i);
            double average = 0;
            if (count > 0) {
                average = totalPayment / count;
            }
            System.out.printf("%-25s $%,.2f \t\t $%,.2f%n", model, totalPayment, average);
        }
    }

    public void print() {
        System.out.println("\n--- User Information ---");
        System.out.println("Name: " + this.name);
        System.out.println("User ID: " + this.userID);
        System.out.println("Address: " + address);
        System.out.println("Policies:");

        if (policies.isEmpty())
            System.out.println("  No policies found.");
        else
            InsurancePolicy.printPolicies(policies);
    }

    // @Override
    // public String toString() {
    // String result = " Name: " + this.name + "\n"
    // + " Address: " + this.address.getCity() + "\n"
    // + " Policies:\n";
    // for (InsurancePolicy policy : policies) {
    // result += " " + policy.toString().replace("\n", "\n ") + "\n";
    // }
    // return result;
    // }

    // lab-4
    // public User(User user) throws CloneNotSupportedException {
    // this.name = user.name;
    // this.userID = idCounter++;
    // this.address = user.address.clone();
    // this.policies = new ArrayList<>();
    // for (InsurancePolicy policy : user.policies) {
    // // this.policies.add(policy.clone());
    // if (policy instanceof ThirdPartyPolicy)
    // this.policies.add(new ThirdPartyPolicy((ThirdPartyPolicy) policy));
    // else {
    // this.policies.add(new ComprehensivePolicy((ComprehensivePolicy) policy));
    // }
    // }
    // }

    @Override
    public User clone() throws CloneNotSupportedException {
        return new User(this);
        // User clonedUser = (User) super.clone();
        // clonedUser.address = this.address.clone();
        // clonedUser.policies = new ArrayList<>();
        // for (InsurancePolicy policy : this.policies) {
        // clonedUser.policies.add(policy.clone());
        // }
        // clonedUser.userID = this.idCounter++;
        // return clonedUser;
    }

    // public static ArrayList<User> shallowCopy(ArrayList<User> users) {
    // ArrayList<User> shallowCopyList = new ArrayList<>();
    // for (User user : users) {
    // shallowCopyList.add(user);
    // }
    // return shallowCopyList;
    // }

    // public static ArrayList<User> deepCopy(ArrayList<User> users) throws
    // CloneNotSupportedException {
    // ArrayList<User> deepCopyList = new ArrayList<>();
    // for (User user : users) {
    // deepCopyList.add(user.clone());
    // }
    // return deepCopyList;
    // }

    // ArrayList<InsurancePolicy> deepCopyPolicies() throws
    // CloneNotSupportedException {
    // ArrayList<InsurancePolicy> deepCopyList = new ArrayList<>();
    // for (InsurancePolicy policy : this.getPolicies()) {
    // // deepCopyList.add(policy.clone());
    // if (policy instanceof ThirdPartyPolicy)
    // deepCopyList.add(new ThirdPartyPolicy((ThirdPartyPolicy) policy));
    // else {
    // deepCopyList.add(new ComprehensivePolicy((ComprehensivePolicy) policy));
    // }
    // }
    // return deepCopyList;
    // }

    // ArrayList<InsurancePolicy> shallowCopyPolicies() {
    // ArrayList<InsurancePolicy> shallowCopyList = new ArrayList<>();
    // for (InsurancePolicy policy : this.policies) {
    // shallowCopyList.add(policy);
    // }
    // return shallowCopyList;
    // }

    public int compareTo1(User otherUser) {
        return this.getAddress().compareTo(otherUser.getAddress());
    }

    public int compareTo(User otherUser) {
        final int flatRate = 2;
        double myTotal = this.calcTotalPremiums(flatRate);
        double otherTotal = otherUser.calcTotalPremiums(flatRate);
        return Double.compare(myTotal, otherTotal);

        // other way
        // if (myTotal == otherTotal) {
        // return 0;
        // }
        // return myTotal > otherTotal ? 1 : -1;
    }

    // public ArrayList<InsurancePolicy> sortPoliciesByDate() {
    // ArrayList<InsurancePolicy> sortedPolicies = new ArrayList<>(this.policies);
    // Collections.sort(sortedPolicies);
    // return sortedPolicies;
    // }

    // lab-5
    public HashMap<Integer, InsurancePolicy> getPolicies() {
        return this.policies;
    }

    public boolean addPolicy(InsurancePolicy policy) {
        if (!InsurancePolicy.existsPolicy(this.policies, policy.getId())) {
            this.policies.put(policy.getId(), policy);
            return true;
        }
        return false;
    }

    InsurancePolicy findPolicy(int policyID) {
        return this.policies.get(policyID);
    }

    void printPolicies(int flatRate) {
        for (InsurancePolicy policy : policies.values()) {
            System.out.println("    Premium:  " + String.format("%.2f", policy.calcPayment(flatRate)));
        }
    }

    public boolean removePolicy(int policyID) {
        return this.policies.remove(policyID) != null;
    }

    ArrayList<String> populateDistinctCarModels() {
        ArrayList<String> cars = new ArrayList<>();
        for (InsurancePolicy policy : this.policies.values()) {
            if (!cars.contains(policy.getCar().getModel().toLowerCase())) {
                cars.add(policy.getCar().getModel().toLowerCase());
            } else
                continue;
        }
        return cars;
    }

    public int getTotalCountForCarModel(String carModel) {
        int count = 0;
        for (InsurancePolicy policy : this.policies.values()) {
            if (policy.getCar().getModel().equalsIgnoreCase(carModel)) {
                count++;
            }
        }
        return count;
    }

    public double getTotalPaymentForCarModel(String carModel, int flatRate) {
        double total = 0;
        for (InsurancePolicy policy : this.policies.values()) {
            if (policy.getCar().getModel().equalsIgnoreCase(carModel)) {
                total += policy.calcPayment(flatRate);
            }
        }
        return total;
    }

    @Override
    public String toString() {
        String result = "  Name: " + this.name + "\n"
                + "  Address: " + this.address.getCity() + "\n"
                + "  Policies:\n";
        for (InsurancePolicy policy : policies.values()) {
            result += "    " + policy.toString().replace("\n", "\n    ") + "\n";
        }
        return result;
    }

    public User(User user) throws CloneNotSupportedException {
        this.name = user.name;
        this.userID = idCounter++;
        this.address = user.address.clone();
        this.policies = new HashMap<Integer, InsurancePolicy>();
        for (InsurancePolicy policy : user.policies.values()) {
            // this.policies.add(policy.clone());
            if (policy instanceof ThirdPartyPolicy)
                this.policies.put(policy.getId(), new ThirdPartyPolicy((ThirdPartyPolicy) policy));
            else {
                this.policies.put(policy.getId(), new ComprehensivePolicy((ComprehensivePolicy) policy));
            }
        }
    }

    // lab-5
    public static ArrayList<User> shallowCopy(HashMap<Integer, User> users) {
        ArrayList<User> shallowCopyList = new ArrayList<>();
        for (User user : users.values()) {
            shallowCopyList.add(user);
        }
        return shallowCopyList;
    }

    public static ArrayList<User> deepCopy(HashMap<Integer, User> users) throws CloneNotSupportedException {
        ArrayList<User> deepCopyList = new ArrayList<>();
        for (User user : users.values()) {
            deepCopyList.add(user.clone());
        }
        return deepCopyList;
    }

    ArrayList<InsurancePolicy> deepCopyPolicies() throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> deepCopyList = new ArrayList<>();
        for (InsurancePolicy policy : this.policies.values()) {
            // deepCopyList.add(policy.clone());
            if (policy instanceof ThirdPartyPolicy)
                deepCopyList.add(new ThirdPartyPolicy((ThirdPartyPolicy) policy));
            else {
                deepCopyList.add(new ComprehensivePolicy((ComprehensivePolicy) policy));
            }
        }
        return deepCopyList;
    }

    ArrayList<InsurancePolicy> shallowCopyPolicies() {
        ArrayList<InsurancePolicy> shallowCopyList = new ArrayList<>();
        for (InsurancePolicy policy : this.policies.values()) {
            shallowCopyList.add(policy);
        }
        return shallowCopyList;
    }

    // with HashMap input and output
    public static HashMap<Integer, User> shallowCopyHashMap(HashMap<Integer, User> users) {
        HashMap<Integer, User> shallowCopyList = new HashMap<Integer, User>();
        for (User user : users.values()) {
            shallowCopyList.put(user.getUserID(), user);
        }
        return shallowCopyList;
    }

    public static HashMap<Integer, User> deepCopyHashMap(HashMap<Integer, User> users)
            throws CloneNotSupportedException {
        HashMap<Integer, User> deepCopyList = new HashMap<Integer, User>();
        for (User user : users.values()) {
            deepCopyList.put(user.getUserID(), user.clone());
        }
        return deepCopyList;
    }

    HashMap<Integer, InsurancePolicy> deepCopyPoliciesHashMap() throws CloneNotSupportedException {
        HashMap<Integer, InsurancePolicy> deepCopyList = new HashMap<Integer, InsurancePolicy>();
        for (InsurancePolicy policy : this.policies.values()) {
            // deepCopyList.add(policy.clone());
            if (policy instanceof ThirdPartyPolicy)
                deepCopyList.put(policy.getId(), new ThirdPartyPolicy((ThirdPartyPolicy) policy));
            else {
                deepCopyList.put(policy.getId(), new ComprehensivePolicy((ComprehensivePolicy) policy));
            }
        }
        return deepCopyList;
    }

    HashMap<Integer, InsurancePolicy> shallowCopyPoliciesHashMap() {
        HashMap<Integer, InsurancePolicy> shallowCopyList = new HashMap<Integer, InsurancePolicy>();
        for (InsurancePolicy policy : this.policies.values()) {
            shallowCopyList.put(policy.getId(), policy);
        }
        return shallowCopyList;
    }

    public ArrayList<InsurancePolicy> sortPoliciesByDate() throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> sortedPolicies = new ArrayList<>(deepCopyPoliciesHashMap().values());
        Collections.sort(sortedPolicies);
        return sortedPolicies;
    }

    HashMap<String, Integer> getTotalCountPerCarModel() {
        HashMap<String, Integer> countsAndCarModel = new HashMap<String, Integer>();
        for (InsurancePolicy policy : this.policies.values()) {
            String model = policy.getCar().getModel().toLowerCase();
            if (countsAndCarModel.get(model) == null) {
                countsAndCarModel.put(model, 1);
            } else {
                countsAndCarModel.put(model, countsAndCarModel.get(model) + 1);
            }
        }
        return countsAndCarModel;
    }

    HashMap<String, Double> getTotalPremiumPerCarModel(int flatRate) {
        HashMap<String, Double> paymentsAndCarModel = new HashMap<String, Double>();
        for (InsurancePolicy policy : this.policies.values()) {
            String model = policy.getCar().getModel().toLowerCase();
            double payment = policy.calcPayment(flatRate);
            if (paymentsAndCarModel.get(model) == null) {
                paymentsAndCarModel.put(model, payment);
            } else {
                paymentsAndCarModel.put(model, paymentsAndCarModel.get(model) + payment);
            }
        }
        return paymentsAndCarModel;
    }

    void reportCarModels(int flatRate) {
        System.out.printf("%-25s %-25s %-25s%n", "Car Model", "Total Premium Payment", "Average Premium Payment");
        HashMap<String, Double> data = getTotalPremiumPerCarModel(flatRate);
        HashMap<String, Integer> counts = getTotalCountPerCarModel();

        for (String model : data.keySet()) {
            int count = counts.get(model);
            double totalPayment = data.get(model);
            double average = 0;
            if (count > 0) {
                average = totalPayment / count;
            }
            System.out.printf("%-25s $%,.2f \t\t    $%,.2f%n", model, totalPayment, average);
        }
    }

    // lab-6
    // binary-file
    static HashMap<Integer, User> load(String fileName)
            throws IOException, ClassNotFoundException {
        HashMap<Integer, User> loadedUsers = new HashMap<Integer, User>();
        ObjectInputStream inputFile = null;
        try {
            inputFile = new ObjectInputStream(
                    Files.newInputStream(Paths.get(fileName)));
            while (true) {
                try {
                    User user = (User) inputFile.readObject();
                    loadedUsers.put(user.getUserID(), user);
                } catch (EOFException ex) {
                    break;
                }
            }
        } finally {
            if (inputFile != null) {
                inputFile.close();
            }
        }
        return loadedUsers;
    }

    static Boolean save(HashMap<Integer, User> usersList, String fileName)
            throws IOException {
        ObjectOutputStream outputFile = null;
        try {
            outputFile = new ObjectOutputStream(
                    Files.newOutputStream(Paths.get(fileName)));
            for (User user : usersList.values()) {
                outputFile.writeObject(user);
            }
        } finally {
            if (outputFile != null) {
                outputFile.close();
            }
        }
        return true;
    }

    // text-file
    static HashMap<Integer, User> loadTextFile(String fileName)
            throws IOException, PolicyException {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        HashMap<Integer, User> loadedUsers = new HashMap<>();
        String currentLine;
        while ((currentLine = fileReader.readLine()) != null) {
            currentLine = currentLine.trim();
            if (currentLine.isEmpty())
                continue;

            String[] fields = currentLine.split(",", -1);
            int userID = Integer.parseInt(fields[1]);
            String name = fields[2];
            int streetNum = Integer.parseInt(fields[4]);
            String street = fields[5];
            String suburb = fields[6];
            String city = fields[7];
            Address address = new Address(streetNum, street, suburb, city);
            User user = new User(name, address);

            int i = 8;
            while (i < fields.length) {
                int policyID = Integer.parseInt(fields[i + 1]);
                String policyHolderName = fields[i + 2];
                int numberOfClaims = Integer.parseInt(fields[i + 3]);

                String carModel = fields[i + 5];
                CarType carType = CarType.valueOf(fields[i + 6]);
                int carYear = Integer.parseInt(fields[i + 7]);
                double carPrice = Double.parseDouble(fields[i + 8]);
                Car car = new Car(carModel, carType, carYear, carPrice);

                int year = Integer.parseInt(fields[i + 10]);
                int month = Integer.parseInt(fields[i + 11]);
                int day = Integer.parseInt(fields[i + 12]);
                MyDate date = new MyDate(year, month, day);

                String policyType = fields[i + 13];
                int policyLength;
                InsurancePolicy policy;
                if (policyType.equals("Third")) {
                    String comments = fields[i + 14];
                    policy = new ThirdPartyPolicy(policyHolderName, policyID, car, numberOfClaims, comments, date);
                    // اینجا میشه نقطه ای که اطلاعات پالیسی تموم میشه و چون پالیسی از نوع third 15
                    // تا فیلد داره پالیسی بعدیمون باید
                    // از ایندکس 8 که شروع پالیسی ها هست + 15 بشه تا برسیم به پالیسی بعدی
                    policyLength = 15;
                } else {
                    int driverAge = Integer.parseInt(fields[i + 14]);
                    int level = Integer.parseInt(fields[i + 15]);
                    policy = new ComprehensivePolicy(policyHolderName, policyID, car, numberOfClaims, driverAge, level,
                            date);
                    // اینجا هم همینطور اما 16 چون پالیسی از این نوع 16 تا فیلد داره
                    policyLength = 16;
                }
                user.addPolicy(policy);
                i += policyLength;
            }
            loadedUsers.put(userID, user);
        }
        fileReader.close();
        return loadedUsers;
    }

    static Boolean saveTextFile(String fileName, HashMap<Integer, User> users)
            throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : users.values()) {
                fileWriter.write(user.toDilimatedString());
                fileWriter.write("\n");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String toDilimatedString() {
        String result = "user," + this.userID + "," + this.name + ","
                + this.address.toDilimatedString();
        for (InsurancePolicy policy : this.policies.values()) {
            result += "," + policy.toDilimatedString();
        }
        return result;
    }
}
