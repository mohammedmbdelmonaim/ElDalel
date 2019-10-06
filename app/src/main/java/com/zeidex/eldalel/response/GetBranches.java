package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBranches {

    @SerializedName("data")
    @Expose
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("showrooms")
        @Expose
        private List<Branch> showrooms = null;

        public List<Branch> getShowrooms() {
            return showrooms;
        }

        public void setShowrooms(List<Branch> showrooms) {
            this.showrooms = showrooms;
        }
    }

    public class Branch{
        @SerializedName("id")
        String id;

        @SerializedName("name_ar")
        String name_ar;

        @SerializedName("name_en")
        String name_en;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName_ar() {
            return name_ar;
        }

        public void setName_ar(String name_ar) {
            this.name_ar = name_ar;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }
    }
}
