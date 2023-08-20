//Service class executing actual tasks

package wj.wjarosinski.services;

import wj.wjarosinski.models.Rates;
import wj.wjarosinski.models.requests.AdminRequest;
import wj.wjarosinski.models.Reimbursement;

import java.util.*;

public class AdminService {
    public void setRatesAndLimits(AdminRequest adminRequest){
        Rates.CAR_USAGE_RATE.setRate(adminRequest.getCarUsageRate());
        Rates.DAILY_ALLOWANCE_RATE.setRate(adminRequest.getDailyAllowanceRate());
        Rates.COMPENSATION_LIMIT.setRate(adminRequest.getCompensationLimit());

        setAvailableReimbursementOptions(adminRequest.getReimbursementList());
    }

    public static List<List<String>> getCurrentRates(){
        List<List<String>> rates = new ArrayList<>();
        Arrays.stream(Rates.values()).forEach(r -> {
           rates.add(List.of(r.toString(), r.getRate() == null ? "null" : r.getRate().toString(), r.getDescription()));
        });
        return rates;
    }
    public static void setAvailableReimbursementOptions(List<Reimbursement> options) {
        for (var type : Reimbursement.Type.values()) {
            for (var option : options) {
                if (option.getType().toString().equals(type.toString())) {
                    type.setEnabled(option.isEnabled());
                }
            }
        }
    }

    public static Map<Reimbursement.Type, Reimbursement.Type.Meta> getAvailableReimbursementOptions(){
        final Map<Reimbursement.Type, Reimbursement.Type.Meta> map = new HashMap<>();
        Arrays.stream(Reimbursement.Type.values()).filter(Reimbursement.Type::getEnabled)
                .forEach(type -> map.put(type, type.getMeta()));
        return map;
    }

    public static Map<Reimbursement.Type, Reimbursement.Type.Meta> getAllReimbursementOptions(){
       final Map<Reimbursement.Type, Reimbursement.Type.Meta> map = new HashMap<>();
       Arrays.stream(Reimbursement.Type.values()).forEach(type -> map.put(type, type.getMeta()));
       return map;
    }

}
