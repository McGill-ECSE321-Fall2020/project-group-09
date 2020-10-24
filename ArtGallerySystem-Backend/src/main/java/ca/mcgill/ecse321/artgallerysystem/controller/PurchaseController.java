package ca.mcgill.ecse321.artgallerysystem.controller;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.PaymentDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.PurchaseDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.AddressService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtGallerySystemService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtPieceService;
import ca.mcgill.ecse321.artgallerysystem.service.CustomerService;
import ca.mcgill.ecse321.artgallerysystem.service.PaymentService;
import ca.mcgill.ecse321.artgallerysystem.service.PurchaseService;

@CrossOrigin(origins="*")
@RestController
public class PurchaseController {
	@Autowired 
	private PaymentService service;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired 
	private CustomerService customerService;
	@Autowired
	private ArtPieceService artpieceService;
	@GetMapping(value = {"/purchases", "/purchases/"})
	public List<PurchaseDTO> getAllPurchases(){
		
		List<Purchase> purchases = purchaseService.getAllPurchases();
		return toList(purchases.stream().map(this::convertToDto).collect(Collectors.toList()));
		
	}
	@PostMapping(value = {"/purchase", "/purchase/"})
	public PurchaseDTO createPurchase(@RequestParam("id")String id,@RequestParam("status")String status, @RequestParam("date")String date, @RequestParam("artpieceid")String artpieceid, @RequestParam("customerid")String customerid) {
		Date dates = Date.valueOf(date);
		OrderStatus ostatus = convertToStatus(status);
		ArtPiece artpiece = artPieceService.getArtPiece(artpieceid);
		Customer customer = customerService.getCustomer(customerid);
		Purchase purchase = purchaseService.createPurchase(id, dates, ostatus, artpiece, customer);
		return convertToDto(purchase);
	}
	@GetMapping(value = {"/purchases/{id}", "/purchases/{id}/"})
	public PurchaseDTO getPurchaseById(@PathVariable("id")String id) {
		return convertToDto(purchaseService.getPurchase(id));
	}
	@DeleteMapping(value = {"/purchases/{id}", "/purchases/{id}/"})
	public void deletePurchase(@PathVariable("id") String id) {
		purchaseService.deletePurchase(id);
	}
	@PutMapping (value = {"/purchase/update/{id}", "/purchase/update/{id}/"})
	public PurchaseDTO updatePurchase(@PathVariable("id")String id, @RequestParam("status")String status) {
		return convertToDto(purchaseService.updatePurchaseStatus(id, convertToStatus(status)));
	}
	public OrderStatus convertToStatus(String status) {
		switch (status) {
		case "Successful":
			return OrderStatus.Successful;
		case "Failed":
			return OrderStatus.Failed;
		case "Cancelled":
			return OrderStatus.Cancelled;
		case "Pending":
			return OrderStatus.Pending;
		}
		return null;
	}
	public PurchaseDTO convertToDto(Purchase purchase) {
		PurchaseDTO purchasedto = new PurchaseDTO();
		purchasedto.setArtPiece(purchase.getArtPiece());
		purchasedto.setCustomer(purchase.getCustomer());
		purchasedto.setDate(purchase.getDate());
		purchasedto.setOrderId(purchase.getOrderId());
		purchasedto.setOrderStatus(purchase.getOrderStatus());
		return purchasedto;
	}
	private <T> List<T> toList(Iterable<T> iterable) {
	    List<T> resultList = new ArrayList<>();
	    for (T t : iterable) {
	        resultList.add(t);
	    }
	    return resultList;
	}

}
