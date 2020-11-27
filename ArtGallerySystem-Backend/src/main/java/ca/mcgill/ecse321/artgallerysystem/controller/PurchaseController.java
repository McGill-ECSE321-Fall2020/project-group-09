package ca.mcgill.ecse321.artgallerysystem.controller;
import java.sql.Date;
import java.util.ArrayList;
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

import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemUserDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtPieceDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.CustomerDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.InStorePickUpDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ParcelDeliveryDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.PurchaseDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUp;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.ArtPieceService;
import ca.mcgill.ecse321.artgallerysystem.service.CustomerService;
import ca.mcgill.ecse321.artgallerysystem.service.InStorePickUpService;
import ca.mcgill.ecse321.artgallerysystem.service.ParcelDeliveryService;
import ca.mcgill.ecse321.artgallerysystem.service.PaymentService;
import ca.mcgill.ecse321.artgallerysystem.service.PurchaseService;

/**
 * REST controller for functionalities related to purchases.
 * @author Zhekai Jiang
 */
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
	
	/**
	 * Get all the purchases in the system.
	 * @author Amelia Cui
	 * @return A list of DTOs for all the purchases in the system.
	 */
	@GetMapping(value = {"/purchases", "/purchases/"})
	public List<PurchaseDTO> getAllPurchases(){
		List<Purchase> purchases = purchaseService.getAllPurchases();
		return toList(purchases.stream().map(this::convertToDto).collect(Collectors.toList()));
	}
	
	/**
	 * Create a new purchase.
	 * @author Amelia Cui, Zhekai Jiang
	 * @param id The id of the new purchase.
	 * @param status The status of the new purchase as a String, which could be "Pending", "Successful", "Failed", or "Cancelled".
	 * @param dateAsString The date of purchase as a String, in the format "YYYY-MM-DD".
	 * @param artPieceId The id of the art piece purchased.
	 * @param customerId The role id of the customer who made the purchase.
	 * @return The DTO of the new purchase created.
	 */
	@PostMapping(value = {"/purchase/{id}", "/purchase/{id}/"})
	public PurchaseDTO createPurchase(@PathVariable("id") String id,
			@RequestParam("status") String status, @RequestParam("date") String dateAsString, 
			@RequestParam("artpieceid") String artPieceId, @RequestParam("customerid") String customerId) {
		Date date = Date.valueOf(dateAsString);
		OrderStatus orderStatus = convertToStatus(status);
		ArtPiece artPiece = artPieceService.getArtPiece(artPieceId);
		Customer customer = customerService.getCustomer(customerId);
		Purchase purchase = purchaseService.createPurchase(id, date, orderStatus, artPiece, customer);
		return convertToDto(purchase);
	}
	
	/**
	 * Get the purchase with the given id.
	 * @author Amelia Cui
	 * @param id The id of the purchase.
	 * @return The DTO of the purchase.
	 */
	@GetMapping(value = {"/purchase/{id}", "/purchase/{id}/"})
	public PurchaseDTO getPurchaseById(@PathVariable("id") String id) {
		return convertToDto(purchaseService.getPurchase(id));
	}
	
	/**
	 * Get all the purchase made by a given customer.
	 * @author Zhekai Jiang
	 * @param customerId The id of the customer.
	 * @return A list of DTO-s of the purchases made by the customer.
	 */
	@GetMapping(value = {"/purchasesbycustomer/{id}", "/purchasesbycustomer/{id}/"})
	public List<PurchaseDTO> getPurchasesMadeByCustomer(@PathVariable("id") String customerId) {
		Customer customer = customerService.getCustomer(customerId);
		List<Purchase> purchases = purchaseService.getPurchasesMadeByCustomer(customer);
		return toList(purchases.stream().map(this::convertToDto).collect(Collectors.toList()));
	}
	
	/**
	 * Get all the purchases made by the given user (-'s customer role).
	 * Note that user and customer are different. A user *has-a* customer role.
	 * Added Nov 10 for more convenient access from the frontend.
	 * @author Zhekai Jiang
	 * @param userName The name of the user.
	 * @return A list of DTO-s of the purchases made by the user (-'s customer role).
	 */
	@GetMapping(value = {"/purchasesbyuser/{username}", "/purchasesbyuser/{username}/"}) 
	public List<PurchaseDTO> getPurchasesMadeByUser(@PathVariable("username") String userName) {
		Customer customer = customerService.getCustomerByUserName(userName);
		return getPurchasesMadeByCustomer(customer.getUserRoleId());
	}
	
	/**
	 * Delete a purchase.
	 * @author Amelia Cui
	 * @param id The id of the purchase to be deleted.
	 */
	@DeleteMapping(value = {"/purchase/{id}", "/purchase/{id}/"})
	public void deletePurchase(@PathVariable("id") String id) {
		purchaseService.deletePurchase(id);
	}
	
	/**
	 * Update the status of a purchase.
	 * @param id The id of the purchase to be updated.
	 * @author Amelia Cui
	 * @param status The new status as a String, which could be "Pending", "Successful", "Failed", or "Cancelled".
	 * @return The DTO of the updated purchase.
	 */
	@PutMapping(value = {"/purchase/updatestatus/{id}", "/purchase/updatestatus/{id}/"})
	public PurchaseDTO updatePurchaseStatus(@PathVariable("id") String id, @RequestParam("status") String status) {
		return convertToDto(purchaseService.updatePurchaseStatus(id, convertToStatus(status)));
	}
	
	/**
	 * Associate a parcel delivery to the purchase.
	 * @author Zhekai Jiang
	 * @param id The id of the purchase to be updated.
	 * @param deliveryId The delivery id of the parcel delivery.
	 * @return The DTO of the updated purchase.
	 */
	@PutMapping(value = {"/purchase/setparceldelivery/{id}", "/purchase/setparceldelivery/{id}/"})
	public PurchaseDTO setParcelDelivery(@PathVariable("id") String id, @RequestParam("deliveryid") String deliveryId) {
		Delivery delivery = parcelDeliveryService.getParcelDelivery(deliveryId);
		return convertToDto(purchaseService.setDelivery(id, delivery));
	}
	
	/**
	 * Associate an in-store pick-up to the purchase.
	 * @author Zhekai Jiang
	 * @param id The id of the purchase to be updated.
	 * @param deliveryId The delivery id of the in-store pick-up.
	 * @return The DTO of the updated purchase.
	 */
	@PutMapping(value = {"/purchase/setinstorepickup/{id}", "/purchase/setinstorepickup/{id}/"})
	public PurchaseDTO setInStorePickUp(@PathVariable("id") String id, @RequestParam("deliveryid") String deliveryId) {
		Delivery delivery = inStorePickUpService.getInStorePickUp(deliveryId);
		return convertToDto(purchaseService.setDelivery(id, delivery));
	}
	
	/**
	 * Add a payment to the purchase.
	 * @author Zhekai Jiang
	 * @param id The id of the purchase to be updated.
	 * @param paymentId The id of the payment to be added.
	 * @return The DTO of the updated purchase.
	 */
	@PutMapping(value = {"/purchase/addpayment/{id}", "/purchase/addpayment/{id}/"})
	public PurchaseDTO addPayment(@PathVariable("id") String id, @RequestParam("paymentid") String paymentId) {
		Payment payment = paymentService.getPayment(paymentId);
		return convertToDto(purchaseService.addPayment(id, payment));
	}
	
	/**
	 * Convert the status, as a String, to an OrderStatus enumeration.
	 * @author Amelia Cui
	 * @param status The status as a String, which could be "Pending", "Successful", "Failed", or "Cancelled".
	 * @return The corresponding OrderStatus enumeration, or null if the input string is not valid.
	 */
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
	
	/**
	 * Convert a purchase instance to DTO.
	 * 
	 * Updated Nov 10 & 15 by Zhekai Jiang.
	 * Added several attributes to the DTO to enable the frontend to access more conveniently:
	 * - A String deliveryMethod, which could be either "Parcel Delivery" or "In-Store Pick-Up",
	 * - A boolean isParcelDelivery (which becomes parcelDelivery in JSON),
	 * - A boolean isInStorePickUp (which becomes inStorePickUp in JSON), and
	 * - A String deliveryStatus, which stores the status regardless of delivery type in a string. 
	 * 
	 * @author Amelia Cui, Zhekai Jiang
	 * @param purchase The purchase instance.
	 * @return The DTO of the purchase.
	 */
	public PurchaseDTO convertToDto(Purchase purchase) {
		PurchaseDTO purchaseDto = new PurchaseDTO();
		purchaseDto.setArtPiece(convertToDto(purchase.getArtPiece()));
		purchaseDto.setCustomer(convertToDto(purchase.getCustomer()));
		purchaseDto.setDate(purchase.getDate());
		purchaseDto.setOrderId(purchase.getOrderId());
		purchaseDto.setOrderStatus(purchase.getOrderStatus());
		Delivery delivery = purchase.getDelivery();
		if(delivery instanceof ParcelDelivery) {
			purchaseDto.setDeliveryMethod("Parcel Delivery");
			purchaseDto.setIsParcelDelivery();
			purchaseDto.setDeliveryStatus(((ParcelDelivery) delivery).getParcelDeliveryStatus().toString());
			purchaseDto.setDelivery(convertToDto((ParcelDelivery) delivery));
		} else if(delivery instanceof InStorePickUp) {
			purchaseDto.setDeliveryMethod("In-Store Pick-Up");
			purchaseDto.setIsInStorePickUp();
			purchaseDto.setDeliveryStatus(((InStorePickUp) delivery).getInStorePickUpStatus().toString());
			purchaseDto.setDelivery(convertToDto((InStorePickUp) delivery));
		}
		return purchaseDto;
	}

	/**
	 * Helper method to convert a customer instance to DTO.
	 * Note that this conversion copies only the user, balance, and user role id.
	 * It does NOT copy the saved addresses and purchases, to avoid circular references.
	 * @author Amelia Cui
	 * @param customer The customer instance.
	 * @return The DTO of the customer.
	 */
	private CustomerDTO convertToDto(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setArtGallerySystemUser(convertToDto(customer.getArtGallerySystemUser()));
		customerDTO.setBalance(customer.getBalance());
		customerDTO.setUserRoleId(customer.getUserRoleId());
		return customerDTO;
	}

	/**
	 * Helper method to convert a user instance to DTO.
	 * Note that this conversion copies only the name, email, password, and avatar.
	 * It does NOT copy the user role and system, to avoid circular references.
	 * @author Amelia Cui
	 * @param user The user instance.
	 * @return The DTO of the user.
	 */
	private ArtGallerySystemUserDTO convertToDto(ArtGallerySystemUser user) {
		ArtGallerySystemUserDTO userDTO = new ArtGallerySystemUserDTO();
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setAvatar(user.getAvatar());
		return userDTO;
	}
	
	/**
	 * Helper method to convert an art piece to DTO.
	 * Updated Nov 10 (to avoid infinite circular reference).
	 * Note that this conversion copies only the id, name, description, author, price, date, and status of the art piece.
	 * It does NOT copy the artists (instances), purchase, and system, to avoid circular references.
	 * @author Amelia Cui, Zhekai Jiang
	 * @param artPiece The art piece instance.
	 * @return The DTO of the art piece.
	 */
	private ArtPieceDTO convertToDto(ArtPiece artPiece) {
		ArtPieceDTO artPieceDTO = new ArtPieceDTO();
		artPieceDTO.setArtPieceId(artPiece.getArtPieceId());
		artPieceDTO.setName(artPiece.getName());
		artPieceDTO.setDescription(artPiece.getDescription());
		artPieceDTO.setAuthor(artPiece.getAuthor());
		artPieceDTO.setPrice(artPiece.getPrice());
		artPieceDTO.setDate(artPiece.getDate());
		artPieceDTO.setArtPieceStatus(artPiece.getArtPieceStatus());
		return artPieceDTO;
	}
	
	/**
	 * Helper method to convert a parcel delivery instance to DTO.
	 * Note that this conversion copies only the tracking number, carrier, delivery id, delivery address, status.
	 * It does NOT copy the purchase associated with the delivery, to avoid circular reference.
	 * @author Zhekai Jiang
	 * @param delivery The parcel delivery instance.
	 * @return The DTO of the parcel delivery.
	 */
	private ParcelDeliveryDTO convertToDto(ParcelDelivery delivery) {
		ParcelDeliveryDTO parcelDeliveryDto = new ParcelDeliveryDTO();
		parcelDeliveryDto.setTrackingNumber(delivery.getTrackingNumber());
		parcelDeliveryDto.setCarrier(delivery.getCarrier()); 
		parcelDeliveryDto.setDeliveryId(delivery.getDeliveryId());
		parcelDeliveryDto.setDeliveryAddress(convertToDto(delivery.getDeliveryAddress()));
		parcelDeliveryDto.setParcelDeliveryStatus(delivery.getParcelDeliveryStatus());
		return parcelDeliveryDto;
	}
	
	/**
	 * Helper method to convert an in-store pick-up instance to DTO.
	 * Note that this conversion copies only the reference number, delivery id, store address, and status.
	 * It does NOT copy the purchase associated with the delivery, to avoid circular reference.
	 * @author Zhekai Jiang
	 * @param delivery The in-store pick-up instance.
	 * @return The DTO of the in-store pick-up.
	 */
	private InStorePickUpDTO convertToDto(InStorePickUp delivery) {
		InStorePickUpDTO inStorePickUpDto = new InStorePickUpDTO();
		inStorePickUpDto.setPickUpReferenceNumber(delivery.getPickUpReferenceNumber());
		inStorePickUpDto.setDeliveryId(delivery.getDeliveryId());
		inStorePickUpDto.setStoreAddress(convertToDto(delivery.getStoreAddress()));
	    inStorePickUpDto.setInStorePickUpStatus(delivery.getInStorePickUpStatus());
	    return inStorePickUpDto;
	}
	
	/**
	 * Helper method to convert an address instance to DTO.
	 * @author Zhekai Jiang
	 * @param address The address instance.
	 * @return The DTO of the address.
	 */
	private AddressDTO convertToDto(Address address) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAddressId(address.getAddressId());
		addressDTO.setCity(address.getCity());
		addressDTO.setCountry(address.getCountry());
		addressDTO.setName(address.getName());
		addressDTO.setPhoneNumber(address.getPhoneNumber());
		addressDTO.setPostalCode(address.getPostalCode());
		addressDTO.setProvince(address.getProvince());
		addressDTO.setStreetAddress(address.getStreetAddress());
		return addressDTO;
	}
	
	/**
	 * Helper method from tutorial notes to convert an Iterable object to List.
	 * @param <T> The type of the elements.
	 * @param iterable The Iterable object.
	 * @return The list containing all elements in the Iterable object in order.
	 */
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
