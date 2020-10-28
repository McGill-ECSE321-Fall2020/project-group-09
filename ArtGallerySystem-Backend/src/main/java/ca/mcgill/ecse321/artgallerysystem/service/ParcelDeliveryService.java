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
import ca.mcgill.ecse321.artgallerysystem.dao.ParcelDeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ParcelDeliveryException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.PaymentException;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;
@Service
public class ParcelDeliveryService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	ParcelDeliveryRepository parcelDeliveryRepository;
    @Transactional
	public ParcelDelivery createparcelDelivery(String trackingNumber, String carrier,
			String parcelDeliveryStatus, String deliveryAddress) {
		// TODO Auto-generated method stub
    	ParcelDelivery pardel = new ParcelDelivery ();
		return pardel;
	}
	
	@Transactional
	public ParcelDelivery deleteParcelDelivery(String trackingNumber) {
		
		ParcelDelivery pardel = null;
		return pardel;
	}
	@Transactional
	public ParcelDelivery getParcelDelivery(String trackingNumber) {
		if (trackingNumber == null||trackingNumber == "") {
			throw new ParcelDeliveryException ("provide vaild trackingnumber");
		}
		ParcelDelivery pardel = parcelDeliveryRepository.findParcelDeliveryByDeliveryId(trackingNumber);
		if (pardel == null) {
			throw new ParcelDeliveryException ("not exist parceldelivery");
		}
		return pardel;
	}
	public static List<ParcelDelivery> getAllParcelDeliveris(String trackingNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
