package ca.mcgill.ecse321.artgallerysystem.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgallerysystem.model.Delivery;

public interface DeliveryRepository extends CrudRepository<Delivery, String>{

	Delivery findDeliveryById(String id);

}