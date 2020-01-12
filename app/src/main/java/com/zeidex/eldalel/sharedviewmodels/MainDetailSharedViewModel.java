package com.zeidex.eldalel.sharedviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zeidex.eldalel.models.ProductsCategory;
import com.zeidex.eldalel.response.GetCompaniesOrders;
import com.zeidex.eldalel.response.GetFavorites;

import java.util.ArrayList;
import java.util.List;


public class MainDetailSharedViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ProductsCategory>> home_category1;
    private MutableLiveData<ArrayList<ProductsCategory>> home_category2;
    private MutableLiveData<ArrayList<ProductsCategory>> home_category3;
    private MutableLiveData<ArrayList<ProductsCategory>> offers;
    private MutableLiveData<List<GetFavorites.Favorite>> favorites;


    public MainDetailSharedViewModel() {
        home_category1 = new MutableLiveData<>();
        home_category2 = new MutableLiveData<>();
        home_category3 = new MutableLiveData<>();
        offers = new MutableLiveData<>();
        favorites = new MutableLiveData<>();
    }

    public void setHome_category1(ArrayList<ProductsCategory> productsCategories){
        home_category1.setValue(productsCategories);
    }

    public void setHome_category2(ArrayList<ProductsCategory> productsCategories){
        home_category2.setValue(productsCategories);
    }

    public void setHome_category3(ArrayList<ProductsCategory> productsCategories){
        home_category3.setValue(productsCategories);
    }

    public void setOffers(ArrayList<ProductsCategory> offers){
        this.offers.setValue(offers);
    }

    public void setFavorites(List<GetFavorites.Favorite> favorites){
        this.favorites.setValue(favorites);
    }

    public LiveData<ArrayList<ProductsCategory>> getHome_category1(){
        return home_category1;
    }

    public LiveData<ArrayList<ProductsCategory>> getHome_category2(){
        return home_category2;
    }

    public LiveData<ArrayList<ProductsCategory>> getHome_category3(){
        return home_category3;
    }

    public LiveData<ArrayList<ProductsCategory>> getOffers(){
        return offers;
    }

    public LiveData<List<GetFavorites.Favorite>> getFavorites(){
        return favorites;
    }


    public void changeCart1Status(int pos){
        home_category1.getValue().get(pos).setCart("0");
    }

    public void changeCart2Status(int pos){
        home_category2.getValue().get(pos).setCart("0");
    }

    public void changeCart3Status(int pos){
        home_category3.getValue().get(pos).setCart("0");
    }

    public void changeCartOfferStatus(int pos){
        offers.getValue().get(pos).setCart("0");
    }

    public void changeFav1Status(int pos){
        home_category1.getValue().get(pos).setLike("1");
    }

    public void changeFav2Status(int pos){
        home_category2.getValue().get(pos).setLike("1");
    }

    public void changeFav3Status(int pos){
        home_category3.getValue().get(pos).setLike("1");
    }

    public void changeFavOfferStatus(int pos){
        offers.getValue().get(pos).setLike("1");
    }







}
