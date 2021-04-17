<template>
  <!-- 友情链接 -->
      <!-- 搜索栏 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="作者名">
        <el-input v-model="formTitle" placeholder="请输入作者名"></el-input>
      </el-form-item>
    </el-form>

    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="操作：">
          <el-button type="success"  @click="handleAdd()" style="font-size:15px;">新增友链</el-button>
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
      align="center"
      label="头像"
      width="100">
      <template #default="scope">
        <el-popover effect="light" placement="top">
          <template #default>
            <p>{{ scope.row.img }}</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" style="height:50px;">
                <el-image :src="scope.row.img" fit="cover"></el-image>
              </el-tag>
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
      label="简介"
      width="260">
      <template #default="scope">
        <el-popover effect="light" placement="top">
          <template #default>
            <p>{{ scope.row.descInfo }}</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" style="font-size:15px">{{ scope.row.descInfo }}</el-tag>
            </div>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column
      label="网站地址"
      width="280">
      <template #default="scope">
        <el-popover effect="light" placement="top">
          <template #default>
            <p>该用户的网站地址是：{{ scope.row.url }}</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" style="font-size:15px;">{{ scope.row.url }}</el-tag>
            </div>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column label="操作" width="160">
      <template #default="scope">
        <el-button
          size="mini"
          @click="handleUpdate(scope.$index, scope.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {getLinkList,deleteLinkById} from '@/api/index'

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
            if(item.nickname.includes(newCont)){  //如果所有分类名里包含有 fromTitle 的值就执行
              temp.push(item)
            }
          }
           this.tableData = temp; //将包含搜索的内容对象赋值给 tableData
        }
      }
    },
    methods: {
      handleAdd(){
        this.$router.push({name:"AddLinkPage"})
      },
      handleUpdate(index, row) {
        this.$router.push({name:"EditLinkPage",params:{id:row.id,nickname:row.nickname,descInfo:row.descInfo,img:row.img,url:row.url}})
      },
      handleDelete(index, row) {
        let _this = this;
        deleteLinkById(row.id).then((response) =>{
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
        getLinkList().then((response) =>{ //获取文章分类信息
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