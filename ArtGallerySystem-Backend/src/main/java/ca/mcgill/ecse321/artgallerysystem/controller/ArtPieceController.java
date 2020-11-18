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

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/artPiece")
public class ArtPieceController {

    @Autowired
    ArtPieceService artPieceService;
    @Autowired
    private ArtistService artistService;

    @GetMapping("/artPieceList")
    public List<ArtPieceDTO> artPieceList(){
        List<ArtPiece> artPieceList= artPieceService.getAllArtPieces();
        return toList(artPieceList.stream().map(this::convertToDto).collect(Collectors.toList()));
    }
    /**
     * added by amelia
     * @return
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

    @GetMapping("/getArtPiece/{id}")
    public ArtPieceDTO getArtPieceById(@PathVariable("id") String id){
        return convertToDto(artPieceService.getArtPiece(id));
    }
    
    @GetMapping("/getArtPiecesByArtist/{id}")
    public List<ArtPieceDTO> getgetArtPiecesByArtist(@PathVariable("id") String id){
        List<ArtPiece> artistArtPieces = artPieceService.getArtPiecesByArtist(id);
        return toList(artistArtPieces.stream().map(this::convertToDto).collect(Collectors.toList()));
    }
    
    /**
     * Added Nov 10
     * @author Zhekai Jiang
     */
    @GetMapping(value = {"/user/{username}", "/user/{username}/"})
    public List<ArtPieceDTO> getArtPiecesByUserName(@PathVariable("username") String userName) {
    	Artist artist = artistService.getArtistByUserName(userName);
    	return getgetArtPiecesByArtist(artist.getUserRoleId());
    }
    @GetMapping(value = {"/userrole/{userrole}", "/userrole/{userrole}/"})
    public List<ArtPieceDTO> getArtPiecesByUserRole(@PathVariable("userrole") String userName) {
    	Artist artist = artistService.getArtist(userName);
    	return getgetArtPiecesByArtist(artist.getUserRoleId());
    }
    
    @PostMapping("/createArtPiece")
    public ArtPieceDTO createArtPiece(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("des") String des, @RequestParam("author") String author, @RequestParam("price") double price, @RequestParam("date") Date date, @RequestParam("status")String status) {
    	ArtPieceStatus artstatus = convertStatus(status);
    	Set<Artist> arts = new HashSet<Artist>();
    	
    	
    	
    	
        return convertToDto(artPieceService.createArtPiece(id,name,des,author,price,date,artstatus,arts));
    }
    
    /**
     * Added Nov 10
     * @author Zhekai Jiang
     */
    @PutMapping(value = {"/addArtist/{id}", "/addArtist/{id}/"})
    public ArtPieceDTO addArtist(@PathVariable("id") String id, @RequestParam("artistid") String artistId) {
    	Artist artist = artistService.getArtist(artistId);
    	return convertToDto(artPieceService.addArtist(id, artist));
    }

    @DeleteMapping("/deleteArtPiece/{id}")
    public void deleteArtPieceById(@PathVariable("id") String id){
        artPieceService.deleteArtPiece(id);
    }

    @PutMapping("/updateArtPieceDate/{id}")
    public ArtPieceDTO updateArtPieceDate(@PathVariable("id") String id, @RequestParam("date") Date date){
        return convertToDto(artPieceService.updateArtPieceDate(id,date));
    }

    @PutMapping("/updateArtPieceName/{id}")
    public ArtPieceDTO updateArtPieceName(@PathVariable("id") String id, @RequestParam("name") String name){
        return convertToDto(artPieceService.updateArtPieceName(id,name));
    }

    @PutMapping("/updateArtPieceDes/{id}")
    public ArtPieceDTO updateArtPieceDes(@PathVariable("id") String id, @RequestParam("des") String des){
        return convertToDto(artPieceService.updateArtPieceDescription(id,des));
    }

    @PutMapping("/updateArtPiecePrice/{id}")
    public ArtPieceDTO updateArtPiecePrice(@PathVariable("id") String id, @RequestParam("price") double price){
        return convertToDto(artPieceService.updateArtPiecePrice(id,price));
    }
    
    @PutMapping("/updateArtPieceStatus/{id}")
    public ArtPieceDTO updateArtPieceStatus(@PathVariable("id") String id, @RequestParam("status") String status){
        return convertToDto(artPieceService.updateArtPieceStatus(id,status));
    }

    @PutMapping("/updateArtPieceAuthor/{id}")
    public ArtPieceDTO updateArtPieceAuthor(@PathVariable("id") String id, @RequestParam("author") String author){
        return convertToDto(artPieceService.updateArtPieceAuthor(id,author));
    }


    /**
     * Updated Nov 10 (to avoid infinite circular reference) & Nov 15 (for frontend access) by Zhekai Jiang
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
        // BeanUtils.copyProperties(artPiece,artPieceDTO);
        return artPieceDTO;
    }
    
    /**
     * Added Nov 15
     * @author Zhekai Jiang
     */
    public ArtistDTO convertToDto(Artist artist){
        ArtistDTO artistDTO = new ArtistDTO();
        HashSet<ArtPieceDTO> artPieces = new HashSet<ArtPieceDTO>();
        
        for (ArtPiece artpiece : artist.getArtPiece()) {
        	artPieces.add(convertToDto(artpiece));
        }
        artistDTO.setArtPiece(artPieces);
        artistDTO.setCredit(artist.getCredit());
        //BeanUtils.copyProperties(artist,artistDTO);
        return artistDTO;
    }
    
    /**
     * Added Nov 15
     * @author Zhekai Jiang
     */
    public PurchaseDTO convertToDto(Purchase purchase) {
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
	 * Added Nov 15
	 * @author Zhekai Jiang
	 */
	public ParcelDeliveryDTO convertToDto(ParcelDelivery delivery) {
		ParcelDeliveryDTO parcelDeliveryDto = new ParcelDeliveryDTO();
		parcelDeliveryDto.setTrackingNumber(delivery.getTrackingNumber());
		parcelDeliveryDto.setCarrier(delivery.getCarrier()); 
		parcelDeliveryDto.setDeliveryId(delivery.getDeliveryId());
		parcelDeliveryDto.setDeliveryAddress(convertToDto(delivery.getDeliveryAddress()));
		parcelDeliveryDto.setParcelDeliveryStatus(delivery.getParcelDeliveryStatus());
		return parcelDeliveryDto;
	}
	
	/**
	 * Added Nov 15
	 * @author Zhekai Jiang
	 */
	public InStorePickUpDTO convertToDto(InStorePickUp delivery) {
		InStorePickUpDTO inStorePickUpDto = new InStorePickUpDTO();
		inStorePickUpDto.setPickUpReferenceNumber(delivery.getPickUpReferenceNumber());
		inStorePickUpDto.setDeliveryId(delivery.getDeliveryId());
		inStorePickUpDto.setStoreAddress(convertToDto(delivery.getStoreAddress()));
	    inStorePickUpDto.setInStorePickUpStatus(delivery.getInStorePickUpStatus());
	    return inStorePickUpDto;
	}
	
	/**
	 * Added Nov 15
	 * @author Zhekai Jiang
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
		return addressDTO;
	}

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
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
