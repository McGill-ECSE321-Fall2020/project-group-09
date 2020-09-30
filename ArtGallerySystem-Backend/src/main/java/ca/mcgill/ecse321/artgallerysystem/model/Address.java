package ca.mcgill.ecse321.artgallerysystem.model;

import java.util.Set;
import java.util.HashSet;

public class Address {
private String addressId;

public void setAddressId(String value) {
   this.addressId = value;
}

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
 *           deliveryAddress        &gt;       parcelDelivery
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
 * Address ------------------------- InStorePickUp
 *           storeAddress        &gt;       inStorePickUp
 * </pre>
 */
private Set<InStorePickUp> inStorePickUp;

public Set<InStorePickUp> getInStorePickUp() {
   if (this.inStorePickUp == null) {
this.inStorePickUp = new HashSet<InStorePickUp>();
   }
   return this.inStorePickUp;
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
