<template>
  <!-- 时间线页面 -->
  <!-- banner -->
  <div class="timeline-banner banner">
    <h1 class="banner-title">时间线</h1>
  </div>

  <div class="main" style="margin-top:400px;">
    <div class="block">
      <el-timeline>
        <el-timeline-item
          v-for="(activity, index) in activities"
          :key="index"
          color="#45f"
          size="large"
          @click="TitleClick(activity.blogId,activity.blogTitle)"
          :timestamp="activity.gmtCreate">
          {{activity.blogTitle}}
        </el-timeline-item>
      </el-timeline>
    </div>
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
import {getAllBlogBaseInfo} from '@/api/index'
export default {
  data() {
    return {
      activities: [],
    };
  },
  methods:{
    go(){
      let _this = this;
      getAllBlogBaseInfo().then((response)=>{
        if(response.code === 20000){
          _this.activities = response.data.list;
        }
      })
    },
    TitleClick(id,title){ //文章点击事件，跳转至文件显示页
      this.$router.push({path: `/pages/${title}/${id}`});//将博客id和title做为参数传输
    },
  },
  mounted(){
    this.go();
  }
};
</script>

<style scoped>
.timeline-banner {
  background: url("/api/resources/img/Banner/timeline.jpg") center
    center / cover no-repeat;
  background-color: #49b1f5;
}

.main{
  margin: 100px;
  background-color: rgba(62, 187, 204, 0.267);
  box-shadow: 0 0 10px rgb(0, 0, 0);
  border-radius: 20px;
  padding: 20px;
}
.block{
  margin: 20px;
  padding-left: 0;
}
.el-timeline-item{
  font-size: 23px;
  padding-bottom: 40px;
}

.el-timeline-item:hover{
  background-color: white;
  cursor: pointer;
}

/* 当宽度小于 1050px时*/
@media screen and (max-width:1050px){
.main{
  background-color: rgb(99, 202, 168);
}
}
</style>