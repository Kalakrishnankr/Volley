package com.beachpartnerllc.beachpartner.utils;


import com.beachpartnerllc.beachpartner.models.BenefitModel;
import com.beachpartnerllc.beachpartner.models.SubscriptonPlansModel;

import java.util.List;

/**
 * Created by seq-kala on 6/6/18.
 */

public class CheckPlan {

    public static short selectedIndex;
    public static boolean isPlanAvailable(List<SubscriptonPlansModel> plansModelList, String subScriptionPlan, String benifitCodeB6){
        if (plansModelList != null && plansModelList.size() > 0) {
            selectedIndex=-1;
            for (int i = 0; i <plansModelList.size() ; i++) {
                if( call(plansModelList.get(i).getBenefitList(),benifitCodeB6)){
                    selectedIndex= (short) i;
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean call(List<BenefitModel> benefitList, String benefitCode) {
        for (int j = 0; j < benefitList.size(); j++) {
                if (benefitList.get(j).getBenefitName().trim().equalsIgnoreCase(benefitCode)) {
                if (benefitList.get(j).getBenefitStatus().trim().equalsIgnoreCase("Limited") || benefitList.get(j).getBenefitStatus().trim().equalsIgnoreCase("Available")) {
                    return true;
                }
            }
        }
        return false;
    }
}

