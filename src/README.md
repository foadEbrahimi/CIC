# Car Insurance Management System – Week 1

## Overview
Implementation of the foundational classes for a simplified Car Insurance Company. This lab establishes the core object-oriented structure including vehicle representation and an insurance policy inheritance hierarchy.

## Branch Info
**Branch:** `week-01`
Contains the initial class definitions and test harness for the insurance system.

## Implementation Details

### Core Classes
- **CarType** (Enum): Vehicle categories (SUV, SED, LUX, HATCH)
- **Car**: Model, type, manufacturing year, and price
- **InsurancePolicy** (Abstract): Base class with shared attributes (holder name, ID, car, claims count) and abstract premium calculation
- **ThirdPartyPolicy**: Extends InsurancePolicy with comments field
- **ComprehensivePolicy**: Extends InsurancePolicy with driver age and coverage level

### Premium Calculation Logic
- **Third Party**: `carPrice/100 + numberOfClaims×200 + flatRate`
- **Comprehensive**: `carPrice/50 + numberOfClaims×200 + flatRate + ageSurcharge`
  - Age surcharge applies if driver < 30: `(30 - driverAge) × 50`
- `flatRate` is passed as an external parameter

### Key Features
- Polymorphic policy handling via abstract base class
- Overridden `calcPayment()` in each subclass
- `print()` method chain using `super.print()`
- `toString()` implementation across all classes
- Test driver demonstrating mixed policy list, output formatting, and total premium calculation

## Running the Code
1. Checkout this branch: `git checkout week-01`
2. Compile and run the main test class
3. Observe printed policy details and total premium summary

## Project Structure
```src/
├── Car.java
├── CarType.java
├── InsurancePolicy.java
├── ThirdPartyPolicy.java
├── ComprehensivePolicy.java
└── InsuranceTest.java
```
