package ca.mcgill.ecse321.artgallerysystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.DeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.InStorePickUpRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.InStorePickUpException;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUp;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUpStatus;
@Service
public class InStorePickUpService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	DeliveryRepository deliveryRepository;
	@Autowired
	InStorePickUpRepository inStorePickUpRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
	@Transactional
	public InStorePickUp createInStorePickUp(String id, String pickUpReferenceNumber, InStorePickUpStatus status, Address storeAddress, Purchase purchase) {
		if (id == null|| id == "") {
			throw new InStorePickUpException ("Please provide valid id.");
	    }
		if (pickUpReferenceNumber == null|| pickUpReferenceNumber == "") {
			throw new InStorePickUpException ("Please provide valid pickUpReferenceNumber.");
		}
		if (storeAddress == null) {
			throw new InStorePickUpException ("Please provide valid StoreAddress.");
		}
		if(status == null) {
			throw new InStorePickUpException ("Status can not be empty! ");
		}
		if(purchase == null) {
			throw new InStorePickUpException("Purchaseid can not be empty!");
		}
		InStorePickUp pickup = new InStorePickUp();
		//Purchase purchase = purchaseRepository.findPurchaseByOrderId(purid);
    	pickup.setPickUpReferenceNumber(pickUpReferenceNumber);
    	pickup.setStoreAddress(storeAddress);
    	pickup.setDeliveryId(id);
    	pickup.setInStorePickUpStatus(status);
    	pickup.setPurchase(purchase);
    	inStorePickUpRepository.save(pickup);
		return pickup;
	}
	@Transactional
	public InStorePickUp getInStorePickUp(String pickUpReferenceNumber) {

		if (pickUpReferenceNumber == null||pickUpReferenceNumber == "") {
			throw new InStorePickUpException ("Please provide valid pickUpReferenceNumber.");
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
			throw new InStorePickUpException ("provide valid non-empty pickupreferencenumber");
		}
		InStorePickUp pickup = inStorePickUpRepository.findInStorePickUpByDeliveryId(pickUpReferenceNumber);
		if (pickup == null) {
			throw new InStorePickUpException ("provide valid pickupreferencenumber");
		}
		InStorePickUp pic = null;
		inStorePickUpRepository.deleteById(pickUpReferenceNumber);
		
		return pic;
	}
	@Transactional
	public InStorePickUp updateinStorePickUp(String pickUpReferenceNumber, InStorePickUpStatus status) {
		
		if (pickUpReferenceNumber == null|| pickUpReferenceNumber == "") {
			throw new InStorePickUpException ("provide valid pickUpReferenceNumber");
		}InStorePickUp pickup = inStorePickUpRepository.findInStorePickUpByDeliveryId(pickUpReferenceNumber);
		if(status == null) {
			throw new InStorePickUpException ("Status cannot be empty!");
		}
		if (pickup == null) {
			throw new InStorePickUpException ("not exist inStorePickUp");
		}
		if (pickup.getInStorePickUpStatus() == status) {
			throw new InStorePickUpException("same status");
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
