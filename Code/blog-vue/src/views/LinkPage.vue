<template>
  <!-- 友情链接页面 -->
  <!-- banner -->
  <div class="link-banner banner">
    <h1 class="banner-title">友情链接</h1>
  </div>

  <div class="cont">
    <div v-for="item in Linkdata" :key="item">
      <div class="line">
        <el-row :gutter="20" style="display:line-block;">
          <el-col :span="8">
            <div class="autors" @click="goOther(item[0].url)" v-if="item[0] != '空'">
                  <el-image :src="item[0].img" fit="cover" class="autorsLogo"></el-image>
                  <div>
                      <p class="nickname">{{item[0].nickname}}</p>
                      <p class="descInfo">{{item[0].descInfo}}</p>
                  </div>
              </div>
          </el-col>
          <el-col :span="8">
            <div class="autors" @click="goOther(item[1].url)" v-if="item[1] != '空'">
                  <el-image :src="item[1].img" fit="cover" class="autorsLogo"></el-image>
                  <div>
                      <p class="nickname">{{item[1].nickname}}</p>
                      <p class="descInfo">{{item[1].descInfo}}</p>
                  </div>
              </div>
          </el-col>
          <el-col :span="8">
            <div class="autors" @click="goOther(item[2].url)" v-if="item[2] != '空'">
                  <el-image :src="item[2].img" fit="cover" class="autorsLogo"></el-image>
                  <div>
                      <p class="nickname">{{item[2].nickname}}</p>
                      <p class="descInfo">{{item[2].descInfo}}</p>
                  </div>
              </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import { getLinkList } from "@/api/index"

export default ({
  data(){
    return{
      Linkdata: ""
    }
  },
  methods:{
    init(){
      let _this = this;
      let arr = [];
      getLinkList().then((response)=>{
        if(response.data.code === 1){
              arr = response.data.list;
              let a = 0;
              let b = 0;
              let obj = [];
              //将数据分三个为一组进行二次封装，即嵌套数组
              for(;a<arr.length;a+=3){
                let temp = [];
                if(typeof(arr[a]) != "undefined" ){
                  temp.push(arr[a]);
                }else{
                  temp.push("空");
                }
                if(typeof(arr[a+1]) != "undefined" ){
                  temp.push(arr[a+1]);
                }else{
                  temp.push("空");
                }
                if(typeof(arr[a+2]) != "undefined" ){
                  temp.push(arr[a+2]);
                }else{
                  temp.push("空");
                }
                obj[b] = temp;
                b++;
              }
              _this.Linkdata = obj;
        }
      })
      
    },
    goOther(url){
      window.open(url);
    }
  },
  mounted(){
    this.init();
  }
})
</script>

<style scoped>
.link-banner {
  background: url("/api/resources/img/Banner/link.jpg") center
    center / cover no-repeat;
  background-color: #49b1f5;
}

.cont{
  width: 80%;
  margin-left: 10%;
  margin-bottom: 30px;
  box-shadow: 0 0 20px rgb(58, 57, 57);
  border-radius: 13px;
  margin-top: 400px;
}
.cont:hover{
  box-shadow: 0 0 30px rgba(57, 13, 218,0.6);
  transition: all 0.7s;
}


.autors{
    float: left;
    width: 280px;
    height:130px ;
    margin: 30px 30px;;
    border-radius: 10px;
    box-sizing: border-box;
    cursor: pointer;

    /* html5 让显示不完的文字自动省略号 */
    -webkit-line-clamp: 4; /* 限制显示的文本的行数为3*/
    -webkit-box-orient: vertical; /*水平排列<p>，使其不纵向显示*/
    word-break: break-all; /*使<p>中的文字换行*/
    overflow: hidden; /*超出的文字部分隐藏*/
    text-overflow: ellipsis; /*超出的文字部分用...来显示*/
    display: -webkit-box; /*将<p>变成高度定死，宽度自适应的行内块元素*/
}
.autors:hover{
   background-color: chartreuse;
   transition: 0.5s;
    /* cursor: url(/img/link.cur),auto; */
}

.autors .autorsLogo{
    width: 80px;
    height: 80px;
    float: left;
    border-radius: 50%;
     -webkit-border-radius: 50%;
     -moz-border-radius: 50%;
     -o-border-radius: 50%;
     -ms-border-radius: 50%;
    margin: 20px 10px;
    background-size: 100%;
    -webkit-transition: -webkit-transform .5s ease-out;
    -moz-transition: -moz-transform .5s ease-out;
    -o-transition: -o-transform .5s ease-out;
    -ms-transition: -ms-transform .5s ease-out
}

.autors:hover .autorsLogo {
    transform: rotateZ(360deg);
    -webkit-transform: rotateZ(360deg);
    -moz-transform: rotateZ(360deg);
    -o-transform: rotateZ(360deg);
    -ms-transform: rotateZ(360deg);
}

.autors .autorsLogo:hover {
    box-shadow: 0 0 20px #fff;
    border: 2px #fff solid;
}
.autors .nickname{
  padding-top: 25px;
  padding-right: 10px;
  font-size: 20px;
  font-weight: bold;
}
.autors .descInfo{
  padding-top: 10px;
  font-size: 13px;
  padding-right: 10px;
}
</style>