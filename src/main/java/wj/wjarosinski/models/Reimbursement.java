//DTO class for reimbursement type and values

package wj.wjarosinski.models;

public class Reimbursement {
    private Type type;
    private Double inputNumber;
    private boolean enabled;

    public Reimbursement() {
    }

    public Reimbursement(Type type, Double inputNumber) {
        this.type = type;
        this.inputNumber = inputNumber;
    }
    //inner enum declaring the Type for Reimbursement requested
    //either a Receipt type, or Others - car gas refund or daily allowance
    public enum Type {
        RECEIPT_TAXI(Meta.RECEIPT, true),
        RECEIPT_HOTEL(Meta.RECEIPT, true),
        RECEIPT_PLANE_TICKET(Meta.RECEIPT, true),
        RECEIPT_TRAIN(Meta.RECEIPT, true),
        DAILY_ALLOWANCE(Meta.OTHER, true),
        CAR_USAGE(Meta.OTHER, true);
        private final Meta meta;
        private boolean enabled;
        Type(Meta meta, boolean enabled){
            this.meta = meta;
            this.enabled = enabled;
        }
        Type(Meta meta){this(meta, false);}
        public Meta getMeta(){return meta;}
        public boolean getEnabled(){return enabled;}
        public void setEnabled(boolean enabled){this.enabled = enabled;}
        public enum Meta{
            RECEIPT,
            OTHER
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
