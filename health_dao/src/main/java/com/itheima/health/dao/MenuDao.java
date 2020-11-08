package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;

import java.util.List;

public interface MenuDao {
    List<Menu> findByUserId(String username);

    Page<Menu> findByCondition(String queryPageBean);

    void menuAdd(Menu menu);

    Menu menuFindById(int id);

    void menuEdit(Menu menu);

    void deleteMenuRoles(int id);

    void menuDelete(int id);

    List<Role> menuFindByIdByRoles(int id);
}
