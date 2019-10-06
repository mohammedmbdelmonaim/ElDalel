package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShipments {

    @SerializedName("shipments")
    @Expose
    private List<Shipment> shipments = null;

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public class Shipment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("shipment_number")
        @Expose
        private Object shipmentNumber;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("company_id")
        @Expose
        private Object companyId;
        @SerializedName("address_id")
        @Expose
        private Integer addressId;
        @SerializedName("showroom_id")
        @Expose
        private Object showroomId;
        @SerializedName("address_type")
        @Expose
        private String addressType;
        @SerializedName("payment_type")
        @Expose
        private String paymentType;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("orders_count")
        @Expose
        private Integer ordersCount;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("allow_bill")
        @Expose
        private Integer allowBill;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Object getShipmentNumber() {
            return shipmentNumber;
        }

        public void setShipmentNumber(Object shipmentNumber) {
            this.shipmentNumber = shipmentNumber;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Object getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Object companyId) {
            this.companyId = companyId;
        }

        public Integer getAddressId() {
            return addressId;
        }

        public void setAddressId(Integer addressId) {
            this.addressId = addressId;
        }

        public Object getShowroomId() {
            return showroomId;
        }

        public void setShowroomId(Object showroomId) {
            this.showroomId = showroomId;
        }

        public String getAddressType() {
            return addressType;
        }

        public void setAddressType(String addressType) {
            this.addressType = addressType;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getOrdersCount() {
            return ordersCount;
        }

        public void setOrdersCount(Integer ordersCount) {
            this.ordersCount = ordersCount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getAllowBill() {
            return allowBill;
        }

        public void setAllowBill(Integer allowBill) {
            this.allowBill = allowBill;
        }

    }
}
