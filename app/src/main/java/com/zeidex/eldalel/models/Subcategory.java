package com.zeidex.eldalel.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Subcategory implements Parcelable {

    Integer id;
    String nameAr;
    String name;
    String imagePath;
    ArrayList<Subsubcategory> listSubSubCategory;

    public Subcategory(Integer id, String nameAr, String name, String imagePath) {
        this.id = id;
        this.nameAr = nameAr;
        this.name = name;
        this.imagePath = imagePath;
    }

    protected Subcategory(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        nameAr = in.readString();
        name = in.readString();
        imagePath = in.readString();
    }

    public static final Creator<Subcategory> CREATOR = new Creator<Subcategory>() {
        @Override
        public Subcategory createFromParcel(Parcel in) {
            return new Subcategory(in);
        }

        @Override
        public Subcategory[] newArray(int size) {
            return new Subcategory[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<Subsubcategory> getListSubSubCategory() {
        return listSubSubCategory;
    }

    public void setListSubSubCategory(ArrayList<Subsubcategory> listSubSubCategory) {
        this.listSubSubCategory = listSubSubCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(nameAr);
        dest.writeString(name);
        dest.writeString(imagePath);
    }
}
