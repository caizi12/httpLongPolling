package com.lf.annotation;

import com.lf.service.MonitorService;
import org.junit.Test;

/**
 *
 * @author lfeng
 * @date 2017/09/18
 */
public class TestAnnotation {

    @Test
    public void test(){
        MonitorService test=new MonitorService();
        String result=test.calcauteTime(MonitorService.class);
        System.out.println(result);
    }
}
