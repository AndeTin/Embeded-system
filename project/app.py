from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_restful import Api
from flask_jwt_extended import JWTManager
from config import Config

# Initialize extensions
db = SQLAlchemy()
api = Api()
jwt = JWTManager()

def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)
    db.init_app(app)
    api.init_app(app)
    jwt.init_app(app)

    # Import and add resources
    from resources.restaurant import RestaurantListResource, RestaurantResource
    api.add_resource(RestaurantListResource, '/restaurants')
    api.add_resource(RestaurantResource, '/restaurants/<string:restaurant_id>')
    # Add other resources similarly...

    return app

if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)
