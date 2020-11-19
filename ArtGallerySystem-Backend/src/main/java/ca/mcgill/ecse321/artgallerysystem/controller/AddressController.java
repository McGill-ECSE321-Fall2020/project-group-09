package ca.mcgill.ecse321.artgallerysystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.service.AddressService;
import ca.mcgill.ecse321.artgallerysystem.service.CustomerService;
/**
 * this class contains the controller methods to call perform database operations using business methods
 * @author amelia
 *
 */
@CrossOrigin(origins="*")
@RestController
public class AddressController {
@Autowired 
private AddressService addressservice;
@Autowired
private CustomerService customerService;
/**
 * return all addresses in the database 	
 * @return list of addressDTo 
 */
@GetMapping(value = {"/addresses", "/addresses/"})
public List<AddressDTO> getAllAddresses(){
	
	List<Address> addresses = addressservice.getAllAddresses();
	return toList(addresses.stream().map(this::convertToDto).collect(Collectors.toList()));
	
}
/**
 * create a new address 
 * @param id address id
 * @param country 
 * @param city
 * @param postCode
 * @param province
 * @param streetaddress
 * @param number: phone number 
 * @param name: name to be displayed in the frontend 
 * @return
 */
@PostMapping(value = {"/address", "/address/"})
public AddressDTO createAddress(@RequestParam("id")String id, @RequestParam("country")String country, @RequestParam("city")String city, @RequestParam("postcode")String postCode, @RequestParam("province")String province, @RequestParam("streetaddress")String streetaddress, @RequestParam("number")String number, @RequestParam("name")String name) {
	//ArtGallerySystem system = systemservice.getSystemById(id);
	Address address = addressservice.createAddress(id, city, country, postCode, province, streetaddress, number, name);
	return convertToDto(address);
}
/**
 * return address by id 
 * @param id address id
 * @return addressDTO if success 
 */
@GetMapping(value = {"/addresses/{id}", "/addresses/{id}/"})
public AddressDTO getAddressById(@PathVariable("id")String id) {
	return convertToDto(addressservice.getAddressById(id));
}

	/**
	 * Added Nov 10
	 * @author Zhekai Jiang
	 */
	@GetMapping(value = {"/addresses/user/{username}", "addresses/user/{username}"})
	public List<AddressDTO> getAddressesByUser(@PathVariable("username") String userName) {
		Customer customer = customerService.getCustomerByUserName(userName);
		List<Address> addresses = new ArrayList<Address>();
		for(Address address : customer.getAddress()) {
			addresses.add(address);
		}
		return toList(addresses.stream().map(this::convertToDto).collect(Collectors.toList()));
	}
/**
 * delete an existing address using id 
 * @param id
 */
@DeleteMapping(value = {"/addresses/{id}", "/addresses/{id}/"})
public void deleteAddress(@PathVariable("id") String id) {
	addressservice.deleteAddress(id);
}
/**
 * update streetAddress of existing address with a new streetAddress 
 * @param id
 * @param newaddress new streetAddress
 * @return updated AddressDTO
 */
@PutMapping (value = {"/address/update/{id}", "/address/update/{id}/"})
public AddressDTO updateAddress(@PathVariable("id")String id, @RequestParam("address")String newaddress) {
	return convertToDto(addressservice.updateAddress(id, newaddress));
}


	/**
	 * Added Nov 11
	 * @author Zhekai Jiang
	 */
	@PutMapping(value = {"/address/updatefull/{id}", "/address/updatefull/{id}/"})
	public AddressDTO updateFullAddress(@PathVariable("id") String id, @RequestParam("name") String name, 
			@RequestParam("phone") String phoneNumber, @RequestParam("streetaddress") String streetAddress,
			@RequestParam("city") String city, @RequestParam("province") String province, 
			@RequestParam("postalcode") String postalCode, @RequestParam("country") String country) {
		Address address = addressservice.updateAddress(id, name, phoneNumber, streetAddress, city, province, postalCode, country);
		return convertToDto(address);
	}
/**
 * convert address to addressDTO 
 * @param address
 * @return
 */
public AddressDTO convertToDto(Address address) {
    AddressDTO addressDTO = new AddressDTO();
    addressDTO.setAddressId(address.getAddressId());
    addressDTO.setCity(address.getCity());
    addressDTO.setCountry(address.getCountry());
    addressDTO.setName(address.getName());
    addressDTO.setPhoneNumber(address.getPhoneNumber());
    addressDTO.setPostalCode(address.getPostalCode());
    addressDTO.setProvince(address.getProvince());
    addressDTO.setStreetAddress(address.getStreetAddress());
    addressDTO.setArtGallerySystem(address.getArtGallerySystem());
    return addressDTO;
}
/**
 * useful helper method 
 * @param <T>
 * @param iterable
 * @return
 */
private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
}
