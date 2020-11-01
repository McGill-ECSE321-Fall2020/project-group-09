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
public class AddressService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	AddressRepository addressRepository;
	@Transactional
	public List<Address> getAllAddresses() {
		//return toList(addressRepository.findAll()).stream().map(this::convertToDto).collect(Collectors.toList());
		return toList(addressRepository.findAll()).stream().collect(Collectors.toList());
		
	}
	
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
		//ArtGallerySystem system = artGallerySystemRepository.findArtGallerySystemByArtGallerySystemId(sysID);
		//address.setArtGallerySystem(system);
		address.setCity(city);
		address.setAddressId(id);
		address.setCountry(country);
		address.setName(name);
		address.setPhoneNumber(number);
		address.setPostalCode(postCode);
		address.setProvince(province);
		address.setStreetAddress(streetAddress);
		//Set<Address> sysAddress = system.getAddress();
		//sysAddress.add(address);
		//s//ystem.setAddress(sysAddress);
		//artGallerySystemRepository.save(system);
		addressRepository.save(address);
		return address;
	}
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
	/*public AddressDTO convertToDto(Address address) {
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
	}*/

	private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }


}
