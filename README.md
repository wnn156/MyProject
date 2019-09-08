# MyProject

20190907 : node.js 에서 router를 분리 시켜야 됐다. "Router.use() requires a middleware function but got a" 에러 발생 -> 각각의 라우터 파일에 app 객체를 넘겼으나 app은 express객체였다. 즉 router.use()가 불려야 되는데 express.use()가 불려서 안된거 같음!

20190908 : ./routes/index.js -> ./routes/로 바꿈. index.js는 디폴트라서 그런지 파일명을 주면 안됐음.

20190908 : Android에서 SharedPreference를 이용하여 로그인 유지 기능을 구현했다. 모듈로 만들어 어디서든 스태틱 함수로 불러올 수 있게 하여 편하게 사용 가능했다. Node.js에서 console.log를 이용하여 로그를 찍었다.
