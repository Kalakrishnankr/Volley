package com.goldemo.beachpartner.models;

/**
 * Created by seq-kala on 15/3/18.
 */

public class PersonModel {
    private String uname;
    private String age;
    private int image;
    public boolean isExpanded = false;
    public PersonModel(String uname,String age,int image){
        this.uname  =uname;
        this.age =age;
        this.image =image;

    }


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


}
