<template>
  <!-- 博客展示页的页头 -->
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="0"><img id="logo" src="../assets/img/logo.png"/></el-menu-item>
      
      <el-menu-item index="1">首页</el-menu-item>

      <el-submenu index="2">
        <template #title>CDN静态资源库</template>
        <el-menu-item index="2-1" >BootCDN</el-menu-item>
        <el-menu-item index="2-2" >360静态资源公共库</el-menu-item>
        <el-menu-item index="2-3" >七牛云</el-menu-item>
        <el-menu-item index="2-4" >jsDelivr</el-menu-item>
        <el-menu-item index="2-5" >字节跳动静态资源公共库</el-menu-item>
      </el-submenu>

      <el-submenu index="3">
          <template #title>4K无版权图片</template>
            <el-menu-item index="3-1" >stockvault</el-menu-item>
            <el-menu-item index="3-2" >pixabay</el-menu-item>
            <el-menu-item index="3-3" >freejpg</el-menu-item>
            <el-menu-item index="3-4" >magdeleine</el-menu-item>
            <el-menu-item index="3-5" >stocksnap</el-menu-item>
            <el-menu-item index="3-6" >pexels</el-menu-item>
            <el-menu-item index="3-7" >unsplash</el-menu-item>
      </el-submenu>

      <el-menu-item index="4">文章分类</el-menu-item>

      <el-menu-item index="5">留言</el-menu-item>

      <el-menu-item index="6">关于</el-menu-item>

      <el-menu-item index="7">时间线</el-menu-item>

      <el-menu-item index="8">文章查询</el-menu-item>

      <el-menu-item index="9">友情链接</el-menu-item>

      <el-menu-item index="10">后台管理</el-menu-item>
    </el-menu>


</template>

<script>
import {mixin} from '@/mixins/columns'

export default {
    mixins:[mixin],
    data() {
      return {
        activeIndex: '1',  //默认聚焦 index为 1 的选项
        GotoUrl: ''
      };
    },
    methods: {
      handleSelect(key) { 
         this.setColumns(key,this); //设置导航栏的栏目地址
      }
    }
  }
</script>

<style scoped>
.el-menu-demo{
  background-color: rgba(0, 0, 0, 0);
}
#logo{
  width: 200px;
  height: 60px; 
  border-radius: 10px;
  margin-left: -15px;
}
/*修改一下样式*/
.el-menu-item{
  font-size: 16px;
}

a{
  text-decoration: none;
  color: green;
}
a:hover{
  color: chocolate;
}


</style>>