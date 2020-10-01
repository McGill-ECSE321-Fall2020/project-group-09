package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
public class Order{
private String orderId;
   
   public void setOrderId(String value) {
this.orderId = value;
    }
@Id
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
private Delivery delivery;

@OneToOne(mappedBy="order", cascade={CascadeType.ALL})
public Delivery getDelivery() {
   return this.delivery;
}

public void setDelivery(Delivery delivery) {
   this.delivery = delivery;
}

private Set<Payment> payment;

@OneToMany(mappedBy="order", cascade={CascadeType.ALL})
public Set<Payment> getPayment() {
   return this.payment;
}

public void setPayment(Set<Payment> payments) {
   this.payment = payments;
}

private Customer customer;

@ManyToOne(optional=false)
public Customer getCustomer() {
   return this.customer;
}

public void setCustomer(Customer customer) {
   this.customer = customer;
}

private ArtPiece artPiece;

@OneToOne(optional=false)
public ArtPiece getArtPiece() {
   return this.artPiece;
}

public void setArtPiece(ArtPiece artPiece) {
   this.artPiece = artPiece;
}

private ArtGallerySystem artGallerySystem;

@ManyToOne(optional=false)
public ArtGallerySystem getArtGallerySystem() {
   return this.artGallerySystem;
}

public void setArtGallerySystem(ArtGallerySystem artGallerySystem) {
   this.artGallerySystem = artGallerySystem;
}

}
