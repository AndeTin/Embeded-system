#TODO: Implement user registration, login, and profile endpoints
from flask_restful import Resource

class UserRegisterResource(Resource):
    def post(self):
        #TODO: Parse input, create user, hash password, save to DB
        pass

class UserLoginResource(Resource):
    def post(self):
        #TODO: Authenticate user, return JWT token
        pass
