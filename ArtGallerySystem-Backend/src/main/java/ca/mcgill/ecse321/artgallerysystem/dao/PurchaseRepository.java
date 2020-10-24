package ca.mcgill.ecse321.artgallerysystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

@Repository
public interface PurchaseRepository extends CrudRepository <Purchase, String>  {
public Purchase findPurchaseByOrderId(String orderId);
public List<Purchase> findByCustomer(Customer customer);
// public Purchase findPurchaseByArtPieceId(String artpieceId);
// public List<Purchase> findPurchaseByUserRoleId(String customerId);
}
