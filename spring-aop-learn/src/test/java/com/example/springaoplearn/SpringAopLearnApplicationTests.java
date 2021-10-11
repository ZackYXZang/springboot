package com.example.springaoplearn;

import com.example.springaoplearn.proxy.cglib.MyCalculator1;
import com.example.springaoplearn.proxy.cglib.MyCglib;
import com.example.springaoplearn.proxy.jdk.Calculator;
import com.example.springaoplearn.proxy.jdk.CalculatorProxy;
import com.example.springaoplearn.proxy.jdk.MyCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@SpringBootTest
class SpringAopLearnApplicationTests {

  @Test
  void contextLoads() {
    //JDK代理，需要类实现接口
//    testJdkProxy();
    //通过cglib动态代理获取代理对象
//    testCglibProxy();
    //通过注解启动spring
    annotationTest();
  }

  public void testJdkProxy () {
    /**
     * JDK代理，需要类实现接口
     * 核心在Proxy.getProxyClass() -> Proxy.ProxyClassFactory.apply.generateProxyClass -> generateClassFile -> 生成代理文件
     * 这里需要InvocationHandler
     */
    System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
    //创建代理对象
    Calculator proxy = CalculatorProxy.getProxy(new MyCalculator());
    //通过代理对象调用目标方法
    proxy.add(1, 2);
    System.out.println(proxy.getClass());
  }

  /**
   * FastClass是一个抽象类，CGLib在运行时通过FastClass内的Generator这个内部类将其子类动态生成出来，
   * 然后再利用ClassLoader将生成的子类加载进JVM中
   */
  public void testCglibProxy () {
    //通过cglib动态代理获取代理对象的过程，创建调用的对象，中间会用到FastClass
    Enhancer enhancer = new Enhancer(); //会创建缓存对象 -> ClassLoaderData
    //设置enhancer对象的父类
    enhancer.setSuperclass(MyCalculator1.class);
    //设置enhancer的回调对象
    enhancer.setCallback(new MyCglib());
    //创建代理对象
    MyCalculator1 myCalculator1 = (MyCalculator1) enhancer.create();
    //通过代理对象调用目标方法
    myCalculator1.add(1, 2);
    System.out.println(myCalculator1.getClass());
  }

  public void annotationTest() {
    //此时已经有了beanFactory，
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
    ac.register(SpringAopLearnApplication.class);
    ac.refresh();

  }
}
