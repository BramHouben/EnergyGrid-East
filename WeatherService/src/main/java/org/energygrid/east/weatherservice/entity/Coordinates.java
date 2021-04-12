package org.energygrid.east.weatherservice.entity;

import java.awt.geom.Point2D;

public class Coordinates {
    private Point2D.Double coordinate;

    public Coordinates() {
    }

    public Coordinates(Point2D.Double coordinate) {
        this.coordinate = coordinate;
    }

    public Point2D.Double getCoordinates() {
        return coordinate;
    }

    public void setCoordinates(Point2D.Double coordinate) {
        this.coordinate = coordinate;
    }
}
