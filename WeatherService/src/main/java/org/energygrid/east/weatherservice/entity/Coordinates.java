package org.energygrid.east.weatherservice.entity;

import java.awt.*;
import java.awt.geom.Point2D;

public class Coordinates {
    private Point2D.Double coordinates;

    public Coordinates() {
    }

    public Coordinates(Point2D.Double coordinates) {
        this.coordinates = coordinates;
    }

    public Point2D.Double getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point2D.Double coordinates) {
        this.coordinates = coordinates;
    }
}
