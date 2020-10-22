package ca.mcgill.ecse321.artgallerysystem.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.PaymentException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.PurchaseException;
@Service
public class PurchaseService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
	@Transactional
	public Purchase createPurchase(String id, Date date,OrderStatus status, ArtPiece artpiece, Customer customer ) {
		if(id == null||id == "") {
			throw new PurchaseException ("provide valid id");
		}
		if (date == null) {
			throw new PurchaseException ("specify date");
		}
		if(status == null) {
			throw new PurchaseException ("specify status");
		}
		if(artpiece == null) {
			throw new PurchaseException ("specify artpiece");
		}
		if(customer == null) {
			throw new PurchaseException ("specify customer");
		}
		Purchase purchase = new Purchase ();
		purchase.setArtPiece(artpiece);
		purchase.setCustomer(customer);
		purchase.setDate(date);
		purchase.setOrderId(id);
		purchase.setOrderStatus(status);
		purchaseRepository.save(purchase);
		return purchase;
	}
	@Transactional
	public Purchase getPurchase(String id) {
		if (id == null||id == "") {
			throw new PurchaseException ("provide vaild id");
		}
		Purchase purchase = purchaseRepository.findPurchaseByOrderId(id);
		if (purchase == null) {
			throw new PurchaseException ("not exist purchase");
		}
		return purchase;
	}
	@Transactional
	public List<Purchase> getAllPurchases(){
		return toList(purchaseRepository.findAll());
	}
	@Transactional
	public Purchase deletePurchase (String id) {
		if (id == null||id == "") {
			throw new PurchaseException ("provide vaild id");
		}
		Purchase purchase = purchaseRepository.findPurchaseByOrderId(id);
		if (purchase == null) {
			throw new PurchaseException ("not exist payment");
		}
		if(purchase.getDelivery()!=null || purchase.getPayment() !=null) {
			throw new PurchaseException ("unable to delete");
		}
		Purchase pur = null;
		purchaseRepository.deleteById(id);
		return pur;
	}
	@Transactional
	public Purchase updatePurchaseStatus (String id, OrderStatus status) {
		if (id == null||id == "") {
			throw new PurchaseException ("provide vaild id");
		}
		Purchase purchase = purchaseRepository.findPurchaseByOrderId(id);
		if (purchase == null) {
			throw new PurchaseException ("not exist payment");
		}
		if(purchase.getOrderStatus()==status) {
			throw new PurchaseException ("same status");
		}
		purchase.setOrderStatus(status);
		purchaseRepository.save(purchase);
		return purchase;
	}
	private <T> List<T> toList(Iterable<T> iterable) {
	    List<T> resultList = new ArrayList<>();
	    for (T t : iterable) {
	        resultList.add(t);
	    }
	    return resultList;
	}

}
