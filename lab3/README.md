# Android Storage Demo App

This project demonstrates saving and loading data using three Android storage methods:
- SharedPreferences
- Internal Storage
- External Storage (SD Card)

## Setup Tutorial

Follow these steps to set up this program in a new Android Studio project:

---

## 1. Create a New Android Studio Project
- Open Android Studio and create a new project (Empty Activity recommended).
- Choose a package name (e.g., `com.example.simplefiledemo`).

---

## 2. Overwrite/Add the Following Files

### **A. MainActivity.kt**
- **Location:** `app/src/main/java/[your/package/name]/MainActivity.kt`
- **Action:** Overwrite the existing `MainActivity.kt` with the provided file.

### **B. activity_main.xml**
- **Location:** `app/src/main/res/layout/activity_main.xml`
- **Action:** Overwrite the existing `activity_main.xml` with the provided file.

### **C. AndroidManifest.xml**
- **Location:** `app/src/main/AndroidManifest.xml`
- **Action:** Add the following line inside the `<manifest>` tag (outside `<application>`):
  ```xml
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
  ```
- Ensure your `<activity>` declaration for `MainActivity` is present.

### **D. user_manual.md**
- **Location:** Project root or documentation folder
- **Action:** Reference for testing and usage instructions.

---

## 3. Sync and Build
- Click **File > Sync Project with Gradle Files** in Android Studio.
- Build and run the app on an emulator or device.

---

## 4. Test All Features
- Use the app to save and load data in each section (SharedPreferences, Internal Storage, External Storage).
- Refer to `user_manual.md` for step-by-step testing instructions.

---

## 5. Troubleshooting
- If you see permission errors for external storage, check app permissions in device settings or use adb to grant permissions.
- Make sure the package name in all files matches your project’s package name.

---

## 6. File Reference Table

| File                | Location                                             | Action                |
|---------------------|-----------------------------------------------------|-----------------------|
| MainActivity.kt     | app/src/main/java/[your/package/name]/MainActivity.kt| Overwrite             |
| activity_main.xml   | app/src/main/res/layout/activity_main.xml            | Overwrite             |
| AndroidManifest.xml | app/src/main/AndroidManifest.xml                     | Add permission line   |
| user_manual.md      | Project root                                         | Reference only        |

---

## 7. Additional Notes
- The app is compatible with Android 8 (API 26) and above.
- All features are contained in a single activity for easy demonstration.
- For more details, see `user_manual.md`.

---

**If you need further help or want to customize the app, feel free to ask!**
