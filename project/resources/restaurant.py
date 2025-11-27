from flask_restful import Resource
from models import Restaurant
from app import db

class RestaurantListResource(Resource):
    def get(self):
        restaurants = Restaurant.query.all()
        return [r.as_dict() for r in restaurants], 200

class RestaurantResource(Resource):
    def get(self, restaurant_id):
        restaurant = Restaurant.query.get_or_404(restaurant_id)
        return restaurant.as_dict(), 200
