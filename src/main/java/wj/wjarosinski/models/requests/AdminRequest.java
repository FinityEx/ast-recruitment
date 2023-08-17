package wj.wjarosinski.models.requests;

public class AdminRequest {
    private Double dailyAllowanceRate;
    private Double carUsageRate;
    private Double compensationLimit;

    public AdminRequest(){}
    public AdminRequest(Double dailyAllowanceRate, Double carUsageRate, Double compensationLimit) {
        this.dailyAllowanceRate = dailyAllowanceRate;
        this.carUsageRate = carUsageRate;
        this.compensationLimit = compensationLimit;
    }
    public Double getDailyAllowanceRate() {
        return dailyAllowanceRate;
    }

    public Double getCarUsageRate() {
        return carUsageRate;
    }

    public Double getCompensationLimit() {
        return compensationLimit;
    }

    public void setDailyAllowanceRate(Double dailyAllowanceRate) {
        this.dailyAllowanceRate = dailyAllowanceRate;
    }

    public void setCarUsageRate(Double carUsageRate) {
        this.carUsageRate = carUsageRate;
    }

    public void setCompensationLimit(Double compensationLimit) {
        this.compensationLimit = compensationLimit;
    }
}
