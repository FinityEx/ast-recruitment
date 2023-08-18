import org.junit.jupiter.api.Test;
import wj.wjarosinski.models.requests.AdminRequest;
import wj.wjarosinski.models.Reimbursement;
import wj.wjarosinski.services.AdminService;
import wj.wjarosinski.utils.Rates;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminServiceTest {

    @Test
    public void testSetRatesAndLimits() {
        AdminService adminService = new AdminService();
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setCarUsageRate(0.5);
        adminRequest.setDailyAllowanceRate(100.0);
        adminRequest.setCompensationLimit(500.0);

        adminService.setRatesAndLimits(adminRequest);

        assertEquals(0.5, Rates.CAR_USAGE_RATE.rate);
        assertEquals(100.0, Rates.DAILY_ALLOWANCE_RATE.rate);
        assertEquals(500.0, Rates.COMPENSATION_LIMIT.rate);
    }

    @Test
    public void testGetCurrentRates() {
        List<List<String>> currentRates = AdminService.getCurrentRates();

        assertEquals(Rates.values().length, currentRates.size());

        for (List<String> rate : currentRates) {
            assertEquals(3, rate.size());
            assertTrue(rate.contains(rate.get(0))); // Rate name
            assertTrue(rate.contains("null") || rate.contains(rate.get(1))); // Rate value (or "null" if null)
            assertTrue(rate.contains(Rates.valueOf(rate.get(0)).getDescription())); // Rate description
        }
    }

    @Test
    public void testGetReimbursementOptions() {
        Map<Reimbursement.Type, Reimbursement.Type.Meta> reimbursementOptions = AdminService.getAllReimbursementOptions();

        assertEquals(Reimbursement.Type.values().length, reimbursementOptions.size());

        for (Reimbursement.Type type : Reimbursement.Type.values()) {
            assertTrue(reimbursementOptions.containsKey(type));
            assertEquals(type.getMeta(), reimbursementOptions.get(type));
        }
    }
}
