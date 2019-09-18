package com.zeidex.eldalel.utils;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddAndEditSinfeltonClass {


    private static AddAndEditSinfeltonClass sSoleInstance;

    int rest_id;
    int dept_id;
    Map<String, RequestBody> addDepToIstrahaPost;
    List<MultipartBody.Part> photosBody;
    Map<String , JSONArray> additionJson;


    JSONArray additionArray;
    JSONArray period1Array;
    JSONArray period2Array;
    JSONArray period3Array;
    JSONArray activitiesArray;


    public JSONArray getAdditionArray() {
        return additionArray;
    }

    public void setAdditionArray(JSONArray additionArray) {
        this.additionArray = additionArray;
    }

    public JSONArray getPeriod1Array() {
        return period1Array;
    }

    public void setPeriod1Array(JSONArray period1Array) {
        this.period1Array = period1Array;
    }

    public JSONArray getPeriod2Array() {
        return period2Array;
    }

    public void setPeriod2Array(JSONArray period2Array) {
        this.period2Array = period2Array;
    }

    public JSONArray getPeriod3Array() {
        return period3Array;
    }

    public void setPeriod3Array(JSONArray period3Array) {
        this.period3Array = period3Array;
    }

    public JSONArray getActivitiesArray() {
        return activitiesArray;
    }

    public void setActivitiesArray(JSONArray activitiesArray) {
        this.activitiesArray = activitiesArray;
    }

    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public Map<String, RequestBody> getAddDepToIstrahaPost() {
        return addDepToIstrahaPost;
    }

    public void setAddDepToIstrahaPost(Map<String, RequestBody> addDepToIstrahaPost) {
        this.addDepToIstrahaPost = addDepToIstrahaPost;
    }

    public List<MultipartBody.Part> getPhotosBody() {
        return photosBody;
    }

    public void setPhotosBody(List<MultipartBody.Part> photosBody) {
        this.photosBody = photosBody;
    }

    public Map<String, JSONArray> getAdditionJson() {
        return additionJson;
    }

    public void setAdditionJson(Map<String, JSONArray> additionJson) {
        this.additionJson = additionJson;
    }

    private AddAndEditSinfeltonClass() {
    }  //private constructor.

    public static AddAndEditSinfeltonClass getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            sSoleInstance = new AddAndEditSinfeltonClass();
        }

        return sSoleInstance;
    }
}
