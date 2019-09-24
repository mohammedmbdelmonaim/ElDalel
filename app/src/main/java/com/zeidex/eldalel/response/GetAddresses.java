package com.zeidex.eldalel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAddresses {

    @SerializedName("primary_address")
    @Expose
    private Address primaryAddress;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }


    public class Address {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("primary")
        @Expose
        private Integer primary;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("country_id")
        @Expose
        private Integer countryId;
        @SerializedName("subsidiary_id")
        @Expose
        private Integer subsidiaryId;
        @SerializedName("city_id")
        @Expose
        private Integer cityId;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("postal_code")
        @Expose
        private String postalCode;
        @SerializedName("street_name_no")
        @Expose
        private Object streetNameNo;
        @SerializedName("building_name_no")
        @Expose
        private Object buildingNameNo;
        @SerializedName("floor_no")
        @Expose
        private Object floorNo;
        @SerializedName("apartment_no")
        @Expose
        private Object apartmentNo;
        @SerializedName("nearest_landmark")
        @Expose
        private Object nearestLandmark;
        @SerializedName("longitude")
        @Expose
        private Object longitude;
        @SerializedName("latitude")
        @Expose
        private Object latitude;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("phone")
        @Expose
        private Object phone;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("country")
        @Expose
        private Country country;
        @SerializedName("subsidiary")
        @Expose
        private Subsidiary subsidiary;
        @SerializedName("city")
        @Expose
        private City city;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPrimary() {
            return primary;
        }

        public void setPrimary(Integer primary) {
            this.primary = primary;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public Object getStreetNameNo() {
            return streetNameNo;
        }

        public void setStreetNameNo(Object streetNameNo) {
            this.streetNameNo = streetNameNo;
        }

        public Object getBuildingNameNo() {
            return buildingNameNo;
        }

        public void setBuildingNameNo(Object buildingNameNo) {
            this.buildingNameNo = buildingNameNo;
        }

        public Object getFloorNo() {
            return floorNo;
        }

        public void setFloorNo(Object floorNo) {
            this.floorNo = floorNo;
        }

        public Object getApartmentNo() {
            return apartmentNo;
        }

        public void setApartmentNo(Object apartmentNo) {
            this.apartmentNo = apartmentNo;
        }

        public Object getNearestLandmark() {
            return nearestLandmark;
        }

        public void setNearestLandmark(Object nearestLandmark) {
            this.nearestLandmark = nearestLandmark;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
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

        public Country getCountry() {
            return country;
        }

        public void setCountry(Country country) {
            this.country = country;
        }

        public Subsidiary getSubsidiary() {
            return subsidiary;
        }

        public void setSubsidiary(Subsidiary subsidiary) {
            this.subsidiary = subsidiary;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

    }


    public class City {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("subsidiary_id")
        @Expose
        private Integer subsidiaryId;
        @SerializedName("name_ar")
        @Expose
        private String nameAr;
        @SerializedName("name_en")
        @Expose
        private String nameEn;
        @SerializedName("active")
        @Expose
        private Integer active;
        @SerializedName("co_name_ar")
        @Expose
        private String coNameAr;
        @SerializedName("co_name_en")
        @Expose
        private String coNameEn;
        @SerializedName("co_active")
        @Expose
        private Integer coActive;
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

        public Integer getSubsidiaryId() {
            return subsidiaryId;
        }

        public void setSubsidiaryId(Integer subsidiaryId) {
            this.subsidiaryId = subsidiaryId;
        }

        public String getNameAr() {
            return nameAr;
        }

        public void setNameAr(String nameAr) {
            this.nameAr = nameAr;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }

        public String getCoNameAr() {
            return coNameAr;
        }

        public void setCoNameAr(String coNameAr) {
            this.coNameAr = coNameAr;
        }

        public String getCoNameEn() {
            return coNameEn;
        }

        public void setCoNameEn(String coNameEn) {
            this.coNameEn = coNameEn;
        }

        public Integer getCoActive() {
            return coActive;
        }

        public void setCoActive(Integer coActive) {
            this.coActive = coActive;
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

    public class Country {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name_ar")
        @Expose
        private String nameAr;
        @SerializedName("name_en")
        @Expose
        private String nameEn;
        @SerializedName("active")
        @Expose
        private Integer active;
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

        public String getNameAr() {
            return nameAr;
        }

        public void setNameAr(String nameAr) {
            this.nameAr = nameAr;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
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

    public class Subsidiary {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("supervisor_id")
        @Expose
        private Integer supervisorId;
        @SerializedName("country_id")
        @Expose
        private Integer countryId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("name_ar")
        @Expose
        private String nameAr;
        @SerializedName("longitude")
        @Expose
        private Object longitude;
        @SerializedName("latitude")
        @Expose
        private Object latitude;
        @SerializedName("active")
        @Expose
        private Integer active;
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

        public Integer getSupervisorId() {
            return supervisorId;
        }

        public void setSupervisorId(Integer supervisorId) {
            this.supervisorId = supervisorId;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
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

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
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
