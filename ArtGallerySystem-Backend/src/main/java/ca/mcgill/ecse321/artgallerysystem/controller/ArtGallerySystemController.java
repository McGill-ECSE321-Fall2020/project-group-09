package ca.mcgill.ecse321.artgallerysystem.controller;

import java.util.HashSet;
import java.util.Set;

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
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemUserDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtPieceDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.PurchaseDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.AddressService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtGallerySystemService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtGallerySystemUserService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtPieceService;
import ca.mcgill.ecse321.artgallerysystem.service.PurchaseService;

/**
 * REST controller for functionalities related to ArtGallerySystem.
 * @author Zhekai Jiang
 */
@CrossOrigin(origins="*")
@RestController
public class ArtGallerySystemController {
	
	@Autowired
	private ArtGallerySystemService artGallerySystemService;
	@Autowired
	private ArtGallerySystemUserService userService;
	@Autowired
	private ArtPieceService artPieceService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressController addressController;
	@Autowired
	private ArtGallerySystemUserController userController;
	@Autowired
	private ArtPieceController artPieceController;
	@Autowired
	private PurchaseController purchaseController;
	
	/**
	 * Get a system DTO by the system's id.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @return The DTO of the system.
	 */
	@GetMapping(value = {"/system/{id}", "/system/{id}/"})
	public ArtGallerySystemDTO getSystemById(@PathVariable("id") String id) {
		return convertToDto(artGallerySystemService.getSystemById(id));
	}
	
	/**
	 * Create a system with the given id.
	 * @author Zhekai Jiang
	 * @param id The id of the system to be created.
	 * @return The DTO of the new system created.
	 */
	@PostMapping(value = {"/system/{id}", "/system/{id}/"})
	public ArtGallerySystemDTO createSystem(@PathVariable("id") String id) {
		return convertToDto(artGallerySystemService.createSystem(id));
	}
	
	/**
	 * Delete the system with the given id.
	 * @author Zhekai Jiang
	 * @param id The id of the system to be deleted.
	 */
	@DeleteMapping(value = {"/system/{id}", "/system/{id}/"})
	public void deleteSystem(@PathVariable("id") String id) {
		artGallerySystemService.deleteSystem(id);
	}
	
	/**
	 * Set the income for the given system (i.e., gallery).
	 * @author Zhekai Jiang
	 * @param id The id of the given system.
	 * @param income The new income of the system.
	 * @return The DTO of the updated system.
	 */
	@PutMapping(value = {"/system/setincome/{id}", "/system/setincome/{id}/"})
	public ArtGallerySystemDTO setIncome(@PathVariable("id") String id, @RequestParam("income") double income) {
		return convertToDto(artGallerySystemService.setIncome(id, income));
	}
	
	/**
	 * Increase the income of the given system (i.e., gallery) by a given value.
	 * @author Zhekai Jiang
	 * @param id The id of the given system.
	 * @param incomeIncrement The increment in income.
	 * @return The DTO of the updated system.
	 */
	@PutMapping(value = {"/system/increaseincome/{id}", "/system/increaseincome/{id}/"})
	public ArtGallerySystemDTO increaseIncome(@PathVariable("id") String id, @RequestParam("incomeincrement") double incomeIncrement) {
		return convertToDto(artGallerySystemService.increaseIncome(id, incomeIncrement));
	}
	
	/**
	 * Add a user to the given system.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @param userId The id of the user to be added.
	 * @return The DTO of the updated system.
	 */
	@PutMapping(value = {"/system/adduser/{id}", "/system/adduser/{id}/"})
	public ArtGallerySystemDTO addUser(@PathVariable("id") String id, @RequestParam("userid") String userId) {
		ArtGallerySystemUser user = userService.getUser(userId);
		return convertToDto(artGallerySystemService.addUser(id, user));
	}
	
	/**
	 * Add an art piece to the given system.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @param artPieceId The id of the art piece to be added.
	 * @return The DTO of the updated system.
	 */
	@PutMapping(value = {"/system/addartpiece/{id}", "/system/addartpiece/{id}/"})
	public ArtGallerySystemDTO addArtPiece(@PathVariable("id") String id, @RequestParam("artpieceid") String artPieceId) {
		ArtPiece artPiece = artPieceService.getArtPiece(artPieceId);
		return convertToDto(artGallerySystemService.addArtPiece(id, artPiece));
	}
	
	/**
	 * Add a purchase to the given system.
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @param purchaseId The id of the purchase to be added.
	 * @return The DTO of the updated system.
	 */
	@PutMapping(value = {"/system/addpurchase/{id}", "/system/addpurchase/{id}/"})
	public ArtGallerySystemDTO addPurchase(@PathVariable("id") String id, @RequestParam("purchaseid") String purchaseId) {
		Purchase purchase = purchaseService.getPurchase(purchaseId);
		return convertToDto(artGallerySystemService.addPurchase(id, purchase));
	}
	
	/**
	 * Add an address to the given system.
	 * Note that this address could be
	 * - a frequently used address saved by a customer,
	 * - an address used for a purchase, or
	 * - the address of a store.
	 * It is not necessarily the address of the gallery itself!
	 * @author Zhekai Jiang
	 * @param id The id of the system.
	 * @param addressId The id of the address to be added.
	 * @return The DTO of the updated system.
	 */
	@PutMapping(value = {"/system/addaddress/{id}", "/system/addaddress/{id}/"})
	public ArtGallerySystemDTO addAddress(@PathVariable("id") String id, @RequestParam("addressid") String addressId) {
		Address address = addressService.getAddressById(addressId);
		return convertToDto(artGallerySystemService.addAddress(id, address));
	}
	
	/**
	 * Helper method to convert an ArtGallerySystem instance to a DTO.
	 * Note that this method will call the convertToDto methods in other controllers.
	 * However, since we later decided to treat the associations from ArtGallerySystem to other classes unidirectional
	 * (despite the initial specification in our domain model), other instances' references to ArtGallerySystem will be null,
	 * so there will not be circular references.
	 * @author Zhekai Jiang
	 * @param system An ArtGallerySystem instance.
	 * @return The DTO of the system.
	 */
	public ArtGallerySystemDTO convertToDto(ArtGallerySystem system) {
		ArtGallerySystemDTO systemDto = new ArtGallerySystemDTO();
		systemDto.setArtGallerySystemId(system.getArtGallerySystemId());
		systemDto.setIncome(system.getIncome());
		systemDto.setAddress(convertAddressSetToAddressDtoSet(system.getAddress()));
		systemDto.setArtGallerySystemUser(convertUserSetToUserDtoSet(system.getArtGallerySystemUser()));
		systemDto.setArtPiece(convertArtPieceSetToArtPieceDtoSet(system.getArtPiece()));
		systemDto.setPurchase(convertPurchaseSetToPurchaseDtoSet(system.getPurchase()));
		return systemDto;
	}
	
	/**
	 * Helper method to convert a set of Address-es to a set of AddressDTO-s.
	 * @author Zhekai Jiang
	 * @param addresses A set of Address-es.
	 * @return A set of AddressDTO-s.
	 */
	private Set<AddressDTO> convertAddressSetToAddressDtoSet(Set<Address> addresses) {
		Set<AddressDTO> addressDtos = new HashSet<AddressDTO>();
		for(Address address : addresses) {
			addressDtos.add(addressController.convertToDto(address));
		}
		return addressDtos;
	}
	
	/**
	 * Helper method to convert a set of users to a set of user DTOs.
	 * @author Zhekai Jiang
	 * @param users A set of users.
	 * @return A set of user DTOs.
	 */
	private Set<ArtGallerySystemUserDTO> convertUserSetToUserDtoSet(Set<ArtGallerySystemUser> users) {
		Set<ArtGallerySystemUserDTO> userDtos = new HashSet<ArtGallerySystemUserDTO>();
		for(ArtGallerySystemUser user : users) {
			userDtos.add(userController.convertToDto(user));
		}
		return userDtos;
	}
	
	/**
	 * Helper method to convert a set of ArtPiece-s to a set of ArtPieceDTO-s.
	 * @author Zhekai Jiang
	 * @param artPieces A set of ArtPiece-s.
	 * @return A set of ArtPieceDTO-s.
	 */
	private Set<ArtPieceDTO> convertArtPieceSetToArtPieceDtoSet(Set<ArtPiece> artPieces) {
		Set<ArtPieceDTO> artPieceDtos = new HashSet<ArtPieceDTO>();
		for(ArtPiece artPiece : artPieces) {
			artPieceDtos.add(artPieceController.convertToDto(artPiece));
		}
		return artPieceDtos;
	}
	
	/**
	 * Helper method to convert a set of Purchase-s to a set of PurchaseDTO-s.
	 * @author Zhekai Jiang
	 * @param purchases A set of Purchase-s.
	 * @return A set of PurchaseDTO-s.
	 */
	private Set<PurchaseDTO> convertPurchaseSetToPurchaseDtoSet(Set<Purchase> purchases) {
		Set<PurchaseDTO> purchaseDtos = new HashSet<PurchaseDTO>();
		for(Purchase purchase : purchases) {
			purchaseDtos.add(purchaseController.convertToDto(purchase));
		}
		return purchaseDtos;
	}
	
}
