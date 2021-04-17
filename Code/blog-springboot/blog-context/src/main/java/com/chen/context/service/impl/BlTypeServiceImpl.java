package com.chen.context.service.impl;

import com.chen.context.entity.BlType;
import com.chen.context.mapper.BlTypeMapper;
import com.chen.context.service.BlTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 帖子类型表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-02-14
 */
@Service
public class BlTypeServiceImpl extends ServiceImpl<BlTypeMapper, BlType> implements BlTypeService {
    @Override
    public List<BlType> listAll() {
        List<BlType> typeList = baseMapper.listAll();
        return typeList;
    }

    @Override
    public Map<String, Object> getTypeMap() {
        //baseMapper是 BlTypeService  extends IService<BlType> 自行封装好的 BlTypeMapper 对象
        //和
        //@Autowired
        //BlTypeMapper blTypeMapper; 功能一样
        List<BlType> blTypes = baseMapper.listAll();
        //通过 流的形式取出 TypeId和TypeName 并封装成 Map形式
        Map<String, Object> collect = blTypes.stream().collect(Collectors.toMap(k -> k.getTypeId().toString(), v -> v.getTypeName()));

        return collect; //返回map，Map<String, Object>要和封装的 R对象方法看齐
    }

    @Override
    public Map<String, Object> getContraryTypeMap() {
        List<BlType> blTypes = baseMapper.listAll();
        //通过 流的形式取出 TypeName和TypeId 并封装成 Map形式
        Map<String, Object> collect = blTypes.stream().collect(Collectors.toMap(k -> k.getTypeName(), v -> v.getTypeId()));

        return collect; //返回map，Map<String, Object>要和封装的 R对象方法看齐
    }


}
