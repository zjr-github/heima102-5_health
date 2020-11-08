package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据登陆用户名称查询用户权限信息
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<User> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 查询条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            // 模糊查询 %
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 条件查询，这个查询语句会被分页
        Page<User> page = userDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<User>(page.getTotal(), page.getResult());
    }

    /**
     * 新增用户
     *
     * @param user
     * @param roleIds
     */
    @Override
    @Transactional
    public void add(User user, Integer[] roleIds) {
        // 添加用户信息
        userDao.add(user);
        // 获取用户的id
        // 添加用户与检查组的关系
        if (roleIds != null) {
            for (Integer roleId : roleIds) {
                userDao.addUserRoleIds(user.getId(), roleId);
            }
        }
    }

    /**
     * 通过id查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    /**
     * 通过id查询选中的角色ids
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findRoleIdsByUserId(int id) {
        return userDao.findRoleIdsByUserId(id);
    }

    /**
     * 编辑用户
     *
     * @param user
     * @param roleIds
     */
    @Override
    @Transactional
    public void edit(User user, Integer[] roleIds) {
        // 先更新用户信息
        userDao.edit(user);
        // 删除旧关系
        userDao.deleteUserRoleIds(user.getId());
        // 添加新关系
        if (roleIds != null) {
            for (Integer roleId : roleIds) {
                userDao.addUserRoleIds(user.getId(), roleId);
            }
        }
    }

    /**
     * 通过id删除用户
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {
        // 是否存在角色，如果存在则不能删除
        int cnt = userDao.findRoleByUserId(id);
        if (cnt > 0) {
            throw new HealthException("已经有角色使用了这个用户，不能删除！");
        }
        // 先删除用户与角色的关系
        userDao.deleteUserRoleIds(id);
        // 再删除用户
        userDao.deleteById(id);
    }
}
