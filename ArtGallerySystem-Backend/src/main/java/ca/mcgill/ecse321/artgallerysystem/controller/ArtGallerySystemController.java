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
	
	@GetMapping(value = {"/system/{id}", "/system/{id}/"})
	public ArtGallerySystemDTO getSystemById(@PathVariable("id") String id) {
		return convertToDto(artGallerySystemService.getSystemById(id));
	}
	
	@PostMapping(value = {"/system/{id}", "/system/{id}/"})
	public ArtGallerySystemDTO createSystem(@PathVariable("id") String id) {
		return convertToDto(artGallerySystemService.createSystem(id));
	}
	
	@DeleteMapping(value = {"/system/{id}", "/system/{id}/"})
	public void deleteSystem(@PathVariable("id") String id) {
		artGallerySystemService.deleteSystem(id);
	}
	
	@PutMapping(value = {"/system/setincome/{id}", "/system/setincome/{id}/"})
	public ArtGallerySystemDTO setIncome(@PathVariable("id") String id, @RequestParam("income") double income) {
		return convertToDto(artGallerySystemService.setIncome(id, income));
	}
	
	@PutMapping(value = {"/system/increaseincome/{id}", "/system/increaseincome/{id}/"})
	public ArtGallerySystemDTO increaseIncome(@PathVariable("id") String id, @RequestParam("incomeincrement") double incomeIncrement) {
		return convertToDto(artGallerySystemService.increaseIncome(id, incomeIncrement));
	}
	
	@PutMapping(value = {"/system/adduser/{id}", "/system/adduser/{id}/"})
	public ArtGallerySystemDTO addUser(@PathVariable("id") String id, @RequestParam("userid") String userId) {
		ArtGallerySystemUser user = userService.getUser(userId);
		return convertToDto(artGallerySystemService.addUser(id, user));
	}
	
	@PutMapping(value = {"/system/addartpiece/{id}", "/system/addartpiece/{id}/"})
	public ArtGallerySystemDTO addArtPiece(@PathVariable("id") String id, @RequestParam("artpieceid") String artPieceId) {
		ArtPiece artPiece = artPieceService.getArtPiece(artPieceId);
		return convertToDto(artGallerySystemService.addArtPiece(id, artPiece));
	}
	
	@PutMapping(value = {"/system/addpurchase/{id}", "/system/addpurchase/{id}/"})
	public ArtGallerySystemDTO addPurchase(@PathVariable("id") String id, @RequestParam("purchaseid") String purchaseId) {
		Purchase purchase = purchaseService.getPurchase(purchaseId);
		return convertToDto(artGallerySystemService.addPurchase(id, purchase));
	}
	
	@PutMapping(value = {"/system/addaddress/{id}", "/system/addaddress/{id}/"})
	public ArtGallerySystemDTO addAddress(@PathVariable("id") String id, @RequestParam("addressid") String addressId) {
		Address address = addressService.getAddressById(addressId);
		return convertToDto(artGallerySystemService.addAddress(id, address));
	}
	
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
	
	private Set<AddressDTO> convertAddressSetToAddressDtoSet(Set<Address> addresses) {
		Set<AddressDTO> addressDtos = new HashSet<AddressDTO>();
		for(Address address : addresses) {
			addressDtos.add(addressController.convertToDto(address));
		}
		return addressDtos;
	}
	
	private Set<ArtGallerySystemUserDTO> convertUserSetToUserDtoSet(Set<ArtGallerySystemUser> users) {
		Set<ArtGallerySystemUserDTO> userDtos = new HashSet<ArtGallerySystemUserDTO>();
		for(ArtGallerySystemUser user : users) {
			userDtos.add(userController.convertToDto(user));
		}
		return userDtos;
	}
	
	private Set<ArtPieceDTO> convertArtPieceSetToArtPieceDtoSet(Set<ArtPiece> artPieces) {
		Set<ArtPieceDTO> artPieceDtos = new HashSet<ArtPieceDTO>();
		for(ArtPiece artPiece : artPieces) {
			artPieceDtos.add(artPieceController.convertToDto(artPiece));
		}
		return artPieceDtos;
	}
	
	private Set<PurchaseDTO> convertPurchaseSetToPurchaseDtoSet(Set<Purchase> purchases) {
		Set<PurchaseDTO> purchaseDtos = new HashSet<PurchaseDTO>();
		for(Purchase purchase : purchases) {
			purchaseDtos.add(purchaseController.convertToDto(purchase));
		}
		return purchaseDtos;
	}
	
}
