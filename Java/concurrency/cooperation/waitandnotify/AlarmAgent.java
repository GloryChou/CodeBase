package cooperation.waitandnotify;

import jdk.nashorn.internal.runtime.Debug;

import java.util.Random;

/**
 * @author Kyle
 * @create 2018/4/23 23:57
 */
public class AlarmAgent {
    // 唯一实例
    private final static AlarmAgent INSTANCE = new AlarmAgent();

    // 是否连上告警服务器
    private boolean connectedToServer = false;

    // 心跳线程，用于检测告警代理和告警服务器网络连接是否正常
    private final HeartbeatThread heartbeatThread = new HeartbeatThread();

    public static AlarmAgent getInstance() {
        return INSTANCE;
    }

    public void init() {
        connectToServer();
        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }

    private void connectToServer() {
        // 创建并启动网络连接线程，在该线程中与告警服务器建立连接
        new Thread(() -> {
            doConnect();
        }).start();
    }

    private void doConnect() {
        // 模拟实际操作耗时
        Random rand = new Random();
        int x = rand.nextInt(100);
        try {
            Thread.sleep(x);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        synchronized (this) {
            connectedToServer = true;
            // 连接建立完毕，通知以唤醒告警发送线程
            notify();
        }
    }

    public void sendAlarm(String message) throws InterruptedException {
        synchronized (this) {
            // 使当前线程等待，直到告警代理与告警服务器的连接建立完毕或者恢复
            while(!connectedToServer) {
                System.out.println("Alarm agent was not connected to Server.");
            }

            // 上传告警消息
            doSendAlarm(message);
        }
    }

    private void doSendAlarm(String message) {
        System.out.println(String.format("Alarm sent:%s", message));
    }

    public class HeartbeatThread extends Thread {
        @Override
        public void run() {
            try{
                // 留一定的时间给网络连接线程与告警服务器建立连接
                Thread.sleep(1000);
                while (true) {
                    // 模拟网络连接线程与告警服务器建立连接
                    Thread.sleep(1000);
                    if(checkConnection()) {
                        connectedToServer = true;
                    } else {
                        connectedToServer = false;
                        System.out.println("Alarm agent was disconnected from server.");

                        // 检测连接中断，重新建立连接
                        connectToServer();
                    }
                    Thread.sleep(2000);
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        // 检测与告警服务器的网络连接情况
        private boolean checkConnection() {
            boolean isConnected = true;
            final Random random = new Random();

            // 模拟随机性的网络断链
            int rand = random.nextInt(1000);
            if(rand <= 500) {
                isConnected = false;
            }

            return isConnected;
        }
    }
}