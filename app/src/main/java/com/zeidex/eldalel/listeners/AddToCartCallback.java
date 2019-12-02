package com.zeidex.eldalel.listeners;

import java.io.Serializable;

public interface AddToCartCallback extends Serializable {
    void setAddToCartResult(String totalCartCount);
}
