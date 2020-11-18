package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.Address;

@Repository
public interface AddressRepository extends CrudRepository <Address, String>  {
public Address findAddressByAddressId(String addressId);
// public List<Address> findAddressByUserRoleId(String customerId);
}
