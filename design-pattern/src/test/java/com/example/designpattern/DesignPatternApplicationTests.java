package com.example.designpattern;

import com.example.designpattern.service.bridge.BridgeServiceImpl;
import com.example.designpattern.service.command.CommandServiceImpl;
import com.example.designpattern.service.composite.CompositeServiceImpl;
import com.example.designpattern.service.decorator.DecoratorServiceImpl;
import com.example.designpattern.service.factory.FactoryServiceImpl;
import com.example.designpattern.service.observer.ObserverServiceImpl;
import com.example.designpattern.service.prototype.PrototypeServiceImpl;
import com.example.designpattern.service.proxy.ProxyServiceImpl;
import com.example.designpattern.service.responsibilityChain.ResponsibilityChainServiceImpl;
import com.example.designpattern.service.strategy.Cat;
import com.example.designpattern.service.strategy.CatHeightComparator;
import com.example.designpattern.service.strategy.CatWeightComparator;
import com.example.designpattern.service.strategy.StrategyServiceImpl;
import com.example.designpattern.service.templateMethod.TemplateMethodServiceImpl;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DesignPatternApplicationTests {

  @Autowired
  private StrategyServiceImpl strategyService;

  @Autowired
  private FactoryServiceImpl factoryService;

  @Autowired
  private DecoratorServiceImpl decoratorService;

  @Autowired
  private ResponsibilityChainServiceImpl responsibilityChainService;

  @Autowired
  private ProxyServiceImpl proxyService;

  @Autowired
  private TemplateMethodServiceImpl templateMethodService;

  @Autowired
  private PrototypeServiceImpl prototypeService;

  @Autowired
  private CommandServiceImpl commandService;

  @Autowired
  private BridgeServiceImpl bridgeService;

  @Autowired
  private CompositeServiceImpl compositeService;

  @Autowired
  private ObserverServiceImpl observerService;


  @Test
  void contextLoads() {

    //策略模式
//    strategyTest();
    //工厂模式
//    factoryTest();
    //装饰模式
//    decoratorTest();
    //责任链模式
//    responsibilityChainTest();
    //代理模式
//    proxyTest();
    //模版模式/钩子函数
//    templateMethodTest();
    //原型模式
//    prototypeTest();
    //命令模式
//    commandTest();
    //桥接模式
//    bridgeTest();
    //组合模式
//    compositeTest();
    //观察者模式
    observerTest();
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

  public void factoryTest() {
    //简单工厂模式
    factoryService.simpleFactory();
    //抽象工厂模式
    factoryService.abstractFactory();

  }

  public void decoratorTest() {
    decoratorService.decorator();
  }

  public void responsibilityChainTest() {
    responsibilityChainService.responsibilityChain();
  }


  public void proxyTest() {
    //静态代理
    proxyService.staticProxy();
    //JDK动态代理
    proxyService.dynamicJDKProxy();
    //cglib动态代理
    proxyService.dynamicCGLibProxy();
  }

  public void templateMethodTest() {
    templateMethodService.templateMethod();
  }

  public void prototypeTest() {
    try {
      prototypeService.prototype();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void commandTest() {
    commandService.command();
  }

  public void bridgeTest() {
    bridgeService.bridge();
  }

  public void compositeTest() {
    compositeService.composite();
  }

  public void observerTest() {
    observerService.observer();
  }
}
