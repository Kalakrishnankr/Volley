package com.goldemo.beachpartner.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by seq-kala on 15/3/18.
 */

public class PersonModel implements Parcelable {
    private String uname;
    private String age;
    private int image;
    public boolean isExpanded = false;
    public PersonModel(String uname,String age,int image){
        this.uname  =uname;
        this.age =age;
        this.image =image;

    }


    protected PersonModel(Parcel in) {
        uname = in.readString();
        age = in.readString();
        image = in.readInt();
        isExpanded = in.readByte() != 0;
    }

    public static final Creator<PersonModel> CREATOR = new Creator<PersonModel>() {
        @Override
        public PersonModel createFromParcel(Parcel in) {
            return new PersonModel(in);
        }

        @Override
        public PersonModel[] newArray(int size) {
            return new PersonModel[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uname);
        parcel.writeString(age);
        parcel.writeInt(image);
        parcel.writeByte((byte) (isExpanded ? 1 : 0));
    }
}
