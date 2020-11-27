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
import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtGallerySystemException;

/**
 * Business services related to the system.
 * @author Zhekai Jiang
 */
@Service
public class ArtGallerySystemService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	
	/**
	 * Get the system with the given id.
	 * An IllegalArgumentException will be thrown if the id is empty or the system does not exist.
	 * @author Amelia Cui, Zhekai Jiang
	 * @param id The id of the system.
	 * @return The system instance of the given id.
	 */
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
	
	/**
	 * Create a system with the given id.
	 * An IllegalArgumentException will be thrown if the id is empty.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @return The created system instance with the given id.
	 */
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
	
	/**
	 * Delete the system with the given id.
	 * @author Zhekai Jiang
	 * @param id The id of the system to be deleted.
	 * @return The original instance of the system.
	 */
	@Transactional
	public ArtGallerySystem deleteSystem(String id) {
		ArtGallerySystem system = getSystemById(id);
		artGallerySystemRepository.deleteById(id);
		return system;
	}
	
	/**
	 * Set the income of a system (i.e., gallery) TO a specific value.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @param income The new income value.
	 * @return The updated system instance.
	 */
	@Transactional
	public ArtGallerySystem setIncome(String id, double income) {
		ArtGallerySystem system = getSystemById(id);
		system.setIncome(income);
		artGallerySystemRepository.save(system);
		return system;
	}
	
	/**
	 * Increase the income of a system (i.e., gallery) BY a given value.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @param increment The increment in income.
	 * @return The updated system instance.
	 */
	@Transactional
	public ArtGallerySystem increaseIncome(String id, double increment) {
		ArtGallerySystem system = getSystemById(id);
		system.setIncome(system.getIncome() + increment);
		artGallerySystemRepository.save(system);
		return system;
	}
	
	/**
	 * Add a user to a system.
	 * An IllegalArgumentException will be thrown if the id or the user is null or empty.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @param user The user to be added to the system.
	 * @return The updated system instance.
	 */
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
		user.setArtGallerySystem(system);
		artGallerySystemRepository.save(system);
		return system;
	}
	
	/**
	 * Add an art piece to a system.
	 * An IllegalArgumentException will be thrown if the id or the art piece is null or empty.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @param artPiece The art piece to be added to the system.
	 * @return The updated system instance.
	 */
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
	
	/**
	 * Add a purchase to a system.
	 * An IllegalArgumentException will be thrown if the id or the purchase is null or empty.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @param purchase The purchase to be added to the system.
	 * @return The updated system instance.
	 */
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
	
	/**
	 * Add an address to a system.
	 * Note that the address could be for a parcel delivery or a store, or a frequently used address saved by a customer.
	 * It is NOT necessarily the address of the gallery!
	 * An IllegalArgumentException will be thrown if the id or the address is null or empty.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @param address The address to be added to the system.
	 * @return The updated system instance.
	 */
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
