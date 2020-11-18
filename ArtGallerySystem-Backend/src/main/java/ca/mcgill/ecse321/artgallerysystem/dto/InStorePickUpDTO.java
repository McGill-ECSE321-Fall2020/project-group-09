package ca.mcgill.ecse321.artgallerysystem.dto;

import javax.persistence.Enumerated;

import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUpStatus;

public class InStorePickUpDTO extends DeliveryDTO{
	private String pickUpReferenceNumber;
	   
	   public void setPickUpReferenceNumber(String value) {
	this.pickUpReferenceNumber = value;
	    }
	public String getPickUpReferenceNumber() {
	return this.pickUpReferenceNumber;
	    }
	@Enumerated
	private InStorePickUpStatus inStorePickUpStatus;

	public void setInStorePickUpStatus(InStorePickUpStatus value) {
	this.inStorePickUpStatus = value;
	    }
	public InStorePickUpStatus getInStorePickUpStatus() {
	return this.inStorePickUpStatus;
	    }
	private AddressDTO storeAddress;

	
	public AddressDTO getStoreAddress() {
	   return this.storeAddress;
	}

	public void setStoreAddress(AddressDTO storeAddress) {
	   this.storeAddress = storeAddress;
	}

}
