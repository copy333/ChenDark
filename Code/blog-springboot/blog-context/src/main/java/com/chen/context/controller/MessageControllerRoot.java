package com.chen.context.controller;

import com.chen.common.utils.Consts;
import com.chen.common.utils.R;
import com.chen.context.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 留言后台接口-删除留言功能
 *
 * @author chen
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/root/message")
public class MessageControllerRoot {

    @Autowired
    MessageService messageService;    //注入messageService

    /**
     * 删除留言
     *
     * @author chen
     */
    @DeleteMapping("delete/{id}")
    public R saveMessage(@PathVariable("id") Integer id) {

        messageService.removeById(id);

        return R.ok().data(Consts.CODE,1);
    }
}

