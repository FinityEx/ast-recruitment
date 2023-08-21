import org.junit.jupiter.api.Test;
import wj.wjarosinski.models.requests.AdminRequest;
import wj.wjarosinski.models.Reimbursement;
import wj.wjarosinski.services.AdminService;
import wj.wjarosinski.models.Rates;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AdminServiceTest {

    @Test
    public void testSetRatesAndLimits() {
        AdminService adminService = new AdminService();
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setCarUsageRate(0.5);
        adminRequest.setDailyAllowanceRate(100.0);
        adminRequest.setCompensationLimit(500.0);
        boolean isEnabled = Reimbursement.Type.RECEIPT_HOTEL.getEnabled();
        Reimbursement.Type.RECEIPT_HOTEL.setEnabled(!isEnabled); //temp opposite value
        Reimbursement reimbursement = new Reimbursement(
                Reimbursement.Type.RECEIPT_HOTEL, 0.0);
        adminRequest.setReimbursementList(List.of(reimbursement));

        adminService.setRatesAndLimits(adminRequest);
        assertEquals(!isEnabled, Reimbursement.Type.RECEIPT_HOTEL.getEnabled());
        assertEquals(0.5, Rates.CAR_USAGE_RATE.rate);
        assertEquals(100.0, Rates.DAILY_ALLOWANCE_RATE.rate);
        assertEquals(500.0, Rates.COMPENSATION_LIMIT.rate);
        Reimbursement.Type.RECEIPT_HOTEL.setEnabled(isEnabled); //restore original value
    }

    @Test
    public void testGetCurrentRates() {
        List<List<String>> currentRates = AdminService.getCurrentRates();

        assertEquals(Rates.values().length, currentRates.size());

        for (List<String> rate : currentRates) {
            assertEquals(3, rate.size());
            assertTrue(rate.contains(rate.get(0)));
            assertTrue(rate.contains("null") || rate.contains(rate.get(1)));
            assertTrue(rate.contains(Rates.valueOf(rate.get(0)).getDescription()));
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
