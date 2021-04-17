<template>
  <!-- banner -->
  <div class="about-banner banner">
    <h1 class="banner-title">关于作者</h1>
  </div>
  <div class="about" style="margin-top:400px;">
    <div style="height:50px"></div>
    <el-row>
      <!-- 左 -->
      <el-col :span="8">
        <div class="grid-content bg-purple">
        </div>
      </el-col>
      <!-- 中 -->
      <el-col :span="8">
        <div class="grid-content bg-purple-light" style="text-align:center">
          <!-- 作者头像 -->
          <div>
            <el-image
            class="author-avatar"
            style="width: 100px; height: 100px"
            :src="authorImg"
            @click="open1"
            fit="cover"></el-image>     
          </div>
          <p>ChenDark</p>
          <p>计算机科学与技术专业</p>
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
        </div>
      </el-col>
      <!-- 右 -->
      <el-col :span="8">
        <div class="grid-content bg-purple">
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus'

export default {
  data(){
    return{
      iconqq: require("../assets/img/webapp/qq.png"),
      icongithub: require("../assets/img/webapp/github.png"),
      icongitee: require("../assets/img/webapp/gitee.png"),
      authorImg: require('@/assets/img/author.jpg'),
      open1() {
        ElMessage.success({
          message: '作者向您问好',
          type: 'success'
        });
      },
    }
  },
  methods:{
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
  }
};
</script>

<style scoped>
.about-banner {
  background: url("/api/resources/img/Banner/about.jpg") center
    center / cover no-repeat;
  background-color: #49b1f5;
}
.about{
  width: 80%;
  margin-left: 10%;
  margin-bottom: 30px;
  box-shadow: 0 0 10px rgb(58, 57, 57);
  border-radius: 13px;
}
.about:hover{
  box-shadow: 0 0 20px rgba(57, 13, 218,0.6);
  transition: all 0.7s;
}
</style>