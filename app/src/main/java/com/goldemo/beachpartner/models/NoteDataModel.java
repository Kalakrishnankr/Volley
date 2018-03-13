package com.goldemo.beachpartner.models;

/**
 * Created by Owner on 3/12/2018.
 */

public class NoteDataModel {

    private String notes;
    private String headerTitle;

    public NoteDataModel(){

    }

    public NoteDataModel(String headerTitle, String notes) {
        this.headerTitle = headerTitle;
        this.notes = notes;
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
}
