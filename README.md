# EduLearn Automation Framework

A comprehensive test automation framework for educational platforms built with Selenium WebDriver, TestNG, and Java.

## Overview

The EduLearn Automation Framework is designed to automate testing of educational web platforms, providing:

- Page Object Model implementation
- Configurable test execution
- Detailed reporting with screenshots
- Parallel test execution support
- Logging and error handling

## Project Structure

```
edulearn_automation_framework/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── edulearn/
│   │               ├── core/
│   │               │   ├── ConfigManager.java
│   │               │   └── DriverFactory.java
│   │               ├── pages/
│   │               │   ├── BasePage.java
│   │               │   ├── CoursePage.java
│   │               │   ├── DashboardPage.java
│   │               │   ├── LessonPage.java
│   │               │   ├── LoginPage.java
│   │               │   └── SearchResultsPage.java
│   │               └── utils/
│   │                   ├── ReportManager.java
│   │                   ├── ScreenshotUtils.java
│   │                   └── WaitUtils.java
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── edulearn/
│       │           └── tests/
│       │               └── LoginTest.java
│       └── resources/
│           ├── config.properties
│           ├── log4j2.xml
│           └── testng.xml
└── pom.xml
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome, Firefox, or Edge browser installed

## Setup

1. Clone this repository or download the source code
2. Open the project in your preferred IDE (IntelliJ IDEA, Eclipse, etc.)
3. Install Maven dependencies:
   ```
   mvn clean install -DskipTests
   ```

## Configuration

The framework can be configured by modifying the `src/test/resources/config.properties` file:

```properties
# Application Configuration
base.url=https://edulearn-demo.example.com

# Test Configuration
browser=chrome
headless=false
default.timeout=10
```

## Running Tests

### Using Maven

To run all tests:
```
mvn clean test
```

To run a specific test class:
```
mvn clean test -Dtest=LoginTest
```

### Using TestNG

Run the `testng.xml` file directly from your IDE.

## Test Reports

After test execution, reports are generated in:
- Extent Reports: `target/extent-reports/edulearn-test-report.html`
- TestNG Reports: `target/surefire-reports/index.html`
- Logs: `target/logs/edulearn-automation.log`

## Adding New Tests

1. Create page objects in `src/main/java/com/edulearn/pages/`
2. Create test classes in `src/test/java/com/edulearn/tests/`
3. Add test methods with TestNG annotations
4. Add the test class to `testng.xml`

## Best Practices

- Follow the Page Object Model pattern
- Keep tests independent and atomic
- Use meaningful test names and descriptions
- Add proper assertions
- Handle timeouts and synchronization
- Implement proper logging

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
