package ca.mcgill.ecse321.artgallerysystem.controller;


import ca.mcgill.ecse321.artgallerysystem.dto.ArtPieceDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtistDTO;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.service.ArtPieceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/artPiece")
public class ArtPieceController {

    @Autowired
    ArtPieceService artPieceService;

    @GetMapping("/artPieceList")
    public List<ArtPieceDTO> artPieceList(){
        List<ArtPiece> artPieceList= artPieceService.getAllArtPieces();
        return toList(artPieceList.stream().map(this::convertToDto).collect(Collectors.toList()));
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

    @PostMapping("/createArtPiece")
    public ArtPieceDTO createArtPiece(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("des") String des, @RequestParam("author") String author, @RequestParam("price") double price, @RequestParam("date") Date date, @RequestParam("status")String status) {
    	ArtPieceStatus artstatus = convertStatus(status);
    	Set<Artist> arts = new HashSet<Artist>();
    	
        return convertToDto(artPieceService.createArtPiece(id,name,des,author,price,date,artstatus,arts));
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



    public ArtPieceDTO convertToDto(ArtPiece artPiece){
        ArtPieceDTO artPieceDTO = new ArtPieceDTO();
        BeanUtils.copyProperties(artPiece,artPieceDTO);
        return artPieceDTO;
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
