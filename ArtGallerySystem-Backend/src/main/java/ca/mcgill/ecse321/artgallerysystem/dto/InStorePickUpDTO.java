package ca.mcgill.ecse321.artgallerysystem.dto;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import ca.mcgill.ecse321.artgallerysystem.model.Address;
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
	private Address storeAddress;

	
	public Address getStoreAddress() {
	   return this.storeAddress;
	}

	public void setStoreAddress(Address storeAddress) {
	   this.storeAddress = storeAddress;
	}

}
