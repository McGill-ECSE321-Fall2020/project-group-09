package ca.mcgill.ecse321.artgallerysystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.service.AddressService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtGallerySystemService;

@CrossOrigin(origins="*")
@RestController
public class AddressController {
@Autowired 
private AddressService addressservice;
@Autowired 
private ArtGallerySystemService systemservice;
@Autowired
private ArtGallerySystemRepository artgallerySystemRepository;
@GetMapping(value = {"/addresses", "/addresses/"})
public List<AddressDTO> getAllAddresses(){
	return addressservice.getAllAddresses();
}
@PostMapping(value = {"/address/{id}", "/address/{id}/"})
public AddressDTO createAddress(@PathVariable("id")String id, @RequestParam("country")String country, @RequestParam("city")String city, @RequestParam("province")String province, @RequestParam("postCode")String postCode, @RequestParam("Name")String name, @RequestParam("phonenumber")String number, @RequestParam("address")String streetaddress, @RequestParam("system") String sysID) {
	ArtGallerySystem system = systemservice.getSystemById(id);
	Address address = addressservice.createAddress(id, city, country, postCode, province, streetaddress, number, name, system);
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
@PutMapping (value = {"/update-address/{address}", "/update-address/{address}/"})
public AddressDTO updateAddress(@PathVariable("address")String newaddress, @RequestParam("id")String id) {
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
/*public ArtGallerySystemDTO convertToDto(ArtGallerySystem sys) {
	//Art
}*/
/*public ArtGallerySystem converToEntity(ArtGallerySystemDTO dto) {
	
}*/
}
