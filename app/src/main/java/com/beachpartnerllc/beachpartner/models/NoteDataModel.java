package com.beachpartnerllc.beachpartner.models;

/**
 * Created by Owner on 3/12/2018.
 */

public class NoteDataModel {

    private String notes;
    private String headerTitle;
    private long timestamp;

    public NoteDataModel(){

    }

    public NoteDataModel(String headerTitle, String notes,long timestamp) {
        this.headerTitle = headerTitle;
        this.notes = notes;
        this.timestamp= timestamp;
    }
    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public String getNotes(){
        return notes;
    }


    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
