package com.chen.context.service;

import com.chen.context.entity.BlBlog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客表 服务类
 * </p>
 *
 * @author chen
 * @since 2021-02-14
 */
public interface BlBlogService extends IService<BlBlog> {


    List<BlBlog> getBlogTypeCount();

    List<BlBlog> listNotCont();

    BlBlog checkBlogByid(String id);

}
