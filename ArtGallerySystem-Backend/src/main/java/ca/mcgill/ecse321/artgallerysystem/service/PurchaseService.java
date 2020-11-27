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

/**
 * Business services related to purchases.
 * @author Zhekai Jiang
 */
@Service
public class PurchaseService {
	
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
	
	/**
	 * Create a new purchase.
	 * An IllegalArgumentException will be thrown if any parameter is empty.
	 * @author Amelia Cui, Zhekai Jiang
	 * @param id The id of the purchase to be created. 
	 * @param date The date of purchase.
	 * @param status The status of the purchase (which is generally supposed to be OrderStatus.Pending initially).
	 * @param artPiece The art piece purchased.
	 * @param customer The customer who made the purchase.
	 * @return The Purchase instance created.
	 */
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
	
	/**
	 * Get the purchase with the given id.
	 * An IllegalArgumentException will be thrown if the id is empty or the purchase with the id does not exist.
	 * @author Amelia Cui, Zhekai Jiang
	 * @param id The id of the purchase to be found.
	 * @return The Purchase instance with the given id.
	 */
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
	
	/**
	 * Get all the purchases in the database.
	 * @author Amelia Cui
	 * @return A List of all Purchase-s in the database.
	 */
	@Transactional
	public List<Purchase> getAllPurchases(){
		return toList(purchaseRepository.findAll());
	}
	
	/**
	 * Get all the purchases made by a specific customer.
	 * An IllegalArgumentException will be thrown if the customer is null.
	 * @author Zhekai Jiang
	 * @param customer The customer.
	 * @return A List of all Purchase-s made by the given customer.
	 */
	@Transactional
	public List<Purchase> getPurchasesMadeByCustomer(Customer customer) {
		if(customer == null) {
			throw new IllegalArgumentException("Customer cannot be empty!");
		}
		return purchaseRepository.findByCustomer(customer);
	}
	
	/**
	 * Delete a purchase.
	 * @author Amelia Cui, Zhekai Jiang
	 * @param id The id of the purchase to be deleted.
	 * @return The original Purchase instance.
	 */
	@Transactional
	public Purchase deletePurchase(String id) {
		Purchase purchase = getPurchase(id);
		
		purchaseRepository.deleteById(id);
		
		return purchase;
	}
	
	/**
	 * Update the status of a purchase.
	 * An IllegalArgumentException will be thrown if the id or status is empty.
	 * @author Amelia Cui, Zhekai Jiang
	 * @param id The id of the purchase to be updated.
	 * @param status The new status of the purchase.
	 * @return The updated Purchase instance.
	 */
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
		
		purchase.setOrderStatus(status);
		purchaseRepository.save(purchase);
		return purchase;
	}
	
	/**
	 * Associate a delivery to a purchase.
	 * An IllegalArgumentException will be thrown if the id or delivery is empty.
	 * @author Zhekai Jiang
	 * @param id The id of the purchase.
	 * @param delivery The Delivery instance to be associated with the purchase.
	 * @return The updated Purchase instance.
	 */
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
	
	/**
	 * Add a payment to the purchase.
	 * An IllegalArgumentException will be thrown if the id or payment is empty.
	 * @author Zhekai Jiang
	 * @param id The id of the purchase.
	 * @param payment The payment instance to be added to the purchase.
	 * @return The updated Purchase instance.
	 */
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
	

	/**
	 * Helper method to convert an Iterable object to a list.
	 * Return an empty List if the object contains no element.
	 * From tutorial notes - 2.8.1.
	 * @param <T> The type of elements.
	 * @param iterable The Iterable object.
	 * @return The List of elements.
	 */
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
