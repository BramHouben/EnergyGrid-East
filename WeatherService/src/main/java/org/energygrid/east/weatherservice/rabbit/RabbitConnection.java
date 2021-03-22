package org.energygrid.east.weatherservice.rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitConnection {

    private static final Logger logger = Logger.getLogger(RabbitConnection.class.getName());

    private static final RabbitConnection rabbitConnection = new RabbitConnection();
    private ConnectionFactory connectionFactory;
    private Connection connection;

    private RabbitConnection() {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
    }

    public static RabbitConnection getInstance() {
        return rabbitConnection;
    }

    private Connection createConnection() {
        int count = 0;
        int maxCount = 3;

        while(true) {
            try {
                count++;
                connection = connectionFactory.newConnection();
                return connection;
            } catch (IOException | TimeoutException e) {
                logger.log(Level.ALL, e.getMessage());
                if(count == maxCount){
                    return null;
                }
            }
        }
    }

    public Connection getConnection() {
        if(connection == null){
            return createConnection();
        }
        return connection;
    }
}
