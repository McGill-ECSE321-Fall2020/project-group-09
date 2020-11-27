package ca.mcgill.ecse321.artgallerysystem.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.DeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ParcelDeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ParcelDeliveryException;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDeliveryStatus;
@Service
/**
 * this class contains useful methods to manipulate data in the backend, used in controller 
 * @author Tianyu Zhao
 * 
 */
public class ParcelDeliveryService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	DeliveryRepository deliveryRepository;
	@Autowired
	ParcelDeliveryRepository parcelDeliveryRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
	// @Autowired
	// DeliveryRepository deliverRepository;
	
	/**
	 * Updated Nov 15 by Zhekai Jiang - carrier and tracking number could be empty when the parcel is not shipped...
	 */
    @Transactional
    /**
     * create a ParcelDelivery by its id, trackingnumber, deliveraddress, carrier, status and purchase
     * @param deliveryid
     * @param trackingNumber
     * @param carrier
     * @param status
     * @param deliveryAddress
     * @param purchase
     * @return  a new ParcelDelivery with above param
     */
	public ParcelDelivery createParcelDelivery(String deliveryid,String trackingNumber, String carrier, ParcelDeliveryStatus status, Address deliveryAddress, Purchase purchase) {
    	if (deliveryid == null|| deliveryid == "") {
			throw new ParcelDeliveryException ("Please provide valid deliveryid.");
		}
    	/* if (trackingNumber == null|| trackingNumber == "") {
			throw new ParcelDeliveryException ("Please provide valid trackingNumber.");
		}*/
		if (deliveryAddress == null) {
			throw new ParcelDeliveryException ("Please provide valid Address.");
		}
		/* if (carrier == null) {
			throw new ParcelDeliveryException ("Carrier can not be empty! ");
		}*/
		if(status == null) {
			throw new ParcelDeliveryException ("Status can not be empty! ");
		}
		if(purchase == null) {
			throw new ParcelDeliveryException("Purchaseid can not be empty!");
		}
    	ParcelDelivery pardel = new ParcelDelivery();
    	pardel.setCarrier(carrier);
    	//Purchase purchase = purchaseRepository.findPurchaseByOrderId(purid);
    	pardel.setDeliveryId(deliveryid);
    	pardel.setParcelDeliveryStatus(status);
    	pardel.setTrackingNumber(trackingNumber);
    	pardel.setDeliveryAddress(deliveryAddress);
    	pardel.setPurchase(purchase);
    	parcelDeliveryRepository.save(pardel);
    	deliveryRepository.save(pardel);
		return pardel;
	}
	
	@Transactional
	/**
	  * delete an existing ParcelDelivery by trackingNumber 
	  * @param trackingNumber
	  * @return old ParcelDelivery instance
	  */
	public ParcelDelivery deleteParcelDelivery(String trackingNumber) {
		ParcelDelivery pardel=null;
		ParcelDelivery par = parcelDeliveryRepository.findParcelDeliveryByDeliveryId(trackingNumber);
	    if (par != null) {
	    	parcelDeliveryRepository.deleteById(trackingNumber);
	    	//parcelDeliveryRepository.delete(par);
	    	deliveryRepository.deleteById(trackingNumber);
		}else {
			throw new ParcelDeliveryException("Order not exist.");
		}
		return pardel;
	}
	@Transactional
	/**
	  * get a ParcelDelivery by trackingNumber
	  * @param trackingNumber
	  * @return  ParcelDelivery instance when succeed, throw exception otherwise 
	  */
	public ParcelDelivery getParcelDelivery(String trackingNumber) {
		if (trackingNumber == null||trackingNumber == "") {
			throw new ParcelDeliveryException ("Please provide vaild trackingNumber.");
		}
		ParcelDelivery pardel = parcelDeliveryRepository.findParcelDeliveryByDeliveryId(trackingNumber);
		if (pardel == null) {
			throw new ParcelDeliveryException ("not exist parcelDelivery");
		}
		return pardel;
	}
	@Transactional
	/**
	  * get all ParcelDeliveris from ParcelDeliveryRepository
	  * @return  list of ParcelDeliveris
	  */
	public List<ParcelDelivery> getAllParcelDeliveris() {
		return toList(parcelDeliveryRepository.findAll());
	}
	
	@Transactional
	/**
	  * update an existing ParcelDelivery(get by trackingNumber)and with a new status
	  * @param trackingNumber
	  * @param status
	  * @return  update a new status when succeed, throw exception otherwise
	  */
	public ParcelDelivery updateparcelDelivery(String trackingNumber, ParcelDeliveryStatus status) {
		ParcelDelivery pardel = parcelDeliveryRepository.findParcelDeliveryByDeliveryId(trackingNumber);
		if (trackingNumber == null|| trackingNumber == "") {
			throw new ParcelDeliveryException ("provide vaild trackingNumber");
		}
		if(status == null) {
			throw new ParcelDeliveryException ("Status cannot be empty!");
		}
		if (pardel == null) {
			throw new ParcelDeliveryException ("not exist delivery");
		}
		if (pardel.getParcelDeliveryStatus()==status) {
			throw new ParcelDeliveryException ("same status");
		}
		pardel.setParcelDeliveryStatus(status);;
		parcelDeliveryRepository.save(pardel);
		deliveryRepository.save(pardel);
		return pardel;
	}
	
	
	/**
	 * Added Nov 15
	 * @author Zhekai Jiang
	 */
	@Transactional
	/**
	  * update a ParcelDelivery(get by trackingNumber)and with a new status
	  * carrier and tracking number could be empty when the parcel is not shipped
	  * @param id
	  * @param status
	  * @param carrier
	  * @param trackingNumber
	  * @return  updated ParcelDelivery when succeed, throw exception otherwise
	  */
	public ParcelDelivery updateParcelDelivery(String id, ParcelDeliveryStatus status, String carrier, String trackingNumber) {
		ParcelDelivery parcelDelivery = parcelDeliveryRepository.findParcelDeliveryByDeliveryId(id);
		String error = "";
		if(id == null || id.length() == 0) {
			error += "Delivery id cannot be empty! ";
		}
		if(status == null) {
			error += "Parcel delivery status cannot be empty! ";
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		parcelDelivery.setParcelDeliveryStatus(status);
		parcelDelivery.setCarrier(carrier);
		parcelDelivery.setTrackingNumber(trackingNumber);
		parcelDeliveryRepository.save(parcelDelivery);
		deliveryRepository.save(parcelDelivery);
		return parcelDelivery;
	}
	
	private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
	}

	
}
