package com.zeidex.eldalel.models;

public class CapacityProduct {
    String product_id;
    String name;

    public CapacityProduct(String product_id, String name) {
        this.product_id = product_id;
        this.name = name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
