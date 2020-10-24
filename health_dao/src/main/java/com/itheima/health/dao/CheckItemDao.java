package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    /**
     * 查询 所有检查项
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    int add(CheckItem checkItem);

    /**
     * 分页条件查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findPage(String queryString);
}
