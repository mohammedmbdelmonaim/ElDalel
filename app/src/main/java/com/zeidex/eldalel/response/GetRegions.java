package com.zeidex.eldalel.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRegions {
    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("data")
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        @SerializedName("subsidiaries")
        List<Region> subsidiaries;

        public List<Region> getSubsidiaries() {
            return subsidiaries;
        }

        public void setSubsidiaries(List<Region> subsidiaries) {
            this.subsidiaries = subsidiaries;
        }
    }

    public class Region{
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
