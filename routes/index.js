module.exports = function (app, Book) {
  // GET ALL BOOKS
  app.get('/test/books', function (req, res) {
    Book.find(function (err, books) {
      if (err) return res.status(500).send({ error: 'database failure' });
      res.json(books);
    })
  });


  // CREATE BOOK
  app.post('/test/books', function (req, res) {
    var book = new Book();
    book.title = req.body.name;
    book.author = req.body.author;
    book.published_date = new Date(req.body.published_date);

    book.save(function (err) {
      if (err) {
        console.error(err);
        res.json({ result: 0 });
        return;
      }

      res.json({ result: 1 });

    });
  });

  // UPDATE THE BOOK
  app.put('/api/books/:book_id', function (req, res) {
    res.end();
  });

  // DELETE BOOK
  app.delete('/api/books/:book_id', function (req, res) {
    res.end();
  });

}