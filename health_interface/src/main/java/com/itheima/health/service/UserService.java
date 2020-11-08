package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 根据登陆用户名称查询用户权限信息
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult<User> findPage(QueryPageBean queryPageBean);

    /**
     * 新增用户
     * @param user
     * @param roleIds
     */
    void add(User user, Integer[] roleIds);

    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    User findById(int id);

    /**
     * 通过id查询选中的角色ids
     * @param id
     * @return
     */
    List<Integer> findRoleIdsByUserId(int id);

    /**
     * 编辑用户
     * @param user
     * @param roleIds
     */
    void edit(User user, Integer[] roleIds);

    /**
     * 通过id删除用户
     * @param id
     */
    void deleteById(int id) throws HealthException;
}
