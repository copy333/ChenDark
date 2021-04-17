package com.chen.context.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.context.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2021-04-13
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 从角色权限关联表 con_role_user 中获取权限：
     * 根据用户名（即username字段）获取用户的角色信息（即 role_name ）
     * mapper.xml的SQL语句会进行如下判断：
     *      在 jh_role（角色表）, jh_user（用户表） and con_role_user（用户&角色连接表）查询 role_name字段
     *      查询条件是：
     *          1.传进来的uername要和 jh_user表的username相等。
     *          2.jh_user表的 id 要和 con_role_user的 user_id相等。
     *          3.jh_role表的 id 要和 con_role_user的 role_id相等。
     *
     * @return
     */
    List<String> getUserRoleByUsername(@Param("username") String username);
}
