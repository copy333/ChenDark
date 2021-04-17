package com.chen.context.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 帖子类型表
 * </p>
 *
 * @author chen
 * @since 2021-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="BlType对象", description="帖子类型表")
public class BlType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类id")
    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId;

    @ApiModelProperty(value = "分类名称")
    private String typeName;

    @ApiModelProperty(value = "帖子数")
    private Integer typeBlogCount;

    @ApiModelProperty(value = "该分类的图片")
    private String typeImg;
}
