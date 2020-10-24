db.createCollection( "coupon",
   {
     capped: false,
     autoIndexId: true,
     collation: {locale: "en"}
   }
)

var user = 'couponService';
var passwd = 'couponService';
var admin = db.getSiblingDB('admin');
admin.auth(user, passwd);
db.createUser({user: user, pwd: passwd, roles: ["readWrite"]});


db.coupon.insertOne(
       { "promoCode" : "https://www.litres.ru/?blabla", "validUntil" : ISODate("2020-04-02T00:00:00Z"), "description" : "Out date" },
       { writeConcern: { w : "majority", wtimeout : 100 } }
);

db.coupon.createIndex( { "promoCode": 1 }, { unique: true } )