package ca.mcgill.ecse321.artgallerysystem.dto;

import javax.persistence.Enumerated;

import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;

public class PaymentDTO {
	private String paymentId;
	   
	   public void setPaymentId(String value) {
	this.paymentId = value;
	    }

	public String getPaymentId() {
	return this.paymentId;
	    }
	@Enumerated
	private PaymentMethod paymentMethod;

	public void setPaymentMethod(PaymentMethod value) {
	this.paymentMethod = value;
	    }
	public PaymentMethod getPaymentMethod() {
	return this.paymentMethod;
	    }
	private boolean isSuccessful;

	public void setIsSuccessful(boolean value) {
	this.isSuccessful = value;
	    }
	public boolean isIsSuccessful() {
	return this.isSuccessful;
	    }
	private PurchaseDTO purchase;

	public PurchaseDTO getPurchase() {
	   return this.purchase;
	}

	public void setPurchase(PurchaseDTO purchase) {
	   this.purchase = purchase;
	}

}
