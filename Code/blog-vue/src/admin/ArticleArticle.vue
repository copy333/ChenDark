<template>
  <!-- 文章管理 -->
  <div>
    <!-- 搜索栏 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="文章标题">
        <el-input
          v-model="formInline.title"
          placeholder="请输入文章标题"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 列表 -->
    <div>
      <el-table
        row-key="date"
        ref="filterTable"
        :data="tableData"
        style="width: 100%"
        align = "center"
      >
        <el-table-column
          prop="gmtCreate"
          label="创建日期"
          sortable
          width="120"
          column-key="date"
        >
        </el-table-column>

        <el-table-column
          prop="gmtModified"
          label="更新日期"
          sortable
          width="120"
          column-key="date"
        >
        </el-table-column>

        <el-table-column prop="blogTitle" label="文章标题" width="170">
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

        <el-table-column prop="blogRemark" label="文章概述" width="230">
        </el-table-column>

        <el-table-column prop="blogType" label="分类" width="190">
          <template #default="scope">
            <el-tag style="font-size: 16px" type="success" effect="dark">
              {{ type[scope.row.blogType] }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="160">
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
              >编辑</el-button
            >
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import {
  getBlogListTitle,
  delBlogById,
  getBlogTypeMap,
  subTypeCount,
} from "@/api/index";

import { mixin } from "@/mixins/index";

export default {
  mixins: [mixin],
  data() {
    return {
      formInline: {
        title: "", //文章标题
      },
      tableData: [], //后台返回的所有内容
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
    onSubmit() {
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
            //只保留时间到天数
            for(let item=0;item<response.data.data.length;item++){
              _this.tableData[item].gmtCreate = response.data.data[item].gmtCreate.slice(0,10);
              _this.tableData[item].gmtModified = response.data.data[item].gmtModified.slice(0,10);
            }
          } else {
            _this.notify("查询结果为空", "error");
          }
        }
      });
    },
    handleEdit(index, row) {
      //编辑操作
      this.$router.push({
        name: "EditArticle",
        params: { blogId: row.blogId },
      });
    },
    handleDelete(index, row) {
      //删除操作
      let _this = this;
      delBlogById(row.blogId).then((response) => {
        if (response.data.code == 1) {
          //博客删除成功
          subTypeCount(row.blogType).then((response) => {
            //该文章所在分类数量 -1
            _this.onSubmit(); //重新查询一篇
            _this.notify("删除成功!", "success");
          });
        } else {
          _this.notify("删除失败!", "error"); //这个提示出现则连基本的删除都失败了
        }
      });
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
  },
};
</script>

<style>
.has-gutter .cell {
  color: green;
  font-size: 18px;
  text-align: center;
}
.el-tag:hover {
  cursor: default;
}
</style>