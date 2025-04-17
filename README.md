# 🗺️ WordQuest — Your Treasure Map to Vocabulary Mastery


**WordQuest** is an interactive JavaFX application that turns vocabulary building into a gamified learning experience. Designed like a treasure hunt, it allows users to explore, test, and grow their vocabulary through personalized journeys.

---

## 📦 Prerequisites

Ensure you have the following installed:
- Eclipse IDE for Java Developers – [Download](https://www.eclipse.org/downloads/packages/)
- Java Development Kit (JDK) 21+ – [Download](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- JavaFX SDK – [Download](https://gluonhq.com/products/javafx/)

--- 

## ⚙️ Tech Stack 
![Java](https://img.shields.io/badge/Java-21+-brightgreen?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-UI%20Framework-orange?style=for-the-badge)
![Gson](https://img.shields.io/badge/Gson-Persistence-yellowgreen?style=for-the-badge)
![Eclipse IDE](https://img.shields.io/badge/Eclipse-IDE-blue?style=for-the-badge&logo=eclipseide)

---

## ⚙️ Setup Instructions

### 1. Install JDK
Install JDK 21 or later and set the `JAVA_HOME` environment variable.

### 2. Configure JavaFX SDK
Download and unzip JavaFX SDK to a known directory.

### 3. Setup Eclipse
- Open Eclipse and choose a workspace.
- Install the `e(fx)clipse` plugin via **Help → Eclipse Marketplace**.
- Search for *e(fx)clipse*, install, and restart Eclipse.

### 4. Import the Project
- Go to **File → Open Projects from File System**.
- Select the project root folder and click **Finish**.

### 5. Add JavaFX Library
- Right-click the project → **Build Path → Configure Build Path**.
- Under **Libraries**, click **Add Library → User Library → User Libraries…**.
- Click **New**, name it `JavaFX`, then **Add External JARs…**.
- Navigate to the `lib` folder of your JavaFX SDK and select all `.jar` files.

### 6. Configure Run Settings
- Right-click the project → **Run As → Run Configurations**.
- Choose your main class.
- Under **Arguments → VM arguments**, enter:

```bash
--add-modules javafx.controls,javafx.fxml
```

## ▶️ Running the Project

Once configured:

- Right-click the project → **Run As → Java Application**.
- Ensure your main class has a proper `main()` method.

## 👨‍💻 Developer

**Srivarini Mandali**  
🔗 [GitHub](https://github.com/srivarinimandali)
