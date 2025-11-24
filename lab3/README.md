# Android 儲存空間示範應用程式

本專案示範使用三種 Android 儲存方法來儲存和載入資料：
- SharedPreferences（共享偏好設定）
- Internal Storage（內部儲存空間）
- External Storage（外部儲存空間 / SD 卡）

## 設定教學

請按照以下步驟在新的 Android Studio 專案中設定此程式：

---

## 1. 建立新的 Android Studio 專案
- 開啟 Android Studio 並建立新專案（建議使用 Empty Activity）。
- 選擇套件名稱（例如：`com.example.simplefiledemo`）。

---

## 2. 覆寫/新增以下檔案

### **A. MainActivity.kt**
- **位置：** `app/src/main/java/[your/package/name]/MainActivity.kt`
- **操作：** 使用提供的檔案覆寫現有的 `MainActivity.kt`。

### **B. activity_main.xml**
- **位置：** `app/src/main/res/layout/activity_main.xml`
- **操作：** 使用提供的檔案覆寫現有的 `activity_main.xml`。

### **C. AndroidManifest.xml**
- **位置：** `app/src/main/AndroidManifest.xml`
- **操作：** 在 `<manifest>` 標籤內（在 `<application>` 外）加入以下行：
  ```xml
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
  ```
- 確保 `MainActivity` 的 `<activity>` 宣告已存在。

### **D. user_manual.md**
- **位置：** 專案根目錄或文件資料夾
- **操作：** 作為測試和使用說明的參考。

---

## 3. 同步並建置
- 在 Android Studio 中點選 **File > Sync Project with Gradle Files**。
- 在模擬器或裝置上建置並執行應用程式。

---

## 4. 測試所有功能
- 使用應用程式在每個區段（SharedPreferences、Internal Storage、External Storage）中儲存和載入資料。
- 參考 `user_manual.md` 以取得逐步測試說明。

---

## 5. 疑難排解
- 如果看到外部儲存空間的權限錯誤，請在裝置設定中檢查應用程式權限，或使用 adb 授予權限。
- 確保所有檔案中的套件名稱與您專案的套件名稱相符。

---

## 6. 檔案參考表

| 檔案                | 位置                                                 | 操作                  |
|---------------------|-----------------------------------------------------|-----------------------|
| MainActivity.kt     | app/src/main/java/[your/package/name]/MainActivity.kt| 覆寫                  |
| activity_main.xml   | app/src/main/res/layout/activity_main.xml            | 覆寫                  |
| AndroidManifest.xml | app/src/main/AndroidManifest.xml                     | 新增權限行            |
| user_manual.md      | 專案根目錄                                           | 僅供參考              |

---

## 7. 其他說明
- 此應用程式相容於 Android 8（API 26）及以上版本。
- 所有功能都包含在單一活動中，方便展示。
- 更多詳細資訊請參閱 `user_manual.md`。

---
