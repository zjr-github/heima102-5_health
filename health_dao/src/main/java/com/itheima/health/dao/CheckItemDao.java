package com.itheima.health.dao;

import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    /**
     * 查询 所有检查项
     * @return
     */
    List<CheckItem> findAll();
}
