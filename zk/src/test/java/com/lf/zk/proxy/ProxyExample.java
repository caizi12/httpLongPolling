package com.lf.zk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyExample {

    static interface Car {
        void running();
    }
    static class Bus implements Car {
        @Override
        public void running() {
            System.out.println("bus is running");
        }
    }
    static class Taxi implements Car {
        @Override
        public void running() {
            System.out.println("taxi is runnig");
        }
    }
    //核心部分 JDK Proxy 代理类
    static class JDKProxy implements InvocationHandler {
        private Object target;

        public Object getInstance(Object target) {
            this.target = target;
            //获得代理对象
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = method.invoke(target, args);
            return result;
        }
    }

    public static void main(String[] args) {
        JDKProxy jdkProxy = new JDKProxy();
        Car instance = (Car) jdkProxy.getInstance(new Taxi());
        instance.running();
    }
}
