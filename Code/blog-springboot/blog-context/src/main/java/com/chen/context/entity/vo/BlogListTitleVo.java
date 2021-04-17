package com.chen.context.entity.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlogListTitleVo {

    @ApiModelProperty(value = "标题")
    private String blogTitle;
}
