package com.zeidex.eldalel.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductDetail {

    @SerializedName("selected_color")
    String selected_color;

    @SerializedName("selected_storage")
    String selected_storage;


        @SerializedName("storage_group_products")
        List<Data> dataAll;

        public List<Data> getDataAll() {
            return dataAll;
        }

        public void setDataAll(List<Data> dataAll) {
            this.dataAll = dataAll;
        }



    public class Data{
        @SerializedName("id")
        String id;

        @SerializedName("subcategory_id")
        String subcategory_id;

        @SerializedName("subsubcategory_id")
        String subsubcategory_id;

        @SerializedName("location_id")
        String location_id;

        @SerializedName("offer_number")
        String offer_number;

        @SerializedName("sku")
        String sku;

        @SerializedName("group")
        String group;

        @SerializedName("storage_group")
        String storage_group;

        @SerializedName("name")
        String name;

        @SerializedName("name_ar")
        String name_ar;

        @SerializedName("price")
        String price;

        @SerializedName("old_price")
        String old_price;

        @SerializedName("wholesale_price")
        String wholesale_price;

        @SerializedName("wholesale_old_price")
        String wholesale_old_price;

        @SerializedName("weight")
        String weight;

        @SerializedName("cartDesc")
        String cartDesc;

        @SerializedName("shortDesc")
        String shortDesc;

        @SerializedName("shortDescAr")
        String shortDescAr;

        @SerializedName("status")
        String status;

        @SerializedName("stock")
        String stock;

        @SerializedName("live")
        String live;

        @SerializedName("unlimited")
        String unlimited;

        @SerializedName("voucher_discount")
        String voucher_discount;

        @SerializedName("offer_content")
        String offer_content;

        @SerializedName("discount_content")
        String discount_content;

        @SerializedName("discount")
        String discount;

        @SerializedName("limit_user_quantity")
        String limit_user_quantity;

        @SerializedName("available_user_quantity")
        String available_user_quantity;

        @SerializedName("created_at")
        String created_at;

        @SerializedName("photos")
        List<Photo> photos;

        @SerializedName("subcategory")
        Subcategory subcategory;

        @SerializedName("optiongroups")
        List<OptionGroups> optiongroups;

        @SerializedName("options")
        List<Option> options;



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubcategory_id() {
            return subcategory_id;
        }

        public void setSubcategory_id(String subcategory_id) {
            this.subcategory_id = subcategory_id;
        }

        public String getSubsubcategory_id() {
            return subsubcategory_id;
        }

        public void setSubsubcategory_id(String subsubcategory_id) {
            this.subsubcategory_id = subsubcategory_id;
        }

        public String getLocation_id() {
            return location_id;
        }

        public void setLocation_id(String location_id) {
            this.location_id = location_id;
        }

        public String getOffer_number() {
            return offer_number;
        }

        public void setOffer_number(String offer_number) {
            this.offer_number = offer_number;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getStorage_group() {
            return storage_group;
        }

        public void setStorage_group(String storage_group) {
            this.storage_group = storage_group;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_ar() {
            return name_ar;
        }

        public void setName_ar(String name_ar) {
            this.name_ar = name_ar;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOld_price() {
            return old_price;
        }

        public void setOld_price(String old_price) {
            this.old_price = old_price;
        }

        public String getWholesale_price() {
            return wholesale_price;
        }

        public void setWholesale_price(String wholesale_price) {
            this.wholesale_price = wholesale_price;
        }

        public String getWholesale_old_price() {
            return wholesale_old_price;
        }

        public void setWholesale_old_price(String wholesale_old_price) {
            this.wholesale_old_price = wholesale_old_price;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getCartDesc() {
            return cartDesc;
        }

        public void setCartDesc(String cartDesc) {
            this.cartDesc = cartDesc;
        }

        public String getShortDesc() {
            return shortDesc;
        }

        public void setShortDesc(String shortDesc) {
            this.shortDesc = shortDesc;
        }

        public String getShortDescAr() {
            return shortDescAr;
        }

        public void setShortDescAr(String shortDescAr) {
            this.shortDescAr = shortDescAr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getLive() {
            return live;
        }

        public void setLive(String live) {
            this.live = live;
        }

        public String getUnlimited() {
            return unlimited;
        }

        public void setUnlimited(String unlimited) {
            this.unlimited = unlimited;
        }

        public String getVoucher_discount() {
            return voucher_discount;
        }

        public void setVoucher_discount(String voucher_discount) {
            this.voucher_discount = voucher_discount;
        }

        public String getOffer_content() {
            return offer_content;
        }

        public void setOffer_content(String offer_content) {
            this.offer_content = offer_content;
        }

        public String getDiscount_content() {
            return discount_content;
        }

        public void setDiscount_content(String discount_content) {
            this.discount_content = discount_content;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getLimit_user_quantity() {
            return limit_user_quantity;
        }

        public void setLimit_user_quantity(String limit_user_quantity) {
            this.limit_user_quantity = limit_user_quantity;
        }

        public String getAvailable_user_quantity() {
            return available_user_quantity;
        }

        public void setAvailable_user_quantity(String available_user_quantity) {
            this.available_user_quantity = available_user_quantity;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

        public Subcategory getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(Subcategory subcategory) {
            this.subcategory = subcategory;
        }

        public List<OptionGroups> getOptiongroups() {
            return optiongroups;
        }

        public void setOptiongroups(List<OptionGroups> optiongroups) {
            this.optiongroups = optiongroups;
        }

        public List<Option> getOptions() {
            return options;
        }

        public void setOptions(List<Option> options) {
            this.options = options;
        }
    }

    public class Photo{
        @SerializedName("id")
        String id;

        @SerializedName("product_id")
        String product_id;

        @SerializedName("option_id")
        String option_id;

        @SerializedName("filename")
        String filename;

        @SerializedName("mime")
        String mime;

        @SerializedName("original_filename")
        String original_filename;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getOption_id() {
            return option_id;
        }

        public void setOption_id(String option_id) {
            this.option_id = option_id;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getMime() {
            return mime;
        }

        public void setMime(String mime) {
            this.mime = mime;
        }

        public String getOriginal_filename() {
            return original_filename;
        }

        public void setOriginal_filename(String original_filename) {
            this.original_filename = original_filename;
        }
    }

    public class Subcategory{
        @SerializedName("id")
        String id;

        @SerializedName("category_id")
        String category_id;

        @SerializedName("name")
        String name;

        @SerializedName("name_ar")
        String name_ar;

        @SerializedName("active")
        String active;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_ar() {
            return name_ar;
        }

        public void setName_ar(String name_ar) {
            this.name_ar = name_ar;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }
    }

    public class OptionGroups{
        @SerializedName("id")
        String id;

        @SerializedName("name")
        String name;

        @SerializedName("pivot")
        PivotGroups pivot;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PivotGroups getPivot() {
            return pivot;
        }

        public void setPivot(PivotGroups pivot) {
            this.pivot = pivot;
        }
    }

    public class PivotGroups{
        @SerializedName("product_id")
        String product_id;

        @SerializedName("optiongroup_id")
        String optiongroup_id;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getOptiongroup_id() {
            return optiongroup_id;
        }

        public void setOptiongroup_id(String optiongroup_id) {
            this.optiongroup_id = optiongroup_id;
        }
    }

    public class Option{
        @SerializedName("id")
        String id;

        @SerializedName("optiongroup_id")
        String optiongroup_id;

        @SerializedName("name")
        String name;

        @SerializedName("pivot")
        Pivot pivot;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Pivot getPivot() {
            return pivot;
        }

        public void setPivot(Pivot pivot) {
            this.pivot = pivot;
        }
    }

    public class Pivot{
        @SerializedName("product_id")
        String product_id;

        @SerializedName("option_id")
        String option_id;

        @SerializedName("optiongroup_id")
        String optiongroup_id;

        @SerializedName("price_increment")
        String price_increment;

        @SerializedName("live")
        String live;

        @SerializedName("discount")
        String discount;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getOption_id() {
            return option_id;
        }

        public void setOption_id(String option_id) {
            this.option_id = option_id;
        }

        public String getOptiongroup_id() {
            return optiongroup_id;
        }

        public void setOptiongroup_id(String optiongroup_id) {
            this.optiongroup_id = optiongroup_id;
        }

        public String getPrice_increment() {
            return price_increment;
        }

        public void setPrice_increment(String price_increment) {
            this.price_increment = price_increment;
        }

        public String getLive() {
            return live;
        }

        public void setLive(String live) {
            this.live = live;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
    }


}
