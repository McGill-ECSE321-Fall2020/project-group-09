package ca.mcgill.ecse321.artgallerysystem.controller;


import ca.mcgill.ecse321.artgallerysystem.dto.ArtPieceDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtistDTO;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.service.ArtGallerySystemUserService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtistService;
import ca.mcgill.ecse321.artgallerysystem.service.UserRoleService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    ArtistService artistService;
    @Autowired
    ArtGallerySystemUserService userService;
    @Autowired
    UserRoleService userroleService;
    

    @GetMapping("/artistList")
    public List<ArtistDTO> getAllArtists(){
        List<Artist> artistList = artistService.getAllArtists();
        return toList(artistList.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/getArtist/{id}")
    public ArtistDTO getArtistById(@PathVariable("id")String id){
        return convertToDto(artistService.getArtist(id));
    }
    @GetMapping (value = {"/artistids"})
    public ArrayList<String> getArtistIds(){
    	ArrayList<String> ids = new ArrayList<String>();
    	List<Artist> artists =artistService.getAllArtists();
    	for (Artist art: artists) {
    		ids.add(art.getUserRoleId());
    	}
    	return ids;
    }
    @PostMapping(value = {"/createArtist/{id}", "/createArtist/{id}/"})
    public ArtistDTO createArtist(@PathVariable("id") String id, @RequestParam("user") String userid, @RequestParam("credit") double credit){
    	//userroleService
    	ArtGallerySystemUser user = userService.getUser(userid);
        Artist artist = artistService.createArtist(user,id,credit);
        return convertToDto(artist);
    }

    @DeleteMapping("/deleteArtist/{id}")
    public void deleteArtist(@PathVariable("id") String id){
        artistService.deleteArtist(id);
    }

    @PutMapping("/updateCredit/{id}")
    public ArtistDTO updateArtistCredit(@PathVariable("id") String id, @RequestParam("credit") double credit){
        return convertToDto(artistService.updateArtistCredit(id,credit));
    }
    public ArtPieceDTO convertToDto(ArtPiece artPiece){
        ArtPieceDTO artPieceDTO = new ArtPieceDTO();
        artPieceDTO.setArtPieceId(artPiece.getArtPieceId());
        artPieceDTO.setName(artPiece.getName());
        artPieceDTO.setDescription(artPiece.getDescription());
        artPieceDTO.setAuthor(artPiece.getAuthor());
        artPieceDTO.setPrice(artPiece.getPrice());
        artPieceDTO.setDate(artPiece.getDate());
        artPieceDTO.setArtPieceStatus(artPiece.getArtPieceStatus());
        // BeanUtils.copyProperties(artPiece,artPieceDTO);
        return artPieceDTO;
    }



    public ArtistDTO convertToDto(Artist artist){
        ArtistDTO artistDTO = new ArtistDTO();
        HashSet<ArtPieceDTO> artPieces = new HashSet<ArtPieceDTO>();
        if (artist.getArtPiece()!=null) {
        	for (ArtPiece artpiece : artist.getArtPiece()) {
            	artPieces.add(convertToDto(artpiece));
            }
        }
        
        artistDTO.setArtPiece(artPieces);
        artistDTO.setCredit(artist.getCredit());
        //BeanUtils.copyProperties(artist,artistDTO);
        return artistDTO;
    }


    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
