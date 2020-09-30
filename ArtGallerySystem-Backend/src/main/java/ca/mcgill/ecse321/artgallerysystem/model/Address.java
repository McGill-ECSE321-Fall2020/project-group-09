package ca.mcgill.ecse321.artgallerysystem.model;

import java.util.Set;
import java.util.HashSet;

public class Address {
private String name;

public void setName(String value) {
   this.name = value;
}

public String getName() {
   return this.name;
}

private String addressId;

public void setAddressId(String value) {
   this.addressId = value;
}

public String getAddressId() {
   return this.addressId;
}

private String phoneNumber;

public void setPhoneNumber(String value) {
   this.phoneNumber = value;
}

public String getPhoneNumber() {
   return this.phoneNumber;
}

private String postalCode;

public void setPostalCode(String value) {
   this.postalCode = value;
}

public String getPostalCode() {
   return this.postalCode;
}

private String street;

public void setStreet(String value) {
   this.street = value;
}

public String getStreet() {
   return this.street;
}

private String country;

public void setCountry(String value) {
   this.country = value;
}

public String getCountry() {
   return this.country;
}

private String province;

public void setProvince(String value) {
   this.province = value;
}

public String getProvince() {
   return this.province;
}

private String city;

public void setCity(String value) {
   this.city = value;
}

public String getCity() {
   return this.city;
}

/**
 * <pre>
 *           0..*     0..1
 * Address ------------------------- Customer
 *           address        &gt;       customer
 * </pre>
 */
private Customer customer;

public void setCustomer(Customer value) {
   this.customer = value;
}

public Customer getCustomer() {
   return this.customer;
}

/**
 * <pre>
 *           1..1     0..*
 * Address ------------------------- ParcelDelivery
 *           deliveryaddress        &gt;       parcelDelivery
 * </pre>
 */
private Set<ParcelDelivery> parcelDelivery;

public Set<ParcelDelivery> getParcelDelivery() {
   if (this.parcelDelivery == null) {
this.parcelDelivery = new HashSet<ParcelDelivery>();
   }
   return this.parcelDelivery;
}

/**
 * <pre>
 *           1..1     0..*
 * Address ------------------------- InstorePickUp
 *           storeaddress        &gt;       instorePickUp
 * </pre>
 */
private Set<InstorePickUp> instorePickUp;

public Set<InstorePickUp> getInstorePickUp() {
   if (this.instorePickUp == null) {
this.instorePickUp = new HashSet<InstorePickUp>();
   }
   return this.instorePickUp;
}

/**
 * <pre>
 *           0..*     1..1
 * Address ------------------------- ArtGallerySystem
 *           address        &lt;       artGallerySystem
 * </pre>
 */
private ArtGallerySystem artGallerySystem;

public void setArtGallerySystem(ArtGallerySystem value) {
   this.artGallerySystem = value;
}

public ArtGallerySystem getArtGallerySystem() {
   return this.artGallerySystem;
}

}
