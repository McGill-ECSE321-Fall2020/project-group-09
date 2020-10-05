package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Address{
private String addressId;
   
   public void setAddressId(String value) {
this.addressId = value;
    }
@Id
public String getAddressId() {
return this.addressId;
    }
private String name;

public void setName(String value) {
this.name = value;
    }
public String getName() {
return this.name;
    }
private String phoneNumber;

public void setPhoneNumber(String value) {
this.phoneNumber = value;
    }
public String getPhoneNumber() {
return this.phoneNumber;
    }
private String streetAddress;

public void setStreetAddress(String value) {
this.streetAddress = value;
    }
public String getStreetAddress() {
return this.streetAddress;
    }
private String city;

public void setCity(String value) {
this.city = value;
    }
public String getCity() {
return this.city;
    }
private String province;

public void setProvince(String value) {
this.province = value;
    }
public String getProvince() {
return this.province;
    }
private String postalCode;

public void setPostalCode(String value) {
this.postalCode = value;
    }
public String getPostalCode() {
return this.postalCode;
    }
private String country;

public void setCountry(String value) {
this.country = value;
    }
public String getCountry() {
return this.country;
    }
private ArtGallerySystem artGallerySystem;

@ManyToOne(optional=false)
public ArtGallerySystem getArtGallerySystem() {
   return this.artGallerySystem;
}

public void setArtGallerySystem(ArtGallerySystem artGallerySystem) {
   this.artGallerySystem = artGallerySystem;
}

}
