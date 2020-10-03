package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class InStorePickUp extends Delivery{
private String pickUpReferenceNumber;
   
   public void setPickUpReferenceNumber(String value) {
this.pickUpReferenceNumber = value;
    }
public String getPickUpReferenceNumber() {
return this.pickUpReferenceNumber;
    }
private InStorePickUpStatus inStorePickUpStatus;

public void setInStorePickUpStatus(InStorePickUpStatus value) {
this.inStorePickUpStatus = value;
    }
public InStorePickUpStatus getInStorePickUpStatus() {
return this.inStorePickUpStatus;
    }
private Address storeAddress;

@ManyToOne(optional=false)
public Address getStoreAddress() {
   return this.storeAddress;
}

public void setStoreAddress(Address storeAddress) {
   this.storeAddress = storeAddress;
}

}
