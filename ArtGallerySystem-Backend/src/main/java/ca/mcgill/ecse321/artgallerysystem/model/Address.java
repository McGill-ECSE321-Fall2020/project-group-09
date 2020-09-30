package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Address{
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
@Id
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
   private Customer customer;
   
   @ManyToOne
   public Customer getCustomer() {
      return this.customer;
   }
   
   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
   
   private Set<ParcelDelivery> parcelDelivery;
   
   @OneToMany(mappedBy="deliveryaddress" )
   public Set<ParcelDelivery> getParcelDelivery() {
      return this.parcelDelivery;
   }
   
   public void setParcelDelivery(Set<ParcelDelivery> parcelDeliverys) {
      this.parcelDelivery = parcelDeliverys;
   }
   
   private Set<InstorePickUp> instorePickUp;
   
   @OneToMany(mappedBy="storeaddress" )
   public Set<InstorePickUp> getInstorePickUp() {
      return this.instorePickUp;
   }
   
   public void setInstorePickUp(Set<InstorePickUp> instorePickUps) {
      this.instorePickUp = instorePickUps;
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
