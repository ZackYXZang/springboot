package com.example.designpattern.service.proxy;


import java.lang.reflect.Method;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CGLibInterceptor implements MethodInterceptor {

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
      throws Throwable {
    System.out.println("cglib interceptor start");
    Object result = methodProxy.invokeSuper(o, objects);
    System.out.println("cglib interceptor end");
    return result;
  }
}
