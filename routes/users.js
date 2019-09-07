module.exports = function (app, User) {
  var express = require('express');
  var router = express.Router();
  // GET ALL BOOKS
  router.get('/test/users', function (req, res) {
    User.find(function (err, users) {
      if (err) return res.status(500).send({ error: 'database failure' });
      res.json(users);
    })
  });

   // GET SINGLE BOOK
  router.get('/test/users/:id', function (req, res) {
    User.findOne({id: req.params.id}, function(err, user){
      if(err) return res.status(500).json({error: err});
      if(!user) return res.status(404).json({error: 'user not found'});
      res.json(user);
    })
  });

  // CREATE BOOK
  router.post('/test/users', function (req, res) {
    var user = new User();
    user.id = req.body.id;
    user.pw = req.body.pw;
    user.name = req.body.name;

    user.save(function (err) {
      if (err) {
        console.error(err);
        res.json({ result: 0 });
        return;
      }

      res.json({ result: 1 });

    });
  });
  return router;
}