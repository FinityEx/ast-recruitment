package wj.wjarosinski.models.requests;

import wj.wjarosinski.models.Reimbursement;

import java.util.List;

public class AdminRequest {
    private Double dailyAllowanceRate;
    private Double carUsageRate;
    private Double compensationLimit;
    private List<Reimbursement> reimbursementList;

    public AdminRequest(){}
    public AdminRequest(Double dailyAllowanceRate, Double carUsageRate, Double compensationLimit,
                        List<Reimbursement> reimbursementList) {
        this.dailyAllowanceRate = dailyAllowanceRate;
        this.carUsageRate = carUsageRate;
        this.compensationLimit = compensationLimit;
        this.reimbursementList = reimbursementList;
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

    public List<Reimbursement> getReimbursementList() {
        return reimbursementList;
    }

    public void setReimbursementList(List<Reimbursement> reimbursementList) {
        this.reimbursementList = reimbursementList;
    }
}
