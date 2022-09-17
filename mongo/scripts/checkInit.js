db = connect("localhost:27017/admin");

db.auth(process.env.MONGO_INITDB_ROOT_USERNAME, process.env.MONGO_INITDB_ROOT_PASSWORD);

db = db.getSiblingDB("gallettabot");
if (!(db.menus.find().hasNext()))
    console.log("0");
else
    console.log("1");