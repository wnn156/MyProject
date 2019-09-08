# MyProject

20190907 : node.js 에서 router를 분리 시켜야 됐다. "Router.use() requires a middleware function but got a" 에러 발생 -> 각각의 라우터 파일에 app 객체를 넘겼으나 app은 express객체였다. 즉 router.use()가 불려야 되는데 express.use()가 불려서 안된거 같음!

20190908 : ./routes/index.js -> ./routes/로 바꿈. index.js는 디폴트라서 그런지 파일명을 주면 안됐음.
