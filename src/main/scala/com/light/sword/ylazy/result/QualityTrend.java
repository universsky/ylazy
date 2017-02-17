package com.light.sword.ylazy.result;

/**
 * Created by jack on 2016/8/25.
 * <p>
 * "id": "123",
 * "totalCases": 227,
 * "failed": 20,
 * "rate": 40.71,
 */
public class QualityTrend {

    private Integer id;
    private Integer totalCases;
    private Integer failed;
    private Double rate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(Integer totalCases) {
        this.totalCases = totalCases;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "QualityTrend{" +
                "id=" + id +
                ", totalCases=" + totalCases +
                ", failed=" + failed +
                ", rate=" + rate +
                '}';
    }
}
