package com.example.designpattern.service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;

/**
 * 代理模式
 * 1。静态代理；2。动态代理；3。spring aop
 */
@Service
public class ProxyServiceImpl {

  //静态代理
  public void staticProxy() {
    System.out.println("静态代理");
    new CarLogStaticProxy(new CarTimeStaticProxy(new Car())).move();
  }

  /**
   * 动态代理：JDK
   * 需要实现接口
   * 底层是ASM
   */
  public void dynamicJDKProxy() {
    //java反射：通过二进制字节码分析类的属性和方法
    //生成动态代理类，且这个类必须实现接口
    // 最终是通过asm包下面的类实现的，直接操作内存里的二进制码文件
    System.out.println("jdk 动态代理");

    Car car = new Car();

    Movable movable = (Movable) Proxy.newProxyInstance(Car.class.getClassLoader(), new Class[]{Movable.class},
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("jdk start");
            Object o = method.invoke(car, args);
            System.out.println("jdk end");
            return o;
          }
        });

    movable.move();

  }

  /**
   * 动态代理：CGLib
   * 不需要实现接口
   * 如果类使用final修饰的，不能使用cglib动态代理
   * 底层也是ASM
   */
  public void dynamicCGLibProxy() {
    System.out.println("cglib 动态代理");

    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(NewCar.class);
    enhancer.setCallback(new CGLibInterceptor());
    NewCar newCar = (NewCar) enhancer.create();
    newCar.move();
  }
}
