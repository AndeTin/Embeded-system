from flask_restful import Resource
# from models import Restaurant

class RestaurantListResource(Resource):
    def get(self):
        restaurants = Restaurant.query.all()
        return [r.as_dict() for r in restaurants], 200

class RestaurantResource(Resource):
    def get(self, restaurant_id):
        restaurant = Restaurant.query.get_or_404(restaurant_id)
        return restaurant.as_dict(), 200
    
import os
from sqlalchemy import create_engine, text
from dotenv import load_dotenv

def get_db_engine():
    load_dotenv()
    database_url = os.getenv("DATABASE_URL")

    if not database_url:
        raise ValueError("DATABASE_URL not set in .env file")
    
    engine = create_engine(database_url)
    return engine

# 餐廳名稱搜尋
def restaurant_name(name):
    try:
        engine = get_db_engine()
        with engine.connect() as connection:
            result = connection.execute(
                text("""SELECT name FROM restaurants WHERE name LIKE :name"""), {"name": f"%{name}%"}
            )
            rows = result.fetchall()

            print("Database成功")
            print("Name Result: ")
            for row in rows:
                print(row)
            print("\n")

    except Exception as e:
        print("Database connection failed: ", e)

restaurant_name('火鍋')

# 餐廳地點搜尋
def restaurant_location(address):
    try:
        engine = get_db_engine()
        with engine.connect() as connection:
            result = connection.execute(
                text("""SELECT name FROM restaurants WHERE address LIKE :address"""), {"address": f"%{address}%"}
            )
            rows =  result.fetchall()

            print('Location Result: ')
            for row in rows:
                print(row)

    except Exception as e:
        print("Database connection failed: ", e)

restaurant_location('板橋')