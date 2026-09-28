import java.util.ArrayList;
import java.util.HashMap;

public class TestCode {
        public static void runTests() throws CloneNotSupportedException, IllegalArgumentException, PolicyException {
                System.out.println("\n========================================");
                System.out.println("   TEST SYSTEM (CORE + STANDARD + ADVANCE)  ");
                System.out.println("========================================\n");

                System.out.println("--- [ADDRESS] ---");
                Address addr = new Address(12, "Valiasr", "District 6", "Tehran");
                System.out.println("TC-01 - setStreetNum(55): " + addr.setStreetNum(55));
                System.out.println("TC-01 - setStreetNum(-1): " + addr.setStreetNum(-1));
                System.out.println("TC-02 - setStreet('Chamran'): " + addr.setStreet("Chamran"));
                System.out.println("TC-02 - setStreet(''): " + addr.setStreet(""));
                System.out.println("TC-02 - setSuburb('D2'): " + addr.setSuburb("D2"));
                System.out.println("TC-02 - setSuburb(null): " + addr.setSuburb(null));
                System.out.println("TC-02 - setCity('Shiraz'): " + addr.setCity("Shiraz"));
                System.out.println("TC-02 - setCity(''): " + addr.setCity(""));
                System.out.println("TC-03 - print():");
                addr.print();
                System.out.println("TC-03 - toString(): " + addr.toString());

                System.out.println("\n--- [MYDATE] ---");
                MyDate date1 = new MyDate(2026, 6, 15);
                MyDate date2 = new MyDate(2026, 12, 30);
                System.out.println("TC-04 - setYear(2027): " + date1.setYear(2027));
                System.out.println("TC-04 - setYear(0): " + date1.setYear(0));
                System.out.println("TC-04 - setMonth(3): " + date1.setMonth(3));
                System.out.println("TC-04 - setMonth(13): " + date1.setMonth(13));
                System.out.println("TC-04 - setDay(10): " + date1.setDay(10));
                System.out.println("TC-04 - setDay(32): " + date1.setDay(32));

                try {
                        new MyDate(0, 1, 1);
                        System.out.println("TC-04 - MyDate(0,1,1): No Exception");
                } catch (IllegalArgumentException e) {
                        System.err.println("TC-04 - MyDate(0,1,1): Exception thrown");
                }
                try {
                        new MyDate(2026, 13, 1);
                        System.out.println("TC-04 - MyDate(2026,13,1): No Exception");
                } catch (IllegalArgumentException e) {
                        System.err.println("TC-04 - MyDate(2026,13,1): Exception thrown");
                }
                try {
                        new MyDate(2026, 1, 32);
                        System.out.println("TC-04 - MyDate(2026,1,32): No Exception");
                } catch (IllegalArgumentException e) {
                        System.err.println("TC-04 - MyDate(2026,1,32): Exception thrown");
                }

                System.out.println("TC-05 - date1.isExpired(date2): " + date1.isExpired(date2));
                System.out.println("TC-05 - date2.isExpired(date1): " + date2.isExpired(date1));

                System.out.println("\n--- [CAR] ---");
                Car car = new Car("Mercedes-Benz CLS 500", CarType.LUX, 2022, 120000);
                System.out.println("TC-06 - setModel('BMW'): " + car.setModel("BMW"));
                System.out.println("TC-06 - setModel(''): " + car.setModel(""));
                System.out.println("TC-06 - setType(SUV): " + car.setType(CarType.SUV));
                System.out.println("TC-06 - setType(null): " + car.setType(null));
                System.out.println("TC-06 - setManufacturingYear(2023): " + car.setManuFacturingYear(2023));
                System.out.println("TC-06 - setManufacturingYear(0): " + car.setManuFacturingYear(0));
                System.out.println("TC-06 - setPrice(130000): " + car.setPrice(130000));
                System.out.println("TC-06 - setPrice(-1): " + car.setPrice(-1));
                car.priceRise(0.10);
                System.out.println("TC-07 - After priceRise(0.10) price: " + car.getPrice());
                System.out.println("TC-08 - toString(): " + car.toString());

                System.out.println("\n--- [COMPANY BASE] ---");
                InsuranceCompany company = new InsuranceCompany("Pars Insurance", "admin", "1234", 100);
                System.out.println("TC-13 - setName('New'): " + company.setName("New"));
                System.out.println("TC-13 - setName(''): " + company.setName(""));
                System.out.println("TC-13 - setAdminUsername('a'): " + company.setAdminUsername("a"));
                System.out.println("TC-13 - setAdminUsername(''): " + company.setAdminUsername(""));
                System.out.println("TC-13 - setAdminPassword('p'): " + company.setAdminPassword("p"));
                System.out.println("TC-13 - setAdminPassword(''): " + company.setAdminPassword(""));
                System.out.println("TC-13 - setFlatRate(200): " + company.setFlatRate(200));
                System.out.println("TC-13 - setFlatRate(-1): " + company.setFlatRate(-1));
                company.setName("Pars Insurance");
                company.setAdminUsername("admin");
                company.setAdminPassword("1234");
                company.setFlatRate(100);
                System.out.println("TC-14 - validateAdmin(valid): " + company.validateAdmin("admin", "1234"));
                System.out.println("TC-14 - validateAdmin(invalid): " + company.validateAdmin("x", "y"));

                User u1 = new User("Foad Ebrahimi", new Address(12, "Valiasr", "District 6", "Tehran"));
                System.out.println("TC-15 - addUser(u1): " + company.addUser(u1));
                System.out.println("TC-15 - addUser(u1 duplicate): " + company.addUser(u1));
                System.out.println("TC-16 - addUser(name,id,addr): "
                                + company.addUser("Reza", new Address(8, "Chamran", "District 2", "Shiraz")));
                System.out.println("TC-17 - existsUser(u1.getUserID()): " + company.existsUser(u1.getUserID()));
                System.out.println("TC-17 - existsUser(99): " + company.existsUser(99));

                try {
                        company.findUser(u1.getUserID());
                        System.out.println("TC-17 - findUser(u1): Found");
                } catch (IllegalArgumentException e) {
                        System.err.println("TC-17 - findUser(u1): " + e.getMessage());
                }
                try {
                        company.findUser(99);
                        System.out.println("TC-17 - findUser(99): Found");
                } catch (IllegalArgumentException e) {
                        System.err.println("TC-17 - findUser(99): " + e.getMessage());
                }

                System.out.println("\n--- [THIRDPARTY POLICY] ---");
                ThirdPartyPolicy tp = new ThirdPartyPolicy("Foad Ebrahimi", 300001, car, 1, "Premium",
                                new MyDate(2026, 1, 20));
                System.out.println("TC-18 - calcPayment(flat=100): " + tp.calcPayment(100));
                System.out.println("TC-19 - setNumberOfClaims(2): " + tp.setNumberOfClaims(2));
                System.out.println("TC-19 - setNumberOfClaims(-1): " + tp.setNumberOfClaims(-1));
                System.out.println("TC-19 - setPolicyHolderName('X'): " + tp.setPolicyHolderName("X"));
                System.out.println("TC-19 - setPolicyHolderName(''): " + tp.setPolicyHolderName(""));
                System.out.println("TC-19 - setCar(newCar): " + tp.setCar(new Car("Toyota", CarType.SED, 2020, 50000)));
                System.out.println("TC-19 - setCar(null): " + tp.setCar(null));
                System.out.println("TC-19 - setExpiryDate(new): " + tp.setExpiryDate(new MyDate(2027, 1, 1)));
                System.out.println("TC-19 - setExpiryDate(null): " + tp.setExpiryDate(null));
                System.out.println("TC-19 - setCarModel('Honda'): " + tp.setCarModel("Honda"));
                tp.carPriceRise(0.01);
                System.out.println("TC-19 - carPriceRise(0.01) done.");

                System.out.println("\n--- [COMPREHENSIVE POLICY] ---");
                ComprehensivePolicy cp = new ComprehensivePolicy("Ali Hosseini", 300002, car, 0, 25, 5,
                                new MyDate(2026, 10, 10));
                System.out.println("TC-20 - calcPayment(age=25, flat=100): " + cp.calcPayment(100));
                System.out.println("TC-21 - getDriverAge(): " + cp.getDriverAge());
                System.out.println("TC-21 - print():");
                cp.print();

                System.out.println("\n--- [USER LOGIC] ---");
                System.out.println("TC-22 - addPolicy(tp): " + u1.addPolicy(tp));
                System.out.println("TC-22 - addPolicy(tp dup): " + u1.addPolicy(tp));
                System.out.println("TC-23 - findPolicy(tp.getId())!=null: " + (u1.findPolicy(tp.getId()) != null));
                System.out.println("TC-23 - findPolicy(999)==null: " + (u1.findPolicy(999) == null));
                System.out.println("TC-24 - createThirdPartyPolicy: "
                                + u1.createThirdPartyPolicy("Foad", 300003, car, 0, "Std", new MyDate(2027, 1, 1)));
                System.out.println("TC-25 - createComprehensivePolicy: "
                                + u1.createComprehensivePolicy("Foad", 300004, car, 0, 35, 3, new MyDate(2027, 6, 1)));
                System.out.println("TC-26 - calcTotalPremiums: " + u1.calcTotalPremiums(100));
                System.out.println("TC-27 - filterByCarModel('Honda').size(): " + u1.filterByCarModel("Honda").size());
                System.out.println("TC-27 - filterByExpiryDate(2027/1/1).size(): "
                                + u1.filterByExpiryDate(new MyDate(2027, 1, 1)).size());
                u1.carPriceRiseAll(0.05);
                System.out.println("TC-28 - After carPriceRiseAll(0.05), car price: " + car.getPrice());
                System.out.println("TC-29 - setName('NewName'): " + u1.setName("NewName"));
                System.out.println("TC-29 - setName(''): " + u1.setName(""));
                System.out.println("TC-29 - setAddress(valid): " + u1.setAddress(new Address(1, "A", "B", "C")));
                System.out.println("TC-29 - setAddress(null): " + u1.setAddress(null));
                System.out.println("TC-29 - setCity('Isfahan'): " + u1.setCity("Isfahan"));
                System.out.println("TC-30 - print():");
                u1.print();
                System.out.println("TC-30 - printPolicies(100):");
                u1.printPolicies(100);

                System.out.println("\n--- [INSURANCEPOLICY STATIC] ---");
                ArrayList<InsurancePolicy> allPolicies = new ArrayList<>();
                allPolicies.add(tp);
                allPolicies.add(cp);
                System.out.println("TC-31 - printPolicies():");
                InsurancePolicy.printPolicies(allPolicies);
                System.out.println("TC-32 - calcTotalPayments(list,100): "
                                + InsurancePolicy.calcTotalPayments(allPolicies, 100));
                System.out.println("TC-33 - existsPolicy(list,tp.getId()): "
                                + InsurancePolicy.existsPolicy(allPolicies, tp.getId()));
                System.out.println("TC-33 - existsPolicy(list,999): " + InsurancePolicy.existsPolicy(allPolicies, 999));
                System.out.println("TC-34 - filterByCarModel(list,'Honda').size(): "
                                + InsurancePolicy.filterByCarModel(allPolicies, "Honda").size());
                System.out.println("TC-35 - filterByExpiryDate(list,2027/1/1).size(): "
                                + InsurancePolicy.filterByExpiryDate(allPolicies, new MyDate(2027, 1, 1)).size());
                InsurancePolicy.carPriceRiseAll(allPolicies, 0.02);
                System.out.println("TC-36 - After static carPriceRiseAll(0.02), car price: " + car.getPrice());

                System.out.println("\n--- [COMPANY WRAPPER] ---");
                System.out.println("TC-37 - addPolicy(u1,tp): " + company.addPolicy(u1.getUserID(), tp));
                System.out.println("TC-38 - calcTotalPayments(u1): " + company.calcTotalPayments(u1.getUserID()));
                System.out.println("TC-38 - calcTotalPayments(all): " + company.calcTotalPayments());
                company.carPriceRise(u1.getUserID(), 0.03);
                System.out.println("TC-39 - carPriceRise(u1,0.03) done.");
                company.carPriceRise(0.01);
                System.out.println("TC-39 - carPriceRise(all,0.01) done.");
                System.out.println("TC-40 - allPolicies().size(): " + company.allPolicies().size());
                System.out.println("TC-41 - filterByCarModel(u1,'Honda').size(): "
                                + company.filterByCarModel(u1.getUserID(), "Honda").size());
                System.out.println("TC-41 - filterByCarModel(all,'Honda').size(): "
                                + company.filterByCarModel("Honda").size());
                System.out.println("TC-42 - filterByExpiryDate(u1,2027/1/1).size(): "
                                + company.filterByExpiryDate(u1.getUserID(), new MyDate(2027, 1, 1)).size());
                System.out.println("TC-42 - filterByExpiryDate(all,2027/1/1).size(): "
                                + company.filterByExpiryDate(new MyDate(2027, 1, 1)).size());
                System.out.println("TC-43 - print():");
                company.print();
                System.out.println("TC-43 - toString() length: " + company.toString().length());

                System.out.println("\n--- [CITY REPORTS] ---");
                ArrayList<String> distinctCities = company.populateDistinctCityNames();
                System.out.println("TC-44 - Distinct Cities: " + distinctCities);
                ArrayList<Double> cityPayments = new ArrayList<>();
                for (String city : distinctCities)
                        cityPayments.add(company.getTotalPaymentForCity(city));
                System.out.println("TC-45 - Report Output:");
                company.reportPaymentPerCity(distinctCities, cityPayments);

                System.out.println("\n--- [CAR MODEL REPORTS] ---");
                User u2 = new User("New User Auto", new Address(10, "St", "Sub", "City"));
                System.out.println("TC-46 - Created User with ID " + u2.getUserID() + ": " + u2.getUserID());
                ArrayList<String> userModels = u1.populateDistinctCarModels();
                System.out.println("TC-47 - User Distinct Car Models: " + userModels);
                double countHonda = u1.getTotalCountForCarModel("Honda");
                System.out.println("TC-48 - Count for 'Honda' (User): " + countHonda);
                double paymentHonda = u1.getTotalPaymentForCarModel("Honda", company.getFlatRate());
                System.out.println("TC-49 - Total Payment for 'Honda' (User): " + paymentHonda);
                ArrayList<Integer> userCounts = u1.getTotalCountPerCarModel(userModels);
                System.out.println("TC-50 - Counts per model (User): " + userCounts);
                ArrayList<Double> userPayments = u1.getTotalPaymentPerCarModel(userModels, company.getFlatRate());
                System.out.println("TC-51 - Payments per model (User): " + userPayments);
                System.out.println("TC-52 - User Report Per Car Model:");
                u1.reportPaymentsPerCarModel(userModels, userCounts, userPayments);
                int policyIdToRemove = tp.getId();
                System.out.println(
                                "TC-53 - Remove Policy " + policyIdToRemove + ": " + u1.removePolicy(policyIdToRemove));
                System.out.println("TC-53 - Verify Removal (findPolicy): " + (u1.findPolicy(policyIdToRemove) == null));
                ArrayList<String> companyModels = company.populateDistinctCarModels();
                System.out.println("TC-54 - Company Distinct Car Models: " + companyModels);
                ArrayList<Integer> companyCounts = company.getTotalCountPerCarModel(companyModels);
                System.out.println("TC-55 - Counts per model (Company): " + companyCounts);
                ArrayList<Double> companyPayments = company.getTotalPaymentPerCarModel(companyModels,
                                company.getFlatRate());
                System.out.println("TC-56 - Payments per model (Company): " + companyPayments);
                System.out.println("TC-57 - Company Report Per Car Model:");
                company.reportPaymentsPerCarModel(companyModels, companyCounts, companyPayments);
                String oldPass = "1234";
                String newPass = "newPass123";
                System.out.println("TC-58 - Change Password (Wrong Old): "
                                + company.changeAdminPassword("wrong", newPass));
                System.out.println("TC-58 - Change Password (Correct Old): "
                                + company.changeAdminPassword(oldPass, newPass));
                System.out.println("TC-58 - Validate New Password: " + company.validateAdmin("admin", newPass));
                company.changeAdminPassword(newPass, oldPass);
                ThirdPartyPolicy tpTemp = new ThirdPartyPolicy("Temp", 300005,
                                new Car("TempCar", CarType.SED, 2020, 10000), 0,
                                "Temp", new MyDate(2027, 1, 1));
                u1.addPolicy(tpTemp);
                System.out.println("TC-59 - Remove Policy via Company (999): "
                                + company.removePolicyFromUser(u1.getUserID(), 999));
                company.addUser(u2);
                System.out.println("TC-60 - Remove User (ID " + u2.getUserID() + "): "
                                + company.removeUser(u2.getUserID()));
                System.out.println("TC-60 - Verify Removal (existsUser): " + company.existsUser(u2.getUserID()));

                System.out.println("\n========================================");
                System.out.println("   FINISH TESTING     ");
                System.out.println("========================================\n");

                // lab-4
                System.out.println("----LAB-4 TESTING----");

                System.out.println("---User Policies Copy---");
                ArrayList<InsurancePolicy> user1DeepCopyPolicies = u1.deepCopyPolicies();
                ArrayList<InsurancePolicy> user1ShallowCopyPolicies = u1.shallowCopyPolicies();

                u1.setCity("New York");
                u1.createThirdPartyPolicy("Foad 2", 300006, car, 12, "Good", new MyDate(2027, 6, 15));

                ArrayList<InsurancePolicy> sortedPolicies = u1.sortPoliciesByDate();

                System.out.println("---original user Sorted policies---");
                InsurancePolicy.printPolicies(sortedPolicies);

                System.out.println("---shallow copy policies---");
                InsurancePolicy.printPolicies(user1ShallowCopyPolicies);

                System.out.println("---deep copy policies---");
                InsurancePolicy.printPolicies(user1DeepCopyPolicies);

                System.out.println("---company users copy---");
                ArrayList<User> companyDeepCopyUsers = company.deepCopyUsers();
                ArrayList<User> companyShallowCopyUsers = company.shallowCopyUsers();

                User newUser = new User("Alireza", new Address(900, "Azadi", "test",
                                "Abadan"));
                company.addUser(newUser);

                ArrayList<User> sortedUsers = company.sortUsers();
                companyShallowCopyUsers.get(0).setName("Test Change shallow list");
                System.out.println("---original company users---");
                for (User user : sortedUsers) {
                        System.out.println("-Id: " + user.getUserID() + ", Name: " + user.getName() + " City: "
                                        + user.getAddress().getCity());
                }
                System.out.println("---shallow copy users---");
                for (User user : companyShallowCopyUsers) {
                        System.out.println("-Id: " + user.getUserID() + ", Name: " + user.getName() + " City: "
                                        + user.getAddress().getCity());
                }
                System.out.println("---deep copy users---");
                for (User user : companyDeepCopyUsers) {
                        System.out.println("-Id: " + user.getUserID() + ", Name: " + user.getName() + " City: "
                                        + user.getAddress().getCity());
                }
                try {
                        InsuranceCompany clonedCompany = company.clone();
                        System.out.println("original company name: " + company.getName());
                        System.out.println("cloned company name: " + clonedCompany.getName());

                        clonedCompany.setName("cloned Pars Insurance");
                        System.out.println("after changing cloned company name to 'Cloned Pars Insurance':");

                        System.out.println("original company name: " + company.getName());
                        System.out.println("cloned company name: " + clonedCompany.getName());
                } catch (CloneNotSupportedException e) {
                        System.err.println("Error while cloning company => " + e.getMessage());
                }

                // lab-5
                System.out.println("----LAB-5 TESTING----");
                System.out.println("---user 1 report By Car Model---");
                u1.reportCarModels(company.getFlatRate());

                if (!company.getUsers().isEmpty()) {
                        User userForReport = company.getUsers().values().iterator().next();
                        System.out.println("---User Report: Car Models---");
                        userForReport.reportCarModels(company.getFlatRate());
                }

                System.out.println("---Company Report: Cities---");
                company.reportCity();

                System.out.println("---Company Report: Car Models---");
                company.reportCarModels();

                System.out.println("---Premium Per City Report");
                HashMap<String, Double> premiumPerCity = company.getTotalPremiumPerCity();
                for (String city : premiumPerCity.keySet()) {
                        System.out.println("City: " + city + " -> $" + premiumPerCity.get(city));
                }

                System.out.println("---Count Per Car Model Report");
                HashMap<String, Integer> countPerModel = company.getTotalCountPerCarModel();
                for (String model : countPerModel.keySet()) {
                        System.out.println("Model: " + model + " -> Count: " + countPerModel.get(model));
                }

                System.out.println("---Premium Per Car Model Report---");
                HashMap<String, Double> premiumPerModel = company.getTotalPremiumPerCarModel();
                for (String model : premiumPerModel.keySet()) {
                        System.out.printf("Model: %-25s -> Premium: $%,.2f%n", model, premiumPerModel.get(model));
                }

                try {
                        company.createComprehensivePolicy(u1.getUserID(), "test", 12,
                                        new Car("foad car", CarType.LUX, 2000, 120000), 122, 19,
                                        15, date2);
                } catch (PolicyException e) {
                        int newID = e.getID();
                        if (company.createComprehensivePolicy(u1.getUserID(), "test", newID,
                                        new Car("foad car", CarType.LUX, 2000, 120000), 122, 19,
                                        15, date2)) {
                                System.out.println("Policy created with generated ID: " + newID);
                                System.err.println(e.toString());
                        } else {
                                System.out.println("Error: Could not create policy with generated ID.");
                        }
                }
                company.printPolicies(u1.getUserID());
                u1.print();
        }

}
