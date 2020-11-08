package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findByUserId(String username) {
        //查询获得所有menu集合
        List<Menu> menuList = menuDao.findByUserId(username);
        //创建一个空的集合
        List<Menu> resultMenu = new ArrayList<>();
        //遍历来把子菜单放入父菜单
        //先判断空值
        if (null == menuList) {
            throw new HealthException("用户没有查看菜单的权限");
        }
        for (Menu menu : menuList) {
            //不为空就是子菜单
            if (null == menu.getParentMenuId()) {
                //把父菜单放入结果集
                resultMenu.add(menu);
            }
        }
        //把子菜单放入父菜单
        for (Menu menu : menuList) {
            if (null != menu.getParentMenuId()) {
                for (Menu parentMenu : resultMenu) {
                    if (menu.getParentMenuId() == parentMenu.getId()) {
                        parentMenu.getChildren().add(menu);
                    }
                }
            }
        }
        return resultMenu;
    }

    /**
     * 菜单分页查询
     *
     * @return
     */
    @Override
    public PageResult<Menu> findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 查询条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            // 模糊查询 %
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 条件查询，这个查询语句会被分页
        Page<Menu> page = menuDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<Menu>(page.getTotal(), page.getResult());
    }

    /**
     * 新增菜单
     *
     * @param menu
     */
    @Override
    public void menuAdd(Menu menu) {
        menuDao.menuAdd(menu);
    }

    /**
     * 回显数据成功
     *
     * @param id
     * @return
     */
    @Override
    public Menu menuFindById(int id) {
        return menuDao.menuFindById(id);
    }

    /**
     * 编辑菜单
     *
     * @param menu
     */
    @Override
    public void menuEdit(Menu menu) {
        menuDao.menuEdit(menu);
    }

    /**
     * 根据id删除菜单
     *
     * @param id
     */
    @Override
    @Transactional
    public void menuDelete(int id) {
        List<Role> roles = menuDao.menuFindByIdByRoles(id);
        if (roles != null) {
            // 先删除菜单与角色的关系
            menuDao.deleteMenuRoles(id);
        }
        menuDao.menuDelete(id);

    }
}
