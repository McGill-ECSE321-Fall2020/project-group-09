package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Address;

public interface AddressRepository extends CrudRepository <Address, String>  {
public Address findAddressByAddressId(String addressId);

}
