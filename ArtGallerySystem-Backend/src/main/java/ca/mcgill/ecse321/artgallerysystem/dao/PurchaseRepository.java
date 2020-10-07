package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

@Repository
public interface PurchaseRepository extends CrudRepository <Purchase, String>  {
public Purchase findPurchaseByOrderId(String id);

}
