# Car Insurance Management System – Week 2

## Overview

Second iteration of the Car Insurance system. This lab focuses on software engineering improvements including encapsulation, utility methods, user management, and list operations.

## Branch Info

**Branch:** `week-02`
Builds upon `week-01` with enhanced class design and new functionality.

## New Additions

### Classes

- **MyDate**: Year, month, day representation for policy expiry dates
- **Address**: Street number, street name, suburb, city
- **User**: Account holder with name, ID, address, and associated policies list

### Enhancements to Existing Classes

- **InsurancePolicy**: Added `expiryDate` field (MyDate), full getters/setters
- **Car, MyDate, Address**: Complete accessor/mutator methods
- All classes now follow proper encapsulation principles

### Static Utility Methods (InsurancePolicy)

- `printPolicies(ArrayList<InsurancePolicy>)`: Display policy list
- `calcTotalPayments(ArrayList<InsurancePolicy>, int flatRate)`: Sum premiums
- `carPriceRiseAll(ArrayList<InsurancePolicy>, double risePercent)`: Batch price update
- `filterByCarModel(ArrayList<InsurancePolicy>, String model)`: Filter by car model

### Instance Methods

- `InsurancePolicy.carPriceRise(double risePercent)`: Delegates to `Car.priceRise()` (good design pattern)
- `User.addPolicy()`: Adds policy with unique ID validation
- `User.findPolicy(int id)`: Retrieves policy by ID
- `User.printPolicies(int flatRate)`: Displays user's policies with premiums
- `User.calcTotalPremiums(int flatRate)`: User-specific total calculation
- `User.carPriceRiseAll(double risePercent)`: Applies price rise to all user policies
- `User.filterByCarModel(String model)`: Filters user's policies by car model

### Key Design Patterns

- **Encapsulation**: Private fields with controlled access via getters/setters
- **Delegation**: `carPriceRise()` calls `Car.priceRise()` instead of direct manipulation
- **Static utilities**: Reusable list operations moved from main to class methods
- **Composition**: User contains Address and ArrayList of policies

## Running the Test Suite

1. Checkout this branch: `git checkout week-02`
2. Run the main test class
3. Follow interactive prompts for address input and car model filtering
4. Observe policy management, price adjustments, and filtered outputs

## Project Structure

```
src/
├── Car.java
├── CarType.java
├── MyDate.java # NEW
├── Address.java # NEW
├── InsurancePolicy.java # UPDATED
├── ThirdPartyPolicy.java
├── ComprehensivePolicy.java
├── User.java # NEW
└── InsuranceTest.java # UPDATED
```
