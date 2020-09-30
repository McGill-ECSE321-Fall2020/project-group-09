package ca.mcgill.ecse321.artgallerysystem.model;


public class InStorePickUp extends Delivery {
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

/**
 * <pre>
 *           0..*     1..1
 * InStorePickUp ------------------------- Address
 *           inStorePickUp        &lt;       storeAddress
 * </pre>
 */
private Address storeAddress;

public void setStoreAddress(Address value) {
   this.storeAddress = value;
}

public Address getStoreAddress() {
   return this.storeAddress;
}

}
