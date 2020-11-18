package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;

@Repository
public interface ParcelDeliveryRepository extends CrudRepository <ParcelDelivery, String>{
	public ParcelDelivery findParcelDeliveryByDeliveryId(String trackingNumber);
	// public List<ParcelDelivery> findParcelDliveryBydeliveryId(String deliveryId);
}
