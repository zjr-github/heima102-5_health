package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    @GetMapping("/showMenu4User")
    public Result showMenu4User() {
        //获取登录的用户信息
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Menu> menuList = menuService.findByUserId(loginUser.getUsername());
        return new Result(true, "菜单展示成功", menuList);
    }

    /**
     * 菜单分页查询
     *
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult<Menu> list = menuService.findPage(queryPageBean);
        return new Result(true, "菜单分页查询成功", list);
    }

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    @PostMapping("/menuAdd")
    public Result menuAdd(@RequestBody Menu menu) {
        menuService.menuAdd(menu);
        return new Result(true, "新增菜单成功");
    }

    /**
     * 回显数据
     *
     * @return
     */
    @GetMapping("/menuFindById")
    public Result menuFindById(int id) {
        Menu menu = menuService.menuFindById(id);
        return new Result(true, "回显数据成功", menu);
    }

    /**
     * 编辑菜单
     *
     * @param menu
     * @return
     */
    @PostMapping("/menuEdit")
    public Result menuEdit(@RequestBody Menu menu) {
        menuService.menuEdit(menu);
        return new Result(true, "编辑菜单成功");
    }

    /**
     * 根据id删除菜单
     *
     * @param id
     * @return
     */
    @GetMapping("/menuDelete")
    public Result menuDelete(int id) {
        menuService.menuDelete(id);
        return new Result(true, "删除菜单成功");
    }
}
