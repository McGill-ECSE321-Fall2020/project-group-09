package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class InstorePickUp extends Delivery{
private String pickupNumber;
   
   public void setPickupNumber(String value) {
this.pickupNumber = value;
    }
public String getPickupNumber() {
return this.pickupNumber;
    }
private InStorePickUpStatus instorePickUpStatus;

public void setInstorePickUpStatus(InStorePickUpStatus value) {
this.instorePickUpStatus = value;
    }
public InStorePickUpStatus getInstorePickUpStatus() {
return this.instorePickUpStatus;
    }
private Address storeaddress;

@ManyToOne(optional=false)
public Address getStoreaddress() {
   return this.storeaddress;
}

public void setStoreaddress(Address storeaddress) {
   this.storeaddress = storeaddress;
}

}
