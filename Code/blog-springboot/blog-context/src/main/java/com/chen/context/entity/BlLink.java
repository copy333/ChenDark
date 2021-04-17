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
 *  友情链接
 * </p>
 *
 * @author chen
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="BlLink对象", description="")
public class BlLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "简介")
    private String descInfo;

    @ApiModelProperty(value = "头像地址")
    private String img;

    @ApiModelProperty(value = "主页地址")
    private String url;

}
