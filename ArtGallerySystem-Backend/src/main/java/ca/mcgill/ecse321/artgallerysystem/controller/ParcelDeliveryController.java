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

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemUserDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtPieceDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.CustomerDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ParcelDeliveryDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.PurchaseDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDeliveryStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.ParcelDeliveryService;
/**
 * this class contains the controller methods to call perform database operations using business methods
 * @author Tianyu Zhao
 *
 */
@CrossOrigin(origins="*")
@RestController
public class ParcelDeliveryController {
@Autowired 
private ParcelDeliveryService parcelDeliveryService;

@Autowired
private AddressRepository addressRepository;
@Autowired
private PurchaseRepository purchaseRepository;
/**
 * return all parcelDeliveries in the database 	
 * @return list of parcelDeliveryDTo 
 */
@GetMapping(value = {"/parcelDeliveries", "/parcelDeliveries/"})
public List<ParcelDeliveryDTO> getAllParcelDeliveries(){
	
	List<ParcelDelivery> parcelDeliveries = parcelDeliveryService.getAllParcelDeliveris();
	return toList(parcelDeliveries.stream().map(this::convertToDto).collect(Collectors.toList()));
	
}
/**
 * create a new parcelDelivery
 * @param deliveryid
 * @param trackingNumber
 * @param carrier
 * @param status
 * @param deliveryAddress
 * @param purid
 * @return  parcelDeliveryDTO when succeed
 */
@PostMapping(value = {"/parcelDelivery", "/parcelDelivery/"})
public ParcelDeliveryDTO createParcelDelivery(@RequestParam("deliveryid")String deliveryid, @RequestParam("trackingNumber")String trackingNumber, @RequestParam("carrier")String carrier, @RequestParam("parcelDeliveryStatus")String status, @RequestParam("deliveryAddress")String deliveryAddress, @RequestParam("purchaseid")String purid) {
	//ArtGallerySystem system = systemservice.getSystemById(id);
	Purchase purchase = purchaseRepository.findPurchaseByOrderId(purid);
	Address address = addressRepository.findAddressByAddressId(deliveryAddress);
	ParcelDeliveryStatus parcelDeliverystatus = getStatus(status);
	ParcelDelivery parcelDelivery = parcelDeliveryService.createParcelDelivery(deliveryid,trackingNumber, carrier, parcelDeliverystatus, address,purchase);
	return convertToDto(parcelDelivery);
}
/**
 * return parcelDelivery by trackingNumber
 * @param trackingNumber
 * @return parcelDeliveryDTO
 */
@GetMapping(value = {"/parcelDeliveryes/{trackingNumber}", "/parcelDeliveryes/{trackingNumber}/"})
public ParcelDeliveryDTO getparcelDeliveryById(@PathVariable("trackingNumber")String trackingNumber) {
	return convertToDto(parcelDeliveryService.getParcelDelivery(trackingNumber));
}
/**
 * delete an existing parcelDelivery using trackingNumber
 * @param trackingNumber
 */
@DeleteMapping(value = {"/parcelDeliveryes/{trackingNumber}", "/parcelDeliveryes/{trackingNumber}/"})
public void deleteparcelDelivery(@PathVariable("trackingNumber") String trackingNumber) {
	parcelDeliveryService.deleteParcelDelivery(trackingNumber);
}
/**
 * update status of an existing parcelDelivery with a new status 
 * @param trackingNumber
 * @param newparcelDelivery
 * @return
 */
@PutMapping (value = {"/parcelDelivery/update/{trackingNumber}", "/parcelDelivery/update/{trackingNumber}/"})
public ParcelDeliveryDTO updateparcelDelivery(@PathVariable("trackingNumber")String trackingNumber, @RequestParam("parcelDelivery")String newparcelDelivery) {
	return convertToDto(parcelDeliveryService.updateparcelDelivery(trackingNumber,getStatus(newparcelDelivery)));
}

/**
 * Update all attributes (id, status, carrier, and tracking number) of a parcel delivery. 
 * Added Nov 15.
 * @author Zhekai Jiang
 * @param id The id of the parcel delivery.
 * @param status The status of the parcel delivery, which could be "Pending", "Shipped", or "Delivered".
 * @param carrier The carrier of the parcel.
 * @param trackingNumber The tracking number of the parcel.
 */
@PutMapping(value = {"/parcelDelivery/updateFull/{deliveryId}", "/parcelDelivery/updateFull/{deliveryId}/"})
public ParcelDeliveryDTO updateParcelDelivery(@PathVariable("deliveryId") String id, @RequestParam("parcelDeliveryStatus") String status, @RequestParam("carrier") String carrier, @RequestParam("trackingNumber") String trackingNumber) {
	return convertToDto(parcelDeliveryService.updateParcelDelivery(id, getStatus(status), carrier, trackingNumber));
}

/**
 * convert parcelDelivery to DTO
 * @param parcelDelivery
 * @return
 */
public ParcelDeliveryDTO convertToDto(ParcelDelivery parcelDelivery) {
    ParcelDeliveryDTO parcelDeliveryDto = new ParcelDeliveryDTO();
    parcelDeliveryDto.setTrackingNumber(parcelDelivery.getTrackingNumber());
    parcelDeliveryDto.setCarrier(parcelDelivery.getCarrier()); 
    parcelDeliveryDto.setDeliveryId(parcelDelivery.getDeliveryId());
    parcelDeliveryDto.setDeliveryAddress(convertToDto(parcelDelivery.getDeliveryAddress()));
    parcelDeliveryDto.setParcelDeliveryStatus(parcelDelivery.getParcelDeliveryStatus());
    parcelDeliveryDto.setPurchase(convertToDto(parcelDelivery.getPurchase()));
    return parcelDeliveryDto;
}
/**
 * Get status for a parcelDelivery
 * @param status
 * @return
 */
public ParcelDeliveryStatus getStatus (String status) {
	switch(status) {
	case "Delivered":
		return ParcelDeliveryStatus.Delivered;
	case "Pending":
		return ParcelDeliveryStatus.Pending;
	case "Shipped":
		return ParcelDeliveryStatus.Shipped;
	
	
	}
	return null;
}
/**
 * convert purchase to DTO
 * @param purchase
 * @return
 */
public PurchaseDTO convertToDto(Purchase purchase) {
	PurchaseDTO purchaseDto = new PurchaseDTO();
	purchaseDto.setCustomer(convertToDto(purchase.getCustomer()));
	purchaseDto.setDate(purchase.getDate());
	purchaseDto.setOrderId(purchase.getOrderId());
	purchaseDto.setOrderStatus(purchase.getOrderStatus());
	return purchaseDto;
}
/*public CustomerDTO convertToDto(Customer customer){
    CustomerDTO customerDTO = new CustomerDTO();
    BeanUtils.copyProperties(customer,customerDTO);
    return customerDTO;
}*/

/**
 * convert customer to DTO
 * @param customer
 * @return
 */
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

/**
 * convert user to DTO
 * @param user
 * @return
 */
public ArtGallerySystemUserDTO convertToDto(ArtGallerySystemUser user) {
	ArtGallerySystemUserDTO userDTO = new ArtGallerySystemUserDTO();
	userDTO.setName(user.getName());
	userDTO.setEmail(user.getEmail());
	userDTO.setPassword(user.getPassword());
	userDTO.setAvatar(user.getAvatar());
	//userDTO.setArtGallerySystem(user.getArtGallerySystem());
	return userDTO;
}
/**
 * convert artpiece to DTO
 * @param artPiece
 * @return
 */
public ArtPieceDTO convertToDto(ArtPiece artPiece){
    ArtPieceDTO artPieceDTO = new ArtPieceDTO();
    BeanUtils.copyProperties(artPiece,artPieceDTO);
    return artPieceDTO;
}
/**
 * convert address to DTO
 * @param address
 * @return
 */
public AddressDTO convertToDto(Address address) {
    AddressDTO addressDTO = new AddressDTO();
    addressDTO.setAddressId(address.getAddressId());
    addressDTO.setCity(address.getCity());
    addressDTO.setCountry(address.getCountry());
    addressDTO.setName(address.getName());
    addressDTO.setPhoneNumber(address.getPhoneNumber());
    addressDTO.setPostalCode(address.getPostalCode());
    addressDTO.setProvince(address.getProvince());
    addressDTO.setStreetAddress(address.getStreetAddress());
    addressDTO.setArtGallerySystem(address.getArtGallerySystem());
    return addressDTO;
}
/**
 * useful helper method 
 * @param iterable
 * @return
 */
private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
}