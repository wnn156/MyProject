// [LOAD PACKAGES]
var express     = require('express');
var app         = express();

var bodyParser  = require('body-parser');
var mongoose    = require('mongoose');
var Post = require('./models/post');
var User = require('./models/user');

// [CONFIGURE APP TO USE bodyParser]
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// [CONFIGURE SERVER PORT]
var port = process.env.PORT || 8080;

// [CONFIGURE ROUTER]
var routerPost = require('./routes/index.js')(app, Post);
var routerUser = require('./routes/users.js')(app, User);
app.use('/', routerPost);
app.use('/users', routerUser);

// [RUN SERVER]
var server = app.listen(port, function(){
  console.log("Express server has started on port " + port)
});



var mongoose = require('mongoose');

// ......

// [ CONFIGURE mongoose ]

// CONNECT TO MONGODB SERVER
var db = mongoose.connection;
db.on('error', console.error);
db.once('open', function(){
    // CONNECTED TO MONGODB SERVER
    console.log("Connected to mongod server");
});

mongoose.connect('mongodb://localhost:27017/test');

module.exports = app;