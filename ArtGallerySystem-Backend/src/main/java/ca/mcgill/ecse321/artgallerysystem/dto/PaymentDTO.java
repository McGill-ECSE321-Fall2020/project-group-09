package ca.mcgill.ecse321.artgallerysystem.dto;

import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

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
	private Purchase purchase;

	public Purchase getPurchase() {
	   return this.purchase;
	}

	public void setPurchase(Purchase purchase) {
	   this.purchase = purchase;
	}

}
