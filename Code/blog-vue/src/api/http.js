import axios from 'axios';

//创建 axios实例
const service = axios.create({
    //baseURL: "http://192.168.68.116:8001",
    baseURL: "/api",
});

axios.defaults.timeout = 5000; //超时时间是5秒
axios.defaults.withCredentials = true; //允许跨域
//Content-type 响应头
//设置默认提交 json，把数据对象序列化成 json 字符串
axios.defaults.headers['Content-Type'] = 'application/json';
axios.defaults.transformRequest = data => JSON.stringify(data); // 把数据对象序列化成 json 字符串

//axios.defaults.withCredentials = true; // 跨域访问需要发送cookie

/*
// request添加请求拦截器，在请求头中加token ，==不用这个==
axios.interceptors.request.use(config => {
    // 从localStorage中获取token
    let token = localStorage.getItem('Authorization');
    // 如果有token, 就把token设置到请求头中Authorization字段中
    if(token != null){
        config.headers.Authorization = token;
    }
    return config;
}, error => {
    return Promise.reject(error);
});
*/

//response响应拦截器，对响应的数据做点什么
axios.interceptors.response.use(
    response => {
        //如果response里面的有数据那就返回
        if (response.code == 20000) {
            return Promise.resolve(response);
        } else {
            return Promise.reject('error');
        }
        //如果前端拿到状态码为401，就清除token信息并跳转到登录页面
        if(response.data == 401){
            localStorage.removeItem('Authorization');
            this.$router.name('LoginPage');
        }

    },
    error => {
        return Promise.reject(error)
    }
);

//================博客内容相关，在后台的 8081端口==================

/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function blogGet(url, params) {
    return new Promise((resolve, reject) => {
        service.get(url, {
            params,
        }).then(res => {
            resolve(res.data)
        }).catch(err => {
            reject(err)
        })
    })
}
/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 **/
export function blogPost(url, params) {
    return new Promise((resolve, reject) => {
        service.post(url, params).then((res) => {
            resolve(res.data)
        }).catch((err) => {
            reject(err)
        })
    })
}

/**
 * put方法，对应put请求
 * @param {String} url [请求的url地址]
 **/
export function blogPut(url) {
    return new Promise((resolve, reject) => {
        service.put(url).then((res) => {
            resolve(res.data)
        }).catch((err) => {
            reject(err)
        })
    })
}

/**
 * delete方法，对应put请求
 * @param {String} url [请求的url地址]
 **/
export function blogDelete(url) {
    return new Promise((resolve, reject) => {
        service.delete(url).then((res) => {
            resolve(res.data)
        }).catch((err) => {
            reject(err)
        })
    })
}