package com.chen.context.controller.outside;
import com.alibaba.fastjson.JSONObject;
import com.chen.common.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 心灵鸡汤api
 * 调用别人的api返回数据
 * @author: chen
 * @date: 2021/4/16 17:15
 * @description: 思路清晰
 */
@RestController
@RequestMapping("/openApi")
public class soup {
    @GetMapping("soup")
    public JSONObject getSoup(){
        //从链接获取到的数据
        JSONObject jsonObject = null;
        HttpsURLConnection httpsURLConnection = null;

        try {
            //创建URL对象
            URL url = new URL("https://www.cxyxiaowu.com/soup/api/jitang.php");

            //打开URL链接获得 HttpURLConnection
            httpsURLConnection = (HttpsURLConnection) url.openConnection();

            //设置请求方式-"GET"方式
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setUseCaches(false); //是否使用缓存
            httpsURLConnection.setInstanceFollowRedirects(false); //是否自动执行 HTTP 重定向
            httpsURLConnection.setConnectTimeout(10000); //设置超时时间
            //设置通用属性，如 Accept、Connection、User-Agent、Content-Type、Cookie 等请求头header信息
            httpsURLConnection.setRequestProperty("Accept","*/*");   //接收任意类型资源
            //http连接的两种方式：串行连接，持久连接，，持久连接可以减少下载的时间，但服务器必须要返回响应头Content-Length，返回内容的长度
            httpsURLConnection.setRequestProperty("connection","Keep-Alive");    //选择持久连接
            httpsURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
            httpsURLConnection.setRequestProperty("content-Type", "text/json;charset=UTF-8");
            httpsURLConnection.setRequestProperty("origin","http://live.b-bs.cn/");


            // 连接
            httpsURLConnection.setDoOutput(true);   //向httpUrlConnection输出数据
            httpsURLConnection.setDoInput(true); //从httpUrlConnection读入数据
            httpsURLConnection.connect();

            // 得到响应状态码 responseCode
            int responseCode = httpsURLConnection.getResponseCode();

            String data = "";
            if(responseCode == 200){    //正常响应
                //从数据流中读取响应信息
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String lineData;
                while ((lineData = bufferedReader.readLine()) != null){   //依次按行读出数据
                    data += lineData;
                }
                bufferedReader.close(); //关闭读取流
            }
            //json字符串转为 json对象{...}
            jsonObject = JSONObject.parseObject(data);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 断开连接，释放资源
            if(null != httpsURLConnection){
                try{
                    httpsURLConnection.disconnect();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }
}
