package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
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
private Order order;

@ManyToOne(optional=false)
public Order getOrder() {
   return this.order;
}

public void setOrder(Order order) {
   this.order = order;
}

}
