import java.util.ArrayList;
import java.util.Collections;

public class InsuranceCompany implements Cloneable {
    String name;
    private ArrayList<User> users;
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
        this.users = new ArrayList<>();
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.flatRate = flatRate;
    }

    // get methods
    public String getName() {
        return this.name;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

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

    boolean addUser(User user) {
        if (user == null) {
            throw new NullPointerException(
                    "User cannot be null.");
        }
        if (existsUser(user.getUserID())) {
            return false;
        }
        this.users.add(user);
        return true;
    }

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

    User findUser(int userID) {
        for (User user : this.users) {
            if (user.getUserID() == userID) {
                return user;
            }
        }
        throw new IllegalArgumentException(
                "User with ID " + userID + " not found.");
    }

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

    boolean createThirdPartyPolicy(int userID, String policyHolderName, Car car, int numberOfClaims,
            String comments, MyDate expiryDate) {
        User user = findUser(userID);
        return user.createThirdPartyPolicy(policyHolderName, car, numberOfClaims, comments, expiryDate);
    }

    boolean createComprehensivePolicy(int userID, String policyHolderName, Car car, int numberOfClaims,
            int driverAge, int level, MyDate expiryDate) {
        User user = findUser(userID);
        return user.createComprehensivePolicy(policyHolderName, car, numberOfClaims, driverAge,
                level, expiryDate);
    }

    public boolean existsUser(int userID) {
        for (User user : this.users) {
            if (user.getUserID() == userID) {
                return true;
            }
        }
        return false;
    }

    double calcTotalPayments(int userID) {
        User user = findUser(userID);
        return user.calcTotalPremiums(this.flatRate);
    }

    double calcTotalPayments() {
        double total = 0;
        for (User user : this.users) {
            total += user.calcTotalPremiums(this.flatRate);
        }
        return total;
    }

    boolean carPriceRise(int userID, double risePercent) {
        User user = findUser(userID);
        user.carPriceRiseAll(risePercent);
        return true;
    }

    void carPriceRise(double risePercent) {
        for (User user : this.users) {
            user.carPriceRiseAll(risePercent);
        }
    }

    ArrayList<InsurancePolicy> allPolicies() {
        ArrayList<InsurancePolicy> allCompanyPolicies = new ArrayList<>();
        for (User user : this.users) {
            for (InsurancePolicy policy : user.getPolicies()) {
                allCompanyPolicies.add(policy);
            }
        }
        return allCompanyPolicies;
    }

    ArrayList<InsurancePolicy> filterByCarModel(int userID, String carModel) {
        User user = findUser(userID);
        return user.filterByCarModel(carModel);
    }

    ArrayList<InsurancePolicy> filterByExpiryDate(int userID, MyDate date) {
        User user = findUser(userID);
        ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<>();

        for (InsurancePolicy policy : user.getPolicies()) {
            if (policy.getExpiryDate().isExpired(date)) {
                filteredPolicies.add(policy);
            }
        }
        return filteredPolicies;
    }

    ArrayList<InsurancePolicy> filterByCarModel(String carModel) {
        ArrayList<InsurancePolicy> filteredList = new ArrayList<>();

        for (User user : this.users) {
            filteredList.addAll(user.filterByCarModel(carModel));
        }

        return filteredList;
    }

    ArrayList<InsurancePolicy> filterByExpiryDate(MyDate date) {
        ArrayList<InsurancePolicy> filteredList = new ArrayList<>();

        for (User user : this.users) {
            filteredList.addAll(user.filterByExpiryDate(date));
        }

        return filteredList;
    }

    ArrayList<String> populateDistinctCityNames() {
        ArrayList<String> cities = new ArrayList<>();
        for (User user : this.users) {
            if (!cities.contains(user.getAddress().getCity().toLowerCase())) {
                cities.add(user.getAddress().getCity().toLowerCase());
            } else
                continue;
        }
        return cities;
    }

    double getTotalPaymentForCity(String city) {
        double total = 0;
        for (User user : this.users) {
            if (user.getAddress().getCity().toLowerCase().equalsIgnoreCase(city)) {
                total += user.calcTotalPremiums(this.flatRate);
            }
        }
        return total;
    }

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

    public boolean removeUser(int userID) {
        for (User user : this.users) {
            if (user.getUserID() == userID) {
                this.users.remove(user);
                return true;
            }
        }
        return false;
    }

    public boolean changeAdminPassword(String oldPass, String newPass) {
        if (this.adminPassword.equals(oldPass)) {
            if (newPass != null && !newPass.trim().isEmpty()) {
                this.adminPassword = newPass;
                return true;
            }
        }
        return false;
    }

    ArrayList<String> populateDistinctCarModels() {
        ArrayList<String> allCarModels = new ArrayList<>();
        for (User user : this.users) {
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
        for (User user : this.users) {
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

        for (User user : this.users) {
            ArrayList<Double> userPayments = user.getTotalPaymentPerCarModel(carModels, flatRate);
            for (int i = 0; i < userPayments.size(); i++) {
                totalPayments.set(i, totalPayments.get(i) + userPayments.get(i));
            }
        }
        return totalPayments;
    }

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

    void print() {
        for (User user : this.users) {
            user.print();
            user.printPolicies(this.flatRate);
        }
    }

    @Override
    public String toString() {
        String result = "Insurance Company: " + this.name + "\n";
        for (User user : this.users) {
            result += "\nUser #" + user.getUserID() + "\n";
            result += user.toString();
        }
        return result;
    }

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
        this.users = new ArrayList<>();
        for (User user : company.getUsers()) {
            users.add(user.clone());
        }
        this.adminUsername = company.adminUsername;
        this.adminPassword = company.adminPassword;
        this.flatRate = company.flatRate;
    }

    @Override
    public InsuranceCompany clone() throws CloneNotSupportedException {
        return (InsuranceCompany) super.clone();
    }

    ArrayList<User> deepCopyUsers() throws CloneNotSupportedException {
        ArrayList<User> deepCopyList = new ArrayList<>();
        for (User user : this.users) {
            deepCopyList.add(user.clone());
        }
        return deepCopyList;
    }

    ArrayList<User> shallowCopyUsers() {
        ArrayList<User> shallowCopyList = new ArrayList<>();
        for (User user : this.users) {
            shallowCopyList.add(user);
        }
        return shallowCopyList;
    }

    ArrayList<User> sortUsers() {
        ArrayList<User> sortedUsers = new ArrayList<>(this.users);
        Collections.sort(sortedUsers);
        return sortedUsers;
    }
}
