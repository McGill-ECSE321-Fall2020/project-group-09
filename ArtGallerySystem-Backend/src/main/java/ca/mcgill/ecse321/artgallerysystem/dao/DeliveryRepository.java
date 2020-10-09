package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.Delivery;

@Repository
public interface DeliveryRepository extends CrudRepository <Delivery, String>  {
public Delivery findDeliveryByDeliveryId(String deliveryId);
public Delivery findDeliveryByOrderId(String orderId);
}
