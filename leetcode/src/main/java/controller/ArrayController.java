package controller;

import com.example.leetcode.service.ArrayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuxiangzang
 * @create 2020-09-01-7:48 下午
 * @desc 数据相关
 **/
@RestController
public class ArrayController {

  @Autowired
  private ArrayServiceImpl arrayService;
  @RequestMapping(value = "/accompany_rec", produces = "application/json;charset=UTF-8", method = {
      RequestMethod.GET, RequestMethod.POST})
  public String test() {
    return null;
  }
}
