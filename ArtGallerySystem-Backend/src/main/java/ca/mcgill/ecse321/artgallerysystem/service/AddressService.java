package ca.mcgill.ecse321.artgallerysystem.service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
@Service
public class AddressService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	AddressRepository addressRepository;
	@Transactional
	public List<AddressDTO> getAllAddresses() {
		return toList(addressRepository.findAll()).stream().map(this::convertToDto).collect(Collectors.toList());
		
	}
	@Transactional
	public Address getAddressById(String id) {
		Optional<Address> address = addressRepository.findById(id);
		if(address.isPresent()) {
			return address.get();
		}else {
			throw new AddressException ("Address Not Found");
		}
		
	}
	@Transactional
	public Address createAddress(String id, String city, String country, String postCode, String province, String streetAddress, String number, String name, ArtGallerySystem system) {
		if (city==null||country==null||postCode==null||province==null||streetAddress==null ) {
			throw new AddressException ("not complete address");
		}
		if (number==null) {
			throw new AddressException ("phone number cannot be null");
		}
		if (name==null) {
			throw new AddressException ("please give a name for the address");
		}
		if (id==null) {
			throw new AddressException ("please give an id for the address");
		}
		Address address = new Address();
		//ArtGallerySystem system = artGallerySystemRepository.findArtGallerySystemByArtGallerySystemId(sysID);
		address.setArtGallerySystem(system);
		address.setCity(city);
		address.setAddressId(id);
		address.setCountry(country);
		address.setName(name);
		address.setPhoneNumber(number);
		address.setPostalCode(postCode);
		address.setProvince(province);
		address.setStreetAddress(streetAddress);
		Set<Address> sysAddress = system.getAddress();
		sysAddress.add(address);
		system.setAddress(sysAddress);
		artGallerySystemRepository.save(system);
		addressRepository.save(address);
		return address;
	}
	@Transactional
	public void deleteAddress(String id) {
		Optional<Address> add = addressRepository.findById(id);
		if (add.isPresent()) {
			addressRepository.deleteById(id);
		}else {
			throw new AddressException ("address not exist");
		}
	}
	@Transactional
	public Address updateAddress(String id, String newaddress) {
		Optional<Address> address = addressRepository.findById(id);
		if (address.isPresent()) {
			Address newAddress = address.get();
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
