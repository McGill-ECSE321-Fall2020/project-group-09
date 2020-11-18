package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository <Customer, String>{
public Customer findCustomerByUserRoleId(String userRoleId);
}
