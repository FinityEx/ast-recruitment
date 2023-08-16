package wj.wjarosinski.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Claim {
    private List<Date> dayDateList;
    private List<Reimbursement> reimbursementList;

    public Claim(){
        this.dayDateList = new ArrayList<>();
        this.reimbursementList = new ArrayList<>();
    }

    public List<Date> getDayDateList() {
        return dayDateList;
    }

    public void setDayDateList(List<Date> dayDateList) {
        this.dayDateList = dayDateList;
    }

    public List<Reimbursement> getReimbursementList() {
        return reimbursementList;
    }

    public void setReimbursementList(List<Reimbursement> reimbursementList) {
        this.reimbursementList = reimbursementList;
    }
}
