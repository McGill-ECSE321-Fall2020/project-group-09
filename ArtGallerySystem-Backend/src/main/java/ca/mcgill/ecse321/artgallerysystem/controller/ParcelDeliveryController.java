package ca.mcgill.ecse321.artgallerysystem.controller;

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

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.ParcelDeliveryDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDeliveryStatus;
import ca.mcgill.ecse321.artgallerysystem.service.ParcelDeliveryService;

@CrossOrigin(origins="*")
@RestController
public class ParcelDeliveryController {
@Autowired 
private ParcelDeliveryService parcelDeliveryService;
@Autowired
private AddressRepository addressRepository;
@GetMapping(value = {"/parcelDeliveries", "/parcelDeliveries/"})
public List<ParcelDeliveryDTO> getAllParcelDeliveries(){
	
	List<ParcelDelivery> parcelDeliveries = parcelDeliveryService.getAllParcelDeliveris();
	return toList(parcelDeliveries.stream().map(this::convertToDto).collect(Collectors.toList()));
	
}
@PostMapping(value = {"/parcelDelivery", "/parcelDelivery/"})
public ParcelDeliveryDTO createParcelDelivery(@RequestParam("deliveryID")String id, @RequestParam("trackingNumber")String trackingNumber, @RequestParam("carrier")String carrier, @RequestParam("parcelDeliveryStatus")String status, @RequestParam("deliveryAddress")String deliveryAddress) {
	//ArtGallerySystem system = systemservice.getSystemById(id);
	Address address = addressRepository.findAddressByAddressId(deliveryAddress);
	ParcelDeliveryStatus parcelDeliverystatus = getStatus(status);
	ParcelDelivery parcelDelivery = parcelDeliveryService.createParcelDelivery(id, trackingNumber, carrier, parcelDeliverystatus, address);
	return convertToDto(parcelDelivery);
}
@GetMapping(value = {"/parcelDeliveryes/{id}", "/parcelDeliveryes/{id}/"})
public ParcelDeliveryDTO getparcelDeliveryById(@PathVariable("id")String id) {
	return convertToDto(parcelDeliveryService.getParcelDelivery(id));
}
@DeleteMapping(value = {"/parcelDeliveryes/{id}", "/parcelDeliveryes/{id}/"})
public void deleteparcelDelivery(@PathVariable("id") String deliveryid) {
	parcelDeliveryService.deleteParcelDelivery(deliveryid);
}
@PutMapping (value = {"/parcelDelivery/update/{id}", "/parcelDelivery/update/{id}/"})
public ParcelDeliveryDTO updateparcelDeliveryStatus(@PathVariable("deliveryID")String id, @RequestParam("parcelDelivery")String newparcelDelivery) {
	return convertToDto(parcelDeliveryService.updateparcelDeliveryStatus(id,getStatus(newparcelDelivery)));
}
public ParcelDeliveryDTO convertToDto(ParcelDelivery parcelDelivery) {
    ParcelDeliveryDTO parcelDeliveryDto = new ParcelDeliveryDTO();
    parcelDeliveryDto.setTrackingNumber(parcelDelivery.getTrackingNumber());
    parcelDeliveryDto.setCarrier(parcelDelivery.getCarrier()); 
    parcelDeliveryDto.setDeliveryId(parcelDelivery.getDeliveryId());
    parcelDeliveryDto.setDeliveryAddress(parcelDelivery.getDeliveryAddress());
    parcelDeliveryDto.setParcelDeliveryStatus(parcelDelivery.getParcelDeliveryStatus());
    
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

private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
}