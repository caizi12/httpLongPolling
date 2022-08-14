package lf.java;

import org.junit.jupiter.api.Test;

public class TestAnnotation {
    @Test
    public void test() {
        MonitorService test = new MonitorService();
        String result = test.calcauteTime(MonitorService.class);
        System.out.println(result);
    }
}
