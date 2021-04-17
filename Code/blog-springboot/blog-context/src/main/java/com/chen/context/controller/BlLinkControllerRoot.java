package com.chen.context.controller;


import com.chen.common.utils.Consts;
import com.chen.common.utils.R;
import com.chen.context.entity.BlLink;
import com.chen.context.service.BlLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  友情链接-root权限API
 * </p>
 *
 * @author chen
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/root/link")
public class BlLinkControllerRoot {
    @Autowired
    BlLinkService blLinkService;

    /**
     * 添加友链
     * @param BlLink
     * @return
     */
    @PostMapping("save")
    public R saveLink(@RequestBody BlLink BlLink){
        blLinkService.save(BlLink);

        return R.ok().data(Consts.CODE,1);
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public R deleteLink(@PathVariable("id") Integer id){

        blLinkService.removeById(id);

        return R.ok().data(Consts.CODE,1);
    }

    /**
     * 更新友链
     * @param blLink
     * @return
     */
    @PostMapping("/updateLink")
    public R updateLink(@RequestBody BlLink blLink){

        blLinkService.updateById(blLink);

        return R.ok().data(Consts.CODE,1);
    }
}

