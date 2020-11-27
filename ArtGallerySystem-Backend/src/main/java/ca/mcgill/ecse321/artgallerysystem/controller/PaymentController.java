package ca.mcgill.ecse321.artgallerysystem.controller;

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


import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemUserDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtPieceDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.CustomerDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.PaymentDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.PurchaseDTO;

import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

import ca.mcgill.ecse321.artgallerysystem.service.PaymentService;
import ca.mcgill.ecse321.artgallerysystem.service.PurchaseService;
/**
 * this class contains the controller methods to call perform database operations using business methods
 * @author amelia
 *
 */
@CrossOrigin(origins="*")
@RestController
public class PaymentController {
@Autowired 
private PaymentService service;
@Autowired 
private PurchaseService purchaseService;

/**
 * get all the existing payments from database 
 * @return list of paymentDTO 
 */
@GetMapping(value = {"/payments", "/payments/"})
public List<PaymentDTO> getAllPayments(){
	
	List<Payment> payments = service.getAllPayments();
	return toList(payments.stream().map(this::convertToDto).collect(Collectors.toList()));
	
}
/**
 * create a new payment 
 * @param id payment id 
 * @param method creditcard/debitcard
 * @param success payment status 
 * @param purchaseid purchase id 
 * @return
 */
@PostMapping(value = {"/payment", "/payment/"})
public PaymentDTO createPayment(@RequestParam("id")String id, @RequestParam("method")String method, @RequestParam("success")String success, @RequestParam("purchaseid")String purchaseid) {
	//ArtGallerySystem system = systemservice.getSystemById(id);
	boolean sus;
	if(success == "true") {
		sus = true;
	}else {
		sus = false;
	}
	PaymentMethod pmethod = convertToMethod(method);
	Purchase purchase = purchaseService.getPurchase(purchaseid);
	Payment payment = service.createPayment(id, pmethod, purchase, sus);
	return convertToDto(payment);
}
/**
 * get payment by id 
 * @param id
 * @return existing paymentDTO if success 
 */
@GetMapping(value = {"/payments/{id}", "/payments/{id}/"})
public PaymentDTO getPaymentById(@PathVariable("id")String id) {
	return convertToDto(service.getPayment(id));
}
/**
 * delete an existing payment 
 * @param id
 */
@DeleteMapping(value = {"/payments/{id}", "/payments/{id}/"})
public void deletePayment(@PathVariable("id") String id) {
	service.deletePayment(id);
}
/**
 * update payment method for existing payment 
 * @param id: payment id 
 * @param method: new method 
 * @return
 */
@PutMapping (value = {"/payment/update/{id}", "/payment/update/{id}/"})
public PaymentDTO updatePayment(@PathVariable("id")String id, @RequestParam("method")String method) {
	return convertToDto(service.updatePaymentMethod(id, convertToMethod(method)));
}

public PaymentDTO convertToDto(Payment payment) {
   PaymentDTO paymentdto = new PaymentDTO();
   paymentdto.setIsSuccessful(payment.isIsSuccessful());
   paymentdto.setPaymentId(payment.getPaymentId());
   paymentdto.setPaymentMethod(payment.getPaymentMethod());
   paymentdto.setPurchase(convertToDto(payment.getPurchase()));
   return paymentdto;
}
public PaymentMethod convertToMethod(String method) {
	// TODO Auto-generated method stub
	//, DebitCard, Balance, PayPal;
	switch(method) {
	case "CreditCard":
		return PaymentMethod.CreditCard;
	case "DebitCard":
		return PaymentMethod.DebitCard;
	case "Balance":
		return PaymentMethod.Balance;
	case "Paypal":
		return PaymentMethod.PayPal;
	
	}
	return null;
}

//the following contains some helper methods to convert a class instance to a DTO object 
public PurchaseDTO convertToDto(Purchase purchase) {
	PurchaseDTO purchaseDto = new PurchaseDTO();
	// purchaseDto.setArtPiece(convertToDto(purchase.getArtPiece())); // removed Nov 15 by Zhekai Jiang (not useful)
	purchaseDto.setCustomer(convertToDto(purchase.getCustomer()));
	purchaseDto.setDate(purchase.getDate());
	purchaseDto.setOrderId(purchase.getOrderId());
	purchaseDto.setOrderStatus(purchase.getOrderStatus());
	return purchaseDto;
}

public CustomerDTO convertToDto(Customer customer){
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setAddress(null);
    customerDTO.setArtGallerySystemUser(convertToDto(customer.getArtGallerySystemUser()));
    customerDTO.setBalance(customer.getBalance());
    customerDTO.setPurchase(null);
    customerDTO.setUserRoleId(customer.getUserRoleId());
    //BeanUtils.copyProperties(customer,customerDTO);
    return customerDTO;
}

public ArtGallerySystemUserDTO convertToDto(ArtGallerySystemUser user) {
	ArtGallerySystemUserDTO userDTO = new ArtGallerySystemUserDTO();
	userDTO.setName(user.getName());
	userDTO.setEmail(user.getEmail());
	userDTO.setPassword(user.getPassword());
	userDTO.setAvatar(user.getAvatar());
	//userDTO.setArtGallerySystem(user.getArtGallerySystem());
	return userDTO;
}
public ArtPieceDTO convertToDto(ArtPiece artPiece){
    ArtPieceDTO artPieceDTO = new ArtPieceDTO();
    BeanUtils.copyProperties(artPiece,artPieceDTO);
    return artPieceDTO;
}
private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
}
