package ca.mcgill.ecse321.artgallerysystem.model;


public class ParcelDelivery extends Delivery {
private String trackingNumber;

public void setTrackingNumber(String value) {
   this.trackingNumber = value;
}

public String getTrackingNumber() {
   return this.trackingNumber;
}

private String carrier;

public void setCarrier(String value) {
   this.carrier = value;
}

public String getCarrier() {
   return this.carrier;
}

private ParcelDeliveryStatus parcelDeliveryStatus;

public void setParcelDeliveryStatus(ParcelDeliveryStatus value) {
   this.parcelDeliveryStatus = value;
}

public ParcelDeliveryStatus getParcelDeliveryStatus() {
   return this.parcelDeliveryStatus;
}

/**
 * <pre>
 *           0..*     1..1
 * ParcelDelivery ------------------------- Address
 *           parcelDelivery        &lt;       deliveryaddress
 * </pre>
 */
private Address deliveryaddress;

public void setDeliveryaddress(Address value) {
   this.deliveryaddress = value;
}

public Address getDeliveryaddress() {
   return this.deliveryaddress;
}

}
