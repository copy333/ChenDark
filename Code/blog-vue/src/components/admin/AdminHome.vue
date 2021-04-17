<template>
  <!-- 博客管理系统页 -->
  <el-container>

    <!-- 页头 -->
    <el-header>
      <AdminNBar></AdminNBar>
    </el-header>
    <!-- 页面内容区 -->
    <el-container>

      <!-- 侧边栏 -->
      <el-aside width="200px">
        <AdminAside></AdminAside>
      </el-aside>

      <!-- 中心显示区 -->
      <el-main style="padding-bottom: 80px" class="tac">
        <router-view></router-view>
      </el-main>

    </el-container>

  </el-container>
</template>

<script>
import AdminNBar from './AdminNBar.vue'
import AdminAside from './AdminAside.vue'

//给路由守卫返回是否登录成功
import { UserStatus } from "@/api/index";

export default {
  components:{  //注册组件
    AdminNBar,
    AdminAside
  }, 
  mounted(){
    
  },

  //设置路由守卫
  beforeRouteEnter(to,from,next){

    UserStatus().then((response) =>{
        //如果response.data没有声明那就是没有登录
        if(typeof(response.data) == "undefined"){
          next({name: "LoginPage"})
        }else if(response.data.code === 1){
          next();
        }else next({
          name: "LoginPage"
        })
    })
      
    //如果token存在那就放行
    /*if(localStorage.getItem('Authorization') != null){
      next()
    }else next({
      name: "LoginPage"
    })*/
  },

};
</script>

<style>
.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  /* background-color: #e9eef3; */
  background-color: rgba(0,0,0,0);
  color: #333;
}

.tac{
  position: absolute;
  left: 200px;
  right: 0;
  top: 60px;
  bottom: 0 !important
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.el-row{
  margin-top:0;
  margin-bottom: 0;
}
</style>

<style scoped>
@media screen and (max-width:1300px) {
    .el-header{
        width: 1500px;
    }
}
</style>