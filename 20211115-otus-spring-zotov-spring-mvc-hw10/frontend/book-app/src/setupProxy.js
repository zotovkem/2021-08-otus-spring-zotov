const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(createProxyMiddleware("/api/authors", {
        target: "http://localhost:8080",
        changeOrigin: true
    }));

    console.log("Proxy")
}
