package com.zeidex.eldalel.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Subsubcategory implements Parcelable {

    private Integer id;
    private String name;
    private String nameAr;

    public Subsubcategory(Integer id, String name, String nameAr) {
        this.id = id;
        this.name = name;
        this.nameAr = nameAr;
    }

    protected Subsubcategory(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        nameAr = in.readString();
    }

    public static final Creator<Subsubcategory> CREATOR = new Creator<Subsubcategory>() {
        @Override
        public Subsubcategory createFromParcel(Parcel in) {
            return new Subsubcategory(in);
        }

        @Override
        public Subsubcategory[] newArray(int size) {
            return new Subsubcategory[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
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
        dest.writeString(name);
        dest.writeString(nameAr);
    }
}
