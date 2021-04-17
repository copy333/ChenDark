<template>
  <!-- 基本信息 -->
  <el-row style="margin-top:20px;">
    <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
    <el-col :span="8">
      <div class="grid-content bg-purple-light">
          <div class="Basic-cont">
            <el-form ref="form" :model="sizeForm" label-width="100px" size="mini">
              <el-form-item label="">
                <el-image :src="sizeForm.avatar" fit="cover" style="margin-left:-60px;"></el-image>
              </el-form-item>

              <el-form-item label="昵称:">
                <p style="font-size:17px;color:orange;">{{sizeForm.nickname}}</p>
              </el-form-item>

              <el-form-item label="权限:">
                <p style="font-size:17px;color:orange;">{{sizeForm.roles}}</p>
              </el-form-item>

              <el-form-item label="简介:">
                <p style="font-size:17px;color:orange;">{{sizeForm.intro}}</p>
              </el-form-item>

            </el-form>
          </div>
      </div>
      </el-col>
    <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
  </el-row>
</template>

<script>
export default {
  data(){
    return {
      sizeForm: { //文章选择区
        nickname: localStorage.getItem("nickname"),
        roles: localStorage.getItem("roles"),
        intro: localStorage.getItem("intro"),
        avatar: localStorage.getItem("avatar")
      }
    }
  },
}
</script>

<style scoped>
.Basic-cont .el-image{
  width: 170px;
  height: 170px;
  border-radius: 50%;
  box-shadow: 0 0 10px rgba(101, 21, 133, 0.596);
  margin-bottom: 5px;
}
.Basic-cont .el-image:hover{
box-shadow: 0 0 10px rgba(24, 182, 45, 0.596);
}

</style>