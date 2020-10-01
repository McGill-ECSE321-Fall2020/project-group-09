package ca.mcgill.ecse321.artgallerysystem.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgallerysystem.model.Order;

public interface OrderRepository extends CrudRepository<Order, String>{

	Order findOrderById(String id);

}
