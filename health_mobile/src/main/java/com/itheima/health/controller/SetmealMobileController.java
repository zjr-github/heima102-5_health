package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.SetMeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("setmeal")
public class SetmealMobileController {

    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("getSetmeal")
    public Result getSetmeal() {
        //获取redis连接对象
        Jedis jedis = jedisPool.getResource();
        String key = "setmealList";
        //获取redis中的套餐列表
        List<SetMeal> setmealInRedis = JSON.parseObject(jedis.get(key), List.class);
        if (null == setmealInRedis) {
            //如果不存在
            //则查询数据库
            List<SetMeal> setMealListInDb = setMealService.getSetmeal();
            //拼接完整图片路径
            if (null != setMealListInDb) {
                setMealListInDb.forEach(s -> s.setImg(QiNiuUtils.DOMAIN + s.getImg()));
                //存入redis中
                jedis.set(key, JSON.toJSONString(setMealListInDb));
            }
            jedis.close();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setMealListInDb);
        }
        jedis.close();
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmealInRedis);
    }

    /**
     * 根据套餐id查询套餐详情信息
     *
     * @param id
     * @return
     */
    @GetMapping("findDetailById")
    public Result findDetailById(int id) {
        Jedis jedis = jedisPool.getResource();
        //获取前端传来的key
        String key4User = "setmeal+setmealId=" + id;
        SetMeal setmealInDb = null;
        //获取redis中的key
        Set<String> keys = jedis.keys("*");
        //判断redis中的key是否包含前端的key
        if (!keys.contains(key4User)) {
            //不存在 查询数据库
            setmealInDb = setMealService.findDetailById(id);
            if (null != setmealInDb) {
                setmealInDb.setImg(QiNiuUtils.DOMAIN + setmealInDb.getImg());
                jedis.set(key4User, JSON.toJSONString(setmealInDb));
            } else {
                jedis.set(key4User, JSON.toJSONString(setmealInDb));
                return new Result(false, "查询的套餐不存在！");
            }
            jedis.close();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealInDb);
        }
        //如果存在
        SetMeal setmealInRedis = JSON.parseObject(jedis.get(key4User), SetMeal.class);
        jedis.close();
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealInRedis);
    }

    /**
     * 套餐基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("findById")
    public Result findById(int id) {
        // 调用服务查询
        SetMeal s = setMealService.findById(id);
        // 图片的完整路径
        s.setImg(QiNiuUtils.DOMAIN + s.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, s);
    }
}
