# 🚀 PROJECT-TEAM-05 (SwagLabs Automated Testing)

![Java](https://img.shields.io/badge/Java-11%2B-orange)  
![Maven](https://img.shields.io/badge/Maven-Build-blue)  
![Selenium](https://img.shields.io/badge/Selenium-Automation-green)  
![TestNG](https://img.shields.io/badge/TestNG-Framework-red)  
![ExtentReports](https://img.shields.io/badge/Reports-Extent-blueviolet)  

> **Internal-use automation suite** for testing the [Swag Labs](https://www.saucedemo.com) demo app using **Selenium + TestNG + ExtentReports**.

---

## 📂 Project Structure

```
PROJECT-TEAM-05/
├── Excel/                     # Excel test data for login & checkout
├── Test data/                 # Additional raw data
├── drivers/                   # Browser drivers (WebDriver executables)
├── screenshots/               # Failure screenshots
├── src/
│   ├── main/java/commons/     # Shared utilities (BaseTest, WaitUtils, ExcelUtils, Screenshot, Reports)
│   └── main/java/element/     # Page Object classes
├── test-output/               # Extent HTML reports
├── target/                    # Maven build output
├── pom.xml                    # Maven build config
└── testng.xml                 # TestNG suite config
```

---

## ⚙️ Prerequisites

- ☕ **Java 11+**  
- 📦 **Maven** installed  
- 🌐 **WebDriver executables** placed inside `/drivers`  
- 📑 **Excel test data files** inside `Excel/`  
- 🛠 IDE: IntelliJ IDEA recommended  

---

## ▶️ Running Tests

### 🔹 From IntelliJ IDEA
1. Import project as **Maven** project  
2. Let Maven download dependencies on first run  
3. Open `testng.xml` in root  
4. Right-click → **Run ‘testng.xml’**  

👉 Generates report at:  
`test-output/ExtentReport.html`

### 🔹 From Command Line
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

---

## 🧪 Test Data

- **Login Data** → `Excel/` (`username`, `password`)  
- **Checkout Data** → `Excel/` (`first name`, `last name`, `postal code`)  

✔️ Tests iterate rows dynamically — no code changes needed to add more test cases.  

---

## 📊 Reporting & Screenshots

- **ExtentReports** → Auto-generated at: `test-output/ExtentReport.html`  
- **Screenshots** → Stored in `/screenshots/` on test failures  
- Filenames include: `TestName_User_Timestamp.png`  

Example in report:  
✅ Passed test  
❌ Failed test (with screenshot attached)  
⏩ Skipped test  

---

## 🛠 Adding New Tests

1. Create Page Object in `src/main/java/element/`  
2. Create test class (extend `BaseTest`)  
3. Use:
   ```java
   testLog.get().log(Status.INFO, "Step description");
   ```
4. Add class to `testng.xml` under `<classes>`  

---

## 🆘 Support

👤 **Author**: VSAbraham  
💬 **Contact**: abrahamvs1163@gmail.com 

---

✨ _Built with Selenium + TestNG + ExtentReports for reliable test automation._  
