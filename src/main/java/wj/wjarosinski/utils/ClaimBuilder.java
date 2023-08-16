package wj.wjarosinski.utils;

import wj.wjarosinski.models.Claim;
import wj.wjarosinski.models.Reimbursement;

import java.util.Date;
import java.util.List;

public class ClaimBuilder {

    private Claim claim;

    public ClaimBuilder(){
        this.claim = new Claim();
    }

    public ClaimBuilder withReimbursements(List<Reimbursement> reimbursements){
        this.claim.setReimbursementList(reimbursements);
        return this;
    }

    public ClaimBuilder withDates(List<Date> dates){
        this.claim.setDayDateList(dates);
        return this;
    }


    public Claim build(){
        return this.claim;
    }
}
