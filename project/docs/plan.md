# 餐廳搜尋與導航應用 — 實作計畫

## 專案概述

本專案是一個行動應用程式，提供餐廳搜尋、收藏、以及路線規劃功能。系統包含三大元件：

1. **Android 用戶端 App** — 提供搜尋、收藏、導航、路線規劃等使用者介面。
2. **後端伺服器 (Python + Flask)** — 提供資料管理、認證、商業邏輯的 REST API。
3. **資料庫 (MariaDB)** — 儲存餐廳、用戶、收藏、路線規劃等資料。

---

## 架構圖

```
[Android App] <——> [Flask 後端 API] <——> [MariaDB 資料庫]
```

- Android App 透過 HTTP REST API 與後端溝通。
- 後端透過 MariaDB 進行資料持久化。

---

## 技術棧

- **Android App**：Kotlin（建議）、Android Studio、Retrofit、Google Maps SDK
- **後端**：Python、Flask、Flask-RESTful/RESTX、SQLAlchemy、JWT 認證
- **資料庫**：MariaDB

---

## 功能拆解

- **餐廳搜尋**：依名稱、地點、類型搜尋/篩選
- **收藏**：標記/取消收藏餐廳，瀏覽收藏清單
- **導航**：顯示從用戶位置到餐廳的路線（整合 Google Maps）
- **路線規劃**：建立/編輯欲拜訪餐廳清單，依序導航
- **用戶認證**：註冊、登入、管理用戶資料

---

## 分階段實作計畫

### 第一階段：環境建置與基本功能

1. **資料庫設計**
   - 設計資料表結構，於 MariaDB 建立資料表
   - 匯入範例餐廳資料

2. **後端 API**
   - 建立 Flask 專案結構
   - 實作 API 端點：
     - `/restaurants`（GET，搜尋/篩選）
     - `/favorites`（GET、POST、DELETE）
     - `/route-plans`（GET、POST、DELETE）
     - `/auth`（註冊/登入）
   - 透過 SQLAlchemy 連接 MariaDB

3. **Android App**
   - 於 Android Studio 建立專案
   - 實作登入/註冊畫面
   - 實作餐廳搜尋 UI
   - 透過 Retrofit 串接後端 API
   - 顯示餐廳清單

### 第二階段：進階功能

4. **收藏功能**
   - App 與後端皆實作收藏/取消收藏功能
   - 顯示收藏餐廳清單

5. **導航功能**
   - 整合 Google Maps SDK
   - 顯示從用戶位置到餐廳的路線

6. **路線規劃**
   - 允許用戶建立/編輯拜訪餐廳清單
   - 後端儲存路線規劃
   - App 顯示路線規劃與每一站導航

### 第三階段：優化與測試

7. **認證與安全**
   - API 端點加上 JWT 驗證
   - 資料庫密碼加密儲存

8. **測試**
   - 後端單元測試與整合測試
   - Android App UI 與功能測試

9. **部署**
   - 後端部署（Heroku、AWS 等）
   - App 上架（Play Store）

---

## 建議與最佳實踐

- 採用 RESTful API 設計與版本控管
- 所有輸入皆需驗證與消毒
- 用戶資料與 API 端點需加強安全
- API 端點文件化（Swagger）
- 後端與 App 自動化測試
- UI 需直覺且回應迅速

---

## 資料庫範例結構

```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password_hash VARCHAR(255),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE restaurants (
    id VARCHAR(32) PRIMARY KEY,         -- 字串型主鍵
    name VARCHAR(100),
    description TEXT,
    tel VARCHAR(20),
    address VARCHAR(255),
    zipcode VARCHAR(10),
    opentime VARCHAR(100),
    map VARCHAR(255),                   -- 若需存地圖網址
    lng DOUBLE,                         -- Px
    lat DOUBLE,                         -- Py
    class VARCHAR(20),                  -- 類型/分類
    website VARCHAR(255),
    parkinginfo VARCHAR(255),
    changetime DATETIME                 -- 最後更新時間
);

CREATE TABLE favorites (
    user_id INT,
    restaurant_id INT,
    PRIMARY KEY (user_id, restaurant_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE route_plans (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(100),
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE route_plan_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    route_plan_id INT,
    restaurant_id INT,
    order_num INT,
    FOREIGN KEY (route_plan_id) REFERENCES route_plans(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);
```

---

## 下一步

1. 設計資料庫結構並建置 MariaDB
2. 建立 Flask 後端並連接資料庫
3. 開始 Android App 專案並串接後端
4. 依功能逐步實作與測試

---

*本計畫書為團隊開發路線圖，開發過程可隨時更新。*
