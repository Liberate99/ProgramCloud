package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xjz
 * @since 2017-06-30
 */
public interface UserDao extends BaseMapper<User> {
    /**
     * 根据Id查找用户，并关联出用户的角色类型
     * @param userId
     * @return
     */
    @Select("SELECT u.id,u.token,u.login_name as loginName,u.identity_role as identityRole," +
            "  u.identity_number as identityNumber,u.name,u.gender,u.email,u.phone,u.mobile," +
            "  u.photo,u.login_ip as loginIp,u.login_date as loginDate,u.login_flag as loginFlag," +
            "  u.create_by as createBy,u.create_date as createDate,u.update_by as updateBy,u.update_date as updateDate," +
            "  u.reservation,u.remarks,u.del_flag as delFlag" +
            "  ,r.role_type as roleType FROM sys_user u" +
            "  LEFT JOIN sys_user_role ur ON  u.id = ur.user_id" +
            "  LEFT JOIN sys_role r ON r.id  = ur.role_id where u.id= #{userId}")
    User getUserWithRoleTypeById(@Param("userId") String userId);

}