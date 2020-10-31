package ca.mcgill.ecse321.artgallerysystem.dto;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;

public class PurchaseDTO {
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
	@Enumerated
	private OrderStatus orderStatus;

	public void setOrderStatus(OrderStatus value) {
	this.orderStatus = value;
	    }
	public OrderStatus getOrderStatus() {
	return this.orderStatus;
	    }
	private Delivery delivery;

	
	public Delivery  getDelivery() {
	   return this.delivery;
	}

	public void setDelivery(Delivery delivery) {
	   this.delivery = delivery;
	}

	private Set<Payment> payment;

	
	public Set<Payment> getPayment() {
	   return this.payment;
	}

	public void setPayment(Set<Payment> payments) {
	   this.payment = payments;
	}

	private CustomerDTO customer;


	public CustomerDTO getCustomer() {
	   return this.customer;
	}

	public void setCustomer(CustomerDTO customer) {
	   this.customer = customer;
	}

	private ArtPiece artPiece;

	
	public ArtPiece getArtPiece() {
	   return this.artPiece;
	}

	public void setArtPiece(ArtPiece artPiece) {
	   this.artPiece = artPiece;
	}

	private ArtGallerySystem artGallerySystem;


	public ArtGallerySystem getArtGallerySystem() {
	   return this.artGallerySystem;
	}

	public void setArtGallerySystem(ArtGallerySystem artGallerySystem) {
	   this.artGallerySystem = artGallerySystem;
	}
}
