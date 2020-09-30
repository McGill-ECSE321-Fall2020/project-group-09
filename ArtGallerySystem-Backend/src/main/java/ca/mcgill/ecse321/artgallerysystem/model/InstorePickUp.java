package ca.mcgill.ecse321.artgallerysystem.model;


public class InstorePickUp extends Delivery {
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

/**
 * <pre>
 *           0..*     1..1
 * InstorePickUp ------------------------- Address
 *           instorePickUp        &lt;       storeaddress
 * </pre>
 */
private Address storeaddress;

public void setStoreaddress(Address value) {
   this.storeaddress = value;
}

public Address getStoreaddress() {
   return this.storeaddress;
}

}
