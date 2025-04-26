# Playwright Java Automation Framework

This is a data-driven automation framework built with Playwright for Java, TestNG, and Extent Reports.

## Features

- Data-driven testing using Excel
- Extent Reports for test reporting
- Screenshot capture on test failure
- Page Object Model structure
- Reusable utilities

## Prerequisites

- Java 11 or higher
- Maven
- Playwright browsers

## Setup

1. Clone the repository
2. Install dependencies:
   ```bash
   mvn clean install
   ```
3. Install Playwright browsers:
   ```bash
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
   ```

## Running Tests

Run all tests:
```bash
mvn clean test
```

## Test Reports

After test execution, reports can be found in:
- Extent Reports: `test-output/ExtentReport_*.html`
- Screenshots: `test-output/screenshots/`

## Project Structure

```
├── src
│   ├── main/java/com/playwright
│   │   ├── base
│   │   │   └── BaseTest.java
│   │   └── utils
│   │       └── ExcelUtils.java
│   └── test
│       ├── java/com/playwright/tests
│       │   └── SauceDemoTest.java
│       └── resources/testdata
│           └── TestData.xlsx
├── pom.xml
├── testng.xml
└── README.md
```

## Test Cases

1. Login Success Test
2. Login Failure Test
3. Add to Cart Test

Each test case uses data from the Excel file for test data management.