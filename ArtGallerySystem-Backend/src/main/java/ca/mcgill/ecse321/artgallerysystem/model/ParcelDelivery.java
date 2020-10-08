package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class ParcelDelivery extends Delivery{
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
@Enumerated
private ParcelDeliveryStatus parcelDeliveryStatus;

public void setParcelDeliveryStatus(ParcelDeliveryStatus value) {
this.parcelDeliveryStatus = value;
    }
public ParcelDeliveryStatus getParcelDeliveryStatus() {
return this.parcelDeliveryStatus;
    }
private Address deliveryAddress;

@ManyToOne(optional=false)
public Address getDeliveryAddress() {
   return this.deliveryAddress;
}

public void setDeliveryAddress(Address deliveryAddress) {
   this.deliveryAddress = deliveryAddress;
}

}
