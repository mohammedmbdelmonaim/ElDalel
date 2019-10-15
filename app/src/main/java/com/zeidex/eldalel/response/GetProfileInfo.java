package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfileInfo {
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
    private String smsCode;
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

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
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

}
