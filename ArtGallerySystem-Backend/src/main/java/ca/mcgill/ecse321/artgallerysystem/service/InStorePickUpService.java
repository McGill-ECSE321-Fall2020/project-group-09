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
import ca.mcgill.ecse321.artgallerysystem.dao.DeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.InStorePickUpRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.InStorePickUpException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ParcelDeliveryException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.PaymentException;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUp;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUpStatus;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDeliveryStatus;
@Service
public class InStorePickUpService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	DeliveryRepository deliveryRepository;
	@Autowired
	InStorePickUpRepository inStorePickUpRepository;
	@Transactional
	public InStorePickUp createInStorePickUp(String pickUpReferenceNumber, InStorePickUpStatus status, Address storeAddress) {
    	if (pickUpReferenceNumber == null|| pickUpReferenceNumber == "") {
			throw new InStorePickUpException ("Please provide valid pickUpReferenceNumber.");
		}
		if (storeAddress == null) {
			throw new InStorePickUpException ("Please provide valid StoreAddress.");
		}
		if(status == null) {
			throw new InStorePickUpException ("Status can not be empty! ");
		}
		InStorePickUp pickup = new InStorePickUp();
    	pickup.setPickUpReferenceNumber(pickUpReferenceNumber);
    	pickup.setStoreAddress(storeAddress);
    	pickup.setInStorePickUpStatus(status);
    	inStorePickUpRepository.save(pickup);
		return pickup;
	}
	@Transactional
	public InStorePickUp getInStorePickUp(String pickUpReferenceNumber) {

		if (pickUpReferenceNumber == null||pickUpReferenceNumber == "") {
			throw new InStorePickUpException ("Please provide vaild pickUpReferenceNumber.");
		}
		InStorePickUp pickup = inStorePickUpRepository.findInStorePickUpByDeliveryId(pickUpReferenceNumber);
		if (pickup== null) {
			throw new InStorePickUpException ("InStorePickUp with id " + pickUpReferenceNumber + " does not exist.");
		}
		return pickup;
	}
	@Transactional
	public List<InStorePickUp> getAllInStorePickUps(){
		return toList(inStorePickUpRepository.findAll());
	}
	@Transactional
	public InStorePickUp deleteInStorePickUp(String pickUpReferenceNumber) {
		if (pickUpReferenceNumber == null||pickUpReferenceNumber == "") {
			throw new InStorePickUpException ("provide vaild id");
		}
		InStorePickUp pickup = inStorePickUpRepository.findInStorePickUpByDeliveryId(pickUpReferenceNumber);
		if (pickup == null) {
			throw new InStorePickUpException ("InStorePickUp with id " + pickUpReferenceNumber + " does not exist.");
		}
		InStorePickUp pic = null;
		inStorePickUpRepository.deleteById(pickUpReferenceNumber);
		
		return pic;
	}
	@Transactional
	public InStorePickUp updateinStorePickUp(String pickUpReferenceNumber, InStorePickUpStatus status) {
		InStorePickUp pickup = inStorePickUpRepository.findInStorePickUpByDeliveryId(pickUpReferenceNumber);
		if (pickUpReferenceNumber == null|| pickUpReferenceNumber == "") {
			throw new InStorePickUpException ("provide vaild pickUpReferenceNumber");
		}
		if(status == null) {
			throw new InStorePickUpException ("Status cannot be empty!");
		}if (pickup == null) {
			throw new InStorePickUpException ("not exist inStorePickUp");
		}
		pickup.setInStorePickUpStatus(status);;
		inStorePickUpRepository.save(pickup);
		return pickup;
	}
	private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
	}

}
