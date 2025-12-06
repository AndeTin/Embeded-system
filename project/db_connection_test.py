import os
from sqlalchemy import create_engine
from dotenv import load_dotenv

# Load environment variables from .env
load_dotenv()

DATABASE_URL = os.getenv('DATABASE_URL')

if not DATABASE_URL:
    print("DATABASE_URL not set in .env file.")
    exit(1)

try:
    engine = create_engine(DATABASE_URL)
    with engine.connect() as connection:
        from sqlalchemy import text
        result = connection.execute(text("SELECT * FROM restaurants LIMIT 5"))
        rows = result.fetchall()
        print("Database connection successful! Top 5 rows from restaurants table:")
        for row in rows:
            print(row)
except Exception as e:
    print("Database connection failed:", e)
