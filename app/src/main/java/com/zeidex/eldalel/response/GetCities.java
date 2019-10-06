package com.zeidex.eldalel.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCities {
    @SerializedName("cities")
    List<City> cities;

    @SerializedName("showrooms")
    List<City> showrooms;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public class City{
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
