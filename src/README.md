# Car Insurance Management System – Week 3

## Overview

Third iteration of the Car Insurance system. This lab introduces the top-level `InsuranceCompany` class to manage multiple users and policies, adds date-based filtering, and implements factory-style methods for policy creation.

## Branch Info

**Branch:** `week-03`
Builds upon `week-02` with company-level management and advanced filtering.

## New Additions

### Classes

- **InsuranceCompany**: Central manager for all users and policies
  - Admin authentication (`validateAdmin`)
  - User management (`addUser`, `findUser`)
  - Policy operations across all users (`calcTotalPayments`, `carPriceRise`, `filterBy...`)
  - Global policy aggregation (`allPolicies`)

### Enhancements to Existing Classes

#### MyDate

- `boolean isExpired(MyDate date)`: Checks if a given date is after the object's expiry date

#### InsurancePolicy

- `static ArrayList<InsurancePolicy> filterByExpiryDate(...)`: Filters policies expired by a specific date

#### User

- `boolean createThirdPartyPolicy(...)`: Factory method to create and add a Third-Party policy
- `boolean createComprehensivePolicy(...)`: Factory method to create and add a Comprehensive policy
- `ArrayList<InsurancePolicy> filterByExpiryDate(MyDate date)`: Filters user's policies by expiry

### Key Features

- **Hierarchical Management**: Company → Users → Policies
- **Delegation Pattern**: Company methods delegate to User methods, which delegate to Policy static methods
- **Factory Methods**: Simplified policy creation via `createThirdPartyPolicy` and `createComprehensivePolicy`
- **Global Operations**: Calculate total premiums or raise prices across all users in one call
- **Advanced Filtering**: Filter by car model or expiry date at both user and company levels

## Running the Test Suite

1. Checkout this branch: `git checkout week-03`
2. Run the main test class
3. Follow interactive prompts for:
   - Admin login (successful and failed attempts)
   - Adding users and policies (including error handling for duplicates)
   - Filtering policies by date and car model
   - Global price adjustments and premium calculations

## Project Structure

````src/
├── Car.java
├── CarType.java
├── MyDate.java # UPDATED
├── Address.java
├── InsurancePolicy.java # UPDATED
├── ThirdPartyPolicy.java
├── ComprehensivePolicy.java
├── User.java # UPDATED
├── InsuranceCompany.java # NEW
└── InsuranceTest.java # UPDATED```
````
