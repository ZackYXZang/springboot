package com.example.designpattern;

import com.example.designpattern.service.strategy.Cat;
import com.example.designpattern.service.strategy.CatHeightComparator;
import com.example.designpattern.service.strategy.CatWeightComparator;
import com.example.designpattern.service.strategy.StrategyServiceImpl;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DesignPatternApplicationTests {

  @Autowired
  private StrategyServiceImpl strategyService;
  @Test
  void contextLoads() {

    //策略模式
    strategyTest();
  }


  public void strategyTest() {
    Cat[] cats = new Cat[3];
    cats[1] = new Cat(1,1);
    cats[0] = new Cat(2,2);
    cats[2] = new Cat(3,3);
    strategyService.sort(cats, new CatWeightComparator());
    strategyService.sort(cats, new CatHeightComparator());
    System.out.println(Arrays.toString(cats));
  }
}
