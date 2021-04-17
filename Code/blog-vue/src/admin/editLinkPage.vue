<template>
  <!-- 友情链接编辑页面 -->
  <div>
    <el-form ref="form" :model="sizeForm" label-width="100px" size="mini">

      <el-form-item label="ID">
        <p style="font-size:17px;color:orange;">{{sizeForm.id}}</p>
      </el-form-item>

      <el-form-item label="昵称：">
        <el-input v-model="sizeForm.nickname"></el-input>
      </el-form-item>

      <el-form-item label="头像URL:">
        <el-input v-model="sizeForm.img"></el-input>
      </el-form-item>

      <el-form-item label="简介:">
        <el-input v-model="sizeForm.descInfo"></el-input>
      </el-form-item>

      <el-form-item label="网站:">
        <el-input v-model="sizeForm.url"></el-input>
      </el-form-item>

      <el-form-item size="large">
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="clearAll">清空</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import {mixin} from '@/mixins/index'

import { updateLink } from '@/api/index' //更新修改的分类内容

export default {
  mixins:[mixin],
  data(){
    return {
      sizeForm: { //文章选择区
        id: '',
        nickname: '',
        img: '',
        url: '',
        descInfo: ''
      }
    }
  },
  methods:{
    getMessage(){ //通过传来的 分类对象 找到文章信息
      this.sizeForm.id = this.$route.params.id;  
      this.sizeForm.nickname = this.$route.params.nickname;
      this.sizeForm.descInfo = this.$route.params.descInfo;
      this.sizeForm.img = this.$route.params.img;
      this.sizeForm.url = this.$route.params.url;
    },
    onSubmit() {  //提交
      let _this = this;
      let params = new Object;
      params.id = this.sizeForm.id;  //后端要使用 updateById()方法来更新数据，所以blogType对象把id也传过去
      params.nickname = this.sizeForm.nickname;
      params.descInfo = this.sizeForm.descInfo;
      params.img = this.sizeForm.img;
      params.url = this.sizeForm.url;

      updateLink(params).then((response) =>{
        if(response.data.code === 1){
          _this.notify("提交成功!", "success");
          _this.$router.push("links")
        } else{
          _this.notify("提交失败!", "error");
        }
      })
    },
    clearAll() {
      // 重置输入
      this.sizeForm.id = '';
      this.sizeForm.nickname = '';
      this.sizeForm.img = '';
      this.sizeForm.url = '';
      this.sizeForm.descInfo = '';
      this.notify("清空成功!", "success");
    },
  },
  mounted(){
    this.getMessage();  //进入编辑页面后先通过传来的 分类对象 确定数据
  },
}
</script>

<style scoped>
</style>