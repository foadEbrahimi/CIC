import java.util.ArrayList;

public class InsuranceCompany {
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
            throw new IllegalArgumentException(
                    "User cannot be null.");
        }
        if (existsUser(user.getUserID())) {
            return false;
        }
        this.users.add(user);
        return true;
    }

    boolean addUser(String name, int userID, Address address) {
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
        return addUser(new User(name, userID, address));
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

    boolean createThirdPartyPolicy(int userID, String policyHolderName, int id, Car car, int numberOfClaims,
            String comments, MyDate expiryDate) {
        User user = findUser(userID);
        return user.createThirdPartyPolicy(policyHolderName, id, car, numberOfClaims, comments, expiryDate);
    }

    boolean createComprehensivePolicy(int userID, String policyHolderName, int id, Car car, int numberOfClaims,
            int driverAge, int level, MyDate expiryDate) {
        User user = findUser(userID);
        return user.createComprehensivePolicy(policyHolderName, id, car, numberOfClaims, driverAge,
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

}
