package com.chen.context.mapper;

import com.chen.context.entity.BlType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 帖子类型表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2021-02-14
 */
public interface BlTypeMapper extends BaseMapper<BlType> {

    List<BlType> listAll();
}

