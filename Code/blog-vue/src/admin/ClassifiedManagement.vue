<template>
  <!-- 分类管理 -->

    <!-- 搜索栏 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="分类名">
        <el-input v-model="formTitle" placeholder="请输入分类名"></el-input>
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
            <p>分类ID号是: {{ scope.row.typeId }}</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" style="font-size:15px">{{ scope.row.typeId }}</el-tag>
            </div>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column
      label="分类名"
      width="230">
      <template #default="scope">
        <el-popover effect="light" placement="top">
          <template #default>
            <p>该分类名称是: {{ scope.row.typeName }}</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" effect="dark" style="font-size:15px">{{ scope.row.typeName }}</el-tag>
            </div>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column
      align="center"
      label="文章数"
      width="100">
      <template #default="scope">
        <el-popover effect="light" placement="top">
          <template #default>
            <p>该分类有{{ scope.row.typeBlogCount }}篇文章</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" style="font-size:15px">{{ scope.row.typeBlogCount }}</el-tag>
            </div>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column
      label="图片地址"
      width="560">
      <template #default="scope">
        <el-popover effect="light" placement="top">
          <template #default>
            <p>该分类的代表图片地址是：{{ scope.row.typeImg }}</p>
          </template>
          <template #reference>
            <div class="name-wrapper">
              <el-tag size="medium" style="font-size:15px">{{ scope.row.typeImg }}</el-tag>
            </div>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column label="操作" width="200">
      <template #default="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {getBlogType,delBlogTypeById} from '@/api/index'

import {mixin} from '@/mixins/index'

export default {
  mixins: [mixin],
    data() {
      return {
        tableData: [],  //存储从数据库中取到的 分类数据
        staticData: [], //用户随机搜索交互后展示全部的值
        formTitle: '',  //文章标题
      }
    },
    watch:{
      formTitle(newCont,oldCont){ //侦听 fromTitle 的值
        if(newCont == ''){
          this.tableData = this.staticData;
        }else{
          let temp = [];
          for(let item of this.tableData){
            if(item.typeName.includes(newCont)){  //如果所有分类名里包含有 fromTitle 的值就执行
              temp.push(item)
            }
          }
           this.tableData = temp; //将包含搜索的内容对象赋值给 tableData
        }
      }
    },
    methods: {
      handleEdit(index, row) {
        this.$router.push({name:"EditClassified",params:{typeId:row.typeId,typeName:row.typeName,typeImg:row.typeImg}})
      },
      handleDelete(index, row) {
        let _this = this;
        delBlogTypeById(row.typeId).then((response) =>{
          if(response.data.code === 1){
            _this.notify("删除成功","success")
          }else{
            _this.notify("删除失败","error") 
          }
        })
      },
      message(){
        let _this = this;
        getBlogType().then((response) =>{ //获取文章分类信息
          if(response.code === 20000){
            _this.tableData = response.data.typeList;
            _this.staticData = response.data.typeList;
          }
        })
      },
    },
    mounted(){  //页面初始化完成后执行
      this.message(); //获取内容并更新
  }
}
</script>

<style>

</style>