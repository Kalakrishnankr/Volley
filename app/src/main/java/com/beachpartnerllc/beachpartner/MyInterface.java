package com.beachpartnerllc.beachpartner;

/**
 * Created by seq-kala on 14/2/18.
 */

public interface MyInterface {

     void addView(String url, String firstName,String lastName,String age,String userType);
     void onClick(String bpf_id, String bpf_deviceId, String bpf_fcmToken, String bpf_topFinishes);
}
