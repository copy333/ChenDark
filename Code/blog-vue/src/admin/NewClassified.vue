<template>
  <!-- 新建分类页 -->

  <!-- 已有分类 -->
  <div>
    <p style="font-size:22px">已有分类：</p>
    <el-tag v-for="item in sizeForm.typeNames"  :key="item" type="success">
      {{ item.typeName }} 
    </el-tag>
  </div>
  <!-- 新建分类 -->
  <div style="margin-top:50px;">
    <p style="font-size:22px;margin-bottom:20px;">新建分类：</p>
    <el-form ref="form" :model="sizeForm" label-width="80px" size="mini">

      <el-form-item label="分类名">
        <el-input v-model="sizeForm.typeName"></el-input>
      </el-form-item>

      <el-form-item size="medium">
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="clearAll">清空</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import {mixin} from '@/mixins/index'

import { saveBlogType,getBlogType } from '@/api/index' //更新修改的分类内容

export default {
  mixins:[mixin],
  data(){
    return {
      sizeForm: { 
        typeName: '',
        typeNames: [] //所有的已有分类名
      }
    }
  },
  methods:{
    getMessage(){ 
      let _this = this;
      getBlogType().then((response) =>{ //获取文章分类信息
        if(response.code === 20000){
          _this.sizeForm.typeNames = response.data.typeList; 

        }
      })
    },
    onSubmit() {  //提交
      let _this = this;
      let params = new Object;
      params.typeName = this.sizeForm.typeName;
      saveBlogType(params).then((response) =>{
        if(response.data.code === 1){
          _this.notify("提交成功!", "success");
          _this.$router.push("classified")
        } else{
          _this.notify("提交失败!", "error");
        }
      })
    },
    clearAll() {
      // 重置输入
      this.sizeForm.typeName = '';
      this.notify("清空成功!", "success");
    },
  },
  mounted(){
    this.getMessage();  //初始化页面后显示已有信息
  },
}
</script>

<style scoped>
.el-tag{
  font-size: 23px;
  margin: 10px;
}
</style>