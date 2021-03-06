package ca.mcgill.ecse321.artgallerysystem.dto;

import javax.persistence.Enumerated;

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
	private AddressDTO deliveryAddress;


	public AddressDTO getDeliveryAddress() {
	   return this.deliveryAddress;
	}

	public void setDeliveryAddress(AddressDTO deliveryAddress) {
	   this.deliveryAddress = deliveryAddress;
	}

}
