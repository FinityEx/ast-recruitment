import org.junit.jupiter.api.Test;
import wj.wjarosinski.models.Claim;
import wj.wjarosinski.models.Reimbursement;
import wj.wjarosinski.services.ClaimService;
import wj.wjarosinski.models.Rates;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClaimServiceTest {


    private final ClaimService claimService = new ClaimService();

    @Test
    public void testCalculateClaim_NoReimbursement() {
        Claim claim = new Claim();
        double calculatedClaim = claimService.calculateClaim(claim);
        assertEquals(0.0, calculatedClaim);
    }

    @Test
    public void testCalculateClaim_DailyAllowanceReimbursement() {
        Reimbursement reimbursement = new Reimbursement(Reimbursement.Type.DAILY_ALLOWANCE, 3.00);
        Claim claim = new Claim();
        claim.setDayDateList(Arrays.asList(Date.from(Instant.parse("2023-08-15T10:15:30.00Z")),
                Date.from(Instant.parse("2023-08-16T10:15:30.00Z"))));
        claim.setReimbursementList(Collections.singletonList(reimbursement));
        double calculatedClaim = claimService.calculateClaim(claim);
        assertEquals(2 * Rates.DAILY_ALLOWANCE_RATE.rate, calculatedClaim);
    }

    @Test
    public void testCalculateClaim_CarUsageReimbursement() {
        Reimbursement reimbursement = new Reimbursement(Reimbursement.Type.CAR_USAGE, 100.00);
        Claim claim = new Claim();
        claim.setDayDateList(Collections.singletonList(Date.from(Instant.parse("2023-08-15T10:15:30.00Z"))));
        claim.setReimbursementList(Collections.singletonList(reimbursement));
        Rates.CAR_USAGE_RATE.setRate(0.5);  //Temp value
        double calculatedClaim = claimService.calculateClaim(claim);
        assertEquals(100 * Rates.CAR_USAGE_RATE.rate, calculatedClaim);

        Rates.CAR_USAGE_RATE.setRate(0.3);  //Reset to original value
    }

    @Test
    public void testCalculateClaim_LimitedClaim() {
        Reimbursement reimbursement = new Reimbursement(Reimbursement.Type.CAR_USAGE, 200.00);
        Claim claim = new Claim();
        claim.setDayDateList(Collections.singletonList(Date.from(Instant.parse("2023-08-15T10:15:30.00Z"))));
        claim.setReimbursementList(Collections.singletonList(reimbursement));
        Rates.CAR_USAGE_RATE.setRate(1.0);  //temp
        Rates.COMPENSATION_LIMIT.setRate(150.0);  //temp
        double calculatedClaim = claimService.calculateClaim(claim);
        assertEquals(150.0, calculatedClaim);

        Rates.CAR_USAGE_RATE.setRate(0.3); //reset
        Rates.COMPENSATION_LIMIT.setRate(null); //reset
    }

    @Test
    public void testCalculateClaim_NoLimit() {
        Reimbursement reimbursement = new Reimbursement(Reimbursement.Type.CAR_USAGE, 200.00);
        Claim claim = new Claim();
        claim.setDayDateList(Collections.singletonList(Date.from(Instant.parse("2023-08-15T10:15:30.00Z"))));
        claim.setReimbursementList(Collections.singletonList(reimbursement));
        Rates.CAR_USAGE_RATE.setRate(1.0); //temp
        double calculatedClaim = claimService.calculateClaim(claim);
        assertEquals(200.0, calculatedClaim);

        Rates.CAR_USAGE_RATE.setRate(0.3); //temp
    }
}
