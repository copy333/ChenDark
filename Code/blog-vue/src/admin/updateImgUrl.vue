<template>
  <!-- 更新文章内的图片地址 -->
  <div>
    <!-- 工作区 -->
    <p class="title">修改文章封面的图片地址(这个地址是文章封面图片所在目录)：</p>
    <p class="title" style="color:red">案例：https://xxx/xxx/</p>
    <el-form :inline="true"  class="demo-form-inline">
      <el-form-item label="新服务器地址">
        <el-input v-model="pageUrl" placeholder="如：https://www.baidu.com/PageImg/" style="width:600px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmitPage">提交</el-button>
      </el-form-item>
    </el-form>

  <!-- 第二种 -->
    <p class="title" style="color:red">案例：https://xxx/xxx/xxx/</p>
    <el-form :inline="true"  class="demo-form-inline">
      <el-form-item label="新服务器地址">
        <el-input v-model="pageUrlP" placeholder="如：https://www.baidu.com/resources/PageImg/" style="width:600px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmitPageP">提交</el-button>
      </el-form-item>
    </el-form>

    <div class="line"></div>

    <!-- 工作区 -->
    <p class="title">修改Markdown文章内容里的图片地址(这个地址是文章图片仓库所在目录)：</p>
    <p class="title" style="color:red">案例：https://xxx/xxx/</p>
    <el-form :inline="true"  class="demo-form-inline">
      <el-form-item label="新服务器地址">
        <el-input v-model="url" placeholder="如：https://www.baidu.com/ContentImg/" style="width:600px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>

    <p class="title" style="color:red">案例：https://xxx/xxx/xxx/</p>
    <el-form :inline="true"  class="demo-form-inline">
      <el-form-item label="新服务器地址">
        <el-input v-model="urlP" placeholder="如：https://www.baidu.com/resources/ContentImg/" style="width:600px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmitP">提交</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div class="line"></div>

    <div class="UpdateUrl-tip">
      <p style="font-size:26px;color:blue;margin-bottom:10px;"><i class="el-icon-info"></i>教程：</p>
      <span>如果服务器的图片地址是<strong>http://xxx/xxx/1.png</strong>，那么 <strong>http://</strong>后有两个<strong>/</strong>，那就选择<strong>第一个案例https://www.baidu.com/PageImg/</strong>在新服务器地址框里输入：<strong>https://www.baidu.com/PageImg/</strong>。后台自动将<strong>https://xxx/xxx/1.png</strong>变成<strong>https://www.baidu.com/PageImg/1.png</strong>，如果输入的是：<strong>https://www.baidu.com/PageImg/demo</strong>那么地址就会变成<strong>https://www.baidu.com/PageImg/demo</strong>。</span>

      <p style="font-size:26px;color:blue;margin-bottom:10px;margin-top:20px;"><i class="el-icon-s-flag"></i>原理：</p>
      <span>后台是Java使用正则配匹<strong>http://xxx/xxx/</strong>这个内容来达到替换成新内容的功能，所有<strong>http://</strong>后面有几个 <strong>/</strong> 要看清楚。</span>


      <p style="font-size:26px;color:blue;margin-bottom:10px;margin-top:20px;"><i class="el-icon-s-opportunity"></i>说明：</p>
      <span>该页提供了<strong>修改文章封面的图片地址</strong>和<strong>修改Markdown文章内容里的图片地址(如Markdown里的 ![](http://xxx/1.png))</strong>两个功能，</span>
  </div>
</template>

<script>
import {updateImgUrl,updateImgUrlP, updatePageImgUrl,updatePageImgUrlP} from '@/api/index'
import {mixin} from '@/mixins/index'

export default {
  mixins:[mixin],
  data() {
      return {
        url: '', //新服务器（存储文章图片的服务器）地址
        urlP: '',
        pageUrl: '', //文章封面地址
        pageUrlP: ''
      }
    },
    methods: {
      onSubmit() {  //markdown文章内地址 - 配匹两个 /
        let _this = this;
        let param = new Object();
        param.url = this.url;
        updateImgUrl(param).then((response) =>{
          if(response.data.code === 1){
            _this.notify("修改成功","success");
          }
        })
      },
      onSubmitPage() {  //文章封面地址 - 配匹两个 /
        let _this = this;
        let param = new Object();
        param.url = this.pageUrl;
        updatePageImgUrl(param).then((response) =>{
          if(response.data.code === 1){
            _this.notify("修改成功","success");
          }
        })
      },
      onSubmitP() {  //markdown文章内地址 - 配匹三个 /
        let _this = this;
        let param = new Object();
        param.url = this.urlP;
        updateImgUrlP(param).then((response) =>{
          if(response.data.code === 1){
            _this.notify("修改成功","success");
          }
        })
      },
      onSubmitPageP() {  //文章封面地址 - 配匹三个 /
        let _this = this;
        let param = new Object();
        param.url = this.pageUrlP;
        updatePageImgUrlP(param).then((response) =>{
          if(response.data.code === 1){
            _this.notify("修改成功","success");
          }
        })
      }
    }
}
</script>

<style scoped>
.el-form{
  margin: 20px;
}
.title{
  font-size: 23px;
  margin: 10px;
}

/* 教程 */
.UpdateUrl-tip span{
  line-height: 160%;
  font-size: 21px;
}

.UpdateUrl-tip strong{
  color: red;
}

.line{
  width: 100%;
  border: 1px solid black;
  margin-bottom: 30px;
}
</style>