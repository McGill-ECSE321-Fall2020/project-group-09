package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Payment{
private String paymentId;
   
   public void setPaymentId(String value) {
this.paymentId = value;
    }
@Id
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

@ManyToOne(optional=false)
public Purchase getPurchase() {
   return this.purchase;
}

public void setPurchase(Purchase purchase) {
   this.purchase = purchase;
}

}
