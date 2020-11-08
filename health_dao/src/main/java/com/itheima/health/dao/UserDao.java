package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    /**
     * 根据登陆用户名称查询用户权限信息
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 分页条件查询
     * @param queryString
     * @return
     */
    Page<User> findByCondition(String queryString);

    /**
     * 新增用户
     * @param user
     */
    void add(User user);

    /**
     * 添加用户与角色的关系
     * @param id
     * @param roleId
     */
    void addUserRoleIds(@Param("user_id") Integer id, @Param("role_id") Integer roleId);

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
     * 更新用户信息
     * @param user
     */
    void edit(User user);

    /**
     * 删除旧关系
     * @param id
     */
    void deleteUserRoleIds(Integer id);

    /**
     * 通过用户的id查询使用了这个用户的角色个数
     * @param id
     * @return
     */
    int findRoleByUserId(int id);

    /**
     * 删除用户
     * @param id
     */
    void deleteById(int id);
}
