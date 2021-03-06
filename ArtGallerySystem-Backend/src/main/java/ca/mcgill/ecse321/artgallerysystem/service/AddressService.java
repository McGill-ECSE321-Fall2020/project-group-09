package ca.mcgill.ecse321.artgallerysystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
@Service
/**
 * this class contains useful business methods to manipulate data in backend, used in controller 
 * @author amelia
 *
 */
public class AddressService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	AddressRepository addressRepository;
	/**
 	 * get all addresses from addressRepository
 	 * @return list of addresses 
 	 */
	@Transactional
	public List<Address> getAllAddresses() {
		//return toList(addressRepository.findAll()).stream().map(this::convertToDto).collect(Collectors.toList());
		return toList(addressRepository.findAll()).stream().collect(Collectors.toList());
		
	}
	/**
 	 * get an existing address by id 
 	 * @param id address id 
 	 * @return address instance if success 
 	 */
	@Transactional
	public Address getAddressById(String id) {
		Address address = addressRepository.findAddressByAddressId(id);
		if (id == null || id == "") {
			throw new AddressException ("provide id please");
		}
		if(address != null) {
			return address;
		}else {
			//return null;
			throw new AddressException ("Address Not Found");
		}
		
	}
	/**
 	 * create a new address 
 	 * @param id
 	 * @param city
 	 * @param country
 	 * @param postCode
 	 * @param province
 	 * @param streetAddress
 	 * @param number
 	 * @param name: name wants to display in frontend 
 	 * @return
 	 */
	@Transactional
	public Address createAddress(String id, String city, String country, String postCode, String province, String streetAddress, String number, String name) {
		if (city==null||country==null||postCode==null||province==null||streetAddress==null 
				||city==""||country==""||postCode==""||province==""||streetAddress=="") {
			throw new AddressException ("not complete address");
		}
		if (number==null||number =="") {
			throw new AddressException ("phone number cannot be null");
		}
		if (name==null||name=="") {
			throw new AddressException ("please give a name for the address");
		}
		if (id==null||id=="") {
			throw new AddressException ("please give an id for the address");
		}
		Address address = new Address();
		address.setCity(city);
		address.setAddressId(id);
		address.setCountry(country);
		address.setName(name);
		address.setPhoneNumber(number);
		address.setPostalCode(postCode);
		address.setProvince(province);
		address.setStreetAddress(streetAddress);
		addressRepository.save(address);
		return address;
	}
	/**
 	 * delete an existing address by id 
 	 * @param id
 	 * @return old address instance 
 	 */
	@Transactional
	public Address deleteAddress(String id) {
		Address address = null;
		Address add = addressRepository.findAddressByAddressId(id);
		if (add !=null) {
			addressRepository.deleteById(id);
		}else {
			throw new AddressException ("address not exist");
		}
		return address;
	}
	/**
 	 * update streetAddress with new streetAddress 
 	 * @param id
 	 * @param newaddress
 	 * @return
 	 */
	@Transactional
	public Address updateAddress(String id, String newaddress) {
		Address address = addressRepository.findAddressByAddressId(id);
		if (newaddress == null|| newaddress == "") {
			throw new AddressException ("please provide a not null address");
		}
		if (address != null) {
			Address newAddress = address;
			if (newAddress.getStreetAddress().equals(newaddress)) {
				throw new AddressException ("address is the same");
			}
			newAddress.setStreetAddress(newaddress);
			addressRepository.save(newAddress);
			return (newAddress);
		}
		else {
			throw new AddressException ("address not exist");
		}
	}
	
	/**
	 * Update all attributes of an address.
	 * An IllegalArgumentException will be thrown if any parameter provided is empty or if the id is not valid.
	 * Added Nov 11.
	 * @author Zhekai Jiang
	 * @param id The id of the address.
	 * @param name The name associated with the address.
	 * @param phoneNumber The phone number.
	 * @param streetAddress The street address.
	 * @param city The city.
	 * @param province The province.
	 * @param postalCode The postal code.
	 * @param country The country.
	 * @return The updated Address instance. 
	 */
	@Transactional
	public Address updateAddress(String id, String name, String phoneNumber, String streetAddress, String city, String province, String postalCode, String country) {
		String error = "";
		if(id == null || id.length() == 0) {
			error += "Address id cannot be empty! ";
		}
		if(name == null || name.length() == 0) {
			error += "Name cannot be empty! ";
		}
		if(phoneNumber == null || phoneNumber.length() == 0) {
			error += "Phone number cannot be empty! ";
		}
		if(streetAddress == null || streetAddress.length() == 0) {
			error += "Street address cannot be empty! ";
		}
		if(city == null || city.length() == 0) {
			error += "City cannot be empty! ";
		}
		if(province == null || province.length() == 0) {
			error += "Province cannot be empty! ";
		}
		if(postalCode == null || postalCode.length() == 0) {
			error += "Postal code cannot be empty! ";
		}
		if(country == null || country.length() == 0) {
			error += "Country cannot be empty!";
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		Address address = addressRepository.findAddressByAddressId(id);
		if(address == null) {
			throw new IllegalArgumentException("Address with id " + id + " does not exist.");
		}
		
		address.setName(name);
		address.setPhoneNumber(phoneNumber);
		address.setStreetAddress(streetAddress);
		address.setCity(city);
		address.setProvince(province);
		address.setPostalCode(postalCode);
		address.setCountry(country);
		
		addressRepository.save(address);
		return address;
	}
	
	

	private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }


}
