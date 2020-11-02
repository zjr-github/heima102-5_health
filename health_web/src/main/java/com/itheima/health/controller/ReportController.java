package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetMealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计报表
 */
@RestController
@RequestMapping("report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetMealService setmealService;

    @Reference
    private ReportService reportService;

    /**
     * 会员折线图
     * @return
     */
    @GetMapping("getMemberReport")
    public Result getMemberReport(){
        // 组装过去12个月的数据, 前端是一个数组
        List<String> months = new ArrayList<>();
        // 使用java中的calendar来操作日期, 日历对象
        Calendar calendar = Calendar.getInstance();
        // 过去一年, 年-1
        calendar.add(Calendar.YEAR,-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 遍历12次，依次加1个月
        for (int i = 0; i < 12; i++) {
            // +1个月
            calendar.add(Calendar.MONTH,1);
            months.add(sdf.format(calendar.getTime()));
        }
        // 调用服务去查询12个月的数据
        List<Integer> memberCount = memberService.getMemberReport(months);
        // 构建返回的数据
        Map<String, Object> map = new HashMap<>();
        map.put("months",months);
        map.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    /**
     *
     * @return
     */
    @GetMapping("getSetmealReport")
    public Result getSetmealReport(){
        // 调用服务查询
        // 套餐数量
        List<Map<String, Object>> setmealCount = setmealService.findSetmealCount();
        // 套餐名称集合
        List<String> setmealNames = new ArrayList<>();
        // [{name:,value}]
        // 抽取套餐名称
        if (setmealCount != null) {
            setmealCount.forEach(s -> setmealNames.add((String)s.get("name")));
        }
        // 封装返回的结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("setmealNames",setmealNames);
        resultMap.put("setmealCount",setmealCount);
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,resultMap);
    }

    /**
     * 运营统计数据
     * @return
     */
    @GetMapping("getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> businessReport = reportService.getBusinessReport();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,businessReport);
    }
}
