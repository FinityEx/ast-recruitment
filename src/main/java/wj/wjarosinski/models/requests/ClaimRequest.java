package wj.wjarosinski.models.requests;

import wj.wjarosinski.models.Reimbursement;

import java.util.Date;
import java.util.List;

public class ClaimRequest {
    private List<Date> dayDateList;
    private List<Reimbursement> reimbursementList;

    public ClaimRequest() {
    }

    public ClaimRequest(List<Date> dayDateList, List<Reimbursement> reimbursementList) {
        this.dayDateList = dayDateList;
        this.reimbursementList = reimbursementList;
    }

    public void setDayDateList(List<Date> dayDateList) {
        this.dayDateList = dayDateList;
    }

    public void setReimbursementList(List<Reimbursement> reimbursementList) {
        this.reimbursementList = reimbursementList;
    }

    public List<Date> getDayDateList() {
        return dayDateList;
    }

    public List<Reimbursement> getReimbursementList() {
        return reimbursementList;
    }
}
