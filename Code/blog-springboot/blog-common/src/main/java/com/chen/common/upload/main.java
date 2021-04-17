package com.chen.common.upload;

import com.chen.common.utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author chen
 */
@RestController
@RequestMapping("/main")
public class main {

    /**
     * 文章分类图片（ClassificationImg）资源上传接口
     * @param files
     * @param request
     * @return
     * 注意，前端数据传输name值必须和@RequestParam("files")值对应，如：
     *  name = "files" 和 @RequestParam("files")
     */
    @PostMapping("/uploadClassificationImg")
    public R uploadClassificationImg(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        //路径
        //String realPath = request.getServletContext().getRealPath("/img");
        //File folder = new File(realPath);
        String realPath = null;
        /**
         * 文件地址定位，兼容windows和Linux不同平台的写法
         * System.getProperty("user.dir")在Windows下指向的是项目的根目录，
         * 在Linux下指向的是 / 目录，测试如下（docker的openjdk11镜像中部署测试）：
         * root@7f93e1991064:/# find / -name 'img'
         *          /usr/src/myapp/resources/img
         *          /resources/img
         */
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {   //windows
             realPath = System.getProperty("user.dir") + System.getProperty("file.separator") +
                            "resources" + System.getProperty("file.separator");
        }else{  //linux or mac
            realPath = System.getProperty("user.dir") +
                            "usr" + System.getProperty("file.separator") +
                            "src" + System.getProperty("file.separator") +
                            "myapp" + System.getProperty("file.separator") +
                            "resources" + System.getProperty("file.separator");
        }
        //找到项目根目录下的resources文件夹，没有手动创建的话找到的是临时路径
        File directory = new File(realPath + "img/ClassificationImg");
        String basePath = null;
        try {
            //获取到总资源存储位置
            basePath = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //如果存储资源的文件夹（如：img,video,mp3...）不存在那就新建文件夹
        File folder = new File(basePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //判断file数组不能为空并且长度大于0
        if(files != null && files.length>0){
            //循环获取file数组中得文件
            for(int i = 0;i < files.length;i++){
                MultipartFile file = files[i];
                //获取上传的文件名
                String Name = file.getOriginalFilename();
                System.out.println(Name);
                //String oldName = file.getOriginalFilename();
                //或者将文件名改为UUID + 转换后的值
                //String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));

                //获取上传的文件后缀，endsWith() 方法用于测试字符串是否以指定的后缀结束
                //if(Name.endsWith(".jpg")){ ... }

                try {
                    file.transferTo(new File(folder, Name));
                    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/resources/" + Name;
                    return R.ok().data("name",Name).data("url",url);
                } catch (IOException e) {
                    return R.error();
                }
            }
        }
        return R.ok();
    }

    /**
     * 文章内容的图片（ContentImg）资源上传接口
     * 以后新文章内容都放在img/ContentImg/Base这个目录里了
     * @param files
     * @param request
     * @return
     */
    @PostMapping("/uploadContentImg")
    public R uploadContentImg(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        String realPath = null;
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {   //windows
            realPath = System.getProperty("user.dir") + System.getProperty("file.separator") +
                    "resources" + System.getProperty("file.separator");
        }else{  //linux or mac
            realPath = System.getProperty("user.dir") +
                    "usr" + System.getProperty("file.separator") +
                    "src" + System.getProperty("file.separator") +
                    "myapp" + System.getProperty("file.separator") +
                    "resources" + System.getProperty("file.separator");
        }
        //找到项目根目录下的resources文件夹，没有手动创建的话找到的是临时路径
        File directory = new File(realPath + "img/ContentImg/Base");
        String basePath = null;
        try {
            //获取到总资源存储位置
            basePath = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //如果存储资源的文件夹（如：img,video,mp3...）不存在那就新建文件夹
        File folder = new File(basePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //判断file数组不能为空并且长度大于0
        if(files != null && files.length>0){
            //循环获取file数组中得文件
            for(int i = 0;i < files.length;i++){
                MultipartFile file = files[i];
                //获取上传的文件名
                String Name = file.getOriginalFilename();
                try {
                    file.transferTo(new File(folder, Name));
                    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/resources/" + Name;
                    return R.ok().data("name",Name).data("url",url);
                } catch (IOException e) {
                    return R.error();
                }
            }
        }
        return R.ok();
    }

    /**
     * 文章封面图片（PageImg）资源上传接口
     * @param files
     * @param request
     * @return
     */
    @PostMapping("/uploadPageImg")
    public R PageImg(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        String realPath = null;
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {   //windows
            realPath = System.getProperty("user.dir") + System.getProperty("file.separator") +
                    "resources" + System.getProperty("file.separator");
        }else{  //linux or mac
            realPath = System.getProperty("user.dir") +
                    "usr" + System.getProperty("file.separator") +
                    "src" + System.getProperty("file.separator") +
                    "myapp" + System.getProperty("file.separator") +
                    "resources" + System.getProperty("file.separator");
        }
        //找到项目根目录下的resources文件夹，没有手动创建的话找到的是临时路径
        File directory = new File(realPath + "img/PageImg");
        String basePath = null;
        try {
            //获取到总资源存储位置
            basePath = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //如果存储资源的文件夹（如：img,video,mp3...）不存在那就新建文件夹
        File folder = new File(basePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //判断file数组不能为空并且长度大于0
        if(files != null && files.length>0){
            //循环获取file数组中得文件
            for(int i = 0;i < files.length;i++){
                MultipartFile file = files[i];
                //获取上传的文件名
                String Name = file.getOriginalFilename();
                try {
                    file.transferTo(new File(folder, Name));
                    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/resources/" + Name;
                    return R.ok().data("name",Name).data("url",url);
                } catch (IOException e) {
                    return R.error();
                }
            }
        }
        return R.ok();
    }

}
