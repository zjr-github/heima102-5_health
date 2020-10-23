package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 体检检查项管理
 */
@RestController
@RequestMapping("checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询检查项
     * @return
     */
    @RequestMapping("findAll")
    public Result findAll(){
        // 调用服务查询
        List<CheckItem> checkItemList = checkItemService.findAll();

        if (checkItemList!=null){
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemList);
        }else {
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 新增检查项
     * @param checkItem 接收前端提交过来的formData
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody CheckItem checkItem){
        // 调用服务添加
       boolean flag = checkItemService.add(checkItem);
       return new Result(flag,flag?MessageConstant.ADD_CHECKITEM_SUCCESS:MessageConstant.ADD_CHECKITEM_FAIL);
    }
}
