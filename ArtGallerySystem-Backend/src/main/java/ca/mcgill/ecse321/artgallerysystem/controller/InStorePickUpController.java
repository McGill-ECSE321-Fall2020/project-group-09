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
import ca.mcgill.ecse321.artgallerysystem.dao.InStorePickUpRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.InStorePickUpDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUp;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUpStatus;
import ca.mcgill.ecse321.artgallerysystem.service.InStorePickUpService;

@CrossOrigin(origins="*")
@RestController
public class InStorePickUpController {
@Autowired 
private InStorePickUpService inStorePickUpService;
@Autowired
private AddressRepository addressRepository;
/*@GetMapping(value = {"/parcelDeliveries", "/parcelDeliveries/"})
public List<InStorePickUpDTO> getAllParcelDeliveries(){
	
	List<InStorePickUp> inStorePickUps = inStorePickUpService.getAllInStorePickUps();
	return toList(inStorePickUps.stream().map(this::convertToDto).collect(Collectors.toList()));
	
}*/
@PostMapping(value = {"/inStorePickUp", "/inStorePickUp/"})
public InStorePickUpDTO createInStorePickUp(@RequestParam("deliveryID")String id, @RequestParam("pickUpReferenceNumber")String pickUpReferenceNumber, @RequestParam("inStorePickUpStatus")String status, @RequestParam("storeAddress")String storeAddress, @RequestParam("purchaseid")String purid) {
	//ArtGallerySystem system = systemservice.getSystemById(id);
	Address address = addressRepository.findAddressByAddressId(storeAddress);
	InStorePickUpStatus inStorePickUpstatus = getStatus(status);
	InStorePickUp inStorePickUp = inStorePickUpService.createInStorePickUp(pickUpReferenceNumber, inStorePickUpstatus, address, purid);
	return convertToDto(inStorePickUp);
}
@GetMapping(value = {"/inStorePickUps/{id}", "/inStorePickUps/{id}/"})
public InStorePickUpDTO getinStorePickUpById(@PathVariable("id")String id) {
	return convertToDto(inStorePickUpService.getInStorePickUp(id));
}
@DeleteMapping(value = {"/inStorePickUps/{id}", "/inStorePickUps/{id}/"})
public void deleteinStorePickUp(@PathVariable("id") String deliveryid) {
	inStorePickUpService.deleteInStorePickUp(deliveryid);
}
@PutMapping (value = {"/inStorePickUp/update/{id}", "/inStorePickUp/update/{id}/"})
public InStorePickUpDTO updateparcelDeliveryStatus(@PathVariable("deliveryID")String id, @RequestParam("inStorePickUp")String newinStorePickUp) {
	return convertToDto(inStorePickUpService.updateinStorePickUp(id,getStatus(newinStorePickUp)));
}
public InStorePickUpDTO convertToDto(InStorePickUp inStorePickUp) {
	InStorePickUpDTO inStorePickUpDto = new InStorePickUpDTO();
	inStorePickUpDto.setPickUpReferenceNumber(inStorePickUp.getPickUpReferenceNumber());
	inStorePickUpDto.setDeliveryId(inStorePickUp.getDeliveryId());
	inStorePickUpDto.setStoreAddress(inStorePickUp.getStoreAddress());
    inStorePickUpDto.setInStorePickUpStatus(inStorePickUp.getInStorePickUpStatus());
    
    return inStorePickUpDto;
}
public InStorePickUpStatus getStatus (String status) {
	switch(status) {
	case "Pending":
		return InStorePickUpStatus.Pending;
	case "Available":
		return InStorePickUpStatus.AvailableForPickUp;
	case "PickedUp":
		return InStorePickUpStatus.PickedUp;
	
	
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
