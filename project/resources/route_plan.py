#TODO: Implement route plan create/edit/delete/list endpoints
from flask_restful import Resource

# class RoutePlanResource(Resource):
#     def post(self):
#         #TODO: Create a new route plan for user
#         pass

#     def get(self):
#         #TODO: List user's route plans
#         pass

#     def delete(self):
#         #TODO: Delete a route plan
#         pass

import os
from sqlalchemy import create_engine,text
from dotenv import load_dotenv

def get_db_engine():
    load_dotenv()
    database_url = os.getenv("DATABASE_URL")

    if not database_url:
        raise ValueError("DATABASE_URL not set in .env file")
    
    engine = create_engine(database_url)
    return engine

# 功能
# 顯示所有路線規劃
def show_all_route_plans(user_id):
    try:
        engine = get_db_engine()
        with engine.connect() as connection:
            result = connection.execute(
                text("""SELECT name FROM route_plans WHERE user_id = :user_id"""), {"user_id":f"{user_id}"}
            )
            rows = result.fetchall()
            
            print("Route Plans List: ")
            for row in rows:
                print(row)
            print("\n")
    except Exception as e:
        print("Database connection failed: ", e)

# 顯示特定路線所存的店家
def show_route_plan_details(route_plan_id: int):
    try:
        engine = get_db_engine()
        with engine.connect() as connection:
            result = connection.execute(
                text("""SELECT restaurants.name
                        FROM route_plan_items JOIN restaurants
                        ON route_plan_items.restaurant_id = restaurants.id
                        WHERE route_plan_items.route_plan_id = :route_plan_id"""), {"route_plan_id": f"{route_plan_id}"}
            )
            rows = result.fetchall()
            
            print(f"{route_plan_id} 's route plan: ")
            for row in rows:
                print(row)
            print("\n")
    except Exception as e:
        print("Database connection failed: ", e)

show_route_plan_details(1)


# (id, route_plan_id, restaurant_id)
# 新增店家
def add_restaurant_to_route_plan(route_plan_id, restaurant_id):
    try:
        engine = get_db_engine()
        with engine.begin() as connection:
            sql_result = connection.execute(
                text("""
                    SELECT COALESCE(MAX(order_num), 0) + 1 AS next_order
                    FROM route_plan_items
                    WHERE route_plan_id = :route_plan_id
                """), {"route_plan_id": route_plan_id}
            )
            order_num = sql_result.scalar()
            connection.execute(
                text("""
                    INSERT INTO route_plan_items
                    (route_plan_id, restaurant_id, order_num)
                    VALUES
                    (:route_plan_id, :restaurant_id, :order_num)
                """), 
                {
                    "route_plan_id": route_plan_id,
                    "restaurant_id": restaurant_id,
                    "order_num": order_num
                }
            )
            
            id_result = connection.execute(text("SELECT LAST_INSERT_ID()"))
            new_id = id_result.scalar()

            return {
                "id": new_id,
                "route_plan_id": route_plan_id,
                "restaurant_id": restaurant_id,
                "order_num": order_num
            }

    except Exception as e:
        print("Database connection failed: ", e)

# 刪除店家
def remove_restaurant_from_route_plan(route_plan_id, restaurant_id):
    try: 
        engine = get_db_engine()
        with engine.begin() as connection:
            # connection.execute(
            #     text("""DELETE FROM route_plan_items WHERE route_plan_id = :route_plan_id AND restaurant_id = :restaurant_id"""), {"route_plan_id": route_plan_id, "restaurant_id": restaurant_id}
            # )
            row = connection.execute(
                text("""
                    SELECT order_num 
                    FROM route_plan_items
                    WHERE route_plan_id = :route_plan_id
                      AND restaurant_id = :restaurant_id
                """), 
                {
                    "route_plan_id": route_plan_id,
                    "restaurant_id": restaurant_id
                }
            ).fetchone()

            if not row:
                raise ValueError("Restarurant not found in this route plan")
            
            deleted_order = row.order_num

            connection.execute(
                text("""
                    DELETE FROM route_plan_items
                    WHERE route_plan_id = :route_plan_id
                      AND restaurant_id = :restaurant_id
                """), 
                {
                    "route_plan_id": route_plan_id,
                    "restaurant_id": restaurant_id
                }
            )

            connection.execute(
                text("""
                    UPDATE route_plan_items
                    SET order_num = order_num -1
                    WHERE route_plan_id = :route_plan_id
                      AND order_num > :deleted_order
                """),
                {
                    "route_plan_id": route_plan_id,
                    "deleted_order": deleted_order
                }
            )

        return {
            "message": "Restaurant remov3d from route plan",
            "route_plan_id": route_plan_id,
            "restaurant_id": restaurant_id
        }

    except Exception as e:
        print("Database connection failed: ", e)

show_all_route_plans(1)

# add_restaurant_to_route_plan(1, 'C3_382000000A_208066')

remove_restaurant_from_route_plan(1, 'C3_382000000A_403205') 