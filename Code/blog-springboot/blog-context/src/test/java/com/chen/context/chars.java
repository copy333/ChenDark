package com.chen.context;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.context.entity.BlBlog;
import com.chen.context.service.BlBlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class chars {

    @Autowired
    BlBlogService blBlogService;

    /**
     * 正则表达式
     * 基础测试：判断字符串中是否存在指定字符
     */
    @Test
    public void baseTest() {
        String content = "hello 张三" + "![](www.baidu.com/1.png)";
        String pattern = ".*三.*";
        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("content字符串中包含了‘三’：" + isMatch);
    }

    /**
     * 正则表达式
     * 捕获组：把多个字符当一个单独单元进行处理的方法，通过对括号内的字符分组来创建。
     * 捕获组是通过从左至右计算其开括号来编号。
     * 例如，在表达式（（A）（B（C））），有四个这样的组：
     * ((A)(B(C)))
     * (A)
     * (B(C))
     * (C)
     * 案例：从"A B C 123D 456 e 789"字符串中找到数字串：
     */
    @Test
    public void group() {
        String content = "A B C 123D 456 e 789";
        String pattern = "(\\D*)(\\d+)(.*)";
        //创建 Pattern 对象
        Pattern p = Pattern.compile(pattern);
        //创建 matcher 对象
        Matcher matcher = p.matcher(content);
        if (matcher.find()) {
            //group(0)代表整个表达式
            System.out.println("发现值（group0）：" + matcher.group(0));
            System.out.println("发现值（group1）：" + matcher.group(1));
            System.out.println("发现值（group2）：" + matcher.group(2));
            System.out.println("发现值（group3）：" + matcher.group(3));
        } else {
            System.out.println("没有匹配到内容");
        }
    }

    /**
     * 正则表达式：Matcher 类的 replaceAll 方法
     * replaceFirst 替换首次匹配，replaceAll 替换所有匹配。
     * 回到第一个例子，实现替换![](1.png)内容
     * 换成![](www.baidu.com/1.png)
     */
    @Test
    public void mdTestOne() {
        String content =
                "hello 张三\n" +
                        "![](1.png)\n" +
                        "李四何在？\n" +
                        "![](f20.png)";

        //被替换内容，正则匹配 ![]( 这段内容
        String replacedContent = "!\\[\\]\\(";
        //将内容变成
        String replaceContent = "![](www.baidu.com/";

        //创建 Pattern 对象
        Pattern p = Pattern.compile(replacedContent);
        //创建 matcher 对象
        Matcher matcher = p.matcher(content);
        content = matcher.replaceAll(replaceContent);
        System.out.println(content);
    }

    /**
     * 正则表达式：还是 Matcher 类的 replaceAll 方法
     * 回到上一个例子，实现替换![](www.baidu.com/1.png)内容
     * 换成![](www.chen.com/1.png)
     */
    @Test
    public void mdTestTwo() {
        String content =
                "hello 张三\n" +
                        "![](www.baidu.com/1.png)\n" +
                        "李四何在？\n" +
                        "![](www.baidu.com/f20.png)";

        //被替换内容，正则匹配 ![](www.baidu.com/ 这段内容
        String replacedContent = "!\\[\\]\\(.*?/";
        //将内容变成
        String replaceContent = "![](www.chen.com/";

        //创建 Pattern 对象
        Pattern p = Pattern.compile(replacedContent);
        //创建 matcher 对象
        Matcher matcher = p.matcher(content);
        content = matcher.replaceAll(replaceContent);
        System.out.println(content);
    }

    @Test
    public void hh(){
        String title = "vuex4.x与vue-router4.x";
        QueryWrapper<BlBlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("blog_content").eq("blog_title",title);
        blBlogService.getOne(queryWrapper);
        System.out.println();
    }


}
