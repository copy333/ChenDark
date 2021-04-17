package com.chen.context.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.common.utils.Consts;
import com.chen.common.utils.R;
import com.chen.context.entity.BlLink;
import com.chen.context.service.BlLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  友情链接-公共API
 * </p>
 *
 * @author chen
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/blog/link")
public class BlLinkController {

    @Autowired
    BlLinkService blLinkService;

    /**
     * 获取友链列表
     * @return
     */
    @GetMapping("getLinkList")
    public R getList(){
        List<BlLink> list = blLinkService.list();

        return R.ok().data(Consts.CODE,1).data("list",list);
    }
}

