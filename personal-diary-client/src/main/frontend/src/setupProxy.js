const {createProxyMiddleware} = require('http-proxy-middleware');
module.exports = function (app) {
    app.use(
        '/registration-api/**',
        createProxyMiddleware({
            target: 'http://localhost:8710',
            changeOrigin: true,
        })
    );
};