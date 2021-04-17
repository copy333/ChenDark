<template>
  <!-- 留言页面 -->
  <!-- banner -->
  <div class="msg-banner banner">
    <h1 class="banner-title">留言</h1>
  </div>

  <div class="msg" style="margin-top:400px;">
    <div style="height:340px;">
      <el-row :gutter="20">
        <el-col :span="2"></el-col>
        <el-col :span="10">
          <div class="grid-content bg-purple">
                <el-input
                  type="text"
                  placeholder="昵称(可选)"
                  v-model="nickname"
                  maxlength="18"
                  show-word-limit
                >
                </el-input>
                <div style="margin: 10px 0;">
                <el-input
                  type="text"
                  placeholder="邮箱/网址(可选)"
                  v-model="url"
                  show-word-limit
                >
                </el-input>
              </div>
                <div style="margin: 10px 0;">
                <el-input
                  type="textarea"
                  placeholder="请输入内容"
                  v-model="content"
                  maxlength="360"
                  rows = 8
                  show-word-limit
                >
                </el-input>
              </div>
          </div>
          </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple">
              <el-button type="success" @click="save">发送</el-button>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
  
    <!-- 留言信息显示区 -->
    <div class="msg" style="margin-top:30px;">
      <el-card class="box-card">
        <div v-for="item in msgList[currentPage-1]" :key="item" class="item">
            <el-row>
              <!-- 列表左留言用户信息显示区 -->
              <el-col :span="8">
                <div class="grid-content bg-purple user-left">
                    <p><i class="el-icon-user-solid"></i>{{item.nickname}}</p>
                    <span style="font-size:16px;"><i class="el-icon-link"></i>{{item.url}}</span>
                </div>
                </el-col>
                <!-- 列表右是留言内容 -->
              <el-col :span="16">
                <div class="grid-content bg-purple-light user-right">
                  <span><i class="el-icon-chat-dot-round"></i>{{item.content}}</span>
                </div>
              </el-col>
            </el-row>
        </div>
    </el-card>
    <!-- 分页 -->
    <div style="margin-bottom:30px;margin-top:30px;">
      <el-pagination
        background
        :page-size="pageSize"
        layout="prev, pager, next"
        :current-page="currentPage" 
        :total="allPage"
        @current-change="handleCurrentChange">
      </el-pagination>
    </div>

  </div>
</template>

<script>
import { defineComponent, ref } from 'vue'

import { saveMessage,getMessageList } from "@/api/index"

import {mixin} from "@/mixins/index"

export default defineComponent ({
  mixins: [mixin],
  setup() {
    return {
      nickname: ref(''),
      url: ref(''),
      content: ref(''),
    }
  },
  data(){
    return{
      msgList: [],
      pageSize: 4, //每页显示多少留言，为 4 表示显示六篇留言
      allPage: 0,  //总共多少留言，记录数量
      currentPage: 1, //当前页数，为 1 表示当前是第一页
    }
  },
  methods:{
    handleCurrentChange(val){ //分页新增内容 - 获取当前页
      this.currentPage = val;
    },
    save(){
      let _this = this;
      let params = new Object;
      params.nickname = this.nickname;
      params.url = this.url;
      params.content = this.content;
      //保存留言至数据库
      saveMessage(params).then((response)=>{
        if(response.data.code === 1){
          _this.notify("保存成功","success");
        }
      })

      //发送保存请求后重新加载页面
      location.reload();
    },
    getMsgList(){
      let _this = this;
      getMessageList().then((response)=>{
        if(response.data.code === 1){
          let arr = response.data.list;
          _this.allPage = arr.length; //后台返回的所有内容总共多少留言
          for(let i = 0;i < arr.length;i += _this.pageSize){ //按照pageSize等分留言数据
           _this.msgList.push(arr.reverse().slice(i,i + _this.pageSize))  //reverse()倒序再slice()等分
          }

        }
      })
    },
  },
  mounted(){
   this.getMsgList();
  }
})
</script>

<style scoped>
.msg-banner {
  background: url("/api/resources/img/Banner/searchpage.jpg") center
    center / cover no-repeat;
  background-color: #49b1f5;
}

.msg{
  width: 80%;
  margin-left: 10%;
  margin-bottom: 30px;
  box-shadow: 0 0 20px rgb(58, 57, 57);
  border-radius: 13px;
  background: url("/api/resources/img/Banner/board.jpg");
}
.msg:hover{
  box-shadow: 0 0 30px rgba(57, 13, 218,0.6);
  transition: all 0.7s;
}

.message-title {
  color: #eee;
  animation: title-scale 1s;
}

@keyframes title-scale{
  0% {
    opacity: 0;
    filter: alpha(opacity=0);
    transform: scale(.7);
  }
  100% {
    opacity: 1;
    filter: none;
    transform: scale(1);
  }
}

.msg .grid-content{
  padding: 30px;
}

.user-left,.user-right{
  height: 98px;
}
.user-left{
  font-size: 22px;
      /* html5 让显示不完的文字自动省略号 */
    -webkit-line-clamp: 5; /* 限制显示的文本的行数为3*/
    -webkit-box-orient: vertical; /*水平排列<p>，使其不纵向显示*/
    word-break: break-all; /*使<p>中的文字换行*/
    overflow: hidden; /*超出的文字部分隐藏*/
    text-overflow: ellipsis; /*超出的文字部分用...来显示*/
    display: -webkit-box; /*将<p>变成高度定死，宽度自适应的行内块元素*/
}
.user-right{
  font-size: 19px;
      /* html5 让显示不完的文字自动省略号 */
    -webkit-line-clamp: 5; /* 限制显示的文本的行数为3*/
    -webkit-box-orient: vertical; /*水平排列<p>，使其不纵向显示*/
    word-break: break-all; /*使<p>中的文字换行*/
    overflow: hidden; /*超出的文字部分隐藏*/
    text-overflow: ellipsis; /*超出的文字部分用...来显示*/
    display: -webkit-box; /*将<p>变成高度定死，宽度自适应的行内块元素*/
}
</style>

<style>
.el-textarea__inner{
  resize: none !important;
}

</style>