package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询 所有检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItemList = checkItemDao.findAll();
        return checkItemList;
    }

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    @Override
    public boolean add(CheckItem checkItem) {
        int row = checkItemDao.add(checkItem);
        return row>0;
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 模糊查询 拼接 %
        // 判断是否有查询条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())){
            // 有查询条件，拼接%
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        // 紧接着的查询语句会被分页
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean.getQueryString());
        // 封装到分页结果对象中
        return new PageResult<CheckItem>(page.getTotal(),page.getResult());
    }
}
