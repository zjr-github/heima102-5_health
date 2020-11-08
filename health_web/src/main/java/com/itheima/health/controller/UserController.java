package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference
    private UserService userService;

    /**
     * 获取登陆用户名
     */
    @GetMapping("getUsername")
    public Result getUsername() {
        // 获取登陆用户的认证信息
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 登陆用户名
        String username = loginUser.getUsername();
        // 返回给前端
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
    }

    /**
     * 登录成功
     *
     * @return
     */
    @RequestMapping("loginSuccess")
    public Result loginSuccess() {
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }

    /**
     * 登录失败
     *
     * @return
     */
    @RequestMapping("loginFail")
    public Result loginFail() {
        return new Result(false, "用户名或密码不正确");
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        return new Result(true,"分页条件查询成功",userService.findPage(queryPageBean));
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody com.itheima.health.pojo.User user,Integer[] roleIds) {
        // 调用业务服务添加
        userService.add(user,roleIds);
        // 响应结果
        return new Result(true,"新增用户成功");
    }

    /**
     * 通过id查询用户信息
     * @return
     */
    @GetMapping("findById")
    public Result findById(int id) {
        // 调用服务查询
       com.itheima.health.pojo.User user = userService.findById(id);
       return new Result(true,"通过id查询用户信息成功",user);
    }

    /**
     * 通过id查询选中的角色ids
     * @param id
     * @return
     */
    @GetMapping("findRoleIdsByUserId")
    public Result findRoleIdsByUserId(int id) {
        List<Integer> list = userService.findRoleIdsByUserId(id);
        return new Result(true,"通过id查询选中的角色ids成功",list);
    }

    /**
     * 编辑用户
     * @param user
     * @param roleIds
     * @return
     */
    @PostMapping("edit")
    public Result edit(@RequestBody com.itheima.health.pojo.User user,Integer[] roleIds) {
        userService.edit(user,roleIds);
        return new Result(true,"编辑用户成功");
    }

    /**
     * 通过id删除用户
     * @param id
     * @return
     */
    @GetMapping("deleteById")
    public Result deleteById(int id) {
        userService.deleteById(id);
        return new Result(true,"通过id删除用户");
    }
}
