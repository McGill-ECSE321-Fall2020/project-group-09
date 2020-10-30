package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUp;

@Repository
public interface InStorePickUpRepository extends CrudRepository <InStorePickUp, String>{
	public InStorePickUp findInStorePickUpByDeliveryId(String pickUpReferenceNumber);
}

