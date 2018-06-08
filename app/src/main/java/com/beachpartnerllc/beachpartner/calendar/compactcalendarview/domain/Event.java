package com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.beachpartnerllc.beachpartner.models.InvitationsModel;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable, Parcelable {



    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }



    public void setColor(int color) {
        this.color = color;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Object data;
    private String eventId;
    private String eventName;
    private String eventDescription;
    private String eventLocation;
    private String eventVenue;
    private long eventStartDate;
    private long eventEndDate;
    private String eventStatus;
    private long eventRegStartDate;
    private long eventRegEndDate;
    private String regType;
    private String userMessage;
    private long timeInMillis;
    private int color;
    public String eventAdmin;
    private String invitationStatus;
    private String eventState;
    private String eventTeamSize;

    public long[] getEventDates() {
        return eventDates;
    }

    public void setEventDates(long[] eventDates) {
        this.eventDates = eventDates;
    }

    private long [] eventDates;

    public void setEventState(String state){
        this.eventState = state;
    }

    public String getEventState(){
        return eventState;
    }




    public long getEventRegStartDate() {
        return eventRegStartDate;
    }

    public void setEventRegStartDate(long eventRegStartDate) {
        this.eventRegStartDate = eventRegStartDate;
    }

    public long getEventRegEndDate() {
        return eventRegEndDate;
    }

    public void setEventRegEndDate(long eventRegEndDate) {
        this.eventRegEndDate = eventRegEndDate;
    }

    public void setInvitationList(List<InvitationsModel> invitationList) {
        this.invitationList = invitationList;
    }

    private String eventUrl;
    List<InvitationsModel>invitationList;

    public List<InvitationsModel> getInvitationList() {
        return invitationList;
    }




    public long getEventRegStartdate() {
        return eventRegStartDate;
    }

    public void setEventRegStartdate(long eventRegStartdate) {
        this.eventRegStartDate = eventRegStartdate;
    }

    public long getEventRegEnddate() {
        return eventRegEndDate;
    }

    public void setEventRegEnddate(long eventRegEnddate) {
        this.eventRegEndDate = eventRegEnddate;
    }



    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public long getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(long eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public long getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(long eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String status) {
        this.eventStatus = status;
    }

    public void setInvitationStatus(String mInvitationStatus) {
        this.invitationStatus = mInvitationStatus;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setRegType(String registerType) {
        this.regType = registerType;
    }

    public String getRegType() {
        return regType;
    }

    public void setUserMessage(String userMsg) {
        this.userMessage = userMsg;
    }


    public String getUserMessage() {
        return userMessage;
    }

    public void setEventUrl(String Url) {
        this.eventUrl = Url;
    }
    public String getEventUrl() {
        return eventUrl;
    }
//    public EventAdminModel getEventAdmin() {
//        return eventAdmin;
//    }
//
//    public void setEventAdmin(EventAdminModel eventAdmin) {
//        this.eventAdmin = eventAdmin;
//    }

    public String getEventAdmin(){return eventAdmin;}

    public void setEventAdmin(String EventAdmin) {
        this.eventAdmin=EventAdmin;
    }

    /*public Event() {
        this.color = color;
        this.timeInMillis = timeInMillis;
    }*/

    /*public Event(int color, long timeInMillis, Object data) {
        this.color = color;
        this.timeInMillis = timeInMillis;
        this.data = data;
    }
*/
    public int getColor() {
        return color;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    @Nullable
    public Object getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (color != event.color) return false;
        if (timeInMillis != event.timeInMillis) return false;
        if (data != null ? !data.equals(event.data) : event.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = color;
        result = 31 * result + (int) (timeInMillis ^ (timeInMillis >>> 32));
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "color=" + color +
                ", timeInMillis=" + timeInMillis +
                ", data=" + data +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


    public String getEventTeamSize() {
        return eventTeamSize;
    }

    public void setEventTeamSize(String eventTeamSizes) {
        this.eventTeamSize = eventTeamSizes;
    }

    public void setEventTeamSizes(String eventTeamSizes) {
        this.eventTeamSize = eventTeamSizes;
    }
}