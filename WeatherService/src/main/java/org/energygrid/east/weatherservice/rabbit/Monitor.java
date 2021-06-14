package org.energygrid.east.weatherservice.rabbit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Monitor {

    private static final Logger logger = Logger.getLogger(Monitor.class.getName());

    private final Object monitorObject;

    public Monitor() {
        monitorObject = new Object();
    }

    public void start() {
        while (true) {
            synchronized (monitorObject) {
                try {
                    monitorObject.wait();
                    break;
                } catch (InterruptedException e) {
                    logger.log(Level.ALL, e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
