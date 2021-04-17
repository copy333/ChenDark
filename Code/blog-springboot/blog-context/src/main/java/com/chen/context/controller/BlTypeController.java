package com.chen.context.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chen.common.utils.Consts;
import com.chen.common.utils.R;
import com.chen.context.entity.BlBlog;
import com.chen.context.entity.BlType;
import com.chen.context.service.BlTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 帖子类型表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/blog/type")
public class BlTypeController {

    @Autowired
    BlTypeService blTypeService;    //注入BlTypeService

    /**
     * 全部分类信息
     * @return
     */
    @GetMapping("list")
    public R list(){
        List<BlType> typeList = blTypeService.listAll();    //查看博客类型全部的内容
        return R.ok().data("typeList",typeList);
    }

    /**
     * 获取分类信息的 id:name
     * @return
     */
    @GetMapping("getTypeMap")
    public R getTypeMap(){
        Map<String, Object> typeMap = blTypeService.getTypeMap();
        return R.ok().data(typeMap);
    }

    /**
     * 获取分类信息的 name:id
     * @return
     */
    @GetMapping("getContraryTypeMap")
    public R getContraryTypeMap(){
        Map<String, Object> contraryTypeMap = blTypeService.getContraryTypeMap();
        return R.ok().data(contraryTypeMap);
    }
}

