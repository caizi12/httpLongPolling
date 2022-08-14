package lf.java;

import java.lang.reflect.Method;

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