module.exports = {
  //不需要生产环境的 source map加速生产环境构建，大大减小打包后的体积
  productionSourceMap: false,
  //解决 Invalid Host header问题（服务器域名访问出现的问题），
  //即 nginx代理但无法访问，跳过vue的host检查
  devServer: {
    disableHostCheck: true,
    //将 vue的 /api 的请求都转发到 服务器的8001端口
    proxy: {
      "/api": {
        target: "http://localhost:8001",
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""
        }
      }
    },
  },
  //将 build 后的文件输出到 dist 目录
  outputDir: 'dist',
}