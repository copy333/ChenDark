<template> 
  <!-- 博客展示页 -->
  <el-container>
    <el-header> 
        <!-- 导航 -->
        <NavigationBarVue></NavigationBarVue>
    </el-header>

    <el-main>  
        <!-- css3 彩带样式-->
        <canvas id="ribbon" width="1171" height="728"
    style="display: block; position: fixed; margin: 0px; padding: 0px; border: 0px; outline: 0px; left: 0px; top: 0px; width: 100%; height: 100%; z-index: -1;"></canvas>
      <router-view></router-view>
    </el-main>
    <el-footer>
      <Footer></Footer>
    </el-footer>
  </el-container>
</template>

<script>
import NavigationBarVue from "./NavigationBar.vue";
import Footer from './Footer.vue'


export default {
  name: 'App',
  components:{
    NavigationBarVue,
    Footer
  },
}
</script>

<style scoped>
.el-header, .el-footer {
  color: #333;
  background-color: rgba(0, 0, 0, 0);
  text-align: center;
  line-height: 40px;
  padding: 0;

  z-index: 2;
}

.el-aside {
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  color: #333;
  text-align: center;
  line-height: normal;
  background-color: rgba(0, 0, 0, 0);
  padding: 0;
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

a{
  text-decoration: none;
  color: green;
}

/* css3彩带特效 */
canvas {
    -webkit-animation: to_show 4s;
    animation: to_show 4s;
}
@keyframes to_show{
  0% {
    opacity: 0;
    filter: alpha(opacity=0);
  }
100% {
  opacity: 1;
  -ms-filter: none;
  filter: none;
  }
}
</style>
