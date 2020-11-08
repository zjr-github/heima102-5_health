package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> findByUserId(String username) throws HealthException;

    PageResult<Menu> findPage(QueryPageBean queryPageBean);

    void menuAdd(Menu menu);

    Menu menuFindById(int id);

    void menuEdit(Menu menu);

    void menuDelete(int id);
}
