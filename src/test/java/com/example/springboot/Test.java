package com.example.springboot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Test {

    private static Logger log = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        String test = String.format("%s_%s_a_his", "12", "280315565");
        System.out.println(test);
        List<String> months = getLastMonths(2);
        log.info("months{}", months);



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
