package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUsersOrders {

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
        private Integer userId;
        @SerializedName("company_id")
        @Expose
        private Object companyId;
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
        private Double totalPriceWithTax;
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
        private Object companyReceipt;
        @SerializedName("company_note")
        @Expose
        private Object companyNote;
        @SerializedName("subsidiary_id")
        @Expose
        private Integer subsidiaryId;
        @SerializedName("user_receipt")
        @Expose
        private String userReceipt;
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
        @SerializedName("user")
        @Expose
        private User user;
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

        public Double getTotalPriceWithTax() {
            return totalPriceWithTax;
        }

        public void setTotalPriceWithTax(Double totalPriceWithTax) {
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

        public Object getCompanyReceipt() {
            return companyReceipt;
        }

        public void setCompanyReceipt(Object companyReceipt) {
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

        public String getUserReceipt() {
            return userReceipt;
        }

        public void setUserReceipt(String userReceipt) {
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

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
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
        private Integer shipmentNumber;
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
        private Integer addressType;
        @SerializedName("payment_type")
        @Expose
        private Integer paymentType;
        @SerializedName("discount")
        @Expose
        private Object discount;
        @SerializedName("total_price")
        @Expose
        private Double totalPrice;
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

        public Integer getShipmentNumber() {
            return shipmentNumber;
        }

        public void setShipmentNumber(Integer shipmentNumber) {
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

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
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

    public class User {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("country_id")
        @Expose
        private Integer countryId;
        @SerializedName("subsidiary_id")
        @Expose
        private Integer subsidiaryId;
        @SerializedName("city_id")
        @Expose
        private Integer cityId;
        @SerializedName("state")
        @Expose
        private Object state;
        @SerializedName("zip")
        @Expose
        private Object zip;
        @SerializedName("emailVerified")
        @Expose
        private Integer emailVerified;
        @SerializedName("verificationCode")
        @Expose
        private Object verificationCode;
        @SerializedName("sms_code")
        @Expose
        private Object smsCode;
        @SerializedName("sms_verified")
        @Expose
        private Integer smsVerified;
        @SerializedName("ip")
        @Expose
        private Object ip;
        @SerializedName("phone")
        @Expose
        private Object phone;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("block_mobile")
        @Expose
        private Integer blockMobile;
        @SerializedName("fax")
        @Expose
        private Object fax;
        @SerializedName("addressHome")
        @Expose
        private String addressHome;
        @SerializedName("addressWork")
        @Expose
        private Object addressWork;
        @SerializedName("ios_token")
        @Expose
        private Object iosToken;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("p_id")
        @Expose
        private String pId;
        @SerializedName("p_address")
        @Expose
        private String pAddress;
        @SerializedName("payment_type")
        @Expose
        private Integer paymentType;
        @SerializedName("showroom_id")
        @Expose
        private Object showroomId;
        @SerializedName("name")
        @Expose
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        public Integer getSubsidiaryId() {
            return subsidiaryId;
        }

        public void setSubsidiaryId(Integer subsidiaryId) {
            this.subsidiaryId = subsidiaryId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getZip() {
            return zip;
        }

        public void setZip(Object zip) {
            this.zip = zip;
        }

        public Integer getEmailVerified() {
            return emailVerified;
        }

        public void setEmailVerified(Integer emailVerified) {
            this.emailVerified = emailVerified;
        }

        public Object getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(Object verificationCode) {
            this.verificationCode = verificationCode;
        }

        public Object getSmsCode() {
            return smsCode;
        }

        public void setSmsCode(Object smsCode) {
            this.smsCode = smsCode;
        }

        public Integer getSmsVerified() {
            return smsVerified;
        }

        public void setSmsVerified(Integer smsVerified) {
            this.smsVerified = smsVerified;
        }

        public Object getIp() {
            return ip;
        }

        public void setIp(Object ip) {
            this.ip = ip;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Integer getBlockMobile() {
            return blockMobile;
        }

        public void setBlockMobile(Integer blockMobile) {
            this.blockMobile = blockMobile;
        }

        public Object getFax() {
            return fax;
        }

        public void setFax(Object fax) {
            this.fax = fax;
        }

        public String getAddressHome() {
            return addressHome;
        }

        public void setAddressHome(String addressHome) {
            this.addressHome = addressHome;
        }

        public Object getAddressWork() {
            return addressWork;
        }

        public void setAddressWork(Object addressWork) {
            this.addressWork = addressWork;
        }

        public Object getIosToken() {
            return iosToken;
        }

        public void setIosToken(Object iosToken) {
            this.iosToken = iosToken;
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

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public String getPAddress() {
            return pAddress;
        }

        public void setPAddress(String pAddress) {
            this.pAddress = pAddress;
        }

        public Integer getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(Integer paymentType) {
            this.paymentType = paymentType;
        }

        public Object getShowroomId() {
            return showroomId;
        }

        public void setShowroomId(Object showroomId) {
            this.showroomId = showroomId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}