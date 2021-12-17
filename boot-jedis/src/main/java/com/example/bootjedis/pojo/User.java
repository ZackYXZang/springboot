package com.example.bootjedis.pojo;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;
import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2020-05-29-6:40 下午
 * @desc 用户实体
 **/
@Data
public class User implements Serializable {


    private Integer id;

    private String name;

    private Integer age;


    private String basePoint;

    private void setBasePoint() {

    }


}
