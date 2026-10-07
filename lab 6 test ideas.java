//testing binary file and list of policies
HashMap<Integer,InsurancePolicy> policies=InsurancePolicy.load("policies.ser");
InsurancePolicy.printPolicies(policies);

policies.put( 12,new ThirdPartyInsurance(12,......));
InsurancePolicy.save(policies,"policies.ser");
policies.clear();

policies=InsurancePolicy.load("policies.ser");
InsurancePolicy.printPolicies(policies);

//------------------------------------------------------------------------
//testing binary file and list of users
HashMap<Integer,User> users=User.load("users.ser");
User.printUsers(users);

User user=new User(120,......);
user.addPolicy(new ThirdPartyInsurance(.....));
users.put(120,user);
User.save(users,"users.ser");
users.clear();

users=User.load("users.ser");
User.printUsers(users);

//----------------------------------------------------------------------
//InsuranceCompany and binary file
InsuranceCompany insuranceCompany1=new InsuranceCompany();//using default constructor
insuranceCompany1.load("company.ser");//the whole company including all users and all policies are loaded
System.out.println(insuranceCompany1);

insuranceCompany1.addUser(116,....);
insuranceCompany1.addPolicy(11,.....);
insuranceCompany1.save("company.ser");
InsuranceCompany insuranceCompany2=new InsuranceCompany();

insuranceCompany2.load("company.ser");
System.out.println(insuranceCompany2);

//-----------------------------------------------------------------------------
//testing text file and list of policies with toDilimitedString
HashMap<Integer,InsurancePolicy> policies=InsurancePolicy.load("policies.txt");
InsurancePolicy.printPolicies(policies);

policies.put( 15,new ThirdPartyInsurance(15,......));
InsurancePolicy.saveTextFile(policies,"policies.txt");
policies.clear();

policies=InsurancePolicy.loadTextFile("policies.txt");
InsurancePolicy.printPolicies(policies);

//------------------------------------------------------------------------------
//testing text file and list of users with toDilimitedString
HashMap<Integer,User> users=User.loadTextFile("users.txt");
User.printUsers(users);

User user=new User(122,......);
user.addPolicy(new ThirdPartyInsurance(.....));
users.put(122,user);
User.saveTextFile("users.txt",users);
users.clear();

users=User.loadTextFile("users.txt");
User.printUsers(users);

//-------------------------------------------------------------------
//InsuranceCompany and text file
InsuranceCompany insuranceCompany1=new InsuranceCompany();//using default constructor
insuranceCompany1.loadTextFile("company.txt");//the whole company including all users and all policies are loaded
System.out.println(insuranceCompany1);

insuranceCompany1.addUser(117,....);
insuranceCompany1.addPolicy(12,.....);
insuranceCompany1.saveTextFile("company.txt");
InsuranceCompany insuranceCompany2=new InsuranceCompany();

insuranceCompany2.loadTextFile("company.txt");
System.out.println(insuranceCompany2);





