<template>
  <!-- 从分类管理页到来的编辑分类页 -->
  <div>
    <el-form ref="form" :model="sizeForm" label-width="100px" size="mini">

      <el-form-item label="ID">
        <p style="font-size:17px;color:orange;">{{sizeForm.typeId}}</p>
      </el-form-item>

      <el-form-item label="分类名：">
        <el-input v-model="sizeForm.typeName"></el-input>
      </el-form-item>

      <el-form-item label="图片URL:">
        <el-input v-model="sizeForm.typeImg"></el-input>
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

import { updateBlogType } from '@/api/index' //更新修改的分类内容

export default {
  mixins:[mixin],
  data(){
    return {
      sizeForm: { //文章选择区
        typeId: '',
        typeName: '',
        typeImg: ''
      }
    }
  },
  methods:{
    getMessage(){ //通过传来的 分类对象 找到文章信息
      this.sizeForm.typeId = this.$route.params.typeId;  
      this.sizeForm.typeName = this.$route.params.typeName;
      this.sizeForm.typeImg = this.$route.params.typeImg;
    },
    onSubmit() {  //提交
      let _this = this;
      let params = new Object;
      params.typeId = this.$route.params.typeId;  //后端要使用 updateById()方法来更新数据，所以blogType对象把id也传过去
      params.typeName = this.sizeForm.typeName;
      params.typeImg = this.sizeForm.typeImg;

      updateBlogType(params).then((response) =>{
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
      this.sizeForm.typeId = '';
      this.sizeForm.typeName = '';
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