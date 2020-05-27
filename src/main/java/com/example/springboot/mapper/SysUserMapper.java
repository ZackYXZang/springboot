package com.example.springboot.mapper;

import com.example.springboot.pojo.SysUser;
import java.util.List;

public interface SysUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated
     */
    int insert(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated
     */
    List<SysUser> selectAll();

    SysUser findUserById(String id);

    SysUser findByUsername(String name);
}