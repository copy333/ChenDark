<template>
  <!-- 分类页面 -->
  <!-- banner -->
  <div class="category-banner banner">
    <h1 class="banner-title">分类</h1>
  </div>

  <div class="cont">
    <el-row v-for="item in typeData" :key="item">
      <!-- 分类说明 -->
      <el-col :span="3">
          <div class="cardCont">
              <!-- <p style="color:green;font-size:26px">ChenDark</p> -->
          </div>
      </el-col>
      <!-- 图片展示 -->
      <el-col :span="8">
        <el-card :body-style="{ padding: '0px' }">
            <el-image
            style="width: 100%; height: 270px;cursor:pointer"
            :src="item.typeImg"
            @click="goClass(item.typeId,item.typeName)"
            fit="cover"></el-image>
        </el-card>
      </el-col>
      <!-- 该分类信息 -->
      <el-col :span="11">
        <div class="cardCont">
            <p>{{ item.typeName }}</p>
            <span>文章数：<span class="num">{{ item.typeBlogCount }}</span></span>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getBlogType } from "@/api/index";

export default {
  data() {
    return {
      typeData: [], //分类数据
    };
  },
  methods: {
    getData() {
      //获取分类信息
      getBlogType().then((response) => {
        if (response.code === 20000) {
          this.typeData = response.data.typeList;
        }
      });
    },
    goClass(typeId,typeName){  //点击分类按钮后跳转
      this.$router.push({name:'ClassPage',params:{id:typeId,name:typeName}})
    }
  },
  mounted() {
    this.getData();
  },
};
</script>

<style scope>
.category-banner {
  background: url("/api/resources/img/Banner/category.jpg") center
    center / cover no-repeat;
  background-color: #49b1f5;
}
.banner-title {
  text-align: center;
  font-size: 36px;
  line-height: 2;
  background-color: rgba(0, 0, 0, 0.6);
}

.cont{
  margin-top: 400px;
}

.el-row{
    margin-top: 50px;
}
.el-row .el-col {
  font-size: 23px;
}
.button {
  padding: 0;
  min-height: auto;
}

/* 文章内容区 */
.cardCont{
    padding-top: 100px;
    line-height: 200%;
}
.num{
  color:red;
}
</style>