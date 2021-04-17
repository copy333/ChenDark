package com.chen.context.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.common.utils.Consts;
import com.chen.common.utils.R;
import com.chen.common.utils.ValidateImageCodeUtils;
import com.chen.context.entity.BlBlog;
import com.chen.context.entity.vo.BlogListTitleVo;
import com.chen.context.entity.vo.Iscode;
import com.chen.context.service.BlBlogService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/blog")
public class BlBlogController {

    @Autowired
    BlBlogService blBlogService;  //注入 BlBlogService

    @Autowired
    @Qualifier(value = "restHighLevelClient")
    private RestHighLevelClient client;

    //验证码
    private String securityCode;

    /**
     * 查询所有博客
     *
     * @author chen
     */
    @GetMapping("listAll")
    public R list() {
        List<BlBlog> list = blBlogService.list();
        return R.ok().data("data", list);
    }

    /**
     * 查询所有博客（但是不会获取文章的内容（blog_content）字段）
     * 只有点击进了某个文章才通过 id 来定向查询 文章内容（blog_content）字段
     * "blog_id","blog_title","blog_image","blog_type","blog_remark","gmt_create","gmt_modified"
     * 这里只查询了博客id，博客标题，博客图片，博客分类，博客简介，博客的创建时间和更新时间
     *
     * @author chen
     */
    @GetMapping("getBlogListNotCont")
    public R getBlogListNotCont() {
        List<BlBlog> list = blBlogService.listNotCont();
        return R.ok().data("data", list);
    }

    /**
     * 使用博客标题作为查询条件，查询出所有匹配的列表，但是不会获取文章的内容（blog_content）字段）
     *
     * @author chen
     */
    @PostMapping("listTitle")
    public R listTitle(@RequestBody BlogListTitleVo vo) {
        //条件构造器
        QueryWrapper<BlBlog> queryWrapper = new QueryWrapper<>();
        if (vo != null) {
            queryWrapper.select("blog_id", "blog_title", "blog_image", "blog_type", "blog_remark", "gmt_create", "gmt_modified").like("blog_title", vo.getBlogTitle());
            List<BlBlog> list = blBlogService.list(queryWrapper);
            return R.ok().data("data", list);
        } else {
            return R.error();
        }
    }

    /**
     * 根据id查询博客文章信息
     *
     * @author chen
     */
    @GetMapping("blogByid/{id}")
    public R blogByid(@PathVariable("id") String id) {
        BlBlog blog = blBlogService.getById(id);
        return R.ok().data("data", blog);    //把文章对象传回前端
    }

    /**
     * 根据分类id查询博客文章信息
     * "blog_id","blog_title","blog_image","blog_remark","gmt_create","gmt_modified"
     * 这里只查询了博客id，博客标题，博客图片，博客简介，博客的创建时间和更新时间
     *
     * @author chen
     */
    @GetMapping("getBlogByTypeId/{id}")
    public R getBlogByTypeId(@PathVariable("id") String id) {
        QueryWrapper<BlBlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("blog_id", "blog_title", "blog_image", "blog_remark", "gmt_create", "gmt_modified").eq("blog_type", id);
        List<BlBlog> list = blBlogService.list(queryWrapper);
        return R.ok().data("info", list);    //把文章对象传回前端
    }

    /**
     * 根据id判断是否存在该博客文章
     *
     * @author chen
     */
    @GetMapping("checkBlogByid/{id}")
    public R checkBlogByid(@PathVariable("id") String id) {
        BlBlog blBlog = blBlogService.checkBlogByid(id);

        if (blBlog != null) {
            return R.ok().data(Consts.CODE, 1);  //如果通过id找到了那就返回 code === 1
        } else {
            return R.error().data(Consts.CODE, 0);
        }
    }

    /**
     * 查询博客文章信息
     * "blog_id","blog_title","gmt_create"
     * 这里只查询了博客id，博客标题 和 博客的创建时间
     * 并且按照"创建时间字段"逆序排列
     *
     * @author chen
     */
    @GetMapping("getAllBlogBaseInfo")
    public R getAllBlogBaseInfo() {
        QueryWrapper<BlBlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("blog_id", "blog_title","gmt_create").orderByDesc("gmt_create");
        List<BlBlog> list = blBlogService.list(queryWrapper);
        return R.ok().data("list", list);    //把文章对象传回前端
    }

    /**
     * 全站搜索，elasticsearch
     *
     * @author chen
     */
    @GetMapping("searchES/{content}")
    public R searchES(@PathVariable("content") String content){
        //搜索请求
        SearchRequest searchRequest = new SearchRequest("blog");

        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("blogContent")  //blogContent字段高亮
            .requireFieldMatch(true)    //多个高亮显示
            .preTags("<span style='color:red'>")
            .postTags("</span>");

        searchSourceBuilder.highlighter(highlightBuilder);


        //查询条件，matchAllQuery匹配所有文件
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("blogContent", content);
        searchSourceBuilder.query(matchQueryBuilder);

        searchSourceBuilder.timeout(new TimeValue(60));

        //将查询条件放入请求
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Object> list = new ArrayList<>();
        Map<String, HighlightField> highlightFields = null;
        for (SearchHit documentFields:response.getHits().getHits()) {
            //获取高亮字段
            highlightFields = documentFields.getHighlightFields();
            //获取到要高亮的字段内容
            HighlightField blogContent = highlightFields.get("blogContent");
            Text[] fragments = blogContent.getFragments();
            String highlightInfo = null;
            //遍历高亮集合，取出高亮片段
            for (Text t: fragments) {
                highlightInfo = highlightInfo + t +"<br/><div style='height:20px'></div>";
            }

            documentFields.getSourceAsMap().put("highlightInfo", highlightInfo);
            //文章信息
            list.add(documentFields.getSourceAsMap());
        }
        if (list.isEmpty()){
            return R.error();
        }else {
            return R.ok().data("list",list);
        }
    }

    /**
     * 获取验证码
     * @param session
     * @param response
     * @throws IOException
     */
    @GetMapping("/getVcode")
    public void getVerification(HttpSession session, HttpServletResponse response) throws IOException {
        //生成验证码
        securityCode = ValidateImageCodeUtils.getSecurityCode();
        //生成验证码图片
        BufferedImage image = ValidateImageCodeUtils.createImage(securityCode);
        //存入session
        session.setAttribute("code",securityCode);
        //响应图片
        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIO.write(image,"png",os);
    }

    /**
     * 验证码是否输入正确
     * @param iscode
     * @return
     */
    @PostMapping("/iscode")
    public R getVcodeStatus(@RequestBody Iscode iscode) {

        if (iscode.getCode() == null){
            return R.error();
        }

        String code = iscode.getCode();

        boolean flag = code.equals(securityCode);
        if (flag) {
            return R.ok().data(Consts.CODE,1);
        }
        return R.error().data(Consts.CODE,0);
    }

    /**
     * 退出登录后跳转的链接
     *
     * @author chen
     */
    @GetMapping("/333")
    public R Afterlogout() {
        return R.ok().data(Consts.CODE,1).data("msg","退出登录");
    }

}