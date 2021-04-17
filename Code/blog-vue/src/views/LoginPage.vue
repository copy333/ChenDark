<template>

<el-row style="margin-top:160px">
  <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
  <el-col :span="8">
    <div class="grid-content bg-purple-light div-form">
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
      >
        <el-form-item label="账号" prop="name">
          <el-input
            v-model="ruleForm.name"
            type="text"
            autocomplete="on"
            prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pwd">
          <el-input
            type="password"
            v-model="ruleForm.pwd"
            autocomplete="on"
            show-password
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item label="验证码">
          <el-input v-model="ruleForm.isCode" placeholder="验证码" type="text" autocomplete="off"></el-input>
          <!-- 验证码 显示 -->
          <!-- 给 src赋值 正确写法是 :src="" -->
          <center>
          <img
            @click="getvCode()"
            :src="ruleForm.vCode"
            style="margin-top: 20px;"
          />
          </center>
        </el-form-item>
        
        <el-form-item>
          <center>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
          </center>
        </el-form-item>
        
      </el-form>
    </div>
  </el-col>
  <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
</el-row>
</template>

<script>
// 引入mixins文件夹的index.js文件
import { mixin } from "@/mixins/index";

// 引入api文件夹的index.js文件
import { getVcodeStatus,getLoginData } from "@/api/index";

import { encrypt } from '../secureUtil/index' //加密和解密

export default {
  mixins: [mixin],
  data() {
    // 定义ruleForm
    var validateInput = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入账号"));
      } else {
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (this.validatePass(value)) {
        // 正则表达式验证密码格式是否合乎预期
        callback(
          new Error("密码格式错误，验证密码 6-20 位，包含大小写字母和数字")
        );
      } else {
        callback();
        this.getvCode(); //验证码
      }
    };
    return {
      ruleForm: {
        name: "",
        pwd: "",
        vCode: "", //验证码地址
        isCode: ""  //获取到的验证码
      },
      rules: {
        //表单验证
        name: [{ validator: validateInput, trigger: "blur" }], // blur指失去焦点的时候触发
        pwd: [{ validator: validatePass, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      //提交验证
      let _this = this; // 获取当当前 this 实例，为得是下面调用 mixins 里定义的 notify()方法
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //判断验证码是否正确
          let params = new Object();
          params.code = _this.ruleForm.isCode;
          getVcodeStatus(params).then((response) =>{
            if(response.data.code == 1){
              _this.pass(); //提交密码账号验证
            }else{
              _this.notify("请确认验证码!", "error");
            }
          })
        }
      });
    },
    pass(){
       let _this = this; // 获取当当前 this 实例，为得是下面调用 mixins 里定义的 notify()方法
      //校验通过了就向服务器端发送请求
      let params = new Object();  //params参数对象 -> json对象
      params.username = encrypt(this.ruleForm.name); //取得vue双向绑定的用户名的值
      params.password = encrypt(this.ruleForm.pwd); //密码
      //getLoginStatus(params).then((response) => {
        getLoginData(params).then((response) => {
        if (response.data.code == 1) {
          _this.$router.push("/admin");
          _this.notify( response.data.user.nickname + "欢迎登录", "success");

          //vuex 中保存用户的信息
          let info = new Object();
          info.username = response.data.user.username;
          info.avatar = response.data.user.avatar;  //头像地址
          info.intro = response.data.user.intro;  //个人简介
          info.nickname = response.data.user.nickname;  //昵称
          info.roles = response.data.roles; //权限

          _this.$store.commit("setUserInfo",info);

        } else {
          _this.notify("账号或密码错误!", "error");
        }
      });
    },
    resetForm(formName) {
      // 重置输入
      this.$refs[formName].resetFields();
    },
    validatePass(value) {
      // 验证密码 6-20 位，包含大小写字母和数字
      let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[\s\S]{6,20}$/;
      return !reg.test(value) ? true : false;
    },
    getvCode() {
      //获取验证码-通过时间不同达到多次请求图片
      this.ruleForm.vCode = "api/blog/getVcode?time=" + (new Date()).getTime();
    },
  },
};
</script>

<style scope>
  /* body{
    background-image: url("../assets/img/bg.jpg");
    background-size: 100%;
    min-width: 1200px;
  } */

  .div-form form{
    background-color: rgba(173, 15, 15, 0.5);
    border-radius: 11px;
    padding-right: 100px;
    padding-top: 50px;
    padding-bottom: 20px;
  }
  .el-form-item__label{
    font-size: 17px !important;
    font-weight: bold;
    color: rgb(27, 29, 146) !important;
  }
</style>