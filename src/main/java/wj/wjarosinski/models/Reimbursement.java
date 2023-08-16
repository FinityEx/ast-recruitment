package wj.wjarosinski.models;

import java.util.function.Predicate;

public class Reimbursement {
    private Type type;
    private Double inputNumber;

    public Reimbursement() {
    }

    public Reimbursement(Type type, Double inputNumber) {
        this.type = type;
        this.inputNumber = inputNumber;
    }

    public enum Type {
        RECEIPT_TAXI,
        RECEIPT_HOTEL,
        RECEIPT_PLANE_TICKET,
        RECEIPT_TRAIN,
        DAILY_ALLOWANCE,
        CAR_USAGE;
        Type(){}
    }

    public Type getType() {
        return type;
    }

    public Double getInputNumber() {
        return inputNumber;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setInputNumber(Double inputNumber) {
        this.inputNumber = inputNumber;
    }
}
