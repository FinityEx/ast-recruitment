import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wj.wjarosinski.models.Claim;
import wj.wjarosinski.models.Reimbursement;
import wj.wjarosinski.services.ClaimService;
import wj.wjarosinski.utils.Rates;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClaimServiceTest {

    private ClaimService claimService;

    @BeforeEach
    public void setUp() {
        claimService = new ClaimService();
    }

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
        claim.setDayDateList(Arrays.asList(Date.from(Instant.parse("2023-08-15T10:15:30.00Z")), Date.from(Instant.parse("2023-08-16T10:15:30.00Z"))));
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
        Rates.CAR_USAGE_RATE.setRate(0.5);  // Set a temporary rate for testing
        double calculatedClaim = claimService.calculateClaim(claim);
        assertEquals(100 * Rates.CAR_USAGE_RATE.rate, calculatedClaim);

        Rates.CAR_USAGE_RATE.setRate(null);  // Reset rate after testing
    }

    @Test
    public void testCalculateClaim_LimitedClaim() {
        Reimbursement reimbursement = new Reimbursement(Reimbursement.Type.CAR_USAGE, 200.00);
        Claim claim = new Claim();
        claim.setDayDateList(Collections.singletonList(Date.from(Instant.parse("2023-08-15T10:15:30.00Z"))));
        claim.setReimbursementList(Collections.singletonList(reimbursement));
        Rates.CAR_USAGE_RATE.setRate(1.0);  // Set a temporary rate for testing
        Rates.COMPENSATION_LIMIT.setRate(150.0);  // Set a temporary limit for testing
        double calculatedClaim = claimService.calculateClaim(claim);
        assertEquals(150.0, calculatedClaim);

        Rates.CAR_USAGE_RATE.setRate(null);  // Reset rate after testing
        Rates.COMPENSATION_LIMIT.setRate(null);  // Reset limit after testing
    }

    @Test
    public void testCalculateClaim_NoLimit() {
        Reimbursement reimbursement = new Reimbursement(Reimbursement.Type.CAR_USAGE, 200.00);
        Claim claim = new Claim();
        claim.setDayDateList(Collections.singletonList(Date.from(Instant.parse("2023-08-15T10:15:30.00Z"))));
        claim.setReimbursementList(Collections.singletonList(reimbursement));
        Rates.CAR_USAGE_RATE.setRate(1.0);  // Set a temporary rate for testing
        double calculatedClaim = claimService.calculateClaim(claim);
        assertEquals(200.0, calculatedClaim);

        Rates.CAR_USAGE_RATE.setRate(null);  // Reset rate after testing
    }
}
