package test.com.lf.http;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by lfeng on 17/6/1.
 *
 * @author lfeng
 * @date 2017/06/01
 */
public class ClientWorker {
    private void test(){

        //��ʱ����ÿ��1ms�ӷ�������ȥ�仯������
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("com.taobao.diamond.client.Worker."+ env.serverMgr.name);
                t.setDaemon(true);
                return t;
            }
        });
        //scheduleWithFixedDelay ���̶�ʱ�����ִ��,ִ��ǰ����ȴ�ǰһ������ִ����
        executor.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                try {
//                    checkLocalConfigInfo();
////��ȡ�������ı仯��������Ϣ
//                    checkServerConfigInfo();
                } catch (Throwable e) {
                    log.error(env.getName(), "DIAMOND-XXXX", "[sub-check] rotate check error", e);
                }
            }
        }, 1L, 1L, TimeUnit.MILLISECONDS);
    }
}
