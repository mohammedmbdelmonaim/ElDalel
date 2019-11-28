package com.zeidex.eldalel.response;

import com.google.gson.annotations.SerializedName;

public class GetStaticPageRespons {
    @SerializedName("status")
    boolean status;

    @SerializedName("code")
    String code;

    @SerializedName("data")
    Data data;


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        @SerializedName("name")
        String name;

        @SerializedName("content_ar")
        String content_ar;

        @SerializedName("content_en")
        String content_en;

        @SerializedName("ar_content")
        String ar_content;

        @SerializedName("en_content")
        String en_content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent_ar() {
            return content_ar;
        }

        public void setContent_ar(String content_ar) {
            this.content_ar = content_ar;
        }

        public String getContent_en() {
            return content_en;
        }

        public void setContent_en(String content_en) {
            this.content_en = content_en;
        }

        public String getAr_content() {
            return ar_content;
        }

        public void setAr_content(String ar_content) {
            this.ar_content = ar_content;
        }

        public String getEn_content() {
            return en_content;
        }

        public void setEn_content(String en_content) {
            this.en_content = en_content;
        }
    }
}
