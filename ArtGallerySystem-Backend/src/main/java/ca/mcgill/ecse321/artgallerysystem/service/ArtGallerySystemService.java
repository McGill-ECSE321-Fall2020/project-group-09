package ca.mcgill.ecse321.artgallerysystem.service;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtGallerySystemException;

@Service
public class ArtGallerySystemService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	
	@Transactional
	public ArtGallerySystem getSystemById(String id) {
		if(id == null || id.length() == 0) {
			throw new IllegalArgumentException("System id cannot be empty!");
		}
		
		Optional<ArtGallerySystem> system = artGallerySystemRepository.findById(id);
		if(system.isPresent()) {
			return system.get();
		} else {
			throw new ArtGallerySystemException("System with id " + id + " does not exist.");
		}
		
	}
	
	@Transactional
	public ArtGallerySystem createSystem(String id) {
		if(id == null || id.length() == 0) {
			throw new IllegalArgumentException("System id cannot be empty!");
		}
		
		ArtGallerySystem system = new ArtGallerySystem();
		
		system.setArtGallerySystemId(id);
		system.setIncome(0);
		
		system.setArtGallerySystemUser(new HashSet<ArtGallerySystemUser>());
		system.setArtPiece(new HashSet<ArtPiece>());
		system.setPurchase(new HashSet<Purchase>());
		system.setAddress(new HashSet<Address>());
		
		artGallerySystemRepository.save(system);
		return system;
	}
	
	@Transactional
	public ArtGallerySystem deleteSystem(String id) {
		ArtGallerySystem system = getSystemById(id);
		artGallerySystemRepository.deleteById(id);
		return system;
	}
	
	@Transactional
	public ArtGallerySystem setIncome(String id, double income) {
		ArtGallerySystem system = getSystemById(id);
		system.setIncome(income);
		artGallerySystemRepository.save(system);
		return system;
	}
	
	@Transactional
	public ArtGallerySystem increaseIncome(String id, double increment) {
		ArtGallerySystem system = getSystemById(id);
		system.setIncome(system.getIncome() + increment);
		artGallerySystemRepository.save(system);
		return system;
	}
	
	@Transactional
	public ArtGallerySystem addUser(String id, ArtGallerySystemUser user) {
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = getSystemById(id);
		} catch(Exception e) {
			error += e.getMessage() + " ";
		}
		if(user == null) {
			error += "User cannot be empty! ";
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		system.getArtGallerySystemUser().add(user);
		artGallerySystemRepository.save(system);
		return system;
	}
	
	@Transactional
	public ArtGallerySystem addArtPiece(String id, ArtPiece artPiece) {
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = getSystemById(id);
		} catch(IllegalArgumentException e) {
			error += e.getMessage() + " ";
		}
		if(artPiece == null) {
			error += "Art piece cannot be empty! ";
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		system.getArtPiece().add(artPiece);
		artGallerySystemRepository.save(system);
		return system;
	}
	
	@Transactional
	public ArtGallerySystem addPurchase(String id, Purchase purchase) {
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = getSystemById(id);
		} catch(IllegalArgumentException e) {
			error += e.getMessage() + " ";
		}
		if(purchase == null) {
			error += "Purchase cannot be empty! ";
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		system.getPurchase().add(purchase);
		artGallerySystemRepository.save(system);
		return system;
	}
	
	@Transactional
	public ArtGallerySystem addAddress(String id, Address address) {
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = getSystemById(id);
		} catch(IllegalArgumentException e) {
			error += e.getMessage() + " ";
		}
		if(address == null) {
			error += "Address cannot be empty!" ;
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		system.getAddress().add(address);
		artGallerySystemRepository.save(system);
		return system;
	}

}
