package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCompaniesOrders {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private Data data;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public Integer getCode() {
return code;
}

public void setCode(Integer code) {
this.code = code;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

    public class Data {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("orders")
        @Expose
        private List<Order> orders = null;
        @SerializedName("empty_orders")
        @Expose
        private String emptyOrders;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public List<Order> getOrders() {
            return orders;
        }

        public void setOrders(List<Order> orders) {
            this.orders = orders;
        }

        public String getEmptyOrders() {
            return emptyOrders;
        }

        public void setEmptyOrders(String emptyOrders) {
            this.emptyOrders = emptyOrders;
        }

    }

    public class Order {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("group_number")
        @Expose
        private Integer groupNumber;
        @SerializedName("supervisor_id")
        @Expose
        private Object supervisorId;
        @SerializedName("user_id")
        @Expose
        private Object userId;
        @SerializedName("company_id")
        @Expose
        private Integer companyId;
        @SerializedName("manager_id")
        @Expose
        private Integer managerId;
        @SerializedName("vendor_id")
        @Expose
        private Integer vendorId;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("shipment_id")
        @Expose
        private Integer shipmentId;
        @SerializedName("offer_number")
        @Expose
        private String offerNumber;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("available_quantity")
        @Expose
        private Integer availableQuantity;
        @SerializedName("product_price")
        @Expose
        private Integer productPrice;
        @SerializedName("total_price")
        @Expose
        private Integer totalPrice;
        @SerializedName("total_price_with_tax")
        @Expose
        private Integer totalPriceWithTax;
        @SerializedName("supervisor_status")
        @Expose
        private String supervisorStatus;
        @SerializedName("supervisor_note")
        @Expose
        private Object supervisorNote;
        @SerializedName("manager_status")
        @Expose
        private String managerStatus;
        @SerializedName("manager_note")
        @Expose
        private Object managerNote;
        @SerializedName("company_receipt")
        @Expose
        private String companyReceipt;
        @SerializedName("company_note")
        @Expose
        private Object companyNote;
        @SerializedName("subsidiary_id")
        @Expose
        private Integer subsidiaryId;
        @SerializedName("user_receipt")
        @Expose
        private Object userReceipt;
        @SerializedName("user_note")
        @Expose
        private Object userNote;
        @SerializedName("company_status")
        @Expose
        private Object companyStatus;
        @SerializedName("vendor_delivery")
        @Expose
        private String vendorDelivery;
        @SerializedName("vendor_note")
        @Expose
        private Object vendorNote;
        @SerializedName("vendor_delivery_date")
        @Expose
        private Object vendorDeliveryDate;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("p_id")
        @Expose
        private Object pId;
        @SerializedName("company")
        @Expose
        private Company company;
        @SerializedName("shipment")
        @Expose
        private Shipment shipment;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getGroupNumber() {
            return groupNumber;
        }

        public void setGroupNumber(Integer groupNumber) {
            this.groupNumber = groupNumber;
        }

        public Object getSupervisorId() {
            return supervisorId;
        }

        public void setSupervisorId(Object supervisorId) {
            this.supervisorId = supervisorId;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public Integer getManagerId() {
            return managerId;
        }

        public void setManagerId(Integer managerId) {
            this.managerId = managerId;
        }

        public Integer getVendorId() {
            return vendorId;
        }

        public void setVendorId(Integer vendorId) {
            this.vendorId = vendorId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getShipmentId() {
            return shipmentId;
        }

        public void setShipmentId(Integer shipmentId) {
            this.shipmentId = shipmentId;
        }

        public String getOfferNumber() {
            return offerNumber;
        }

        public void setOfferNumber(String offerNumber) {
            this.offerNumber = offerNumber;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getAvailableQuantity() {
            return availableQuantity;
        }

        public void setAvailableQuantity(Integer availableQuantity) {
            this.availableQuantity = availableQuantity;
        }

        public Integer getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(Integer productPrice) {
            this.productPrice = productPrice;
        }

        public Integer getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Integer getTotalPriceWithTax() {
            return totalPriceWithTax;
        }

        public void setTotalPriceWithTax(Integer totalPriceWithTax) {
            this.totalPriceWithTax = totalPriceWithTax;
        }

        public String getSupervisorStatus() {
            return supervisorStatus;
        }

        public void setSupervisorStatus(String supervisorStatus) {
            this.supervisorStatus = supervisorStatus;
        }

        public Object getSupervisorNote() {
            return supervisorNote;
        }

        public void setSupervisorNote(Object supervisorNote) {
            this.supervisorNote = supervisorNote;
        }

        public String getManagerStatus() {
            return managerStatus;
        }

        public void setManagerStatus(String managerStatus) {
            this.managerStatus = managerStatus;
        }

        public Object getManagerNote() {
            return managerNote;
        }

        public void setManagerNote(Object managerNote) {
            this.managerNote = managerNote;
        }

        public String getCompanyReceipt() {
            return companyReceipt;
        }

        public void setCompanyReceipt(String companyReceipt) {
            this.companyReceipt = companyReceipt;
        }

        public Object getCompanyNote() {
            return companyNote;
        }

        public void setCompanyNote(Object companyNote) {
            this.companyNote = companyNote;
        }

        public Integer getSubsidiaryId() {
            return subsidiaryId;
        }

        public void setSubsidiaryId(Integer subsidiaryId) {
            this.subsidiaryId = subsidiaryId;
        }

        public Object getUserReceipt() {
            return userReceipt;
        }

        public void setUserReceipt(Object userReceipt) {
            this.userReceipt = userReceipt;
        }

        public Object getUserNote() {
            return userNote;
        }

        public void setUserNote(Object userNote) {
            this.userNote = userNote;
        }

        public Object getCompanyStatus() {
            return companyStatus;
        }

        public void setCompanyStatus(Object companyStatus) {
            this.companyStatus = companyStatus;
        }

        public String getVendorDelivery() {
            return vendorDelivery;
        }

        public void setVendorDelivery(String vendorDelivery) {
            this.vendorDelivery = vendorDelivery;
        }

        public Object getVendorNote() {
            return vendorNote;
        }

        public void setVendorNote(Object vendorNote) {
            this.vendorNote = vendorNote;
        }

        public Object getVendorDeliveryDate() {
            return vendorDeliveryDate;
        }

        public void setVendorDeliveryDate(Object vendorDeliveryDate) {
            this.vendorDeliveryDate = vendorDeliveryDate;
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

        public Object getPId() {
            return pId;
        }

        public void setPId(Object pId) {
            this.pId = pId;
        }

        public Company getCompany() {
            return company;
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Shipment getShipment() {
            return shipment;
        }

        public void setShipment(Shipment shipment) {
            this.shipment = shipment;
        }

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
        private Object userId;
        @SerializedName("company_id")
        @Expose
        private Integer companyId;
        @SerializedName("address_id")
        @Expose
        private Object addressId;
        @SerializedName("showroom_id")
        @Expose
        private Object showroomId;
        @SerializedName("address_type")
        @Expose
        private Integer addressType;
        @SerializedName("payment_type")
        @Expose
        private Integer paymentType;
        @SerializedName("discount")
        @Expose
        private Object discount;
        @SerializedName("total_price")
        @Expose
        private Integer totalPrice;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

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

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public Object getAddressId() {
            return addressId;
        }

        public void setAddressId(Object addressId) {
            this.addressId = addressId;
        }

        public Object getShowroomId() {
            return showroomId;
        }

        public void setShowroomId(Object showroomId) {
            this.showroomId = showroomId;
        }

        public Integer getAddressType() {
            return addressType;
        }

        public void setAddressType(Integer addressType) {
            this.addressType = addressType;
        }

        public Integer getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(Integer paymentType) {
            this.paymentType = paymentType;
        }

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public Integer getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
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

    }

    public class Company {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("supervisor_id")
        @Expose
        private Integer supervisorId;
        @SerializedName("subsidiary_id")
        @Expose
        private Integer subsidiaryId;
        @SerializedName("country_id")
        @Expose
        private Integer countryId;
        @SerializedName("city_id")
        @Expose
        private Integer cityId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("responsible")
        @Expose
        private String responsible;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("token")
        @Expose
        private Object token;
        @SerializedName("ios_token")
        @Expose
        private Object iosToken;
        @SerializedName("emailVerified")
        @Expose
        private Integer emailVerified;
        @SerializedName("postpay")
        @Expose
        private Integer postpay;
        @SerializedName("verificationCode")
        @Expose
        private Object verificationCode;
        @SerializedName("sms_verified")
        @Expose
        private Integer smsVerified;
        @SerializedName("sms_code")
        @Expose
        private Object smsCode;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("p_id")
        @Expose
        private Object pId;
        @SerializedName("payment_type")
        @Expose
        private Object paymentType;
        @SerializedName("showroom_id")
        @Expose
        private Object showroomId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSupervisorId() {
            return supervisorId;
        }

        public void setSupervisorId(Integer supervisorId) {
            this.supervisorId = supervisorId;
        }

        public Integer getSubsidiaryId() {
            return subsidiaryId;
        }

        public void setSubsidiaryId(Integer subsidiaryId) {
            this.subsidiaryId = subsidiaryId;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getResponsible() {
            return responsible;
        }

        public void setResponsible(String responsible) {
            this.responsible = responsible;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public Object getIosToken() {
            return iosToken;
        }

        public void setIosToken(Object iosToken) {
            this.iosToken = iosToken;
        }

        public Integer getEmailVerified() {
            return emailVerified;
        }

        public void setEmailVerified(Integer emailVerified) {
            this.emailVerified = emailVerified;
        }

        public Integer getPostpay() {
            return postpay;
        }

        public void setPostpay(Integer postpay) {
            this.postpay = postpay;
        }

        public Object getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(Object verificationCode) {
            this.verificationCode = verificationCode;
        }

        public Integer getSmsVerified() {
            return smsVerified;
        }

        public void setSmsVerified(Integer smsVerified) {
            this.smsVerified = smsVerified;
        }

        public Object getSmsCode() {
            return smsCode;
        }

        public void setSmsCode(Object smsCode) {
            this.smsCode = smsCode;
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

        public Object getPId() {
            return pId;
        }

        public void setPId(Object pId) {
            this.pId = pId;
        }

        public Object getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(Object paymentType) {
            this.paymentType = paymentType;
        }

        public Object getShowroomId() {
            return showroomId;
        }

        public void setShowroomId(Object showroomId) {
            this.showroomId = showroomId;
        }

    }

}