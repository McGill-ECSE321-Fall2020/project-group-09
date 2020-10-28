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
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
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
    @Transactional
	public ParcelDelivery createParcelDelivery(String deliveryid, String trackingNumber, String carrier,
			ParcelDeliveryStatus status, Address deliveryAddress) {
    	if(deliveryid == null || deliveryid.length() == 0) {
    		throw new ParcelDeliveryException ("Please provide valid deliveryid.");
		}
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
    	pardel.setDeliveryId(deliveryid);
    	pardel.setCarrier(carrier);
    	pardel.setParcelDeliveryStatus(status);
    	pardel.setTrackingNumber(trackingNumber);
    	pardel.setDeliveryAddress(deliveryAddress);
    	parcelDeliveryRepository.save(pardel);
		return pardel;
	}
	
	@Transactional
	public ParcelDelivery deleteParcelDelivery(String deliveryid) {
		if (deliveryid == null||deliveryid == "") {
			throw new PaymentException ("provide vaild id");
		}
	    ParcelDelivery pardel = parcelDeliveryRepository.findParcelDeliveryByDeliveryId(deliveryid);
		if (pardel == null) {
			throw new ParcelDeliveryException ("ParcelDelivery with id " + deliveryid + " does not exist.");
		}
		
	    parcelDeliveryRepository.deleteById(deliveryid);
		ParcelDelivery par = null;
		return par;
	}
	@Transactional
	public ParcelDelivery getParcelDelivery(String deliveryid) {
		if (deliveryid == null||deliveryid == "") {
			throw new ParcelDeliveryException ("Please provide vaild deliveryid.");
		}
		ParcelDelivery pardel = parcelDeliveryRepository.findParcelDeliveryByDeliveryId(deliveryid);
		if (pardel == null) {
			throw new ParcelDeliveryException ("ParcelDelivery with id " + deliveryid + " does not exist.");
		}
		return pardel;
	}
	@Transactional
	public List<ParcelDelivery> getAllParcelDeliveris() {
		return toList(parcelDeliveryRepository.findAll());
	}
	
	@Transactional
	public ParcelDelivery updateparcelDeliveryStatus(String deliveryid, ParcelDeliveryStatus status) {
		ParcelDelivery pardel = parcelDeliveryRepository.findParcelDeliveryByDeliveryId(deliveryid);
		if (deliveryid == null|| deliveryid == "") {
			throw new ParcelDeliveryException ("provide vaild deliveryid");
		}
		if (pardel == null) {
			throw new ParcelDeliveryException ("not exist delivery");
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
