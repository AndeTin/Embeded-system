# test_user.py
import os
from dotenv import load_dotenv
from werkzeug.security import check_password_hash
from resources.user import query_db  # <-- 使用 user.py 內的原生查詢功能
import sys

sys.stdout.reconfigure(encoding='utf-8')

load_dotenv()
DATABASE_URL = os.getenv("DATABASE_URL")

print("=== Testing user.py DB functions ===")

if not DATABASE_URL:
    print("❌ DATABASE_URL not found in .env")
    exit(1)

print("✔ DATABASE_URL loaded")

# -------------------------
# 1️⃣ 測試註冊（直接呼叫 SQL）
# -------------------------

test_username = "test_user_123"
test_email = "test123@example.com"
test_password = "mypassword"

print("\n--- Step 1: Testing Registration SQL ---")

# 先確認是否已存在
exists = query_db("""
    SELECT id FROM users WHERE username = :username OR email = :email
""", {"username": test_username, "email": test_email}, fetchone=True)

if exists:
    print("⚠ 測試帳號已存在，跳過新增")
else:
    from werkzeug.security import generate_password_hash
    password_hash = generate_password_hash(test_password)

    query_db("""
        INSERT INTO users (username, password_hash, email)
        VALUES (:username, :password_hash, :email)
    """, {
        "username": test_username,
        "password_hash": password_hash,
        "email": test_email
    })

    print("✔ 註冊 SQL 測試成功（已插入資料）")


# -------------------------
# 2️⃣ 測試登入 SQL
# -------------------------
print("\n--- Step 2: Testing Login SQL ---")

user = query_db("""
    SELECT id, username, email, password_hash
    FROM users
    WHERE username = :account OR email = :account
""", {"account": test_username}, fetchone=True)

if not user:
    print("❌ 無法找到測試帳號，登入 SQL 測試失敗")
    exit(1)

print("✔ 找到使用者:", user.username)

# 測試密碼驗證
if check_password_hash(user.password_hash, test_password):
    print("✔ 密碼驗證成功")
else:
    print("❌ 密碼驗證失敗")

print("\n🎉 SQL 查詢全部正常運作，user.py 功能可以正常使用！")
