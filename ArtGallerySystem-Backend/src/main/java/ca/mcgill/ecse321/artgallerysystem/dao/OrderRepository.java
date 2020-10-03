package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Order;

public interface OrderRepository extends CrudRepository <Order, String>  {
public Order findOrderById(String id);

}
