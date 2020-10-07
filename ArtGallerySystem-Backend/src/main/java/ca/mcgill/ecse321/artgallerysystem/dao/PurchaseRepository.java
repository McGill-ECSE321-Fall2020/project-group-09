package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

public interface PurchaseRepository extends CrudRepository <Purchase, String>  {
public Purchase findPurchaseById(String id);

}
