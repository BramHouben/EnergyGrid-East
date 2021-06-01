package org.energygrid.east.energybalanceservice.rabbit.rabbitservice;

public interface IRabbitService {

    /**
     * @param message with latest wind details
     */
    void addLatestWind(String message);

    /**
     * @param message with latest solar details
     */
    void addLatestSolar(double message);

    /**
     * @param message with latest nuclear details
     */
    void addLatestNuclear(String message);
}
