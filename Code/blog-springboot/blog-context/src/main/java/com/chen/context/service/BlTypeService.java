package com.chen.context.service;

import com.chen.context.entity.BlType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 帖子类型表 服务类
 * </p>
 *
 * @author chen
 * @since 2021-02-14
 */
public interface BlTypeService extends IService<BlType> {

    List<BlType> listAll();

    Map<String, Object> getTypeMap();

    Map<String, Object> getContraryTypeMap();


}
