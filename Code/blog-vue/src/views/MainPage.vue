<template>
  <!-- 首页内容 -->

      <!-- 首页横幅（banner）样式 -->
    <div class="home-banner">
      <div class="banner-container">
        <!-- 联系方式 -->
        <h1 class="blog-title">
          ChenDark的个人博客
        </h1>
        <!-- 一言 -->
        <div class="blog-intro">
          <span style="font-size:24px">业精于勤荒于嬉，行成于思毁于随。</span>
          <span class="typed-cursor">|</span>
        </div>
      </div>
      <!-- 向下滚动 -->
      <div class="scroll-down" @click="scrollDown">
        <i color="#fff" class="el-icon-bottom scroll-down-effects"></i>
      </div>
    </div>

  <!-- 公告 -->
  <div class="tip">
    <p style="text-align:center;font-size:26px;color:blue;">生命不是要超越别人，而是要超越自己。</p>
  </div>

  <!-- 文章展示区 -->
  <div class="left">
    <el-card class="box-card">
      <div v-for="item in tableData[currentPage-1]" :key="item" class="item">
          <el-row>
            <!-- 列表左的图片显示区 -->
            <el-col :span="8">
              <div class="grid-content bg-purple">
                <div class="block" @click="imgClick(item.blogId,item.blogTitle)">
                  <el-image :src="item.blogImage" fit="cover"></el-image>
                </div>
              </div>
              </el-col>
              <!-- 列表右的文章概述区 -->
            <el-col :span="16">
              <div class="grid-content bg-purple-light">
                <!-- 文章标题 -->
                <div class="articleTitle">
                    <el-tag type="danger" effect="dark" style="font-size:18px;margin-right:10px;">
                        {{ changType[item.blogType] }}
                    </el-tag>
                    <span @click="imgClick(item.blogId,item.blogTitle)" style="font-weight:bold;cursor: pointer;">{{ item.blogTitle }}</span>
                </div>
                <!-- 文章简介 -->
                <div class="articleTntroduction">
                  <p>{{ item.blogRemark }}</p>
                </div>
                <!-- 文章信息 -->
                <div class="articleInformation">
                  <div style="float:left">
                    创建时间：{{ item.gmtCreate }}
                  </div>
                  <div style="float:right" class="update">
                    更新时间：{{ item.gmtModified}}
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
      </div>
    </el-card>

    <!-- 分页 -->
    <div style="margin-bottom:30px;margin-top:30px;">
      <el-pagination
        background
        :page-size="pageSize"
        layout="prev, pager, next"
        :current-page="currentPage" 
        :total="allPage"
        @current-change="handleCurrentChange">
      </el-pagination>
    </div>
  </div>

  <div class="right">
    <el-card class="box-card">
      <!-- 作者信息 -->
      <div style="margin-bottom:10px;">
        <!-- 作者头像 -->
        <div>
          <el-image
          class="author-avatar"
          style="width: 100px; height: 100px"
          :src="authorImg"
          @click="open1"
          fit="cover"></el-image>     
        </div>
        <p style="margin:5px">ChenDark</p>
        <p style="margin:5px">计算机科学与技术专业</p>
        <!-- 收藏按钮 -->
        <a 
          class="collection-btn" 
          @click="open2">
          <i color="#fff" class="el-icon-s-management"></i>
          加入书签
        </a>
      </div>

      <!-- 联系方式 -->
      <div class="card-info-social">
        <el-image
          class="webapp"
          :src="iconqq"
          fit="cover"
          @click="goqq"
        ></el-image>
        <el-image
          class="webapp"
          :src="icongithub"
          fit="cover"
          @click="gogithub"
        ></el-image>
        <el-image
          class="webapp"
          :src="icongitee"
          fit="cover"
          @click="gogitee"
        ></el-image>
      </div>
    </el-card>

    <!-- 网站信息 -->
    <el-card class="box-card info-card">
      <div class="web-info-title">
        <i class="el-icon-connection"></i>
        网站资讯
      </div>
      <div class="web-info-cont">
        <span>运行时间:    {{ time }}</span>
      </div>
      <el-image
        :src="plan"
        fit="cover">
      </el-image>
    </el-card>
  </div>

  <!-- 回到顶部 -->
  <el-backtop :bottom="100" :right="0" :visibility-height="800">
    <div
      style="{
        height: 100%;
        width: 100%;
        background-color: #f2f5f6;
        box-shadow: 0 0 9px rgb(50,40,80);
        text-align: center;
        line-height: 40px;
        color: #1989fa;
      }"
    >
      <i class="el-icon-top"></i>
    </div>
  </el-backtop>
</template>

<script>
import {getBlogListNotCont,getBlogTypeMap} from '@/api/index'

import { ElMessage } from 'element-plus'

export default {
  data(){
    return{
      plan: require("../assets/img/plan.gif"),
      time: "",
      open1() {
        ElMessage.success({
          message: '博主向您问好',
          type: 'success'
        });
      },
      open2() {
        ElMessage.success({
          message: '按CTRL+D 键即可将本页加入书签',
          type: 'success'
        });
      },
      iconqq: require("../assets/img/webapp/qq.png"),
      icongithub: require("../assets/img/webapp/github.png"),
      icongitee: require("../assets/img/webapp/gitee.png"),
      //imgs: ["http://192.168.68.116:8001/resources/img/lbt/l1.jpg","http://192.168.68.116:8001/resources/img/lbt/l2.jpg","http://192.168.68.116:8001/resources/img/lbt/l3.jpg"],
      pageSize: 6, //每页显示多少文章，为 6 表示显示六篇文章
      allPage: 0,  //总共多少文章，记录数量
      currentPage: 1, //当前页数，为 1 表示当前是第一页
      tableData: [],  //后台返回的所有内容
      changType: {},  //分类从 num 变成 String的名称显示
      authorImg: require('@/assets/img/author.jpg'),
      dialogVisible: false,
      dialogVisible2: false
    }
  },
  methods:{
    handleCurrentChange(val){ //分页新增内容 - 获取当前页
      this.currentPage = val;
    },
    imgClick(id,title){ //文章点击事件，跳转至文件显示页
      this.$router.push({path: `/pages/${title}/${id}`});//将博客id和title做为参数传输
    },
    getAllData(){
      let _this = this;
      getBlogListNotCont().then((response) =>{
        if(response.code === 20000){
          _this.allPage = response.data.data.length; //后台返回的所有内容总共多少文章
          for(let i = 0;i < response.data.data.length;i += _this.pageSize){ //按照pageSize等分文章数据
              _this.tableData.push(response.data.data.slice(i,i + _this.pageSize))  //reverse()可以倒序，slice()可以等分
          }
        }
      })
    },
    changeTypeShow(){ //将 博客分类 从 id 转为 name
      let _this = this;
      getBlogTypeMap().then((response) =>{
        if(response.code === 20000){
          _this.changType = response.data;
        }
      })
    },
    scrollDown() {
      window.scrollTo({
        behavior: "smooth",
        top: document.documentElement.clientHeight
      });
    },
    //点击了qq图标....
    goqq(){
      window.open("http://wpa.qq.com/msgrd?v=3&uin=295283059&site=qq&menu=yes");
    },
    gogithub(){
      window.open("https://github.com/ChenDark");
    },
    gogitee(){
      window.open("https://gitee.com/chendark");
    },
    //计算网站运行时间
    runTime() {
      /*
      1~12月的英文分别是：
        January、February、March 、April 、May 、 June 、July 、August、September、 October、November、December
      */
      var timeold =
        new Date().getTime() - new Date("October 13,2019 22:00:00").getTime();
      var msPerDay = 24 * 60 * 60 * 1000;
      var daysold = Math.floor(timeold / msPerDay);
      var str = "";
      var day = new Date();
      str += daysold + "天";
      str += day.getHours() + "时";
      str += day.getMinutes() + "分";
      str += day.getSeconds() + "秒";
      this.time = str;
    }
  },
  mounted(){
    this.getAllData();//页面初始化完后获取博客数据
    this.changeTypeShow();  //id:name

    //添加时间计算定时器
    this.timer = setInterval(this.runTime, 1000);

  },
  //离开路由前先销毁 定时器
  beforeRouteLeave (to, from, next) {
      clearInterval(this.timer)
      next();
  }

}
</script>

<style scope>

.left{
  width: 70%;
  margin-left: 4%;
  margin-top: 60px;
  float: left;

}
.right{
  width: 20%;
  margin-top: 60px;
  margin-right: 4%;
  float: right;
  line-height: 160%;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}


.item {
  margin: 18px 0;
}

/* 公告 */
.tip{
  box-shadow: 0 0 10px  green;
  border-radius: 5px;
}

/* card */
.box-card{  /* card的容器 */
  /* margin: 60px; */
  box-shadow: 0 0 10px  blue;
  border-radius: 10px;
}

.el-row {
  margin-bottom: 20px;
}
/*.bg-purple{ /* card左侧框 
  background-color: green;
}*/
.bg-purple .el-image img{ /* 文章图片 */
  height: 220px;
  border-radius: 10px;
}

.bg-purple-light { /* card右侧框 */
  text-align: left;
  line-height: normal; /* el-main自动设置了行高为 160px */
  margin-left: 20px;
}
.bg-purple-light .articleTitle{ /*文章标题 */
  height: 40px;
  font-size: 24px;
  color:green;
  /* html中将不能完全显示的文字用...代替 */
  text-overflow:ellipsis; 
  white-space:nowrap; 
  overflow:hidden;
}
.bg-purple-light .articleTntroduction{ /*文章简介 */
  height: 150px;
  font-size: 18px;
  color:rgb(103, 34, 124);
  font-weight:bold;

    /* html5 让显示不完的文字自动省略号 */
    -webkit-line-clamp: 7; /* 限制显示的文本的行数为3*/
    -webkit-box-orient: vertical; /*水平排列<p>，使其不纵向显示*/
    word-break: break-all; /*使<p>中的文字换行*/
    overflow: hidden; /*超出的文字部分隐藏*/
    text-overflow: ellipsis; /*超出的文字部分用...来显示*/
    display: -webkit-box; /*将<p>变成高度定死，宽度自适应的行内块元素*/
}
.bg-purple-light .articleInformation{ /*文章信息 */
  height: 30px;
  font-size: 16px;
  color:blue;
  font-weight:bold;
}
.grid-content{  /* card内容样式（包含左右侧） */
  height: 220px;
}

/* 首页公告 */
.tip{
  margin-bottom: -20px;
  padding: 5px;

  /* 撑开屏幕，配合横幅（banner），100vh即屏幕可见高度的100% */
  margin: calc(100vh) auto 0 auto;
}

/* 轮播图下面的选择条 */
.el-carousel:last-child ul{
  display: none;
}

/* 首页banner样式 */
.home-banner {
  position: absolute;
  top: 0px;
  left: 0;
  right: 0;
  height: 100vh;
  background: url("/api/resources/img/Banner/home.jpg")
    center center / cover no-repeat;
  background-color: #49b1f5;
  background-attachment: fixed;
  text-align: center;
  color: #fff !important;
  animation: header-effect 1s;
}
.banner-container {
  margin-top: 43vh;
  line-height: 1.5;
  color: #eee;
}
.blog-contact a {
  color: #fff !important;
}

@keyframes header-effect{
  0% {
    opacity: 0;
    filter: alpha(opacity=0);
    transform: translateY(-50px);
  }
  100% {
    opacity: 1;
    filter: none;
    transform: translateY(0);
  }
}

.blog-title{
  font-size: 46px;
}

/* 首页下拉图标 */
.scroll-down {
  cursor: pointer;
  position: absolute;
  bottom: 10px;
  width: 100%;
}
.scroll-down i {
  font-size: 2rem;
}
.scroll-down-effects {
  color: #eee !important;
  text-align: center;
  text-shadow: 0.1rem 0.1rem 0.2rem rgba(0, 0, 0, 0.15);
  line-height: 1.5;
  display: inline-block;
  text-rendering: auto;
  -webkit-font-smoothing: antialiased;
  animation: scroll-down-effect 1.6s infinite;
}
@keyframes scroll-down-effect {
  0% {
    opacity: 0.4;
    filter: alpha(opacity=40);
  }
  50% {
    margin-bottom: 16px;
    opacity: 1;
    filter: none;
  }
  100% {
    
    opacity: 0.4;
    filter: alpha(opacity=40);
  }
}

/* 首页封面文字右边的光标 */
.typed-cursor{
  opacity: 1;
  animation: blink 0.7s infinite;
  font-size: 26px;
  margin-left: 10px;
}

@keyframes blink{
  0%{
    opacity: 1;
  }
    
  50%{
    opacity: 0;
  }
    
  100%{
    opacity: 1;
  } 
}

/* 博客首页右边的加入书签 */
.collection-btn {
  text-align: center;
  z-index: 1;
  font-size: 14px;
  position: relative;
  display: block;
  background-color: #49b1f5;
  color: #fff !important;
  height: 32px;
  line-height: 32px;
  transition-duration: 1s;
  transition-property: color;
  cursor: pointer;
}
.collection-btn:before {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: -1;
  background: #ff7242;
  content: "";
  transition-timing-function: ease-out;
  transition-duration: 0.5s;
  transition-property: transform;
  transform: scaleX(0);
  transform-origin: 0 50%;
}
.collection-btn:hover:before {
  transition-timing-function: cubic-bezier(0.45, 1.64, 0.47, 0.66);
  transform: scaleX(1);
}
/* 作者头像 */
.author-avatar {
  transition: all 0.7s;
  cursor: pointer;
}
.author-avatar:hover {
  transform: rotate(360deg);
}

/* qq gitee github ... */
.webapp{
  width:36px;
  height:36px;
  margin-right:10px;
  cursor: pointer;
}

/* 网站首页右边的小卡片 */
.info-card{
  line-height: 20px;
  margin-top: 30px;
}
.info-card .web-info-title{
  float: left;
}
.info-card .web-info-cont{
  margin-top: 10px;
  font-size: 15px;
  float: left;
}
</style>
