from app import db

class Restaurant(db.Model):
    __tablename__ = 'restaurants'
    id = db.Column(db.String(32), primary_key=True)
    name = db.Column(db.String(100))
    description = db.Column(db.Text)
    tel = db.Column(db.String(20))
    address = db.Column(db.String(255))
    zipcode = db.Column(db.String(10))
    opentime = db.Column(db.String(100))
    map = db.Column(db.String(255))
    lng = db.Column(db.Float)
    lat = db.Column(db.Float)
    class_ = db.Column('class', db.String(20))
    website = db.Column(db.String(255))
    parkinginfo = db.Column(db.String(255))
    changetime = db.Column(db.DateTime)

    def as_dict(self):
        return {c.name: getattr(self, c.name) for c in self.__table__.columns}
