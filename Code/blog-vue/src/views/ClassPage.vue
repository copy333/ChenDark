<template>
  <!-- 文章分类信息单独显示页 -->
  <!-- banner -->
  <div class="classpage-banner banner">
    <h1 class="banner-title">{{name}}</h1>
  </div>
  <!-- 列表 -->
  <div class="show">
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
        width="160"
        column-key="date"
      >
      </el-table-column>

      <el-table-column
        prop="gmtModified"
        label="更新日期"
        sortable
        width="160"
        column-key="date"
      >
      </el-table-column>

      <el-table-column
        prop="blogTitle"
        label="文章标题"
        style="cursor: pointer"
        width="230"
      >
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

      <el-table-column
        prop="blogRemark"
        style="background-color: #00ffcc"
        label="文章概述"
        width="400"
      >
      </el-table-column>

      <el-table-column label="操作" width="80">
        <template #default="scope">
          <el-button
            size="mini"
            type="success"
            @click="handleGo(scope.$index, scope.row)"
            >进入</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getBlogByTypeId } from "@/api/index";

export default {
  data() {
    return {
      tableData: [], //该分类数据
      name: ""  //该分类的名称
    };
  },
  methods: {
    getInfo() {
      let _this = this;
      this.name = this.$route.params.name;
      getBlogByTypeId(this.$route.params.id).then((response) => {
        if (response.code === 20000) {
          _this.tableData = response.data.info;
        }
      });
    },
    handleGo(index, row) {
      //进入操作
      this.$router.push({ path: `/pages/${row.blogTitle}/${row.blogId}` }); //将博客id和title做为参数传输
    },
  },
  mounted() {
    this.getInfo();
  },
};
</script>

<style scope>
.show {
  margin: 50px;
  margin-top: 400px;
  border: 2px solid black;
  box-shadow: 0 0 10px black;
}
.cell {
  color: teal;
}
.cell:hover {
  cursor: default;
}

.classpage-banner {
  background: url("/api/resources/img/Banner/classpage.jpg") center
    center / cover no-repeat;
  background-color: #49b1f5;
}

/* 当宽度小于 1050px时*/
@media screen and (max-width:1050px){
  .show {
    margin-left: 0;
    margin-right: 0;
  }
}
</style>