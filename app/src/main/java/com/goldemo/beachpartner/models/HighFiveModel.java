package com.goldemo.beachpartner.models;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by Owner on 3/26/2018.
 */

public class HighFiveModel  {
    String name;
    String imageUrl;

    public HighFiveModel(String name, String imageUrl) {
        this.name=name;
        this.imageUrl=imageUrl;
    }

    public HighFiveModel() {

    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
