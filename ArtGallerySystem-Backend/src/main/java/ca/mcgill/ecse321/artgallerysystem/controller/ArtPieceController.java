package ca.mcgill.ecse321.artgallerysystem.controller;


import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtPieceDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtistDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.InStorePickUpDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ParcelDeliveryDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.PurchaseDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUp;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.ArtPieceService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * this class contains the controller methods to call perform database operations using business methods
 */
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/artPiece")
public class ArtPieceController {

    @Autowired
    ArtPieceService artPieceService;
    @Autowired
    private ArtistService artistService;

    /**
     * returns all artPieces in database
     * @author Zheyan Tu
     * @return list of artPieceDTo
     */
    @GetMapping("/artPieceList")
    public List<ArtPieceDTO> artPieceList(){
        List<ArtPiece> artPieceList= artPieceService.getAllArtPieces();
        return toList(artPieceList.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    /**
     * returns list of artistPiece with "Available" Status
     * added by amelia
     * @return artPieceDTO List
     */
    @GetMapping("/availableartPieceList")
    public List<ArtPieceDTO> availableartPieceList(){
        List<ArtPiece> artPieceList= artPieceService.getAllArtPieces();
        List<ArtPiece> availables= new ArrayList<ArtPiece>();
        for (ArtPiece art: artPieceList) {
        	if (art.getArtPieceStatus()== ArtPieceStatus.Available) {
        		availables.add(art);
        	}
        }
        return toList(availables.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    /**
     * @author Zheyan Tu
     * get a artpiece by artPiece id
     * @param id artPiece id
     * @return artPieceDTO
     */
    @GetMapping("/getArtPiece/{id}")
    public ArtPieceDTO getArtPieceById(@PathVariable("id") String id){
        return convertToDto(artPieceService.getArtPiece(id));
    }

    /**
     * @author Zheyan Tu
     * get List of artpiece by artist userRole id
     * @param id artist userRole id
     * @return List of ArtPieceDTO
     */
    @GetMapping("/getArtPiecesByArtist/{id}")
    public List<ArtPieceDTO> getgetArtPiecesByArtist(@PathVariable("id") String id){
        List<ArtPiece> artistArtPieces = artPieceService.getArtPiecesByArtist(id);
        return toList(artistArtPieces.stream().map(this::convertToDto).collect(Collectors.toList()));
    }
    
    /**
     * Get all the art pieces uploaded by the given user (-'s artist role).
     * Added Nov 10 for more convenient frontend access from my account page.
     * @author Zhekai Jiang
     * @param userName The user's name.
     * @return List of ArtPiece-s uploaded by the user.
     */
    @GetMapping(value = {"/user/{username}", "/user/{username}/"})
    public List<ArtPieceDTO> getArtPiecesByUserName(@PathVariable("username") String userName) {
    	Artist artist = artistService.getArtistByUserName(userName);
    	return getgetArtPiecesByArtist(artist.getUserRoleId());
    }

    /**
     *
     * @param userName
     * @return
     */
    @GetMapping(value = {"/userrole/{userrole}", "/userrole/{userrole}/"})
    public List<ArtPieceDTO> getArtPiecesByUserRole(@PathVariable("userrole") String userName) {
    	Artist artist = artistService.getArtist(userName);
    	return getgetArtPiecesByArtist(artist.getUserRoleId());
    }

    /**
     * @author Zheyan Tu
     * create a new art piece
     * @param id artPieceId
     * @param name artPieceName
     * @param des artPieceURL
     * @param author artPieceAuthor
     * @param price artPiecePrice
     * @param date artPieceDate
     * @param status artPieceStatus
     * @return
     */
    @PostMapping("/createArtPiece")
    public ArtPieceDTO createArtPiece(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("des") String des, @RequestParam("author") String author, @RequestParam("price") double price, @RequestParam("date") Date date, @RequestParam("status")String status) {
    	ArtPieceStatus artstatus = convertStatus(status);
    	Set<Artist> arts = new HashSet<Artist>();
        return convertToDto(artPieceService.createArtPiece(id,name,des,author,price,date,artstatus,arts));
    }
    
    /**
     * Associate an art piece to an artist.
     * Added Nov 10.
     * @author Zhekai Jiang
     * @param id The id of the art piece.
     * @param artistId The id of the artist role.
     * @return The DTO of the updated art piece.
     */
    @PutMapping(value = {"/addArtist/{id}", "/addArtist/{id}/"})
    public ArtPieceDTO addArtist(@PathVariable("id") String id, @RequestParam("artistid") String artistId) {
    	Artist artist = artistService.getArtist(artistId);
    	return convertToDto(artPieceService.addArtist(id, artist));
    }

    /**
     * @author Zheyan Tu
     * Delete an artPiece by Id
     * @param id artPieceId
     */
    @DeleteMapping("/deleteArtPiece/{id}")
    public void deleteArtPieceById(@PathVariable("id") String id){
        artPieceService.deleteArtPiece(id);
    }

    /**
     * update artPiece Date by artPiece id
     * @author Zheyan Tu
     * @param id artPieceId
     * @param date new Date
     * @return updated artPieceDTO
     */
    @PutMapping("/updateArtPieceDate/{id}")
    public ArtPieceDTO updateArtPieceDate(@PathVariable("id") String id, @RequestParam("date") Date date){
        return convertToDto(artPieceService.updateArtPieceDate(id,date));
    }

    /**
     * update artPiece Name by artPiece id
     * @author Zheyan Tu
     * @param id artPieceId
     * @param name new Name
     * @return updated artPieceDTO
     */
    @PutMapping("/updateArtPieceName/{id}")
    public ArtPieceDTO updateArtPieceName(@PathVariable("id") String id, @RequestParam("name") String name){
        return convertToDto(artPieceService.updateArtPieceName(id,name));
    }

    /**
     * update artPiece image URL by artPiece id
     * @author Zheyan Tu
     * @param id artPieceId
     * @param des new artPiece URL
     * @return updated artPieceDTO
     */
    @PutMapping("/updateArtPieceDes/{id}")
    public ArtPieceDTO updateArtPieceDes(@PathVariable("id") String id, @RequestParam("des") String des){
        return convertToDto(artPieceService.updateArtPieceDescription(id,des));
    }

    /**
     * update Price by artPiece id
     * @author Zheyan Tu
     * @param id artPieceId
     * @param price new Price
     * @return updated artPieceDTO
     */
    @PutMapping("/updateArtPiecePrice/{id}")
    public ArtPieceDTO updateArtPiecePrice(@PathVariable("id") String id, @RequestParam("price") double price){
        return convertToDto(artPieceService.updateArtPiecePrice(id,price));
    }

    /**
     * update Status by artPiece id
     * @author Zheyan Tu
     * @param id artPieceId
     * @param status new Status
     * @return updated artPieceDTO
     */
    @PutMapping("/updateArtPieceStatus/{id}")
    public ArtPieceDTO updateArtPieceStatus(@PathVariable("id") String id, @RequestParam("status") String status){
        return convertToDto(artPieceService.updateArtPieceStatus(id,status));
    }

    /**
     * update Author by artPiece id
     * @author Zheyan Tu
     * @param id artPieceId
     * @param author new Author
     * @return updated artPieceDTO
     */
    @PutMapping("/updateArtPieceAuthor/{id}")
    public ArtPieceDTO updateArtPieceAuthor(@PathVariable("id") String id, @RequestParam("author") String author){
        return convertToDto(artPieceService.updateArtPieceAuthor(id,author));
    }


    /**
     * Convert an art piece to DTO.
     * Updated Nov 10 (to avoid infinite circular reference caused by the BeanUtils method)
     * & Nov 15 (for more convenient frontend access) by Zhekai Jiang
     * @param artPiece The art piece instance.
     * @return ArtPieceDTO The DTO of the art piece.
     */
    public ArtPieceDTO convertToDto(ArtPiece artPiece){
        ArtPieceDTO artPieceDTO = new ArtPieceDTO();
        artPieceDTO.setArtPieceId(artPiece.getArtPieceId());
        artPieceDTO.setName(artPiece.getName());
        artPieceDTO.setDescription(artPiece.getDescription());
        artPieceDTO.setAuthor(artPiece.getAuthor());
        artPieceDTO.setPrice(artPiece.getPrice());
        artPieceDTO.setDate(artPiece.getDate());
        artPieceDTO.setArtPieceStatus(artPiece.getArtPieceStatus());
        artPieceDTO.setPurchase(convertToDto(artPiece.getPurchase()));
        return artPieceDTO;
    }
    
    /**
     * Convert an artist to DTO (copying set of art pieces and credit only).
     * Added Nov 15.
     * @author Zhekai Jiang
     * @param artist The artist instance.
     * @return ArtistDTO The DTO of the artist.
     */
    public ArtistDTO convertToDto(Artist artist){
        ArtistDTO artistDTO = new ArtistDTO();
        HashSet<ArtPieceDTO> artPieces = new HashSet<ArtPieceDTO>();
        
        for (ArtPiece artpiece : artist.getArtPiece()) {
        	artPieces.add(convertToDto(artpiece));
        }
        artistDTO.setArtPiece(artPieces);
        artistDTO.setCredit(artist.getCredit());
        
        return artistDTO;
    }
    
    /**
     * Convert a purchase to DTO.
     * This conversion copies the date, id, status, and delivery only.
     * It does NOT copy payment, customer, art piece, and system to avoid circular references.
     * It also adds several attributes to the DTO to enable the frontend to access more conveniently:
     * - A String deliveryMethod, which could be either "Parcel Delivery" or "In-Store Pick-Up",
     * - A boolean isParcelDelivery (which becomes parcelDelivery in JSON),
     * - A boolean isInStorePickUp (which becomes inStorePickUp in JSON), and
     * - A String deliveryStatus, which stores the status regardless of delivery type in a string. 
     * Added Nov 15.
     * @author Zhekai Jiang
     * @param purchase The purchase instance.
     * @return The DTO of the purchase.
     */
    private PurchaseDTO convertToDto(Purchase purchase) {
    	if(purchase == null) {
    		return null;
    	}
    	PurchaseDTO purchaseDto = new PurchaseDTO();
		purchaseDto.setDate(purchase.getDate());
		purchaseDto.setOrderId(purchase.getOrderId());
		purchaseDto.setOrderStatus(purchase.getOrderStatus());
		Delivery delivery = purchase.getDelivery();
		if(delivery instanceof ParcelDelivery) {
			purchaseDto.setDeliveryMethod("Parcel Delivery");
			purchaseDto.setIsParcelDelivery();
			purchaseDto.setDeliveryStatus(((ParcelDelivery) delivery).getParcelDeliveryStatus().toString());
			purchaseDto.setDelivery(convertToDto((ParcelDelivery) delivery));
		} else if(delivery instanceof InStorePickUp) {
			purchaseDto.setDeliveryMethod("In-Store Pick-Up");
			purchaseDto.setIsInStorePickUp();
			purchaseDto.setDeliveryStatus(((InStorePickUp) delivery).getInStorePickUpStatus().toString());
			purchaseDto.setDelivery(convertToDto((InStorePickUp) delivery));
		}
		return purchaseDto;
    }
    
    /**
     * Convert a parcel delivery to DTO.
	 * Added Nov 15.
	 * @author Zhekai Jiang
	 * @param delivery The parcel delivery instance.
	 * @return The DTO of the parcel delivery.
	 */
	private ParcelDeliveryDTO convertToDto(ParcelDelivery delivery) {
		ParcelDeliveryDTO parcelDeliveryDto = new ParcelDeliveryDTO();
		parcelDeliveryDto.setTrackingNumber(delivery.getTrackingNumber());
		parcelDeliveryDto.setCarrier(delivery.getCarrier()); 
		parcelDeliveryDto.setDeliveryId(delivery.getDeliveryId());
		parcelDeliveryDto.setDeliveryAddress(convertToDto(delivery.getDeliveryAddress()));
		parcelDeliveryDto.setParcelDeliveryStatus(delivery.getParcelDeliveryStatus());
		return parcelDeliveryDto;
	}
	
	/**
     * Convert an in-store pick-up instance to DTO.
	 * Added Nov 15.
	 * @author Zhekai Jiang
	 * @param delivery The in-store pick-up instance.
	 * @return The DTO of the in-store pick-up.
	 */
	private InStorePickUpDTO convertToDto(InStorePickUp delivery) {
		InStorePickUpDTO inStorePickUpDto = new InStorePickUpDTO();
		inStorePickUpDto.setPickUpReferenceNumber(delivery.getPickUpReferenceNumber());
		inStorePickUpDto.setDeliveryId(delivery.getDeliveryId());
		inStorePickUpDto.setStoreAddress(convertToDto(delivery.getStoreAddress()));
	    inStorePickUpDto.setInStorePickUpStatus(delivery.getInStorePickUpStatus());
	    return inStorePickUpDto;
	}
	
	/**
     * Convert an address to DTO.
	 * Added Nov 15.
	 * @author Zhekai Jiang
	 * @param address The address instance.
	 * @return The DTO of the address.
	 */
	private AddressDTO convertToDto(Address address) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAddressId(address.getAddressId());
		addressDTO.setCity(address.getCity());
		addressDTO.setCountry(address.getCountry());
		addressDTO.setName(address.getName());
		addressDTO.setPhoneNumber(address.getPhoneNumber());
		addressDTO.setPostalCode(address.getPostalCode());
		addressDTO.setProvince(address.getProvince());
		addressDTO.setStreetAddress(address.getStreetAddress());
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

    /**
     * Convert method in String into PaymentMethod
     * @param method as String
     * @return method as PaymentMethod
     */
    public PaymentMethod convertToMethod(String method) {
    	// TODO Auto-generated method stub
    	//, DebitCard, Balance, PayPal;
    	switch(method) {
    	case "CreditCard":
    		return PaymentMethod.CreditCard;
    	case "DebitCard":
    		return PaymentMethod.DebitCard;
    	case "Balance":
    		return PaymentMethod.Balance;
    	case "Paypal":
    		return PaymentMethod.PayPal;
    	
    	}
    	return null;
    }

    /**
     * Converts String to Status
     * @param status as String
     * @return status as ArtPieceStatus
     */
    public ArtPieceStatus convertStatus (String status) {
    	switch (status) {
    	case "Available":
    		return ArtPieceStatus.Available;
    	case "sold":
    		return ArtPieceStatus.Sold;
    	}
    	return null;
    }

}
