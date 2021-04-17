<template>
  <!-- 文章查询页 -->
  <!-- banner -->
  <div class="search-banner banner">
    <h1 class="banner-title">文章查询</h1>
  </div>

  <div class="show" style="margin-top:400px;">
    <!-- 搜索栏 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <!-- 标题搜索 -->
      <el-form-item label="文章标题">
        <el-input
          v-model="formInline.title"
          placeholder="请输入文章标题"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
      <!-- 内容搜索 -->
      <el-form-item label="全站内容" style="margin-left:10px;">
        <el-input
          v-model="formInline.content"
          placeholder="请输入要搜索的内容"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmitPlus">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 列表 - 文章标题查询 -->
    <div>
      <el-table
        row-key="date"
        ref="filterTable"
        :data="tableData"
        align="center"
        style="width: 100%"
      >
        <el-table-column
          prop="gmtCreate"
          label="创建日期"
          sortable
          width="180"
          column-key="date"
        >
        </el-table-column>

        <el-table-column
          prop="gmtModified"
          label="更新日期"
          sortable
          width="180"
          column-key="date"
        >
        </el-table-column>

        <el-table-column prop="blogTitle" label="文章标题" width="180">
        </el-table-column>

        <el-table-column label="文章封面" width="160">
          <template #default="scope">
            <el-image
              style="height: 90px"
              :src="scope.row.blogImage"
              fit="cover"
            ></el-image>
          </template>
        </el-table-column>

        <el-table-column prop="blogRemark" label="文章概述" width="200">
        </el-table-column>

        <el-table-column prop="blogType" label="分类" width="150" align="center">
          <template #default="scope">
            <el-tag style="font-size: 16px" type="success" effect="dark">
              {{ type[scope.row.blogType] }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="80">
          <template #default="scope">
            <el-button
              size="mini"
              type="warning"
              @click="handleGo(scope.$index, scope.row)"
              >进入</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 列表 - 文章内容 -->
    <div class="searchCont">
      <el-table
        row-key="date"
        ref="filterTable"
        :data="contData"
        align="center"
        style="width: 100%"
      >
        <el-table-column prop="blogTitle" label="文章标题" width="180">
        </el-table-column>

        <el-table-column label="文章封面" width="160">
          <template #default="scope">
            <el-image
              style="height: 90px"
              :src="scope.row.blogImage"
              fit="cover"
            ></el-image>
          </template>
        </el-table-column>

        <el-table-column prop="blogRemark" label="文章概述" width="180">
        </el-table-column>

        <!-- 文章高亮内容 -->
        <el-table-column prop="highlightInfo" label="文章内容" width="410">
          <template #default="scope">
            <span v-html="scope.row.highlightInfo">
            </span>
        </template>
        </el-table-column>

        <el-table-column prop="blogType" label="分类" width="160" align="center">
          <template #default="scope">
            <el-tag style="font-size: 16px" type="success" effect="dark">
              {{ type[scope.row.blogType] }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="70">
          <template #default="scope">
            <el-button
              size="mini"
              type="warning"
              @click="handleGo(scope.$index, scope.row)"
              >进入</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

  </div>
</template>

<script>
import { getBlogListTitle, getBlogTypeMap, searchES } from "@/api/index";

import { mixin } from "@/mixins/index";

export default {
  mixins: [mixin],
  data() {
    return {
      formInline: {
        title: "", //文章标题
        content: "",  //文章内容
      },
      tableData: [], //后台返回的所有内容 - 文章标题
      contData: [], //后台返回的所有内容 - 文章内容
      type: {}, //分类信息格式转换 id->name
    };
  },
  computed: {
    //计算当前搜索结果表里的数据
    data() {
      return this.tableData.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      );
    },
  },
  mounted() {
    this.typeChange(); //页面初始化完成就执行
  },
  methods: {
    onSubmit() {  //文章标题搜索
      this.contData = [];
      //提交数据申请
      let _this = this;
      let params = new Object(); //params参数对象 -> json对象
      params.blogTitle = this.formInline.title; //取得输入框输入的标题名
      getBlogListTitle(params).then((response) => {
        if (response.code === 20000) {
          if (response.data.data.length != 0) {
            //如果查询结果不为空
            _this.notify(
              `查询成功，共 ${response.data.data.length} 条数据`,
              "success"
            );
            _this.tableData = response.data.data;
          } else {
            _this.notify("查询结果为空", "error");
          }
        }
      });
    },
    onSubmitPlus(){ //全站内容搜索
      this.tableData = [];
      //提交数据申请
      let _this = this;
      let content = this.formInline.content; //取得输入框输入的内容
      searchES(content).then((response) =>{
        if(response.code === 20001){
          _this.notify("查询结果为空", "error");
        } else if (response.data.list.length != 0) {
          //如果查询结果不为空
          _this.notify(
            `查询成功，共 ${response.data.list.length} 条数据`,
            "success"
          );
          //文章信息
          _this.contData = response.data.list;
          //解决头四个字符是 null 的问题，从第 四个 字符开始截取字符串
          for(var item=0; item < response.data.list.length;item++){
            _this.contData[item].highlightInfo = response.data.list[item].highlightInfo.slice(4);
          }

        } else {
          _this.notify("查询结果为空", "error");
        }
      })
    },
    typeChange() {
      // 分类数字id 转为 分类名称显示
      let _this = this;
      getBlogTypeMap().then((response) => {
        if (response.code === 20000) {
          _this.type = response.data;
        }
      });
    },
    handleGo(index, row) {
      //进入操作
      this.$router.push({ path: `/pages/${row.blogTitle}/${row.blogId}` }); //将博客id和title做为参数传输
    },
  },
};
</script>

<style scope>
.search-banner {
  background: url("/api/resources/img/Banner/searchpage.jpg") center
    center / cover no-repeat;
  background-color: #49b1f5;
}

.has-gutter .cell {
  color: green;
  font-size: 18px;
  text-align: center;
}
.el-tag:hover {
  cursor: default;
}
.demo-form-inline {
  float: left;
}
.show {
  margin: 20px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.7);
}
.show:hover{
  box-shadow: 0 0 30px rgba(57, 13, 218,0.6);
  transition: all 0.7s;
}

/* 文章内容显示分行 */
.separate{
  height: 10px;
}

.cell span{
  font-size: 18px;
  line-height: 150%;
}
td{
  border-top: 1px solid black;
}
.searchCont{
  border: 1px solid black;
}

/* 当宽度小于 1050px时*/
@media screen and (max-width:1050px){
  .show {
    margin-left: 0;
    margin-right: 0;
  }
}
</style>