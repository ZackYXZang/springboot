package com.example.springboot;


import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Test {

    private static Logger log = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();

        map.put("username", "zangyx");
        map.put("token", "adfasdf");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("data", map);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("code", "200");
        map2.put("data", map1);
        JSONObject js = new JSONObject(map2);
        System.out.println(JSONObject.toJSONString(map2));





    }

    public static List<String> getLastMonths(int lastMonthNum) {
        List<String> lastMonth = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMM");
        for (int i = 0; i < lastMonthNum; i++) {
            LocalDate localDate = today.minusMonths(i);
            String ss = localDate.format(fmt);
            lastMonth.add(ss);
        }
        return lastMonth;
    }


}
