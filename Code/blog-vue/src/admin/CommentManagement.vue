<template>
    <!-- 评论管理 -->
      <!-- 搜索栏 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="评论内容">
        <el-input v-model="formTitle" placeholder="请输入评论内容"></el-input>
      </el-form-item>
    </el-form>

  <!-- 内容显示 -->
  <el-table
    :data="tableData"
    style="width: 100%">

    <el-table-column
      label="ID"
      width="50">
      <template #default="scope">
        <el-popover effect="light" placement="top">
          <template #default>
            <p>ID号是: {{ scope.row.id }}</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" style="font-size:15px">{{ scope.row.id }}</el-tag>
            </div>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column
      label="昵称"
      width="180">
      <template #default="scope">
        <el-popover effect="light" placement="top">
          <template #default>
            <p>该用户昵称是: {{ scope.row.nickname }}</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" effect="dark" style="font-size:15px">{{ scope.row.nickname }}</el-tag>
            </div>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column
      align="center"
      label="评论内容"
      width="360">
      <template #default="scope">
        <el-popover effect="light" placement="top">
          <template #default>
            <p>{{ scope.row.content }}</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" style="font-size:15px">{{ scope.row.content }}</el-tag>
            </div>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column
      label="联系地址"
      width="360">
      <template #default="scope">
        <el-popover effect="light" placement="top">
          <template #default>
            <p>该用户的连地址是：{{ scope.row.url }}</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" style="font-size:15px;">{{ scope.row.url }}</el-tag>
            </div>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column label="操作" width="100">
      <template #default="scope">
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {getMessageList,deleteMessageById} from '@/api/index'

import {mixin} from '@/mixins/index'

export default {
  mixins: [mixin],
    data() {
      return {
        tableData: [],  //存储从数据库中取到的 分类数据
        staticData: [], //用户随机搜索交互后展示全部的值
        formTitle: '',  //作者名
      }
    },
    watch:{
      formTitle(newCont,oldCont){ //侦听 fromTitle 的值
        if(newCont == ''){
          this.tableData = this.staticData;
        }else{
          let temp = [];
          for(let item of this.tableData){
            if(item.content.includes(newCont)){  //如果所有分类名里包含有 fromTitle 的值就执行
              temp.push(item)
            }
          }
           this.tableData = temp; //将包含搜索的内容对象赋值给 tableData
        }
      }
    },
    methods: {
      handleDelete(index, row) {
        let _this = this;
        deleteMessageById(row.id).then((response) =>{
          if(response.data.code === 1){
            _this.notify("删除成功","success")
            _this.message(); //获取内容并更新
          }else{
            _this.notify("删除失败","error") 
          }
        })
      },
      message(){
        let _this = this;
        getMessageList().then((response) =>{ //获取文章分类信息
          if(response.data.code === 1){
            _this.tableData = response.data.list;
            _this.staticData = response.data.list;
          }
        })
      },
    },
    mounted(){  //页面初始化完成后执行
      this.message(); //获取内容并更新
  }
}
</script>

<style scoped>
</style>