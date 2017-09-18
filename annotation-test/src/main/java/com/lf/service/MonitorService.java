package com.lf.service;

import java.lang.reflect.Method;

import com.lf.annotation.TimeMonitor;

/**
 * Created by lfeng on 17/9/18.
 *
 * @author lfeng
 * @date 2017/09/18
 */
@TimeMonitor
public class MonitorService {

    @TimeMonitor(monitorPointName = "testServiceH")
    public String testService(){
        return "testService";
    }

    public String calcauteTime(Class clazz){
        Method[] methods=clazz.getMethods();
        String monitorNames="";
        for (Method method:methods){
            if(method.isAnnotationPresent(TimeMonitor.class)){
                TimeMonitor timeMonitor=(TimeMonitor)method.getAnnotation(TimeMonitor.class);
                monitorNames+=timeMonitor.monitorPointName()+";";
            }

        }
        return monitorNames;
    }


}
