package com.chen.context.controller;

import com.chen.common.utils.Consts;
import com.chen.common.utils.R;
import com.chen.context.entity.BlBlog;
import com.chen.context.entity.Message;
import com.chen.context.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/blog/message")
public class MessageController {

    @Autowired
    MessageService messageService;    //注入messageService

    /**
     * 添加留言
     *
     * @author chen
     */
    @PostMapping("save")
    public R saveMessage(@RequestBody Message message) {

        if (message.getNickname() == null || message.getNickname() == ""){
            message.setNickname("无名氏");
        }
        if (message.getUrl() == null || message.getUrl() == ""){
            message.setUrl("https://github.com");
        }
        messageService.save(message);

        return R.ok().data(Consts.CODE,1);
    }

    /**
     * 获取留言
     *
     * @author chen
     */
    @GetMapping("getMsgList")
    public R getMessageList() {

        List<Message> list = messageService.list();

        return R.ok().data(Consts.CODE,1).data("list", list);
    }
}

