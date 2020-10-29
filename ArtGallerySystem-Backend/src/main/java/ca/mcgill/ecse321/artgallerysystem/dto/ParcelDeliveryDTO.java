package ca.mcgill.ecse321.artgallerysystem.dto;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDeliveryStatus;

public class ParcelDeliveryDTO extends DeliveryDTO{
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


	public Address getDeliveryAddress() {
	   return this.deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
	   this.deliveryAddress = deliveryAddress;
	}

}
