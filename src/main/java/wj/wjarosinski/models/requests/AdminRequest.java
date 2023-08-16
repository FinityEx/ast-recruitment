package wj.wjarosinski.models.requests;

public class AdminRequest {
    private Double dailyAllowanceRate;
    private Double carUsageRate;
    private Double compensationLimit;
    private Boolean requestCurrentRates;

    public AdminRequest(Double dailyAllowanceRate, Double carUsageRate, Double compensationLimit, Boolean requestCurrentRates) {
        this.dailyAllowanceRate = dailyAllowanceRate;
        this.carUsageRate = carUsageRate;
        this.compensationLimit = compensationLimit;
        this.requestCurrentRates = requestCurrentRates;
    }

    public Boolean getRequestCurrentRates() {
        return requestCurrentRates;
    }

    public void setRequestCurrentRates(Boolean requestCurrentRates) {
        this.requestCurrentRates = requestCurrentRates;
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
}
