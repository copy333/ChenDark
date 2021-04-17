<template>
    <!-- <div class="showImg">
        <img v-lazy="lazyOptions.src" />
    </div> -->
    <div>
        <span id="hello">
            <span id="autor">chendark</span> : 这是一个404页面~
        </span>
        <div class="showImg">
            <img src="../assets/img/404.gif" />
        </div>
    </div>
</template>

<script>
/*
import { reactive } from 'vue'
export default {
    setup(){
        const lazyOptions = reactive({
            src: 'https://w.wallhaven.cc/full/p2/wallhaven-p26kk3.jpg'
        })
        return {lazyOptions}
    }
}
*/
</script>

<style scope>
body{
    background-color: #271840;
}
#hello{
    font-size: 20px;
    color: white;
}
#autor{
    font-size: 32px;
    color: aqua;
}
.showImg{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);    /* 设置div的水平和垂直位置的偏移量为-50% */
}

/*
img[lazy=loaded]{
    width: 100%;
    height: 100%;
}*/

</style>