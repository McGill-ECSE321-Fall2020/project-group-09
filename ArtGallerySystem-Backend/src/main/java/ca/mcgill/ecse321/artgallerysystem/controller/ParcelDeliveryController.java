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

@CrossOrigin(origins="*")
@RestController
public class ParcelDeliveryController {
@Autowired 
private ParcelDeliveryService parcelDeliveryService;

@Autowired
private AddressRepository addressRepository;
@Autowired
private PurchaseRepository purchaseRepository;
@GetMapping(value = {"/parcelDeliveries", "/parcelDeliveries/"})
public List<ParcelDeliveryDTO> getAllParcelDeliveries(){
	
	List<ParcelDelivery> parcelDeliveries = parcelDeliveryService.getAllParcelDeliveris();
	return toList(parcelDeliveries.stream().map(this::convertToDto).collect(Collectors.toList()));
	
}
@PostMapping(value = {"/parcelDelivery", "/parcelDelivery/"})
public ParcelDeliveryDTO createParcelDelivery(@RequestParam("deliveryid")String deliveryid, @RequestParam("trackingNumber")String trackingNumber, @RequestParam("carrier")String carrier, @RequestParam("parcelDeliveryStatus")String status, @RequestParam("deliveryAddress")String deliveryAddress, @RequestParam("purchaseid")String purid) {
	//ArtGallerySystem system = systemservice.getSystemById(id);
	Purchase purchase = purchaseRepository.findPurchaseByOrderId(purid);
	Address address = addressRepository.findAddressByAddressId(deliveryAddress);
	ParcelDeliveryStatus parcelDeliverystatus = getStatus(status);
	ParcelDelivery parcelDelivery = parcelDeliveryService.createParcelDelivery(deliveryid,trackingNumber, carrier, parcelDeliverystatus, address,purchase);
	return convertToDto(parcelDelivery);
}
@GetMapping(value = {"/parcelDeliveryes/{trackingNumber}", "/parcelDeliveryes/{trackingNumber}/"})
public ParcelDeliveryDTO getparcelDeliveryById(@PathVariable("trackingNumber")String trackingNumber) {
	return convertToDto(parcelDeliveryService.getParcelDelivery(trackingNumber));
}
@DeleteMapping(value = {"/parcelDeliveryes/{trackingNumber}", "/parcelDeliveryes/{trackingNumber}/"})
public void deleteparcelDelivery(@PathVariable("trackingNumber") String trackingNumber) {
	parcelDeliveryService.deleteParcelDelivery(trackingNumber);
}
@PutMapping (value = {"/parcelDelivery/update/{trackingNumber}", "/parcelDelivery/update/{trackingNumber}/"})
public ParcelDeliveryDTO updateparcelDelivery(@PathVariable("trackingNumber")String trackingNumber, @RequestParam("parcelDelivery")String newparcelDelivery) {
	return convertToDto(parcelDeliveryService.updateparcelDelivery(trackingNumber,getStatus(newparcelDelivery)));
}
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
public PurchaseDTO convertToDto(Purchase purchase) {
	PurchaseDTO purchaseDto = new PurchaseDTO();
	purchaseDto.setArtPiece(convertToDto(purchase.getArtPiece()));
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
private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
}