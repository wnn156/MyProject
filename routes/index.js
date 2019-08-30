var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Hello Appengine is updated' });
  console.log('Hello Appengine is updated');
});

module.exports = router;