package ca.mcgill.ecse321.artgallerysystem.dto;

import javax.persistence.Id;
import javax.persistence.OneToOne;

import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

public class DeliveryDTO {
	private String deliveryId;
	   
	   public void setDeliveryId(String value) {
	this.deliveryId = value;
	    }
	
	public String getDeliveryId() {
	return this.deliveryId;
	    }
	private PurchaseDTO purchase;

	
	public PurchaseDTO getPurchase() {
	   return this.purchase;
	}

	public void setPurchase(PurchaseDTO purchase) {
	   this.purchase = purchase;
	}

}
