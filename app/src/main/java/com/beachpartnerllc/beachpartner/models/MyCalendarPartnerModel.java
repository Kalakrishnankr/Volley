package com.beachpartnerllc.beachpartner.models;

/**
 * Created by Owner on 5/18/2018.
 */

public class MyCalendarPartnerModel {
    public String partner_Id;
    public String partner_ImageUrl;
    public String partner_Name;
    public String partner_role;
    public String invitationStatus;



    public String getpartner_Id() {
        return partner_Id;
    }

    public void setpartner_Id(String id) {
        this.partner_Id = id;
    }

    public String getPartner_ImageUrl() {
        return partner_ImageUrl;
    }

    public void setPartner_ImageUrl(String image) {
        this.partner_ImageUrl = image;
    }

    public String getpartner_Name() {
        return partner_Name;
    }

    public void setpartner_Name(String name) {
        this.partner_Name = name;
    }

    public String getpartner_role() {
        return partner_role;
    }

    public void setpartner_role(String role) {
        this.partner_role = role;
    }

    public void setPartner_ivitationStatus(String status){
        this.invitationStatus = status;
    }

    public String getPartner_ivitationStatus(){
        return invitationStatus;
    }
}
