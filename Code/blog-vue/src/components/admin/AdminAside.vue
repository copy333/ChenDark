<template>
  <!-- 后台系统的侧边栏组件 -->
  <el-row class="tac">
    <el-col>
      <el-menu
        :uniqueOpened="true"
        default-active="1"
        @select="handleSelect"
        class="el-menu-vertical-demo"
        background-color="#545c64"
        text-color="#BDC4CE"
        active-text-color="#ffd04b">

        <el-menu-item index="1">
          <i class="el-icon-s-home"></i>
          <template #title>后台首页</template>      
        </el-menu-item>

        <el-menu-item index="2">
          <i class="el-icon-setting"></i>
          <template #title>文件管理</template>
        </el-menu-item>

        <el-menu-item index="3">
          <i class="el-icon-s-custom"></i>
          <template #title>用户管理</template>
        </el-menu-item>

        <el-submenu index="4">
          <template #title>
            <i class="el-icon-location"></i>
            <span>博客管理</span>
          </template>
            <el-menu-item index="4-1">新建文章</el-menu-item>
            <el-menu-item index="4-2">文章管理</el-menu-item>
            <el-menu-item index="4-3">分类管理</el-menu-item>
            <el-menu-item index="4-4">新建分类</el-menu-item>
            <el-menu-item index="4-5">更新地址</el-menu-item>
        </el-submenu>

        <el-menu-item index="5">
          <i class="el-icon-chat-dot-square"></i>
          <template #title>评论管理</template>
        </el-menu-item>

        <el-menu-item index="6">
          <i class="el-icon-paperclip"></i>
          <template #title>友情链接</template>
        </el-menu-item>

        <el-menu-item index="7">
          <i class="el-icon-mobile"></i>
          <template #title>基本信息</template>
        </el-menu-item>
       
      </el-menu>
    </el-col>
  </el-row>
</template>

<script>
import {mixin} from '../../mixins/adminColumns'

export default {
  mixins:[mixin],
  methods: {
      handleSelect(key) { 
        this.setColumns(key,this); //设置管理页的侧边栏的地址
    }
  }
}
</script>

<style scoped>
/* 设置侧边栏占页面总高 */
.tac{
  width: 200px;
  position: absolute;
  left: 0;
  top: 60px;
  bottom: -20px;
  background-color: #000000;
  overflow: hidden;
}
/*解决侧边栏右边一半细一半粗的情况*/
ul{    
  height: 100%;
}
</style>