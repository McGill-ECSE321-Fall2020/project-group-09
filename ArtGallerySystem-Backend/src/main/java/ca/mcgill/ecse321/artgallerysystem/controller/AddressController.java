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
import ca.mcgill.ecse321.artgallerysystem.service.AddressService;

@CrossOrigin(origins="*")
@RestController
public class AddressController {
@Autowired 
private AddressService addressservice;
@GetMapping(value = {"/addresses", "/addresses/"})
public List<AddressDTO> getAllAddresses(){
	
	List<Address> addresses = addressservice.getAllAddresses();
	return toList(addresses.stream().map(this::convertToDto).collect(Collectors.toList()));
	
}
@PostMapping(value = {"/address", "/address/"})
public AddressDTO createAddress(@RequestParam("id")String id, @RequestParam("country")String country, @RequestParam("city")String city, @RequestParam("postcode")String postCode, @RequestParam("province")String province, @RequestParam("streetaddress")String streetaddress, @RequestParam("number")String number, @RequestParam("name")String name) {
	//ArtGallerySystem system = systemservice.getSystemById(id);
	Address address = addressservice.createAddress(id, city, country, postCode, province, streetaddress, number, name);
	return convertToDto(address);
}
@GetMapping(value = {"/addresses/{id}", "/addresses/{id}/"})
public AddressDTO getAddressById(@PathVariable("id")String id) {
	return convertToDto(addressservice.getAddressById(id));
}
@DeleteMapping(value = {"/addresses/{id}", "/addresses/{id}/"})
public void deleteAddress(@PathVariable("id") String id) {
	addressservice.deleteAddress(id);
}
@PutMapping (value = {"/address/update/{id}", "/address/update/{id}/"})
public AddressDTO updateAddress(@PathVariable("id")String id, @RequestParam("address")String newaddress) {
	return convertToDto(addressservice.updateAddress(id, newaddress));
}
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

private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
}
