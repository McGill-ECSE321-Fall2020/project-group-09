package ca.mcgill.ecse321.artgallerysystem.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

@Service
public class PurchaseService {
	
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
	
	@Transactional
	public Purchase createPurchase(String id, Date date, OrderStatus status, ArtPiece artPiece, Customer customer) {
		String error = "";
		if(id == null || id.length() == 0) {
			error += "Id cannot be empty! ";
		}
		if(date == null) {
			error += "Date cannot be empty! ";
		}
		if(status == null) {
			error += "Status cannot be empty! ";
		}
		if(artPiece == null) {
			error += "Art piece cannot be empty! ";
		}
		if(customer == null) {
			error += "Customer cannot be empty! ";
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		Purchase purchase = new Purchase();
		artPiece.setArtPieceStatus(ArtPieceStatus.Sold);
		purchase.setArtPiece(artPiece);
		purchase.setCustomer(customer);
		purchase.setDate(date);
		purchase.setOrderId(id);
		purchase.setOrderStatus(status);
		purchase.setPayment(new HashSet<Payment>());
		purchaseRepository.save(purchase);
		return purchase;
	}
	
	@Transactional
	public Purchase getPurchase(String id) {
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException("Id cannot be empty!");
		}
		
		Purchase purchase = purchaseRepository.findPurchaseByOrderId(id);
		
		if (purchase == null) {
			throw new IllegalArgumentException("Purchase with id " + id + " does not exist.");
		}
		
		return purchase;
	}
	
	@Transactional
	public List<Purchase> getAllPurchases(){
		return toList(purchaseRepository.findAll());
	}
	
	@Transactional
	public List<Purchase> getPurchasesMadeByCustomer(Customer customer) {
		if(customer == null) {
			throw new IllegalArgumentException("Customer cannot be empty!");
		}
		return purchaseRepository.findByCustomer(customer);
	}
	
	
	@Transactional
	public Purchase deletePurchase(String id) {
		Purchase purchase = getPurchase(id);
		
/*		if(purchase.getDelivery()!=null || purchase.getPayment() !=null) {
			throw new IllegalArgumentException ("unable to delete");
		}*/
		
		purchaseRepository.deleteById(id);
		
		return purchase;
	}
	
	@Transactional
	public Purchase updatePurchaseStatus(String id, OrderStatus status) {
		Purchase purchase = null;
		String error = "";
		try {
			purchase = getPurchase(id);
		} catch(IllegalArgumentException e) {
			error += e.getMessage() + " ";
		}
		if(status == null) {
			error += "Status cannot be empty! ";
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		// if(purchase.getOrderStatus()==status) {
		//	throw new IllegalArgumentException ("same status");
		// }
		
		purchase.setOrderStatus(status);
		purchaseRepository.save(purchase);
		return purchase;
	}
	
	@Transactional
	public Purchase setDelivery(String id, Delivery delivery) {
		Purchase purchase = null;
		String error = "";
		try {
			purchase = getPurchase(id);
		} catch(IllegalArgumentException e) {
			error += e.getMessage() + " ";
		}
		if(delivery == null) {
			error += "Delivery cannot be empty! ";
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		purchase.setDelivery(delivery);
		purchaseRepository.save(purchase);
		return purchase;
	}
	
	@Transactional
	public Purchase addPayment(String id, Payment payment) {
		Purchase purchase = null;
		String error = "";
		try {
			purchase = getPurchase(id);
		} catch(IllegalArgumentException e) {
			error += e.getMessage() + " ";
		}
		if(payment == null) {
			error += "Payment cannot be empty! ";
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		if(purchase.getPayment() == null) {
			purchase.setPayment(new HashSet<Payment>());
		}
		purchase.getPayment().add(payment);
		purchaseRepository.save(purchase);
		return purchase;
	}
	

	
	// Helper method from tutorial notes - 2.8.1
	private <T> List<T> toList(Iterable<T> iterable) {
		if(iterable == null) {
			return new ArrayList<T>();
		}
	    List<T> resultList = new ArrayList<>();
	    for (T t : iterable) {
	        resultList.add(t);
	    }
	    return resultList;
	}


}
