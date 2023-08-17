package wj.wjarosinski.services;

import wj.wjarosinski.utils.Rates;
import wj.wjarosinski.models.requests.AdminRequest;
import wj.wjarosinski.models.Reimbursement;

import java.util.*;
import java.util.stream.Collectors;

public class AdminService {
    public void setRatesAndLimits(AdminRequest adminRequest){
        Rates.CAR_USAGE_RATE.setRate(adminRequest.getCarUsageRate());
        Rates.DAILY_ALLOWANCE_RATE.setRate(adminRequest.getDailyAllowanceRate());
        Rates.COMPENSATION_LIMIT.setRate(adminRequest.getCompensationLimit());
    }

    public static List<List<String>> getCurrentRates(){
        List<List<String>> rates = new ArrayList<>();
        Arrays.stream(Rates.values()).forEach(r -> {
           rates.add(List.of(r.toString(), r.getRate() == null ? "null" : r.getRate().toString(), r.getDescription()));
        });
        return rates;
    }


    public static Map<Reimbursement.Type, Reimbursement.Type.Meta> getReimbursementOptions(){
       final Map<Reimbursement.Type, Reimbursement.Type.Meta> map = new HashMap<>();
       Arrays.stream(Reimbursement.Type.values()).forEach(type -> map.put(type, type.getMeta()));
       return map;
    }

}
