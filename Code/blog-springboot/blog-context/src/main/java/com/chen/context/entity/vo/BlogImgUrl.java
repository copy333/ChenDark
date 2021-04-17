package com.chen.context.entity.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlogImgUrl {

    @ApiModelProperty(value = "文章内的图片服务器地址")
    private String url;
}
