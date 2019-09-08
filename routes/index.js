module.exports = function (app, Post) {
  var express = require('express');
  var router = express.Router();

  // GET ALL BOOKS
  router.get('/test/posts', function (req, res) {
    Post.find(function (err, posts) {
      if (err) return res.status(500).send({ error: 'database failure' });
      console.log("\n" + posts + "\n" );
      res.json(posts);
    })
  });
  // CREATE BOOK
  router.post('/test/posts', function (req, res) {
    var post = new Post();
    post.title = req.body.title;
    post.author = req.body.author;
    post.body = req.body.body;

    post.save(function (err) {
      if (err) {
        console.error(err);
        res.json({ result: 0 });
        return;
      }

      console.log("\n" + post + "\n" );
      res.json({ result: 1 });

    });
  });
  return router;
}


  // // GET SINGLE BOOK
  // app.get('/api/books/:book_id', function (req, res) {
  //   Book.findOne({ _id: req.params.book_id }, function (err, book) {
  //     if (err) return res.status(500).json({ error: err });
  //     if (!book) return res.status(404).json({ error: 'book not found' });
  //     res.json(book);
  //   })
  // });

  // // GET BOOKS BY AUTHOR
  // app.get('/api/books/author/:author', function (req, res) {
  //   Book.find({ author: req.params.author }, { _id: 0, title: 1, published_date: 1 }, function (err, books) {
  //     if (err) return res.status(500).json({ error: err });
  //     if (books.length === 0) return res.status(404).json({ error: 'book not found' });
  //     res.json(books);
  //   })
  // });

  // // UPDATE THE BOOK
  // app.put('/api/books/:book_id', function (req, res) {
  //   Book.findById(req.params.book_id, function (err, book) {
  //     if (err) return res.status(500).json({ error: 'database failure' });
  //     if (!book) return res.status(404).json({ error: 'book not found' });

  //     if (req.body.title) book.title = req.body.title;
  //     if (req.body.author) book.author = req.body.author;
  //     if (req.body.published_date) book.published_date = req.body.published_date;

  //     book.save(function (err) {
  //       if (err) res.status(500).json({ error: 'failed to update' });
  //       res.json({ message: 'book updated' });
  //     });

  //   });

  // });

  // // UPDATE THE BOOK (ALTERNATIVE)
  // app.put('/api/books/:book_id', function (req, res) {
  //   Book.update({ _id: req.params.book_id }, { $set: req.body }, function (err, output) {
  //     if (err) res.status(500).json({ error: 'database failure' });
  //     console.log(output);
  //     if (!output.n) return res.status(404).json({ error: 'book not found' });
  //     res.json({ message: 'book updated' });
  //   })
  // });