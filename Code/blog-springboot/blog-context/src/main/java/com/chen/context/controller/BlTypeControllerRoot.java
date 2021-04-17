package com.chen.context.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chen.common.utils.Consts;
import com.chen.common.utils.R;
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
@RequestMapping("/root/type")
public class BlTypeControllerRoot {

    @Autowired
    BlTypeService blTypeService;    //注入BlTypeService

    /**
     * 添加分类
     * @param blType
     * @return
     */
    @PostMapping("saveBlogType")
    public R saveBlogType(@RequestBody BlType blType){
        //mybatis-plus 提供的save接口，插入一条记录，只需要传入一个实体类对象
        boolean b = blTypeService.save(blType);
        if (b) return R.ok().data(Consts.CODE,1);   //如果插入成功就返回一个成功的消息
        else return R.error();
    }

    /**
     * 修改博客分类
     * @author chen
     */
    @PostMapping("updateBlogType")
    public R updateBlogType(@RequestBody BlType blType){
        //mybatis-plus 提供的save接口，插入一条记录，只需要传入一个实体类对象
        boolean b = blTypeService.updateById(blType);
        if (b) return R.ok().data(Consts.CODE,1);   //如果插入成功就返回一个成功的消息
        else return R.error();
    }

    /**
     * 删除博客分类
     * @author chen
     */
    @GetMapping("delete/{id}")
    public R delete(@PathVariable("id") String id){
        blTypeService.removeById(id);
        return R.ok().data(Consts.CODE,1);
    }

    /**
     * 添加分类文章数量 +1
     * @author chen
     */
    @GetMapping("typeCount/{id}")
    public R typeCount(@PathVariable("id") String id){
        BlType blType = blTypeService.getById(id);  //先通过传来的 分类id 得到BlType对象
        Integer typeBlogCount = blType.getTypeBlogCount();
        typeBlogCount++;    //为传来id的分类文章数量+1
        blType.setTypeBlogCount(typeBlogCount);

        UpdateWrapper<BlType> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("type_id",id); // 实现 where type_id = id
        blTypeService.update(blType,updateWrapper);

        return R.ok().data(Consts.CODE,1);
    }

    /**
     * 减少分类文章数量 -1
     * @author chen
     */
    @GetMapping("subTypeCount/{id}")
    public R subTypeCount(@PathVariable("id") String id){
        BlType blType = blTypeService.getById(id);  //先通过传来的 分类id 得到BlType对象
        Integer typeBlogCount = blType.getTypeBlogCount();
        typeBlogCount--;    //为传来id的分类文章数量-1
        blType.setTypeBlogCount(typeBlogCount);

        UpdateWrapper<BlType> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("type_id",id); // 实现 where type_id = id
        blTypeService.update(blType,updateWrapper);

        return R.ok().data(Consts.CODE,1);
    }
}

