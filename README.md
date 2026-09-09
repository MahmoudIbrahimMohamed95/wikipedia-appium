# wikipedia-appium

# Appium Mobile Automation Framework

This repository contains an **Android mobile test automation framework** built with **Java** and **Appium**. It is designed to let QA Automation Engineers write, maintain, and execute automated UI tests against Android applications running on emulators or physical devices.

This README is written for a **new engineer starting on a clean Windows machine**. It walks through every tool that must be installed, every environment variable that must be configured, and every project file that must be updated before the first test can run successfully.

> **Read this document from top to bottom, in order.** Each section depends on the one before it (for example, Appium depends on Node.js, and the UiAutomator2 driver depends on Appium). Skipping steps is the single most common cause of setup failures.

## Technology Overview

| Technology     | Purpose                                                                   |
| -------------- | ------------------------------------------------------------------------- |
| Java 17        | Programming language used to write the automation framework and tests     |
| Maven          | Dependency management and build tool for the Java project                 |
| Appium         | Cross-platform mobile automation server that drives the app under test    |
| UiAutomator2   | Appium driver used specifically for automating Android applications       |
| Android Studio | Provides the Android SDK, emulator, and device management tooling         |
| Node.js/npm    | JavaScript runtime and package manager required to install and run Appium |
| Git            | Source control for the framework, tests, and configuration                |

---

## 1. Prerequisites

Before writing or running a single test, the following must be installed and configured on your Windows machine:

- Windows 10 or Windows 11
- JDK 17
- Apache Maven
- Android Studio
- Android SDK (installed via Android Studio)
- Node.js (LTS version)
- npm (bundled with Node.js)
- Appium (installed via npm)
- Appium UiAutomator2 driver (installed via Appium)

**Installation order matters.** Java must exist before you build the project. The Android SDK must exist before Appium can talk to a device. Node.js must exist before Appium can be installed. Follow the sections below in sequence — do not jump ahead.

---

## 2. Install Java JDK 17

1. Download a JDK 17 distribution (e.g., Oracle JDK, Eclipse Temurin/Adoptium, or Amazon Corretto).
2. Run the Windows installer (`.msi` or `.exe`) and follow the installation wizard, accepting the default installation options unless your organization requires otherwise.
3. Note the installation directory shown at the end of the installer. A typical location is:

```text
C:\Program Files\Java\jdk-17
```

> **Important:** The exact folder name depends on the JDK vendor and the specific patch version (for example, `jdk-17`, `jdk-17.0.9`, or `jdk-17.0.12`). Always confirm the real path on your machine rather than assuming it matches the example above.

### Configure `JAVA_HOME`

`JAVA_HOME` is an environment variable that tells Java-based tools (like Maven) where the JDK is installed. Configure it as follows:

```text
JAVA_HOME=C:\Program Files\Java\jdk-17
```

**Step-by-step (Windows GUI):**

1. Click the **Windows Search** bar (or press the Windows key).
2. Type `Environment Variables` and press Enter.
3. Select **Edit the system environment variables**. This opens the _System Properties_ dialog.
4. Click the **Environment Variables...** button near the bottom.
5. Under **System variables** (or **User variables**, depending on your permissions), click **New...**, set:
   - Variable name: `JAVA_HOME`
   - Variable value: the path to your JDK installation (e.g., `C:\Program Files\Java\jdk-17`)
6. Click **OK** to save the new variable.
7. Find the `Path` variable in the same list, select it, and click **Edit...**. Click **New** and add:

```text
%JAVA_HOME%\bin
```

8. Click **OK** on every open dialog to save the changes.

### Verify Java

Open a **new** Command Prompt window (environment variable changes do not apply to already-open windows) and run:

```cmd
java -version
javac -version
echo %JAVA_HOME%
```

A successful setup shows:

- `java -version` and `javac -version` both printing a version string that starts with `17.` (e.g., `17.0.9`).
- `echo %JAVA_HOME%` printing the exact folder path you configured, not the literal text `%JAVA_HOME%`.

## 2A. Install Maven

This project uses **Maven** to manage dependencies and to run the test suite (`mvn clean test`), so Maven must be installed and put on `Path` before you reach Section 18. Maven itself is a separate download from Java — installing JDK 17 does not install Maven.

1. Download the Maven **binary zip archive** (`apache-maven-<version>-bin.zip`) from the official Apache Maven website. You do not need the source archive.
2. Extract the zip to a permanent location. A typical convention is:

```text
C:\Program Files\Apache\maven
```

or, to avoid admin-rights issues writing under `C:\Program Files`, many engineers instead use:

```text
C:\Users\<YOUR_USERNAME>\tools\apache-maven-3.9.9
```

> As with Java, the exact folder name will include the specific Maven version you downloaded (e.g., `apache-maven-3.9.9`). Confirm the real folder name on your machine rather than assuming it matches these examples.

### Configure `MAVEN_HOME` (or `M2_HOME`)

```text
MAVEN_HOME=C:\Users\<YOUR_USERNAME>\tools\apache-maven-3.9.9
```

Create it using the same **Environment Variables** dialog described in Section 2:

Then add Maven's `bin` folder to `Path`:

```text
%MAVEN_HOME%\bin
```

**Important:** Maven requires `JAVA_HOME` to already be correctly configured (Section 2) — it uses that variable internally to locate the JDK it should build with. Confirm `echo %JAVA_HOME%` works correctly before moving on.

### Verify Maven

Open a **new** Command Prompt and run:

```cmd
mvn -version
```

A successful setup prints Maven's version, the Java version it is using (which should be 17), and the Java home path it detected — this is a good secondary confirmation that `JAVA_HOME` is wired correctly.

---

## 3. Install Android Studio

1. Download Android Studio from the official Android Developers website.
2. Run the installer and follow the setup wizard. Accept the default components (Android SDK, Android Virtual Device) unless you have a specific reason not to.
3. Launch Android Studio after installation completes.
4. On first launch, the **Setup Wizard** will prompt you to install the Android SDK, SDK Platform-Tools, and a default emulator image. Allow it to complete this download.

### Finding the SDK Location

Once Android Studio has finished its initial setup, you can view (and change) the SDK location from:

**Android Studio → Settings/Preferences → Appearance & Behavior → System Settings → Android SDK**

The typical Windows SDK installation path is:

```text
C:\Users\<YOUR_USERNAME>\AppData\Local\Android\Sdk
```

> **`<YOUR_USERNAME>` must be replaced with your actual Windows username.** Do not copy a placeholder or example username (such as `KING`) into any configuration file. To find your real username, run `echo %USERNAME%` in a Command Prompt.

---

## 4. Configure Android SDK Environment Variables

Two environment variables are commonly used to point tools at the Android SDK: `ANDROID_HOME` and `ANDROID_SDK_ROOT`.

```text
ANDROID_HOME=C:\Users\<YOUR_USERNAME>\AppData\Local\Android\Sdk
```

```text
ANDROID_HOME=C:\Users\<YOUR_USERNAME>\AppData\Local\Android\Sdk
ANDROID_SDK_ROOT=C:\Users\<YOUR_USERNAME>\AppData\Local\Android\Sdk
```

Create both variables using the same **Environment Variables** dialog described in Section 2.

### Configure Android SDK `Path` Entries

Add the following entries to your `Path` variable so that SDK command-line tools are available from any terminal:
if tools not found on path proceed to step 5 first.

```text
%ANDROID_HOME%\platform-tools
%ANDROID_HOME%\cmdline-tools\bin
```

## 5. Configure Android SDK Using Android Studio

Open the SDK Manager from within Android Studio:

- Recent versions: **Tools → SDK Manager**
- Some versions/layouts: **More Actions → SDK Manager** (from the Welcome screen) or a toolbar icon labeled **SDK Manager**

The exact menu path can shift slightly between Android Studio releases, so if `Tools → SDK Manager` isn't visible, check the Welcome screen's **More Actions** menu instead.

### About "Android SDK Tools (Obsolete)"

You may see a component in the SDK Manager labeled **Android SDK Tools (Obsolete)**. Historically, this package provided the original command-line tools (the legacy `tools` folder referenced in Section 4). It has been superseded by the **Android SDK Command-line Tools** package.

- It is generally **not required** for a modern Android Studio and Appium installation.
- Do not install it unless a specific project requirement or legacy script in this framework explicitly depends on it.

---

## 6. Verify Android SDK

Open a new Command Prompt and run:

```cmd
adb --version
adb devices
emulator -list-avds
```

**What each command does:**

- `adb --version` — Confirms that Android Debug Bridge (`adb`) is installed and accessible on your `Path`. It prints the ADB version number.
- `adb devices` — Lists all Android devices/emulators currently connected to and recognized by `adb`.
- `emulator -list-avds` — Lists the names of all Android Virtual Devices (AVDs) you have created in Android Studio.

**Understanding `adb devices` output states:**

| State          | Meaning                                                                                                                        |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| `device`       | The device/emulator is connected and authorized — ready for use                                                                |
| `unauthorized` | The device is connected, but you have not yet accepted the USB/ADB debugging authorization prompt on the device itself         |
| `offline`      | `adb` sees the device but cannot currently communicate with it (often resolved by reconnecting the device or restarting `adb`) |

---

## 7. Install Node.js and npm

1. Download the **LTS (Long-Term Support)** version of Node.js for Windows.
2. Run the installer and follow the setup wizard, keeping the default options (including the option to add Node.js to `Path`, which is usually checked by default).
3. Note the installation folder. A typical location is:

```text
C:\Program Files\nodejs
```

> The exact path can differ depending on where you chose to install it or your Windows configuration.

If you want an explicit `NODE_HOME` variable (optional, since the installer usually configures `Path` automatically), configure it the same way as `JAVA_HOME`:

```text
NODE_HOME=C:\Program Files\nodejs
```

And add to `Path`:

```text
%NODE_HOME%
```

> **Note:** Most official Node.js Windows installers add Node.js to `Path` automatically during installation, so manually creating `NODE_HOME` is optional and mainly useful for clarity or for tools that specifically expect that variable.

### npm Global Installation Path

npm's global installation directory is **not** a fixed, universal path — it depends on how Node.js/npm was installed and how npm is configured on your specific machine. Do not assume a path such as `/nodeModules/npm/bin` (this is not a valid Windows path and should never be used).

To find the actual global prefix and global module directory on your machine, run:

```cmd
npm config get prefix
```

```cmd
npm root -g
```

### Verify Node.js

```cmd
node -v
npm -v
npm config get prefix
npm root -g
```

## 8. Install Appium

Appium is distributed as an npm package and is installed globally so it can be run as a command-line tool.

1. Open Command Prompt or PowerShell.
2. Run:

```cmd
npm install -g appium
```

3. Verify the installation:

```cmd
appium --version
```

A successful install prints Appium's version number.

> **Note:** Installing Appium itself and installing an Appium **driver** (such as UiAutomator2, covered next) are two separate steps. Installing Appium alone does not give it the ability to automate Android apps — the driver is required.

---

## 9. Install Appium UiAutomator2 Driver

Appium uses pluggable **drivers** to support different platforms. For Android automation, the standard driver is **UiAutomator2**, which is Google's modern UI testing framework wrapped by Appium.

Install the driver:

```cmd
appium driver install uiautomator2
```

Check which drivers are currently installed:

```cmd
appium driver list --installed
```

**Why UiAutomator2?** It is the officially maintained, actively supported Appium driver for Android, built on top of Google's own `UiAutomator2` testing framework. It supports modern Android versions, provides reliable element location strategies, and is the default recommendation for Android automation in the current Appium ecosystem.

## 10. Configure Android Emulator

1. Open **Android Studio**.
2. Open **Device Manager** (usually available from the toolbar icon or **Tools → Device Manager**).
3. Click **Create Device** (or **Create Virtual Device**).
4. Select a device model (e.g., Pixel 7).
5. Select or download an Android **API level/system image** to run on that device.
6. Review the configuration and click **Finish** to create the AVD.
7. Start the emulator by clicking the **Play/Launch** button next to the AVD in Device Manager, or launch it from the command line.

### Listing Available AVDs

```cmd
emulator -list-avds
```

Example output:

```text
Pixel_7
Pixel_8_API_35
```

> **This AVD name matters.** Whatever name appears in this list (exactly as it appears, including underscores) is what you will later enter into `properties/android.properties` in Section 11.1. This name is specific to your machine — do not assume every teammate has an AVD with the same name.

---

## 11. Configure Project Properties

After your environment is fully installed, each developer or tester working with this repository must configure a small set of **machine-specific** property files. These files are intentionally kept separate from the Java source code so that each engineer's local device/Appium configuration does not conflict with anyone else's.

```text
project-root/
├── properties/
│   ├── android.properties
│   └── server.properties
├── src/
├── pom.xml
└── README.md
```

### 11.1 Configure `android.properties`

`properties/android.properties` contains the Android device configuration used when a test session is created.

Example:

```properties
deviceName=Pixel_7
```

`deviceName` must exactly match the name of an Android Virtual Device configured on your machine (see Section 10). It is **not** a free-text label — it must correspond to a real AVD.

To find the correct value:

- **Android Studio → Device Manager**, or
- Run:

```cmd
emulator -list-avds
```

Example output:

```text
Pixel_7
Pixel_8_API_35
```

If you selected `Pixel_7` as your emulator, configure:

```properties
deviceName=Pixel_7
```

**This value is machine-specific.** If a different tester's AVD is named, for example:

```text
My_Test_Device
```

...then _their_ `android.properties` file should contain:

```properties
deviceName=My_Test_Device
```

Do not assume every engineer on the team uses the same emulator name — each person must confirm and set their own value.

---

## 12. Configure `server.properties`

`properties/server.properties` contains the configuration used to connect the framework to a running Appium server.

Example:

```properties
appiumMainJsPATH=C:/Users/<YOUR_USERNAME>/AppData/Roaming/npm/node_modules/appium/build/lib/main.js
appiumIpAddress=127.0.0.1
appiumPortAddress=4723
```

### 13.1 `appiumMainJsPATH`

This property points to the `main.js` file of the Appium package installed by npm — the entry point some frameworks use to launch Appium programmatically (for example, from within a Java test setup process).

Example (**illustrative only**):

```properties
appiumMainJsPATH=C:/Users/KING/AppData/Roaming/npm/node_modules/appium/build/lib/main.js
```

This path is **machine-specific** and can differ depending on how Node.js, npm, and Appium were installed on that particular machine (for example, a custom npm prefix would change this path entirely). Do not treat the example as guaranteed to be correct for your machine — verify it.

**How to determine the correct path on your machine:**

1. Find your Windows username:

```cmd
echo %USERNAME%
```

2. Find npm's global prefix:

```cmd
npm config get prefix
```

3. Find the global `node_modules` directory:

```cmd
npm root -g
```

4. Combine this information to locate the actual `appium/build/lib/main.js` file inside your global `node_modules` folder, and use that **real, verified** path in `server.properties` — do not blindly copy the example path from this README.

### Configure Appium IP Address and Port

```properties
appiumIpAddress=127.0.0.1
appiumPortAddress=4723
```

- `127.0.0.1` is the loopback address, meaning "this same machine" (localhost). Since Appium normally runs on the same machine as the test framework, this is the standard value.
- `4723` is Appium's standard/default server port.

**Starting Appium and confirming the address/port:**

```cmd
appium
```

The Appium console will print a line similar to:

```text
[Appium] Appium REST http interface listener started on 0.0.0.0:4723
```

`0.0.0.0` means Appium is listening on **all** network interfaces of the machine, which includes the loopback interface — so a client on the same machine can still reach it at `127.0.0.1:4723`. This is why `server.properties` uses `127.0.0.1` even though the console output shows `0.0.0.0`.

If Appium is started on a different port (for example, because port 4723 was already in use), the console output and your `server.properties` file must match. For example, if Appium starts on port 4725:

```properties
appiumIpAddress=127.0.0.1
appiumPortAddress=4725
```

---

## 15. Complete Example Configuration

**`properties/android.properties`**

```properties
deviceName=Pixel_7
```

**`properties/server.properties`**

```properties
appiumMainJsPATH=C:/Users/<YOUR_USERNAME>/AppData/Roaming/npm/node_modules/appium/build/lib/main.js
appiumIpAddress=127.0.0.1
appiumPortAddress=4723
```

> These are **examples only**. Every value that references a username, device name, or port must be updated to match your specific machine and setup before running tests.

---

## 16. Verify the Complete Environment

Run all of the following in a single Command Prompt session to confirm every layer of the environment is correctly configured:

```cmd
java -version
javac -version
echo %JAVA_HOME%

mvn -version

node -v
npm -v
npm config get prefix
npm root -g

adb --version
adb devices
emulator -list-avds

appium --version
appium driver list --installed
```

**What this confirms:**

- `java -version` / `javac -version` / `echo %JAVA_HOME%` — Java 17 is installed and `JAVA_HOME` is correctly configured.
- `mvn -version` — Maven is installed, on `Path`, and is using JDK 17 (this command will also echo the Java version and Java home it detected, which doubles as a Java sanity check).
- `node -v` / `npm -v` — Node.js and npm are installed and on your `Path`.
- `npm config get prefix` / `npm root -g` — Where npm installs global packages on this machine (used to locate Appium's files).
- `adb --version` / `adb devices` — The Android SDK platform-tools are installed and can see connected devices/emulators.
- `emulator -list-avds` — At least one AVD exists and can be referenced in `android.properties`.
- `appium --version` — Appium itself is installed globally.
- `appium driver list --installed` — The UiAutomator2 driver (and any others) is installed and available to Appium.

If every command above runs without a "not recognized" error and returns sensible output, your machine is fully configured.

---

## 17. Start Appium Server

Before running any tests, the Appium server must be running.

```cmd
appium
```

**How to confirm Appium started successfully:** The console will print startup logs ending with a line indicating the REST interface has started, along with the address and port it is listening on, for example:

```text
[Appium] Welcome to Appium v2.x.x
[Appium] Appium REST http interface listener started on 0.0.0.0:4723
```

- **Default host/address behavior:** Appium listens on `0.0.0.0` by default, meaning it accepts connections on all of the machine's network interfaces — including `127.0.0.1` (localhost), which is what your local test framework will use to connect.
- **Default port:** `4723`, unless overridden with a startup flag (e.g., `appium -p 4725`) or a different default is configured.
- **Mapping to `server.properties`:** The `appiumIpAddress` and `appiumPortAddress` values in `properties/server.properties` must match what the running Appium server actually reports in its console output.
- **Do not assume the console must display `127.0.0.1`.** Seeing `0.0.0.0:4723` in the console is normal and expected — it does not mean something is misconfigured. A client on the same machine reaches this server via `127.0.0.1:4723`.
- **Stopping Appium:** Press `Ctrl + C` in the terminal window where Appium is running.

---

## 18. Run the Automation Project

This project uses **Maven** for dependency management and build/test execution, and **TestNG** as the underlying test framework (via Maven's test lifecycle).

With the Appium server running (Section 17) and an emulator/device available (`adb devices` shows it as `device`), run the tests from the project root:

```cmd
mvn clean test
```

- `mvn clean` removes previous build artifacts.
- `mvn test` compiles the project and executes the test suite (TestNG tests configured in the project, typically via a `testng.xml` suite file or Maven Surefire configuration in `pom.xml`).

**Expected execution flow:**

```text
Android Emulator / Device
        ↓
ADB
        ↓
Appium UiAutomator2 Driver
        ↓
Appium Server
        ↓
Java Automation Framework
        ↓
Automated Tests
```

In other words: the emulator/device must be running and visible to `adb`, Appium (with the UiAutomator2 driver) must be running and able to reach that device through `adb`, and only then can the Java framework establish a session and execute the test suite against the app.

---

## 20. Environment Variables Summary

| Variable           | Example                                              |
| ------------------ | ---------------------------------------------------- |
| `JAVA_HOME`        | `C:\Program Files\Java\jdk-17`                       |
| `MAVEN_HOME`       | `C:\Users\<YOUR_USERNAME>\tools\apache-maven-3.9.9`  |
| `ANDROID_HOME`     | `C:\Users\<YOUR_USERNAME>\AppData\Local\Android\Sdk` |
| `ANDROID_SDK_ROOT` | `C:\Users\<YOUR_USERNAME>\AppData\Local\Android\Sdk` |
| `NODE_HOME`        | `C:\Program Files\nodejs`                            |

### Important `Path` Entries

| Path Entry                                | Purpose                                                             |
| ----------------------------------------- | ------------------------------------------------------------------- |
| `%JAVA_HOME%\bin`                         | Enables `java`/`javac` commands                                     |
| `%MAVEN_HOME%\bin`                        | Enables the `mvn` command                                           |
| `%ANDROID_HOME%\platform-tools`           | Enables `adb`                                                       |
| `%ANDROID_HOME%\emulator`                 | Enables the `emulator` command                                      |
| `%ANDROID_HOME%\cmdline-tools\latest\bin` | Enables Android SDK command-line tools (`sdkmanager`, `avdmanager`) |
| `%NODE_HOME%`                             | Enables `node`/`npm` commands (if not already added automatically)  |
| npm global binary path                    | Enables globally installed npm command-line tools (e.g., `appium`)  |

> The exact npm global binary path varies by machine and npm configuration — always confirm it with `npm config get prefix` rather than assuming a fixed value.

---

## 21. Troubleshooting

### Maven

**Issue: `mvn` is not recognized**

- **Symptom:** `'mvn' is not recognized as an internal or external command`.
- **Possible Cause:** `%MAVEN_HOME%\bin` is missing from `Path`, or the terminal was opened before the variable was saved.
- **Solution:** Verify `MAVEN_HOME` and its `Path` entry (Section 2A), then open a new terminal window.
- **Verification Command:** `mvn -version`

**Issue: `mvn clean test` fails before any tests run**

- **Symptom:** The build fails during dependency resolution, before Appium or any device interaction happens.
- **Possible Cause:** `JAVA_HOME` is misconfigured, or there is no network access to Maven Central.
- **Solution:** Re-verify `JAVA_HOME` and internet/proxy connectivity; run `mvn -version` to confirm which JDK Maven is using.
- **Verification Command:** `mvn -version`

### Java

**Issue: `java` is not recognized**

- **Symptom:** Command Prompt returns `'java' is not recognized as an internal or external command`.
- **Possible Cause:** `%JAVA_HOME%\bin` is missing from `Path`, or the terminal was opened before the environment variable was saved.
- **Solution:** Verify `JAVA_HOME` and the `Path` entry, then open a brand-new Command Prompt window.
- **Verification Command:** `java -version`

**Issue: `JAVA_HOME` is incorrect**

- **Symptom:** Maven or other Java tools fail with errors referencing an invalid Java home.
- **Possible Cause:** `JAVA_HOME` points to a nonexistent folder, or points to the `bin` folder instead of the JDK root.
- **Solution:** Set `JAVA_HOME` to the JDK root directory (the folder containing `bin`, `lib`, etc.), not to `bin` itself.
- **Verification Command:** `echo %JAVA_HOME%`

**Issue: Multiple Java versions installed**

- **Symptom:** `java -version` reports an unexpected version (not 17).
- **Possible Cause:** An older JDK/JRE appears earlier in `Path` than JDK 17.
- **Solution:** Reorder `Path` so `%JAVA_HOME%\bin` (pointing to JDK 17) comes first, or uninstall conflicting versions.
- **Verification Command:** `java -version` and `where java`

### Android

**Issue: `adb` is not recognized**

- **Symptom:** `'adb' is not recognized as an internal or external command`.
- **Possible Cause:** `%ANDROID_HOME%\platform-tools` is missing from `Path`.
- **Solution:** Add `%ANDROID_HOME%\platform-tools` to `Path` and open a new terminal.
- **Verification Command:** `adb --version`

**Issue: Device not detected**

- **Symptom:** `adb devices` returns an empty list.
- **Possible Cause:** Emulator is not running, USB debugging is disabled on a physical device, or a driver/cable issue exists.
- **Solution:** Start the emulator via Android Studio/`emulator -avd <name>`, or enable USB debugging on the physical device and reconnect.
- **Verification Command:** `adb devices`

**Issue: Device is `unauthorized`**

- **Symptom:** `adb devices` shows the device with state `unauthorized`.
- **Possible Cause:** The "Allow USB debugging" prompt on the device has not been accepted.
- **Solution:** Check the device screen and tap **Allow**; enable "Always allow from this computer" to avoid repeating this.
- **Verification Command:** `adb devices`

**Issue: Device is `offline`**

- **Symptom:** `adb devices` shows the device with state `offline`.
- **Possible Cause:** `adb` lost communication with the device/emulator (common after a system sleep or USB glitch).
- **Solution:** Run `adb kill-server` followed by `adb start-server`, or restart the emulator/reconnect the device.
- **Verification Command:** `adb devices`

**Issue: Emulator cannot start**

- **Symptom:** The AVD fails to launch from Android Studio or the command line.
- **Possible Cause:** Insufficient system resources, missing system image, or virtualization not enabled in BIOS.
- **Solution:** Confirm the system image is downloaded (SDK Manager), close other resource-heavy applications, and confirm hardware virtualization (Intel VT-x/AMD-V) is enabled.
- **Verification Command:** `emulator -list-avds`

**Issue: AVD not found**

- **Symptom:** The framework reports it cannot find the device name configured in `android.properties`.
- **Possible Cause:** The `deviceName` value does not exactly match an existing AVD name.
- **Solution:** Run `emulator -list-avds` and copy the exact name (including underscores/capitalization) into `android.properties`.
- **Verification Command:** `emulator -list-avds`

**Issue: Android SDK path is incorrect**

- **Symptom:** SDK-related tools fail, or Android Studio reports it cannot locate the SDK.
- **Possible Cause:** `ANDROID_HOME`/`ANDROID_SDK_ROOT` point to the wrong folder, or contain a typo.
- **Solution:** Re-confirm the real SDK path via Android Studio settings and update both variables to match exactly.
- **Verification Command:** `echo %ANDROID_HOME%` and `adb --version`

### Node.js/npm

**Issue: `node` is not recognized**

- **Symptom:** `'node' is not recognized as an internal or external command`.
- **Possible Cause:** Node.js was not added to `Path` during installation.
- **Solution:** Reinstall Node.js (ensuring the "Add to PATH" option is checked) or manually add the Node.js install folder to `Path`.
- **Verification Command:** `node -v`

**Issue: `npm` is not recognized**

- **Symptom:** `'npm' is not recognized as an internal or external command`.
- **Possible Cause:** Same as above — npm ships with Node.js and shares its `Path` requirement.
- **Solution:** Confirm `node -v` works first; if it does but `npm -v` fails, repair the Node.js installation.
- **Verification Command:** `npm -v`

**Issue: Global npm commands not recognized**

- **Symptom:** A globally installed package's command (e.g., `appium`) is "not recognized" even though `npm install -g` succeeded.
- **Possible Cause:** npm's global binary folder isn't on `Path`.
- **Solution:** Run `npm config get prefix`, then add that folder to `Path`; open a new terminal.
- **Verification Command:** `npm config get prefix`

**Issue: Incorrect npm global PATH**

- **Symptom:** Global packages install without error but still can't be run from any directory.
- **Possible Cause:** A stale or incorrect path was manually added to `Path` instead of the real prefix.
- **Solution:** Remove any guessed/incorrect entries and add the exact folder returned by `npm config get prefix`.
- **Verification Command:** `npm config get prefix` and `npm root -g`

### Appium

**Issue: `appium` is not recognized**

- **Symptom:** `'appium' is not recognized as an internal or external command`.
- **Possible Cause:** Appium wasn't installed globally, or the npm global binary folder isn't on `Path`.
- **Solution:** Reinstall with `npm install -g appium`, and confirm the npm global path is on `Path` (see Node.js/npm troubleshooting above).
- **Verification Command:** `appium --version`

**Issue: Appium driver is missing**

- **Symptom:** Test session creation fails with an error indicating no driver was found for the requested platform.
- **Possible Cause:** UiAutomator2 driver was never installed.
- **Solution:** Run `appium driver install uiautomator2`.
- **Verification Command:** `appium driver list --installed`

**Issue: UiAutomator2 installation failure**

- **Symptom:** `appium driver install uiautomator2` fails with a network or npm error.
- **Possible Cause:** Network connectivity issue, npm registry problem, or permissions issue.
- **Solution:** Retry the install, check internet connectivity/proxy settings, or run the terminal as Administrator.
- **Verification Command:** `appium driver list --installed`

**Issue: Appium cannot connect to Android**

- **Symptom:** Session creation hangs or fails with a device-connection-related error.
- **Possible Cause:** No device/emulator is visible to `adb`, or `ANDROID_HOME` is misconfigured.
- **Solution:** Run `adb devices` first and resolve any device state issues before starting Appium; re-verify `ANDROID_HOME`.
- **Verification Command:** `adb devices`

**Issue: Appium cannot create a session**

- **Symptom:** The framework throws a session-not-created exception.
- **Possible Cause:** Incorrect capabilities (e.g., wrong `deviceName` or `platformVersion`), Appium server not running, or driver/Appium version mismatch.
- **Solution:** Confirm Appium is running (`appium`), confirm `android.properties` values match a real AVD, and check the Appium console for the detailed error.
- **Verification Command:** `appium --version` and `adb devices`

**Issue: `appiumMainJsPATH` is incorrect**

- **Symptom:** The framework fails to launch Appium programmatically, citing a missing `main.js` file.
- **Possible Cause:** The path in `server.properties` was copied from an example instead of the real machine-specific path.
- **Solution:** Re-derive the path using `npm config get prefix` and `npm root -g`, and confirm the file actually exists at that location before saving it in `server.properties`.
- **Verification Command:** `npm root -g` (then manually confirm `appium\build\lib\main.js` exists under that folder)

**Issue: Appium port conflict**

- **Symptom:** Appium fails to start, or reports the port is already in use.
- **Possible Cause:** Another Appium instance (or another application) is already using port 4723.
- **Solution:** Stop the conflicting process, or start Appium on a different port (e.g., `appium -p 4725`) and update `appiumPortAddress` in `server.properties` to match.
- **Verification Command:** Check the Appium startup console output for the actual listening port