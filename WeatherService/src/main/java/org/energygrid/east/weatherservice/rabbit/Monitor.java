package org.energygrid.east.weatherservice.rabbit;

public class Monitor {

    private static final Monitor monitor = new Monitor();

    private Monitor() {

    }

    public static Monitor getInstance() {
        return monitor;
    }

    public void start(Object monitor) {
        while(true) {
            synchronized (monitor) {
                try {
                    monitor.wait();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
