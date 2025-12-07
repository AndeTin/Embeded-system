#TODO: Implement user registration, login, and profile endpoints
import os
from dotenv import load_dotenv
from sqlalchemy import create_engine, text
from flask_restful import Resource
from flask import request
from werkzeug.security import generate_password_hash, check_password_hash
from flask_jwt_extended import create_access_token

load_dotenv()
DATABASE_URL = os.getenv("DATABASE_URL")

if not DATABASE_URL:
    raise ValueError("DATABASE_URL not found in .env")

engine = create_engine(DATABASE_URL)

def query_db(sql, params=None, fetchone=False):
    """Execute SQL query with SQLAlchemy engine."""
    with engine.connect() as conn:
        result = conn.execute(text(sql), params or {})

        # 判斷是否為 SELECT
        is_select = sql.strip().lower().startswith("select")

        if is_select:  # 只有 SELECT 才能 fetch
            if fetchone:
                return result.fetchone()
            return result.fetchall()
        else:
            # INSERT / UPDATE / DELETE → 直接 commit，不 fetch
            conn.commit()
            return None

class UserRegisterResource(Resource):
    def post(self):
        #TODO: Parse input, create user, hash password, save to DB
        #pass
        data = request.get_json()
        username = data.get("username")
        email = data.get("email")
        password = data.get("password")

        if not username or not email or not password:
            return {"message": "缺少 username、email 或 password"}, 400

        # 檢查是否已有使用者
        sql = """
            SELECT id FROM users 
            WHERE username = :username OR email = :email
        """
        existing = query_db(sql, {"username": username, "email": email}, fetchone=True)

        if existing:
            return {"message": "使用者名稱或 Email 已被註冊"}, 400

        # 密碼雜湊
        password_hash = generate_password_hash(password)

        # 寫入資料庫
        insert_sql = """
            INSERT INTO users (username, password_hash, email)
            VALUES (:username, :password_hash, :email)
        """
        query_db(insert_sql, {
            "username": username,
            "password_hash": password_hash,
            "email": email
        })

        return {"message": "註冊成功"}, 201


class UserLoginResource(Resource):
    def post(self):
        #TODO: Authenticate user, return JWT token
        #pass
        data = request.get_json()
        account = data.get("account")
        password = data.get("password")

        sql = """
            SELECT id, username, email, password_hash 
            FROM users
            WHERE username = :account OR email = :account
        """
        user = query_db(sql, {"account": account}, fetchone=True)

        if not user:
            return {"message": "帳號不存在"}, 401

        if not check_password_hash(user.password_hash, password):
            return {"message": "密碼錯誤"}, 401

        # 產生 Token
        access_token = create_access_token(identity=user.id)

        return {
            "message": "登入成功",
            "token": access_token,
            "username": user.username,
            "email": user.email
        }, 200
        
        