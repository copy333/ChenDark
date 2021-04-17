<template>
  <!-- 文章内容显示页 -->
  <!-- banner -->
  <div class="pages-banner banner">
  </div>
  <div>
    <el-container>
      <!-- 文章目录 -->
      <el-aside width="260px">
        <div id="catalog" v-html="treeData"></div>
      </el-aside>
      <!-- 文章内容 -->
      <el-main>
        <div class="top block" @click="centerDialogVisible = true">
          <el-image :src="pageBg" fit="cover" style="width:100%; height:400px;border-radius:10px;">
          </el-image>
        </div>
        <div class="content">
          <div class="hljs" ref="hlDiv" v-html="code"></div>
        </div>
      </el-main>
    </el-container>
  </div>
    <!-- 回到顶部 -->
    <el-backtop :bottom="100" :right="0" :visibility-height="1500">
      <div
        style="{
          height: 100%;
          width: 100%;
          background-color: #f2f5f6;
          text-align: center;
          line-height: 40px;
          color: #1989fa;
        }"
      >
       <i class="el-icon-top"></i>
      </div>
    </el-backtop>

        <!-- 回到首页 -->
    <el-backtop :bottom="60" :right="0" :visibility-height="1500">
      <div
        @click="goHome"
        style="{
          height: 100%;
          width: 100%;
          background-color: #f2f5f6;
          text-align: center;
          line-height: 40px;
          color: #1989fa;
        }"
      >
       <i class="el-icon-s-home"></i>
      </div>
    </el-backtop>

    <!-- 文章信息框 -->
    <el-dialog
      title="文章信息"
      v-model="centerDialogVisible"
      width="50%"
      center>
      <p>文章标题：{{blogTitle}}</p>
      <p>文章分类：{{changType[tableData.blogType]}}</p>
      <p>文章简介：{{tableData.blogRemark}}</p>
      <p>创建时间：{{tableData.gmtCreate}}</p>
      <p>更新时间：{{tableData.gmtModified}}</p>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
        </span>
      </template>

    </el-dialog>
</template>

<script>
import marked from "marked"; //markdown编译器 npm install --save marked

import hljs from "highlight.js"; //有很多自带风格，如下是仓库地址
import "highlight.js/styles/googlecode.css"; //https://github.com/highlightjs/highlight.js/tree/master/src/styles

//导入自己写的 css样式
import "@/assets/css/codeStyle.css";

import { getBlogById, checkBlogByid,getBlogTypeMap } from "@/api/index";

import {mixin} from '@/mixins/index'

import Clipboard from "clipboard";

export default {
  mixins:[mixin],
  data() {
    return {
      tableData: {}, //博客数据
      blogTitle: '',  //文章标题
      code: "", //要转换的md内容
      catalog: [], //存储目录对象
      treeData: '',  //展示目录数据
      centerDialogVisible: false,
      changType: {},  //分类从 num 变成 String的名称显示
      pageBg: require('@/assets/img/pageBg.jpeg'),
      clipboard: ''
    };
  },
  methods: {
    showData() {
      let _this = this;
      let blogId = this.$route.params.id;
      this.blogTitle = this.$route.params.title;
      getBlogById(blogId).then((response) => {
        if (response.code === 20000) {
          _this.tableData = response.data.data;
          // _this.code = response.data.data.blogContent;

          //转md为html
          this.markdownToHtml();
         
         //使用$nextTick解决code内容没有被进一步转换为需要内容的问题
          this.$nextTick(()=>{   
            // 添加代码复制功能
            this.clipboard = new Clipboard(".copy-btn");
            this.clipboard.on("success", () => {
              this.notify("复制成功","success");
            });
            this.code =  marked(this.tableData.blogContent);
            this.getCatalog();  //生成目录
            this.changeTypeShow();
            _this.notify("点击小白熊即可查看文章信息(文章标题/简介等)","success");
          })   
        }
      });
    },
    markdownToHtml(){
      let _this = this;
      let rendererMD = new marked.Renderer();
          marked.setOptions({
            renderer: rendererMD,
            pedantic: false, //默认为false。 尽可能地兼容 markdown.pl的晦涩部分，不纠正原始模型任何的不良行为和错误。
            gfm: true, //默认为true。 允许 Git Hub标准的markdown.
            tables: true, //默认为true。 允许支持表格语法。该选项要求 gfm 为true。
            breaks: true, //默认为false。 允许回车换行。该选项要求 gfm 为true。
            smartLists: true,
            smartypants: false, //使用更为时髦的标点，比如在引用语法中加入破折号。
            xhtml: false,
                  highlight: function(str, lang) {
                    // 当前时间加随机数生成唯一的id标识
                    var d = new Date().getTime();
                    if (
                      window.performance &&
                      typeof window.performance.now === "function"
                    ) {
                      d += performance.now();
                    }
                    const codeIndex = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(
                      /[xy]/g,
                      function(c) {
                        var r = (d + Math.random() * 16) % 16 | 0;
                        d = Math.floor(d / 16);
                        return (c == "x" ? r : (r & 0x3) | 0x8).toString(16);
                      }
                    );
                    // 复制功能主要使用的是 clipboard.js
                    let html = `
                    <button class="copy-btn" 
                      style="display:block;z-index: 6;color: #ccc;background-color: #525252;
                      border-radius: 6px;font-size: 16px;width: 32px;height: 24px;padding-left: 2px;
                      padding-bottom: 26px;outline: none;" 
                    type="button" data-clipboard-action="copy" data-clipboard-target="#copy${codeIndex}">✍️</button>`;
                    
                    const linesLength = str.split(/\n/).length - 1;
                    
                    if (lang && hljs.getLanguage(lang)) {
                      // highlight.js 高亮代码
                      const preCode = hljs.highlight(lang, str, true).value;
                      html = html + preCode;
                      /* 该代码块的代码格式-如java,xml,yml等
                      if (linesLength) {
                        html = '<b class="name">' + lang + "</b>" + html;
                      }*/

                      if (linesLength) {
                        html = '<center>点击左下角按钮即可复制代码</center>' + html;
                      }

                      // 将代码包裹在 textarea 中，由于防止textarea渲染出现问题，这里将 "<" 用 "<" 代替，不影响复制功能
                      return `<pre class="hljs"><b style="height:32px;">${html}</b></pre><textarea style="position: absolute;top: -19px;left: -19px;z-index: -19;" id="copy${codeIndex}">${str.replace(
                        /<\/textarea>/g,
                        "</textarea>"
                      )}</textarea>`;
                    }
                  }
          });

          //将markdown内容进一步的转换为需要的内容
          let index = 0;
          rendererMD.heading = function (text, level) {
            //设置最大 6 级目录的改变
            if (level < 7) {
              _this.catalog.push({level:level,id:index,cont:text}) //带上是几级标题和标题内容以及 id
              return `<h${level} id="${index++}">${text}</h${level}>`;
            } else {
              return `<h${level}>${text}</h${level}>`;
            }
          };
    },
    checkUrl() {
      //判断是否存在该路由
      let _this = this;
      const loading = this.$loading({
        //开始加载loading
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
      checkBlogByid(this.$route.params.id).then((response) => {
        if (response.data.code === 1) {
          //如果数据库存在该 id的文章那就展示内容
          _this.showData();
        } else {
          _this.$router.push({ name: "404" }); //如果不存在那就去 404吧
        }
      });
      //数据加载完毕就结束loading
      loading.close();
    },
     getCatalog() { //生成目录
         for(let item of this.catalog){
           if(item.level === 1){  // h1
            this.treeData += `<h${item.level} style="font-size:20px;font-weight:bold"><a href="#${item.id}" style="text-decoration: none;color:#00BB66;">${item.cont}</a></h${item.level}>`;
           } else if(item.level === 2){
             this.treeData += `<h${item.level} style="padding-left:15px;font-size:18px;font-weight:bold"><a href="#${item.id}" style="text-decoration: none;color:#bb5500;">${item.cont}</a></h${item.level}>`;
           } else if(item.level === 3){
             this.treeData += `<h${item.level} style="padding-left:30px;font-size:18px;font-weight:bold"><a href="#${item.id}" style="text-decoration: none;color:#BB5500;">${item.cont}</a></h${item.level}>`;
           } else if(item.level === 4){
             this.treeData += `<h${item.level} style="padding-left:45px;font-size:18px;font-weight:bold"><a href="#${item.id}" style="text-decoration: none;color:#668800;">${item.cont}</a></h${item.level}>`;
           } else if(item.level === 5){
             this.treeData += `<h${item.level} style="padding-left:60px;font-size:18px;font-weight:bold"><a href="#${item.id}" style="text-decoration: none;color:#D28EFF;">${item.cont}</a></h${item.level}>`;
           } else if(item.level === 6){
             this.treeData += `<h${item.level} style="padding-left:75px;font-size:18px;font-weight:bold"><a href="#${item.id}" style="text-decoration: none;color:#E38EFF;">${item.cont}</a></h${item.level}>`;
           } else{
             this.treeData += `<h${item.level} style="padding-left:75px;font-size:18px;font-weight:bold"><a href="#${item.id}" style="text-decoration: none;color:#FF77FF;">${item.cont}</a></h${item.level}>`;
           }
         }
    },
    changeTypeShow(){ //将 博客分类 从 id 转为 name
      let _this = this;
      getBlogTypeMap().then((response) =>{
        if(response.code === 20000){
          _this.changType = response.data;
        }
      })
    },
    goHome(){ //回到首页
      this.$router.push({name: "MainPage"})
    },
    getScroll(){  //页面滚动监听事件
    //document.documentElement.scrollTop获取当前页面的滚动条纵坐标位置
       if(document.documentElement.scrollTop>=60){
        //使用obj.style.cssTest来修改嵌入式的css
        document.getElementById("catalog").style.cssText="margin-top:-60px";
      }else if(document.documentElement.scrollTop<60){
        document.getElementById("catalog").style.cssText="margin-top:0px";
      }
      //页面划到底部时，document.documentElement.scrollHeight-document.documentElement.scrollTop===742
      //判断距离底部的距离
      if(document.documentElement.scrollTop>60 && document.documentElement.scrollHeight - document.documentElement.scrollTop < 822){
        document.getElementById("catalog").style.cssText="height:calc(100% - 80px);margin-top:-60px";
      }
    }
  },
  //销毁函数
  unmounted() {
    this.clipboard.destroy();
  },
  mounted() {
    this.checkUrl();
    window.addEventListener('scroll',this.getScroll); //添加页面滚动监听
  },
  beforeRouteLeave (to, from, next) {
    // 导航离开该组件的对应路由时调用
    // 可以访问组件实例 `this`
    window.removeEventListener('scroll',this.getScroll);  //移除页面滚动监听
    next();
  }
};
</script>

<style scoped>
.hljs {
  width: 100%;
  height: 100%;
  font-size: 18px;
  padding: 10px;
  box-sizing: border-box;
  background: rgb(101, 211, 74);
  border: 2px solid black;
  text-align: left;
  line-height: normal;
}
/* 目录 */
#catalog {
  height:100%;
  width: 250px;
  position:fixed;
  overflow-y:auto; 
  overflow-x:auto;
  left:0px;
  line-height: 200%;
  text-align: left;
  margin-left: 10px;
}
.el-aside{
  background-color: rgb(51, 56, 21);
}
.el-main{
  background-color: saddlebrown;
}
/* 文章内容上面的图片 */
.top{
  line-height: normal;
}
/* 文章信息 */
.el-dialog p{
  font-size: 23px;
  font-weight: bold;
  color: green;
  margin-top: 5px;
}

.pages-banner {
  background: url("/api/resources/img/Banner/pages.jpg") center
    center / cover no-repeat;
  background-color: #49b1f5;
  z-index: -3;
}

/* 屏幕宽度小于1300px就不显示目录 */
@media screen and (max-width:1300px) {
  .el-aside{
    display: none;
  }
}
</style>