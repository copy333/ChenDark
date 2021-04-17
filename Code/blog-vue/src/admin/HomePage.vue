<template>
  <!-- 后台系统主页 -->
  <p class="home-title"><i class="el-icon-s-promotion"></i>心灵鸡汤</p>
  <div class="home-cont">  
    <span>{{soup}}</span>
  </div>

  <div class="home-footer">
    <p><i class="el-icon-bell"></i>告示：</P>
    <span>愿代码永无BUG！</span>
  </div>

</template>

<script>
import {getSoup} from "@/api/index"

export default {
  data(){
    return{
      soup: ""  //心灵鸡汤
    }
  },
  methods:{
    init(){
      let _this = this;
      //通过java请求其他网站的公开API，这个API是 言溪API http://live.b-bs.cn/ ，百度搜到的
      getSoup().then((response)=>{
        if(response.status === 1){
          _this.soup = response.data.content.content;
        }
      })
    }
  },
  mounted(){
    this.init();
  }
}
</script>

<style scoped>
.home-title{
  font-size: 25px;
  color: blue;
}
.home-cont{
  position: absolute;
  height: 60px;
  width: 100%;
  left: 0;
  top: 20%;
  text-align: center;
}
.home-cont{
    font-size: 38px;
    padding-bottom: 20px;
}
.home-footer{
  position: absolute;
  bottom: 40%;
}
.home-footer p{
  font-size:25px;
  color:blue;
  margin-bottom: 15px;
}
.home-footer span{
  font-size: 22px;
}
</style>