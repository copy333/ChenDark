package com.chen.context.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 博客表
 * </p>
 *
 * @author chen
 * @since 2021-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="BlBlog对象", description="博客表")
public class BlBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "帖子id")
    @TableId(value = "blog_id", type = IdType.ASSIGN_ID)
    private String blogId;

    @ApiModelProperty(value = "标题")
    private String blogTitle;

    @ApiModelProperty(value = "封面")
    private String blogImage;

    @ApiModelProperty(value = "帖子内容")
    private String blogContent;

    @ApiModelProperty(value = "文章图片仓库")
    private String blogContentImg;

    @ApiModelProperty(value = "博客分类")
    private Integer blogType;

    @ApiModelProperty(value = "简介")
    private String blogRemark;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
