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
        RECEIPT_TAXI(Meta.RECEIPT),
        RECEIPT_HOTEL(Meta.RECEIPT),
        RECEIPT_PLANE_TICKET(Meta.RECEIPT),
        RECEIPT_TRAIN(Meta.RECEIPT),
        DAILY_ALLOWANCE(Meta.OTHER),
        CAR_USAGE(Meta.OTHER);
        private final Meta meta;
        Type(Meta meta){this.meta = meta;}
        public Meta getMeta(){return meta;}
        public enum Meta{
            RECEIPT,
            OTHER
        }
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
