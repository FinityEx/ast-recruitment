package wj.wjarosinski.utils;

public enum Rates {
    CAR_USAGE_RATE("Allowance per driven km in $", 0.3),
    DAILY_ALLOWANCE_RATE("Allowance per day in $", 15.0),
    COMPENSATION_LIMIT("Current limit for overall reimbursement in $", null);
    public Double rate;
    public String description;
    Rates(String description, Double rate){
        this.description = description;
        this.rate = rate;
    }
    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
