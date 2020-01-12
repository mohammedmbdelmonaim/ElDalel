package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserShipmentProducts {
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
        @SerializedName("error")
        @Expose
        private List<String> errors;
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

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
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
        private Double productPrice;
        @SerializedName("total_price")
        @Expose
        private Double totalPrice;
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
        @SerializedName("product")
        @Expose
        private Product product;

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

        public Double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(Double productPrice) {
            this.productPrice = productPrice;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
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

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

    }

    public class Product {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("subcategory_id")
        @Expose
        private Integer subcategoryId;
        @SerializedName("subsubcategory_id")
        @Expose
        private Integer subsubcategoryId;
        @SerializedName("location_id")
        @Expose
        private Object locationId;
        @SerializedName("offer_number")
        @Expose
        private String offerNumber;
        @SerializedName("sku")
        @Expose
        private String sku;
        @SerializedName("group")
        @Expose
        private String group;
        @SerializedName("storage_group")
        @Expose
        private String storageGroup;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("name_ar")
        @Expose
        private String nameAr;
        @SerializedName("price")
        @Expose
        private Double price;
        @SerializedName("old_price")
        @Expose
        private Double oldPrice;
        @SerializedName("wholesale_price")
        @Expose
        private Double wholesalePrice;
        @SerializedName("wholesale_old_price")
        @Expose
        private Double wholesaleOldPrice;
        @SerializedName("weight")
        @Expose
        private Double weight;
        @SerializedName("cartDesc")
        @Expose
        private String cartDesc;
        @SerializedName("shortDesc")
        @Expose
        private String shortDesc;
        @SerializedName("shortDescAr")
        @Expose
        private String shortDescAr;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("status_company")
        @Expose
        private Integer statusCompany;
        @SerializedName("stock")
        @Expose
        private Object stock;
        @SerializedName("live")
        @Expose
        private Integer live;
        @SerializedName("live_company")
        @Expose
        private Integer liveCompany;
        @SerializedName("show_desc")
        @Expose
        private Integer showDesc;
        @SerializedName("unlimited")
        @Expose
        private Object unlimited;
        @SerializedName("voucher_discount")
        @Expose
        private Object voucherDiscount;
        @SerializedName("offer_content")
        @Expose
        private Object offerContent;
        @SerializedName("offer_content_en")
        @Expose
        private Object offerContentEn;
        @SerializedName("discount_content")
        @Expose
        private Object discountContent;
        @SerializedName("discount_content_en")
        @Expose
        private Object discountContentEn;
        @SerializedName("discount")
        @Expose
        private Double discount;
        @SerializedName("content_ar")
        @Expose
        private Object contentAr;
        @SerializedName("content_en")
        @Expose
        private Object contentEn;
        @SerializedName("limit_user_quantity")
        @Expose
        private Integer limitUserQuantity;
        @SerializedName("available_user_quantity")
        @Expose
        private Integer availableUserQuantity;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("photos")
        @Expose
        private List<Photo> photos;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSubcategoryId() {
            return subcategoryId;
        }

        public void setSubcategoryId(Integer subcategoryId) {
            this.subcategoryId = subcategoryId;
        }

        public Integer getSubsubcategoryId() {
            return subsubcategoryId;
        }

        public void setSubsubcategoryId(Integer subsubcategoryId) {
            this.subsubcategoryId = subsubcategoryId;
        }

        public Object getLocationId() {
            return locationId;
        }

        public void setLocationId(Object locationId) {
            this.locationId = locationId;
        }

        public String getOfferNumber() {
            return offerNumber;
        }

        public void setOfferNumber(String offerNumber) {
            this.offerNumber = offerNumber;
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

        public String getStorageGroup() {
            return storageGroup;
        }

        public void setStorageGroup(String storageGroup) {
            this.storageGroup = storageGroup;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameAr() {
            return nameAr;
        }

        public void setNameAr(String nameAr) {
            this.nameAr = nameAr;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(Double oldPrice) {
            this.oldPrice = oldPrice;
        }

        public Double getWholesalePrice() {
            return wholesalePrice;
        }

        public void setWholesalePrice(Double wholesalePrice) {
            this.wholesalePrice = wholesalePrice;
        }

        public Double getWholesaleOldPrice() {
            return wholesaleOldPrice;
        }

        public void setWholesaleOldPrice(Double wholesaleOldPrice) {
            this.wholesaleOldPrice = wholesaleOldPrice;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getStatusCompany() {
            return statusCompany;
        }

        public void setStatusCompany(Integer statusCompany) {
            this.statusCompany = statusCompany;
        }

        public Object getStock() {
            return stock;
        }

        public void setStock(Object stock) {
            this.stock = stock;
        }

        public Integer getLive() {
            return live;
        }

        public void setLive(Integer live) {
            this.live = live;
        }

        public Integer getLiveCompany() {
            return liveCompany;
        }

        public void setLiveCompany(Integer liveCompany) {
            this.liveCompany = liveCompany;
        }

        public Integer getShowDesc() {
            return showDesc;
        }

        public void setShowDesc(Integer showDesc) {
            this.showDesc = showDesc;
        }

        public Object getUnlimited() {
            return unlimited;
        }

        public void setUnlimited(Object unlimited) {
            this.unlimited = unlimited;
        }

        public Object getVoucherDiscount() {
            return voucherDiscount;
        }

        public void setVoucherDiscount(Object voucherDiscount) {
            this.voucherDiscount = voucherDiscount;
        }

        public Object getOfferContent() {
            return offerContent;
        }

        public void setOfferContent(Object offerContent) {
            this.offerContent = offerContent;
        }

        public Object getOfferContentEn() {
            return offerContentEn;
        }

        public void setOfferContentEn(Object offerContentEn) {
            this.offerContentEn = offerContentEn;
        }

        public Object getDiscountContent() {
            return discountContent;
        }

        public void setDiscountContent(Object discountContent) {
            this.discountContent = discountContent;
        }

        public Object getDiscountContentEn() {
            return discountContentEn;
        }

        public void setDiscountContentEn(Object discountContentEn) {
            this.discountContentEn = discountContentEn;
        }

        public Double getDiscount() {
            return discount;
        }

        public void setDiscount(Double discount) {
            this.discount = discount;
        }

        public Object getContentAr() {
            return contentAr;
        }

        public void setContentAr(Object contentAr) {
            this.contentAr = contentAr;
        }

        public Object getContentEn() {
            return contentEn;
        }

        public void setContentEn(Object contentEn) {
            this.contentEn = contentEn;
        }

        public Integer getLimitUserQuantity() {
            return limitUserQuantity;
        }

        public void setLimitUserQuantity(Integer limitUserQuantity) {
            this.limitUserQuantity = limitUserQuantity;
        }

        public Integer getAvailableUserQuantity() {
            return availableUserQuantity;
        }

        public void setAvailableUserQuantity(Integer availableUserQuantity) {
            this.availableUserQuantity = availableUserQuantity;
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

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
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
        private Integer companyId;
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
        private Double discount;
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

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
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

        public Double getDiscount() {
            return discount;
        }

        public void setDiscount(Double discount) {
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

    public class Photo {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("option_id")
        @Expose
        private Integer optionId;
        @SerializedName("filename")
        @Expose
        private String filename;
        @SerializedName("mime")
        @Expose
        private String mime;
        @SerializedName("original_filename")
        @Expose
        private String originalFilename;
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

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getOptionId() {
            return optionId;
        }

        public void setOptionId(Integer optionId) {
            this.optionId = optionId;
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

        public String getOriginalFilename() {
            return originalFilename;
        }

        public void setOriginalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
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

}