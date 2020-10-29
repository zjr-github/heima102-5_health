package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.SetMeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("setmeal")
public class SetmealMobileController {

    @Reference
    private SetMealService setMealService;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("getSetmeal")
    public Result getSetmeal(){
        // 查询所有的套餐
        List<SetMeal> setMeals = setMealService.getSetmeal();
        // 套餐里有图片有全路径吗? 拼接全路径
        setMeals.forEach(s -> s.setImg(QiNiuUtils.DOMAIN+s.getImg()));
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setMeals);
    }
}
