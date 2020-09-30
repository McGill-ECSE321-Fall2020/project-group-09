package ca.mcgill.ecse321.artgallerysystem.model;


public class Payment {
private String paymentId;

public void setPaymentId(String value) {
   this.paymentId = value;
}

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

/**
 * <pre>
 *           0..*     1..1
 * Payment ------------------------- Order
 *           payment        &lt;       order
 * </pre>
 */
private Order order;

public void setOrder(Order value) {
   this.order = value;
}

public Order getOrder() {
   return this.order;
}

}
