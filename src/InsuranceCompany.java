import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class InsuranceCompany implements Cloneable, Serializable {
    String name;
    // private ArrayList<User> users;
    private HashMap<Integer, User> users = new HashMap<Integer, User>();
    private String adminUsername;
    private String adminPassword;
    private int flatRate;

    public InsuranceCompany(
            String name,
            String adminUsername,
            String adminPassword,
            int flatRate) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Company name cannot be empty.");
        }
        if (adminUsername == null || adminUsername.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Admin username cannot be empty.");
        }
        if (adminPassword == null || adminPassword.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Admin password cannot be empty.");
        }
        if (flatRate < 0) {
            throw new IllegalArgumentException(
                    "Flat rate cannot be negative.");
        }
        this.name = name;
        // this.users = new ArrayList<>();
        this.users = new HashMap<>();
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.flatRate = flatRate;
    }

    // get methods
    public String getName() {
        return this.name;
    }

    // public ArrayList<User> getUsers() {
    // return this.users;
    // }

    public String getAdminUsername() {
        return this.adminUsername;
    }

    public String getAdminPassword() {
        return this.adminPassword;
    }

    public int getFlatRate() {
        return this.flatRate;
    }

    // set methods
    public boolean setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
            return true;
        }
        return false;
    }

    public boolean setAdminUsername(String adminUsername) {
        if (adminUsername != null && !adminUsername.trim().isEmpty()) {
            this.adminUsername = adminUsername;
            return true;
        }
        return false;
    }

    public boolean setAdminPassword(String adminPassword) {
        if (adminPassword != null && !adminPassword.trim().isEmpty()) {
            this.adminPassword = adminPassword;
            return true;
        }
        return false;
    }

    public boolean setFlatRate(int flatRate) {
        if (flatRate >= 0) {
            this.flatRate = flatRate;
            return true;
        }
        return false;
    }

    boolean validateAdmin(String username, String password) {
        if (this.adminUsername.equals(username) && this.adminPassword.equals(password)) {
            return true;
        }
        return false;
    }

    // boolean addUser(User user) {
    // if (user == null) {
    // throw new NullPointerException(
    // "User cannot be null.");
    // }
    // if (existsUser(user.getUserID())) {
    // return false;
    // }
    // this.users.add(user);
    // return true;
    // }

    boolean addUser(String name, Address address) {
        // way one
        // if (!existsUser(this.users, userID)) {
        // addUser(new User(name, userID, address));
        // return true;
        // }
        // return false;

        // the better way
        // if (addUser(new User(name, userID, address))) {
        // return true;
        // }
        // return false;

        // the other better way
        return addUser(new User(name, address));
    }

    // User findUser(int userID) {
    // for (User user : this.users) {
    // if (user.getUserID() == userID) {
    // return user;
    // }
    // }
    // throw new IllegalArgumentException(
    // "User with ID " + userID + " not found.");
    // }

    boolean addPolicy(int userID, InsurancePolicy policy) {
        User user = findUser(userID);

        if (InsurancePolicy.existsPolicy(user.getPolicies(), policy.getId())) {
            return false;
        }

        user.addPolicy(policy);
        return true;
    }

    void printPolicies(int userID) {
        User user = findUser(userID);
        user.print();
    }

    boolean createThirdPartyPolicy(int userID, String policyHolderName, int id, Car car, int numberOfClaims,
            String comments, MyDate expiryDate) throws PolicyException {
        User user = findUser(userID);
        return user.createThirdPartyPolicy(policyHolderName, id, car, numberOfClaims, comments, expiryDate);
    }

    boolean createComprehensivePolicy(int userID, String policyHolderName, int id, Car car, int numberOfClaims,
            int driverAge, int level, MyDate expiryDate) throws IllegalArgumentException, PolicyException {
        User user = findUser(userID);
        return user.createComprehensivePolicy(policyHolderName, id, car, numberOfClaims, driverAge,
                level, expiryDate);
    }

    // public boolean existsUser(int userID) {
    // for (User user : this.users) {
    // if (user.getUserID() == userID) {
    // return true;
    // }
    // }
    // return false;
    // }

    double calcTotalPayments(int userID) {
        User user = findUser(userID);
        return user.calcTotalPremiums(this.flatRate);
    }

    // double calcTotalPayments() {
    // double total = 0;
    // for (User user : this.users) {
    // total += user.calcTotalPremiums(this.flatRate);
    // }
    // return total;
    // }

    boolean carPriceRise(int userID, double risePercent) {
        User user = findUser(userID);
        user.carPriceRiseAll(risePercent);
        return true;
    }

    // void carPriceRise(double risePercent) {
    // for (User user : this.users) {
    // user.carPriceRiseAll(risePercent);
    // }
    // }

    // ArrayList<InsurancePolicy> allPolicies() {
    // ArrayList<InsurancePolicy> allCompanyPolicies = new ArrayList<>();
    // for (User user : this.users) {
    // for (InsurancePolicy policy : user.getPolicies()) {
    // allCompanyPolicies.add(policy);
    // }
    // }
    // return allCompanyPolicies;
    // }

    HashMap<Integer, InsurancePolicy> filterByCarModel(int userID, String carModel) {
        User user = findUser(userID);
        return user.filterByCarModel(carModel);
    }

    // ArrayList<InsurancePolicy> filterByExpiryDate(int userID, MyDate date) {
    // User user = findUser(userID);
    // ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<>();

    // for (InsurancePolicy policy : user.getPolicies()) {
    // if (policy.getExpiryDate().isExpired(date)) {
    // filteredPolicies.add(policy);
    // }
    // }
    // return filteredPolicies;
    // }

    // ArrayList<InsurancePolicy> filterByCarModel(String carModel) {
    // ArrayList<InsurancePolicy> filteredList = new ArrayList<>();

    // for (User user : this.users) {
    // filteredList.addAll(user.filterByCarModel(carModel));
    // }

    // return filteredList;
    // }

    // ArrayList<InsurancePolicy> filterByExpiryDate(MyDate date) {
    // ArrayList<InsurancePolicy> filteredList = new ArrayList<>();

    // for (User user : this.users) {
    // filteredList.addAll(user.filterByExpiryDate(date));
    // }

    // return filteredList;
    // }

    // ArrayList<String> populateDistinctCityNames() {
    // ArrayList<String> cities = new ArrayList<>();
    // for (User user : this.users) {
    // if (!cities.contains(user.getAddress().getCity().toLowerCase())) {
    // cities.add(user.getAddress().getCity().toLowerCase());
    // } else
    // continue;
    // }
    // return cities;
    // }

    // double getTotalPaymentForCity(String city) {
    // double total = 0;
    // for (User user : this.users) {
    // if (user.getAddress().getCity().toLowerCase().equalsIgnoreCase(city)) {
    // total += user.calcTotalPremiums(this.flatRate);
    // }
    // }
    // return total;
    // }

    ArrayList<Double> getTotalPaymentPerCity(ArrayList<String> cities) {
        ArrayList<Double> citiesTotalPayment = new ArrayList<>();
        for (String city : cities) {
            citiesTotalPayment.add(getTotalPaymentForCity(city));
        }
        return citiesTotalPayment;
    }

    void reportPaymentPerCity(ArrayList<String> cities, ArrayList<Double> payments) {
        System.out.printf("%-15s %s%n", "City Name", "Total Premium Payment");
        for (int i = 0; i < cities.size(); i++) {
            String cityName = cities.get(i);
            double payment = payments.get(i);
            System.out.printf("%-15s $%,.2f%n", cityName, payment);
        }
    }

    public boolean removePolicyFromUser(int userID, int policyID) {
        try {
            User user = findUser(userID);
            return user.removePolicy(policyID);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // public boolean removeUser(int userID) {
    // for (User user : this.users) {
    // if (user.getUserID() == userID) {
    // this.users.remove(user);
    // return true;
    // }
    // }
    // return false;
    // }

    public boolean changeAdminPassword(String oldPass, String newPass) {
        if (this.adminPassword.equals(oldPass)) {
            if (newPass != null && !newPass.trim().isEmpty()) {
                this.adminPassword = newPass;
                return true;
            }
        }
        return false;
    }

    // ArrayList<String> populateDistinctCarModels() {
    // ArrayList<String> allCarModels = new ArrayList<>();
    // for (User user : this.users) {
    // ArrayList<String> userModels = user.populateDistinctCarModels();
    // for (String model : userModels) {
    // boolean exists = false;
    // for (String existingModel : allCarModels) {
    // if (existingModel.equalsIgnoreCase(model)) {
    // exists = true;
    // break;
    // }
    // }
    // if (!exists) {
    // allCarModels.add(model);
    // }
    // }
    // }
    // return allCarModels;
    // }

    // ArrayList<Integer> getTotalCountPerCarModel(ArrayList<String> carModels) {
    // ArrayList<Integer> totalCounts = new ArrayList<>();
    // for (int i = 0; i < carModels.size(); i++) {
    // totalCounts.add(0);
    // }
    // for (User user : this.users) {
    // ArrayList<Integer> userCounts = user.getTotalCountPerCarModel(carModels);
    // for (int i = 0; i < userCounts.size(); i++) {
    // totalCounts.set(i, totalCounts.get(i) + userCounts.get(i));
    // }
    // }
    // return totalCounts;
    // }

    // public ArrayList<Double> getTotalPaymentPerCarModel(ArrayList<String>
    // carModels, int flatRate) {
    // ArrayList<Double> totalPayments = new ArrayList<>();

    // for (int i = 0; i < carModels.size(); i++) {
    // totalPayments.add(0.0);
    // }

    // for (User user : this.users) {
    // ArrayList<Double> userPayments = user.getTotalPaymentPerCarModel(carModels,
    // flatRate);
    // for (int i = 0; i < userPayments.size(); i++) {
    // totalPayments.set(i, totalPayments.get(i) + userPayments.get(i));
    // }
    // }
    // return totalPayments;
    // }

    public void reportPaymentsPerCarModel(ArrayList<String> carModels, ArrayList<Integer> counts,
            ArrayList<Double> premiumPayments) {
        System.out.println("\n--- Global Premium Report Per Car Model ---");
        System.out.printf("%-25s %-25s %-25s%n", "Car Model", "Total Premium Payment", "Average Premium Payment");

        for (int i = 0; i < carModels.size(); i++) {
            String model = carModels.get(i);
            int count = counts.get(i);
            double totalPayment = premiumPayments.get(i);
            double average = 0;
            if (count > 0) {
                average = totalPayment / count;
            }
            System.out.printf("%-25s $%,.2f \t\t     $%,.2f%n", model, totalPayment, average);
        }
    }

    // void print() {
    // for (User user : this.users) {
    // user.print();
    // user.printPolicies(this.flatRate);
    // }
    // }

    // @Override
    // public String toString() {
    // String result = "Insurance Company: " + this.name + "\n";
    // for (User user : this.users) {
    // result += "\nUser #" + user.getUserID() + "\n";
    // result += user.toString();
    // }
    // return result;
    // }

    // lab-4
    public InsuranceCompany(
            InsuranceCompany company) throws IllegalArgumentException, CloneNotSupportedException {
        if (company.name == null || company.name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Company name cannot be empty.");
        }
        if (company.adminUsername == null || company.adminUsername.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Admin username cannot be empty.");
        }
        if (company.adminPassword == null || company.adminPassword.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Admin password cannot be empty.");
        }
        if (company.flatRate < 0) {
            throw new IllegalArgumentException(
                    "Flat rate cannot be negative.");
        }
        this.name = company.name;
        this.users = new HashMap<Integer, User>();
        for (User user : company.getUsers().values()) {
            users.put(user.getUserID(), new User(user));
        }
        this.adminUsername = company.adminUsername;
        this.adminPassword = company.adminPassword;
        this.flatRate = company.flatRate;
    }

    // ArrayList<User> deepCopyUsers() throws CloneNotSupportedException {
    // ArrayList<User> deepCopyList = new ArrayList<>();
    // for (User user : this.users) {
    // deepCopyList.add(user.clone());
    // }
    // return deepCopyList;
    // }

    // @Override
    // public InsuranceCompany clone() throws CloneNotSupportedException {
    // InsuranceCompany clonedCompany = (InsuranceCompany) super.clone();
    // clonedCompany.users = deepCopyUsers();
    // return clonedCompany;
    // }

    // ArrayList<User> shallowCopyUsers() {
    // ArrayList<User> shallowCopyList = new ArrayList<>();
    // for (User user : this.users) {
    // shallowCopyList.add(user);
    // }
    // return shallowCopyList;
    // }

    // ArrayList<User> sortUsers() {
    // ArrayList<User> sortedUsers = new ArrayList<>(this.users);
    // Collections.sort(sortedUsers);
    // return sortedUsers;
    // }

    // lab-5
    public HashMap<Integer, User> getUsers() {
        return this.users;
    }

    boolean addUser(User user) throws NullPointerException {
        if (user == null) {
            throw new NullPointerException(
                    "User cannot be null.");
        }
        if (existsUser(user.getUserID())) {
            return false;
        }
        this.users.put(user.getUserID(), user);
        return true;
    }

    User findUser(int userID) {
        return this.users.get(userID);
        // throw new IllegalArgumentException(
        // "User with ID " + userID + " not found.");
    }

    public boolean existsUser(int userID) {
        if (this.users.get(userID) != null) {
            return true;
        }
        return false;
    }

    double calcTotalPayments() {
        double total = 0;
        for (User user : this.users.values()) {
            total += user.calcTotalPremiums(this.flatRate);
        }
        return total;
    }

    void carPriceRise(double risePercent) {
        for (User user : this.users.values()) {
            user.carPriceRiseAll(risePercent);
        }
    }

    ArrayList<InsurancePolicy> allPolicies() {
        ArrayList<InsurancePolicy> allCompanyPolicies = new ArrayList<>();
        for (User user : this.users.values()) {
            for (InsurancePolicy policy : user.getPolicies().values()) {
                allCompanyPolicies.add(policy);
            }
        }
        return allCompanyPolicies;
    }

    ArrayList<InsurancePolicy> filterByExpiryDate(int userID, MyDate date) {
        User user = findUser(userID);
        ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<>();

        for (InsurancePolicy policy : user.getPolicies().values()) {
            if (policy.getExpiryDate().isExpired(date)) {
                filteredPolicies.add(policy);
            }
        }
        return filteredPolicies;
    }

    HashMap<Integer, InsurancePolicy> filterByCarModel(String carModel) {
        HashMap<Integer, InsurancePolicy> filteredList = new HashMap<Integer, InsurancePolicy>();

        for (User user : this.users.values()) {
            filteredList.putAll(user.filterByCarModel(carModel));
        }

        return filteredList;
    }

    ArrayList<InsurancePolicy> filterByExpiryDate(MyDate date) {
        ArrayList<InsurancePolicy> filteredList = new ArrayList<>();

        for (User user : this.users.values()) {
            filteredList.addAll(user.filterByExpiryDate(date));
        }

        return filteredList;
    }

    ArrayList<String> populateDistinctCityNames() {
        ArrayList<String> cities = new ArrayList<>();
        for (User user : this.users.values()) {
            if (!cities.contains(user.getAddress().getCity().toLowerCase())) {
                cities.add(user.getAddress().getCity().toLowerCase());
            } else
                continue;
        }
        return cities;
    }

    double getTotalPaymentForCity(String city) {
        double total = 0;
        for (User user : this.users.values()) {
            if (user.getAddress().getCity().toLowerCase().equalsIgnoreCase(city)) {
                total += user.calcTotalPremiums(this.flatRate);
            }
        }
        return total;
    }

    public boolean removeUser(int userID) {
        return this.users.remove(userID) != null;
    }

    ArrayList<String> populateDistinctCarModels() {
        ArrayList<String> allCarModels = new ArrayList<>();
        for (User user : this.users.values()) {
            ArrayList<String> userModels = user.populateDistinctCarModels();
            for (String model : userModels) {
                boolean exists = false;
                for (String existingModel : allCarModels) {
                    if (existingModel.equalsIgnoreCase(model)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    allCarModels.add(model);
                }
            }
        }
        return allCarModels;
    }

    ArrayList<Integer> getTotalCountPerCarModel(ArrayList<String> carModels) {
        ArrayList<Integer> totalCounts = new ArrayList<>();
        for (int i = 0; i < carModels.size(); i++) {
            totalCounts.add(0);
        }
        for (User user : this.users.values()) {
            ArrayList<Integer> userCounts = user.getTotalCountPerCarModel(carModels);
            for (int i = 0; i < userCounts.size(); i++) {
                totalCounts.set(i, totalCounts.get(i) + userCounts.get(i));
            }
        }
        return totalCounts;
    }

    public ArrayList<Double> getTotalPaymentPerCarModel(ArrayList<String> carModels, int flatRate) {
        ArrayList<Double> totalPayments = new ArrayList<>();

        for (int i = 0; i < carModels.size(); i++) {
            totalPayments.add(0.0);
        }

        for (User user : this.users.values()) {
            ArrayList<Double> userPayments = user.getTotalPaymentPerCarModel(carModels, flatRate);
            for (int i = 0; i < userPayments.size(); i++) {
                totalPayments.set(i, totalPayments.get(i) + userPayments.get(i));
            }
        }
        return totalPayments;
    }

    void print() {
        for (User user : this.users.values()) {
            user.print();
            user.printPolicies(this.flatRate);
        }
    }

    @Override
    public String toString() {
        String result = "Insurance Company: " + this.name + "\n";
        for (User user : this.users.values()) {
            result += "\nUser #" + user.getUserID() + "\n";
            result += user.toString();
        }
        return result;
    }

    ArrayList<User> deepCopyUsers() throws CloneNotSupportedException {
        HashMap<Integer, User> deepCopyList = new HashMap<Integer, User>();
        for (User user : this.users.values()) {
            deepCopyList.put(user.getUserID(), user.clone());
        }
        return new ArrayList<User>(deepCopyList.values());
    }

    ArrayList<User> shallowCopyUsers() {
        HashMap<Integer, User> shallowCopyList = new HashMap<Integer, User>();
        for (User user : this.users.values()) {
            shallowCopyList.put(user.getUserID(), user);
        }
        return new ArrayList<User>(shallowCopyList.values());
    }

    // with HashMap input and output
    HashMap<Integer, User> deepCopyUsersHashMap() throws CloneNotSupportedException {
        HashMap<Integer, User> deepCopyList = new HashMap<Integer, User>();
        for (User user : this.users.values()) {
            deepCopyList.put(user.getUserID(), user.clone());
        }
        return deepCopyList;
    }

    HashMap<Integer, User> shallowCopyUsersHashMap() {
        HashMap<Integer, User> shallowCopyList = new HashMap<Integer, User>();
        for (User user : this.users.values()) {
            shallowCopyList.put(user.getUserID(), user);
        }
        return shallowCopyList;
    }

    @Override
    public InsuranceCompany clone() throws CloneNotSupportedException {
        InsuranceCompany clonedCompany = (InsuranceCompany) super.clone();
        clonedCompany.users = deepCopyUsersHashMap();
        return clonedCompany;
    }

    ArrayList<User> sortUsers() {
        ArrayList<User> sortedUsers = new ArrayList<>(shallowCopyUsersHashMap().values());
        Collections.sort(sortedUsers);
        return sortedUsers;
    }

    HashMap<String, Double> getTotalPremiumPerCity() {
        HashMap<String, Double> citiesAndPremium = new HashMap<String, Double>();
        for (User user : this.users.values()) {
            String city = user.getAddress().getCity();
            Double payment = user.calcTotalPremiums(this.flatRate);

            if (citiesAndPremium.get(city) != null) {
                citiesAndPremium.put(city, citiesAndPremium.get(city) + payment);
            } else {
                citiesAndPremium.put(city, payment);
            }
        }
        return citiesAndPremium;
    }

    HashMap<String, Integer> getTotalCountPerCarModel() {
        HashMap<String, Integer> countsPerCarModel = new HashMap<String, Integer>();
        for (User user : this.users.values()) {
            HashMap<String, Integer> userCounts = user.getTotalCountPerCarModel();
            for (String carModel : userCounts.keySet()) {
                int count = userCounts.get(carModel);
                if (countsPerCarModel.get(carModel) != null) {
                    countsPerCarModel.put(carModel, countsPerCarModel.get(carModel) + count);
                } else {
                    countsPerCarModel.put(carModel, count);
                }
            }
        }
        return countsPerCarModel;
    }

    HashMap<String, Double> getTotalPremiumPerCarModel() {
        HashMap<String, Double> paymentsAndCarModel = new HashMap<String, Double>();
        for (User user : this.users.values()) {
            HashMap<String, Double> userPremiums = user.getTotalPremiumPerCarModel(this.flatRate);
            for (String carModel : userPremiums.keySet()) {
                double premium = userPremiums.get(carModel);
                if (paymentsAndCarModel.get(carModel) != null) {
                    paymentsAndCarModel.put(carModel, paymentsAndCarModel.get(carModel) + premium);
                } else {
                    paymentsAndCarModel.put(carModel, premium);
                }
            }
        }
        return paymentsAndCarModel;
    }

    void reportCity() {
        System.out.printf("%-25s %-25s%n", "City", "Total Premium Payment");
        HashMap<String, Double> data = getTotalPremiumPerCity();

        for (String city : data.keySet()) {
            double totalPayment = data.get(city);
            System.out.printf("%-25s $%,.2f%n", city, totalPayment);
        }
    }

    void reportCarModels() {
        System.out.printf("%-25s %-25s %-25s%n", "Car Model", "Total Premium Payment", "Average Premium Payment");
        HashMap<String, Double> data = getTotalPremiumPerCarModel();
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

    public InsuranceCompany() {
        this.name = "";
        this.users = new HashMap<>();
        this.adminUsername = "";
        this.adminPassword = "";
        this.flatRate = 0;
    }

    // lab-6
    Boolean load(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream inputFile = null;
        try {
            inputFile = new ObjectInputStream(
                    Files.newInputStream(Paths.get(fileName)));
            InsuranceCompany loadedCompany = (InsuranceCompany) inputFile.readObject();
            this.name = loadedCompany.name;
            this.users = loadedCompany.users;
            this.adminUsername = loadedCompany.adminUsername;
            this.adminPassword = loadedCompany.adminPassword;
            this.flatRate = loadedCompany.flatRate;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        } finally {
            if (inputFile != null) {
                inputFile.close();
            }
        }
        return true;
    }

    Boolean save(String fileName) throws IOException {
        ObjectOutputStream outputFile = null;
        try {
            outputFile = new ObjectOutputStream(
                    Files.newOutputStream(Paths.get(fileName)));
            outputFile.writeObject(this);
        } catch (IOException e) {
            return false;
        } finally {
            if (outputFile != null) {
                outputFile.close();
            }
        }
        return true;
    }

    // test for development
    void clear() {
        this.getUsers().clear();
    }

    public String toDilimatedString() {
        return "company," + this.name + "," + this.adminUsername + ","
                + this.adminPassword + "," + this.flatRate;
    }

    Boolean saveTextFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));) {
            writer.write(this.toDilimatedString());
            writer.write("\n");
            for (User user : this.users.values()) {
                writer.write(user.toDilimatedString());
                writer.write("\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    Boolean loadTextFile(String fileName)
            throws IOException, PolicyException, ArrayIndexOutOfBoundsException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            this.users = new HashMap<>();
            String currentLine = reader.readLine();
            if (currentLine != null) {
                currentLine = currentLine.trim();
                if (!currentLine.isEmpty()) {
                    String[] companyFields = currentLine.split(",", -1);
                    this.name = companyFields[1];
                    this.adminUsername = companyFields[2];
                    this.adminPassword = companyFields[3];
                    this.flatRate = Integer.parseInt(companyFields[4]);
                }
            }

            while ((currentLine = reader.readLine()) != null) {
                currentLine = currentLine.trim();
                if (currentLine.isEmpty())
                    continue;

                String[] fields = currentLine.split(",", -1);
                int userID = Integer.parseInt(fields[1]);
                String userName = fields[2];
                int streetNum = Integer.parseInt(fields[4]);
                String street = fields[5];
                String suburb = fields[6];
                String city = fields[7];
                Address address = new Address(streetNum, street, suburb, city);
                User user = new User(userName, address);

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
                    InsurancePolicy policy;
                    int policyLength;
                    if (policyType.equals("Third")) {
                        String comments = fields[i + 14];
                        policy = new ThirdPartyPolicy(policyHolderName, policyID, car,
                                numberOfClaims, comments, date);
                        policyLength = 15;
                    } else {
                        int driverAge = Integer.parseInt(fields[i + 14]);
                        int level = Integer.parseInt(fields[i + 15]);
                        policy = new ComprehensivePolicy(policyHolderName, policyID, car,
                                numberOfClaims, driverAge, level, date);
                        policyLength = 16;
                    }
                    user.addPolicy(policy);
                    i += policyLength;
                }
                this.users.put(userID, user);
            }
            reader.close();
            return true;
        } catch (IOException | PolicyException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}
