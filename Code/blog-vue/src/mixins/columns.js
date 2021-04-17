// 设置博客展示页的导航栏的栏目
export const mixin = {
    methods: {
         //设置导航栏的栏目地址
         setColumns(key, _this) {
            if (key === '0' || key === '1') { //首页
                 _this.$router.push({
                     name: 'MainPage'
                 })
            }

            if (key === '2-1') { //BootCDN
                window.open("https://www.bootcdn.cn/", "_Blank")
            }
            if (key === '2-2') { //360静态资源公共库
                window.open("https://cdn.baomitu.com/", "_Blank")
            }
            if (key === '2-3') { //七牛云
                window.open("https://www.staticfile.org/", "_Blank")
            }
            if (key === '2-4') { //jsDelivr
                window.open("https://www.jsdelivr.com/", "_Blank")
            }
            if (key === '2-5') { //字节跳动静态资源公共库
                window.open("http://cdn.bytedance.com/", "_Blank")
            }

            if (key === '3-1') { //stockvault
                window.open("https://www.stockvault.net/", "_Blank")
            }
            if (key === '3-2') { //pixabay
                window.open("https://pixabay.com/zh/", "_Blank")
            }
            if (key === '3-3') { //freejpg
                window.open("https://en.freejpg.com.ar/", "_Blank")
            }
            if (key === '3-4') { //magdeleine
                window.open("https://magdeleine.co/", "_Blank")
            }
            if (key === '3-5') { //stocksnap
                window.open("https://stocksnap.io/", "_Blank")
            }
            if (key === '3-6') { //pexels
                window.open("https://www.pexels.com/zh-cn/", "_Blank")
            }
            if (key === '3-7') { //unsplash
                window.open("https://unsplash.com/", "_Blank")
            }

            if (key === '4') { //文章分类
                _this.$router.push({
                    name: 'Classification'
                })
            }
            if (key === '5') { //留言页面
                _this.$router.push({
                    name: 'MessagePage'
                })
            }
            if (key === '6') { //关于页面
                _this.$router.push({
                    name: 'AboutPage'
                })
            }
            if (key === '7') {  //时间线
                _this.$router.push({
                    name: 'Timeline'
                })
            }
            if (key === '8') { //文章查询
                _this.$router.push({
                    name: 'SearchPage'
                })
            }
            if (key === '9') { //友情链接
                _this.$router.push({
                    name: 'LinkPage'
                })
            }
            if (key === '10') { //后台管理
                _this.$router.push({
                    name: 'LoginPage'
                })
            }
         },
    }
}