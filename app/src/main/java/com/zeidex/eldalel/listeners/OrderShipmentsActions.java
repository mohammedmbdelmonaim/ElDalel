package com.zeidex.eldalel.listeners;

public interface OrderShipmentsActions {

    void onOrderShipmentDeliver(int orderId, int position, String type);
    void onOrderShipmentCancel(int orderId, int position, String type);
    void onOrderShipmentChangeQuantity(int orderId, int position, int quantity, String type);
}
