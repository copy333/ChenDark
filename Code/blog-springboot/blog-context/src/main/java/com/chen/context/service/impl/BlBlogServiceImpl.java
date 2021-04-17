package com.chen.context.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.context.entity.BlBlog;
import com.chen.context.mapper.BlBlogMapper;
import com.chen.context.service.BlBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-02-14
 */
@Service
public class BlBlogServiceImpl extends ServiceImpl<BlBlogMapper, BlBlog> implements BlBlogService {

    @Override
    public List<BlBlog> getBlogTypeCount() {
        //条件构造器
        QueryWrapper<BlBlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("blog_type");    //只查询 字段 为 blogType 的值，即 分类名
        List<BlBlog> blogs = baseMapper.selectList(queryWrapper);
        return blogs;
    }

    @Override
    public List<BlBlog> listNotCont() {
        //条件构造器
        QueryWrapper<BlBlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("blog_id","blog_title","blog_image","blog_type","blog_remark","gmt_create","gmt_modified").orderByDesc("gmt_create");
        List<BlBlog> blogs = baseMapper.selectList(queryWrapper);
        return blogs;
    }

    @Override
    public BlBlog checkBlogByid(String id) {
        //条件构造器
        QueryWrapper<BlBlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("blog_id").eq("blog_id",id);
        BlBlog blBlog = baseMapper.selectOne(queryWrapper);
        return blBlog;
    }
}
