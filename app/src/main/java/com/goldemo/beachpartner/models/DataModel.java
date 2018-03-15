package com.goldemo.beachpartner.models;

import java.util.ArrayList;

/**
 * Created by seq-kala on 20/2/18.
 */

public class DataModel {
    private String headerTitle;
    private ArrayList<SingleItemModel> allItemsInSection;



    public DataModel() {

    }
    public DataModel(String headerTitle, ArrayList<SingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }
}
