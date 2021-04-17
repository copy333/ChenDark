<template>
  <!-- 博客后台管理的页头 -->
  <el-row>
  <el-col :span="8">
    <div class="grid-content bg-purple">
      <img :src="logo" class="logoImg" />
    </div>
  </el-col>

  <el-col :span="8"><div class="grid-content bg-purple-light"></div></el-col>

  <el-col :span="8">
    <div class="grid-content bg-purple">
      <el-dropdown style="float:right;margin-top:10px;">
      <el-button type="primary">
        登录操作<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <template #dropdown>
        <el-dropdown-menu style="background-color:white">
          <el-dropdown-item>
            <img :src="author" id="author" title="管理员" />
          </el-dropdown-item>
          <el-dropdown-item>
            <p style="color:black;text-align:center">{{name}}</p>
          </el-dropdown-item>
          <el-dropdown-item id="out" @click="out" style="color:black;">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    </div>
  </el-col>

</el-row>

</template>

<script>
import logo from '../../assets/img/logo2.png'  //引入作者LOGO

import { logout } from "@/api/index"

// 引入mixins文件夹的index.js文件
import { mixin } from "@/mixins/index";

export default {
  mixins: [mixin],
  data () {
    return {
      logo, //作者LOGO
      author: localStorage.getItem("avatar"),
      name: localStorage.getItem("nickname")
    }
  },
  methods: {
    out() { //退出登录
      let _this = this;
      logout().then((response)=>{
        if(response.data.code === 1){
          _this.$router.push('/');
          _this.notify("退出登录成功", "success");
        }
      })
    }
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
#author{
  width: 50px;
  height: 50px;
  border-radius: 50%;
  float: right;
  margin: 5px;
  box-sizing: border-box;
}
#author:hover{
  border: 1px solid red;
}
.el-dropdown {
  vertical-align: top;
}
.el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.el-button--primary,
.el-button--primary:focus,
.el-button--primary:hover{
  background-color: black;
  border-radius: 10px;
}

#out{
  font-size: 14px;
}
#out:hover{
  color: red;
}
.logoImg{
  width:200px;
  height:60px;
  float:left;
}
</style>