// 设置博客后台侧边栏的栏目
export const mixin = {
    methods: {
        //设置侧边栏的地址
        setColumns(key, _this) {
            if (key === '1') { //后台首页
                _this.$router.push('/admin')
            }

            if (key === '2') { //文件管理
                _this.$router.push('/file')
            }

            if (key === '3') { //用户管理
                _this.$router.push('user')
            }
           
            if (key === '4-1') { //新建文章
                _this.$router.push('newArticle')
            }
            if (key === '4-2') { //文章管理
                _this.$router.push('articleArticle')
            }
            if (key === '4-3') { //分类管理
                _this.$router.push('classified')
            }
            if (key === '4-4') { //新建分类
                _this.$router.push('newClassified')
            }
            if (key === '4-5') { //更新文章内的图片地址
                _this.$router.push('updateImgUrl')
            }

            if (key === '5') { //评论管理
                _this.$router.push('comment')
            }

            if (key === '6') { //友情链接
                _this.$router.push('links')
            }

            if (key === '7') { //基本信息
                _this.$router.push('basicInfo')
            }
        },
    }
}