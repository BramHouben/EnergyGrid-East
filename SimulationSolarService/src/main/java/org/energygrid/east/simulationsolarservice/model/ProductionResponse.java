package org.energygrid.east.simulationsolarservice.model;

public class ProductionResponse {

    private Double todayProduction;
    private Double yearProduction;

    public ProductionResponse() {}

    public ProductionResponse(Double todayProduction, Double yearProduction) {
        this.todayProduction = todayProduction;
        this.yearProduction = yearProduction;
    }

    public Double getTodayProduction() {
        return todayProduction;
    }

    public void setTodayProduction(Double todayProduction) {
        this.todayProduction = todayProduction;
    }

    public Double getYearProduction() {
        return yearProduction;
    }

    public void setYearProduction(Double yearProduction) {
        this.yearProduction = yearProduction;
    }
}
