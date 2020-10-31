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
import ca.mcgill.ecse321.artgallerysystem.dao.ParcelDeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ParcelDeliveryException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.PaymentException;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDeliveryStatus;
@Service
public class ParcelDeliveryService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	DeliveryRepository deliveryRepository;
	@Autowired
	ParcelDeliveryRepository parcelDeliveryRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
    @Transactional
	public ParcelDelivery createParcelDelivery(String trackingNumber, String carrier,
			ParcelDeliveryStatus status, Address deliveryAddress, String purid) {
    	if (trackingNumber == null|| trackingNumber == "") {
			throw new ParcelDeliveryException ("Please provide valid trackingNumber.");
		}
		if (deliveryAddress == null) {
			throw new ParcelDeliveryException ("Please provide valid Address.");
		}
		if (carrier == null) {
			throw new ParcelDeliveryException ("Carrier can not be empty! ");
		}if(status == null) {
			throw new ParcelDeliveryException ("Status can not be empty! ");
		}
    	ParcelDelivery pardel = new ParcelDelivery();
    	pardel.setCarrier(carrier);
    	Purchase purchase = purchaseRepository.findPurchaseByOrderId(purid);
    	pardel.setParcelDeliveryStatus(status);
    	pardel.setTrackingNumber(trackingNumber);
    	pardel.setDeliveryAddress(deliveryAddress);
    	pardel.setPurchase(purchase);
    	pardel.setDeliveryId(trackingNumber);
    	parcelDeliveryRepository.save(pardel);
		return pardel;
	}
	
	@Transactional
	public ParcelDelivery deleteParcelDelivery(String trackingNumber) {
		ParcelDelivery pardel=null;
		ParcelDelivery par = parcelDeliveryRepository.findParcelDeliveryByDeliveryId(trackingNumber);
	    if (par != null) {
	    	parcelDeliveryRepository.deleteById(trackingNumber);
		}else {
			throw new ParcelDeliveryException("Order not exist.");
		}
		return pardel;
	}
	@Transactional
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
	public List<ParcelDelivery> getAllParcelDeliveris() {
		return toList(parcelDeliveryRepository.findAll());
	}
	
	@Transactional
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
		return pardel;
	}
	private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
	}

	
}
