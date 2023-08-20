//Service class

package wj.wjarosinski.services;

import wj.wjarosinski.models.Claim;
import wj.wjarosinski.models.Reimbursement;
import wj.wjarosinski.models.Rates;

import java.util.concurrent.atomic.AtomicReference;

public class ClaimService {

    public Double calculateClaim(final Claim claim){
        final AtomicReference<Double> finalCost = new AtomicReference<>(0.0);
        final Integer numberOfDays = claim.getDayDateList().size();

        claim.getReimbursementList().forEach(r -> {
            if (r.getType() == Reimbursement.Type.DAILY_ALLOWANCE) {
                finalCost.getAndUpdate(v -> v + numberOfDays * Rates.DAILY_ALLOWANCE_RATE.rate);
            } else if (r.getType() == Reimbursement.Type.CAR_USAGE && r.getInputNumber() != null) {
                finalCost.getAndUpdate(v -> v + r.getInputNumber() * Rates.CAR_USAGE_RATE.rate);
            } else {
                finalCost.getAndUpdate(v -> v + r.getInputNumber());
            }
        });

        final boolean noLimit = Rates.COMPENSATION_LIMIT.rate == null;


        return noLimit ? finalCost.get() : finalCost.get() <= Rates.COMPENSATION_LIMIT.rate
                ? finalCost.get() : Rates.COMPENSATION_LIMIT.rate;
    }



}
