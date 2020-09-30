package ca.mcgill.ecse321.artgallerysystem.model;

import java.sql.Date;
import java.util.Set;
import java.util.HashSet;

public class Order {
private String orderId;

public void setOrderId(String value) {
   this.orderId = value;
}

public String getOrderId() {
   return this.orderId;
}

private Date date;

public void setDate(Date value) {
   this.date = value;
}

public Date getDate() {
   return this.date;
}

private OrderStatus orderStatus;

public void setOrderStatus(OrderStatus value) {
   this.orderStatus = value;
}

public OrderStatus getOrderStatus() {
   return this.orderStatus;
}

/**
 * <pre>
 *           1..1     0..1
 * Order ------------------------- Delivery
 *           order        &gt;       delivery
 * </pre>
 */
private Delivery delivery;

public void setDelivery(Delivery value) {
   this.delivery = value;
}

public Delivery getDelivery() {
   return this.delivery;
}

/**
 * <pre>
 *           1..1     0..*
 * Order ------------------------- Payment
 *           order        &gt;       payment
 * </pre>
 */
private Set<Payment> payment;

public Set<Payment> getPayment() {
   if (this.payment == null) {
this.payment = new HashSet<Payment>();
   }
   return this.payment;
}

/**
 * <pre>
 *           0..*     1..1
 * Order ------------------------- Customer
 *           order        &lt;       customer
 * </pre>
 */
private Customer customer;

public void setCustomer(Customer value) {
   this.customer = value;
}

public Customer getCustomer() {
   return this.customer;
}

/**
 * <pre>
 *           0..1     1..1
 * Order ------------------------- ArtPiece
 *           order        &lt;       artPiece
 * </pre>
 */
private ArtPiece artPiece;

public void setArtPiece(ArtPiece value) {
   this.artPiece = value;
}

public ArtPiece getArtPiece() {
   return this.artPiece;
}

/**
 * <pre>
 *           0..*     1..1
 * Order ------------------------- ArtGallerySystem
 *           order        &lt;       artGallerySystem
 * </pre>
 */
private ArtGallerySystem artGallerySystem;

public void setArtGallerySystem(ArtGallerySystem value) {
   this.artGallerySystem = value;
}

public ArtGallerySystem getArtGallerySystem() {
   return this.artGallerySystem;
}

}
