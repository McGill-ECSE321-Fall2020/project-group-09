package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;

public interface CustomerRepository extends CrudRepository <Customer, String>  {
public Customer findCustomerById(String id);

}
