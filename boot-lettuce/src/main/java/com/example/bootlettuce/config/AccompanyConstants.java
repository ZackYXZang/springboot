package com.example.bootlettuce.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxiangzang
 * @create 2020-11-17-6:09 下午
 * @desc 伴奏相关常量
 **/
public interface AccompanyConstants {

  Map<String, Integer> reasonMap = new HashMap<String, Integer>() {{
    put("pool0", 0);
    put("pool1", 1);
    put("pool2", 2);
    put("pool3", 3);
    put("pool4", 4);
    put("pool5", 5);
    put("pool6", 6);
    put("pool7", 7);
    put("pool8", 8);
    put("pool9", 9);
    put("pool10", 10);
    put("pool11", 11);
    put("pool12", 12);
    put("pool13", 13);
  }};

  Map<String, AccompanyReasonEnums> poolReflectMap = new HashMap<String, AccompanyReasonEnums>() {{
    put("pool1",AccompanyReasonEnums.SELECTED_SONG);
    put("pool2", AccompanyReasonEnums.SIMILAR_SONG);
    put("pool3", AccompanyReasonEnums.SELECTED_ARTIST);
    put("pool4", AccompanyReasonEnums.SIMILAR_ARTIST);
    put("pool5", AccompanyReasonEnums.YEAR_SONG);
    put("pool6", AccompanyReasonEnums.STYLE_SONG);
    put("pool7", AccompanyReasonEnums.LANGUAGE_SONG);
    put("pool8", AccompanyReasonEnums.HOT_SONG);
    put("pool9", AccompanyReasonEnums.HOT_ARTIST);
    put("pool10", AccompanyReasonEnums.FOLLOW_SONG);
    put("pool11", AccompanyReasonEnums.SIMILAR_USER_SONG);
    put("pool12", AccompanyReasonEnums.AGE_SONG);
    put("pool13", AccompanyReasonEnums.ASCEND_FASTEST_SONG);
  }};



}
