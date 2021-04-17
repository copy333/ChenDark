<template>
  <!-- 新建文章页 -->
  <!-- 选择区 -->
  <div>
    <el-form ref="form" :model="sizeForm" label-width="80px" size="mini">

      <el-form-item label="文章标题">
        <el-input v-model="sizeForm.blogTitle"></el-input>
      </el-form-item>

      <el-form-item label="简介">
        <el-input v-model="sizeForm.blogRemark"></el-input>
      </el-form-item>

      <el-form-item label="封面">
        <el-input v-model="sizeForm.blogImage"></el-input>
      </el-form-item>

      <el-form-item label="图片仓库">
        <el-input v-model="sizeForm.blogContentImg"></el-input>
      </el-form-item>

      <el-form-item label="分类">
        <el-radio-group v-model="sizeForm.type" size="medium">
          <el-radio 
            border 
            v-for="item in sizeForm.types" 
            :key="item" 
            :label="item">
          </el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item size="large">
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="clearAll">取消</el-button>
        <el-button type="primary" @click="useMarked" style="float:right;">开启markdown同步编译(可能会影响性能)</el-button>
      </el-form-item>

    </el-form>
  </div>
  <!-- markdown编辑区 -->
  <el-row :gutter="10" justify>
  <el-col :span="12" class="left">
    <div class="grid-content bg-purple">
      <textarea 
        v-model="inputCont" 
        @keydown.tab="forbidTab($event)"  
        @keyup.tab="replaceSpacing()"
        autofocus></textarea>
    </div>
  </el-col>
  <el-col :span="10" class="right">
    <div class="grid-content bg-purple">
      <div class="hljs" ref="hlDiv" v-html="code"></div>
    </div>
  </el-col>
</el-row>
</template>

<script>

import marked from 'marked' //markdown编译器 npm install --save marked

//导入自己写的 css样式
import '../assets/css/codeStyle.css'

import {mixin} from '@/mixins/index'

import { getBlogContraryTypeMap,getBlogType,saveBlog,addTypeCount } from '@/api/index' //获取文章分类信息 id:name键值对

export default {
  mixins:[mixin],
  data(){
    return {
      code:'请开始你的编写',
      inputCont: '',
      sizeForm: { //文章选择区
        blogTitle: '',
        blogRemark: '',
        blogImage: '',
        blogContentImg: '',
        type: '',
        types: [],  //所有分类信息
        typeMap: [],  //map集合，里面有 类型的id 和 名称
      },
      useCode: false,  //默认关闭marked实时编译 markdown
    }
  },
  watch:{
    inputCont(newCont,oldCont){ //当inputCont值改变的时候，该方法就执行
      if(this.useCode){
        this.code = marked(newCont)
      }
    }
  },
  methods:{
    //阻止 tab键的默认事件，允许按下 TAB缩进
    forbidTab(e){ //tab键按下
      e.preventDefault(); //通知 Web 浏览器不要执行与事件关联的默认动作
    },
    replaceSpacing(){ //tab键抬起
      this.inputCont += "\t";
    },
    showBlogType(){  //新建文章前先获取文章分类信息
      let _this = this;
      getBlogType().then((response) =>{
        if( response.code === 20000){
          for(let item of response.data.typeList){
            _this.sizeForm.types.push(item.typeName) //将每个 分类 对象的name 都放入 types 数组中
          }
        }
      })
      getBlogContraryTypeMap().then((response) =>{  // 获取到博客分类的 name:id
        if(response.code === 20000){
          _this.sizeForm.typeMap = response.data;
        } 
      })
    },
    onSubmit() {  //提交
      let _this = this;
      let params = new Object;
      params.blogTitle = this.sizeForm.blogTitle;
      params.blogRemark = this.sizeForm.blogRemark;
      params.blogImage = this.sizeForm.blogImage;
      params.blogType = this.sizeForm.typeMap[this.sizeForm.type];
      params.blogContent = this.inputCont;
      params.blogContentImg = this.sizeForm.blogContentImg;
      saveBlog(params).then((response) =>{
        if(response.data.code === 1){ //博客保存成功
          addTypeCount(params.blogType).then((response) =>{ //该分类下的博客数量 +1
            if(response.data.code ===  1){
              _this.notify("提交成功!", "success");
              _this.$router.push("articleArticle") //保存成功跳转至文章管理页
            }
          })
        } else{
          _this.notify("提交失败(也许是标题重名了)!", "error"); //这个消息提示则博客连保存都没保存成功
        }
      })
    },
    clearAll() {
      // 重置输入
      this.sizeForm.blogTitle = '';
      this.sizeForm.blogRemark = '';
      this.sizeForm.blogImage = '';
      this.sizeForm.type = '';
      this.inputCont = '';
      this.sizeForm.blogContentImg = '';
      this.notify("取消成功!", "success");
    },
    useMarked(){  //启用 marked实时编译md
      this.useCode = true;
    }
  },
  mounted(){
    marked.setOptions({
        renderer: new marked.Renderer(),
        pedantic: false,  //默认为false。 尽可能地兼容 markdown.pl的晦涩部分，不纠正原始模型任何的不良行为和错误。
        gfm: true,  //默认为true。 允许 Git Hub标准的markdown.
        tables: true, //默认为true。 允许支持表格语法。该选项要求 gfm 为true。
        breaks: true,  //默认为false。 允许回车换行。该选项要求 gfm 为true。
        smartLists: true,
        smartypants: false, //使用更为时髦的标点，比如在引用语法中加入破折号。
        xhtml: false
      }
    );
    this.code = marked(this.code)
    this.showBlogType() //初始化页面完成后，查询数据库中的文章分类信息
  },
}
</script>

<style scoped>
.el-row {
  height: 100%;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}
.grid-content {
  position: relative;
  border-radius: 4px;
  min-height: 100%;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
/* 自定义样式 */
.left textarea{
  position: absolute;
  width: 100%;
  height: 100%;
  left: 0;
  overflow-x: auto;
  font-size: 23px;
  color: green;
  padding: 10px;
  box-sizing: border-box;
  white-space:nowrap;
  /*
  textarea 的 overflow-x: auto;不管用加上 white-space:nowrap;（规定段落中的文本不进行换行：）
   */
}

.hljs{
  position: absolute;
  width: 100%;
  height: 100%;
  left: 0;
  font-size: 23px;
  padding: 10px;
  box-sizing: border-box;
  background-color: #fff;
  border: 2px solid black;
}

.el-radio-group label{
  margin: 10px;
}
</style>