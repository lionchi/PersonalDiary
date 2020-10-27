const {createProxyMiddleware} = require('http-proxy-middleware');
module.exports = function (app) {
    app.use(
        '/api/**',
        createProxyMiddleware({
            target: process.env.API_URL,
            changeOrigin: true,
        })
    );
    app.use(
        '/mail-api/**',
        createProxyMiddleware({
            target: process.env.API_URL,
            changeOrigin: true,
        })
    );
    app.use(
        '/user-api/**',
        createProxyMiddleware({
            target: process.env.API_URL,
            changeOrigin: true,
        })
    );
    app.use(
        '/diary-api/**',
        createProxyMiddleware({
            target: process.env.API_URL,
            changeOrigin: true,
        })
    );
    app.use(
        '/auth',
        createProxyMiddleware({
            target: process.env.API_URL,
            changeOrigin: true,
        })
    );
};
