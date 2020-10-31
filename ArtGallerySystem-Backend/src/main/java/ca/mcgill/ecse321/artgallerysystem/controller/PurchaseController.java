package ca.mcgill.ecse321.artgallerysystem.controller;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.artgallerysystem.dto.ArtPieceDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.CustomerDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.PurchaseDTO;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.ArtPieceService;
import ca.mcgill.ecse321.artgallerysystem.service.CustomerService;
import ca.mcgill.ecse321.artgallerysystem.service.InStorePickUpService;
import ca.mcgill.ecse321.artgallerysystem.service.ParcelDeliveryService;
import ca.mcgill.ecse321.artgallerysystem.service.PaymentService;
import ca.mcgill.ecse321.artgallerysystem.service.PurchaseService;

@CrossOrigin(origins="*")
@RestController
public class PurchaseController {
	
	@Autowired 
	private PaymentService paymentService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired 
	private CustomerService customerService;
	@Autowired
	private ArtPieceService artPieceService;
	@Autowired
	private ParcelDeliveryService parcelDeliveryService;
	@Autowired
	private InStorePickUpService inStorePickUpService;
	
	@GetMapping(value = {"/purchases", "/purchases/"})
	public List<PurchaseDTO> getAllPurchases(){
		List<Purchase> purchases = purchaseService.getAllPurchases();
		return toList(purchases.stream().map(this::convertToDto).collect(Collectors.toList()));
	}
	
	@PostMapping(value = {"/purchase/{id}", "/purchase/{id}/"})
	public PurchaseDTO createPurchase(@PathVariable("id") String id, // previous: @RequestParam
			@RequestParam("status") String status, @RequestParam("date") String date, 
			@RequestParam("artpieceid") String artPieceId, @RequestParam("customerid") String customerId) {
		Date dates = Date.valueOf(date);
		OrderStatus orderStatus = convertToStatus(status);
		ArtPiece artPiece = artPieceService.getArtPiece(artPieceId);
		Customer customer = customerService.getCustomer(customerId);
		Purchase purchase = purchaseService.createPurchase(id, dates, orderStatus, artPiece, customer);
		return convertToDto(purchase);
	}
	
	@GetMapping(value = {"/purchase/{id}", "/purchase/{id}/"})
	public PurchaseDTO getPurchaseById(@PathVariable("id") String id) {
		return convertToDto(purchaseService.getPurchase(id));
	}
	
	@GetMapping(value = {"/purchasesbycustomer/{id}", "/purchasesbycustomer/{id}/"})
	public List<PurchaseDTO> getPurchasesMadeByCustomer(@PathVariable("id") String customerId) {
		Customer customer = customerService.getCustomer(customerId);
		List<Purchase> purchases = purchaseService.getPurchasesMadeByCustomer(customer);
		return toList(purchases.stream().map(this::convertToDto).collect(Collectors.toList()));
	}
	
	@DeleteMapping(value = {"/purchase/{id}", "/purchase/{id}/"})
	public void deletePurchase(@PathVariable("id") String id) {
		purchaseService.deletePurchase(id);
	}
	
	// previous: updatePurchase
	@PutMapping(value = {"/purchase/updatestatus/{id}", "/purchase/updatestatus/{id}/"})
	public PurchaseDTO updatePurchaseStatus(@PathVariable("id") String id, @RequestParam("status") String status) {
		return convertToDto(purchaseService.updatePurchaseStatus(id, convertToStatus(status)));
	}
	
	@PutMapping(value = {"/purchase/setparceldelivery/{id}", "/purchase/setparceldelivery/{id}/"})
	public PurchaseDTO setParcelDelivery(@PathVariable("id") String id, @RequestParam("deliveryid") String deliveryId) {
		Delivery delivery = parcelDeliveryService.getParcelDelivery(deliveryId);
		return convertToDto(purchaseService.setDelivery(id, delivery));
	}
	
	@PutMapping(value = {"/purchase/setinstorepickup/{id}", "/purchase/setinstorepickup/{id}/"})
	public PurchaseDTO setInStorePickUp(@PathVariable("id") String id, @RequestParam("deliveryid") String deliveryId) {
		Delivery delivery = inStorePickUpService.getInStorePickUp(deliveryId);
		return convertToDto(purchaseService.setDelivery(id, delivery));
	}
	
	@PutMapping(value = {"/purchase/addpayment/{id}", "/purchase/addpayment/{id}/"})
	public PurchaseDTO addPayment(@PathVariable("id") String id, @RequestParam("paymentid") String paymentId) {
		Payment payment = paymentService.getPayment(paymentId);
		return convertToDto(purchaseService.addPayment(id, payment));
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
		PurchaseDTO purchaseDto = new PurchaseDTO();
		purchaseDto.setArtPiece(convertToDto(purchase.getArtPiece()));
		purchaseDto.setCustomer(convertToDto(purchase.getCustomer()));
		purchaseDto.setDate(purchase.getDate());
		purchaseDto.setOrderId(purchase.getOrderId());
		purchaseDto.setOrderStatus(purchase.getOrderStatus());
		return purchaseDto;
	}
	public CustomerDTO convertToDto(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
	public ArtPieceDTO convertToDto(ArtPiece artPiece){
        ArtPieceDTO artPieceDTO = new ArtPieceDTO();
        BeanUtils.copyProperties(artPiece,artPieceDTO);
        return artPieceDTO;
    }
	// Helper method from tutorial notes
	private <T> List<T> toList(Iterable<T> iterable) {
	    List<T> resultList = new ArrayList<>();
	    for (T t : iterable) {
	        resultList.add(t);
	    }
	    return resultList;
	}

}
