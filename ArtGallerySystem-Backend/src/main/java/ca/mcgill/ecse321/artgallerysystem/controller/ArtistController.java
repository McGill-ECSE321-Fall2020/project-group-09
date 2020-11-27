package ca.mcgill.ecse321.artgallerysystem.controller;


import ca.mcgill.ecse321.artgallerysystem.dto.ArtPieceDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtistDTO;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.service.ArtGallerySystemUserService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtistService;
import ca.mcgill.ecse321.artgallerysystem.service.UserRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


/**
 * this class contains the controller methods to call perform database operations using business methods
 * @author Zheyan Tu
 *
 */
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

    /**
     * return all artists in the database
     * @return list of artistDTo
     */
    @GetMapping("/artistList")
    public List<ArtistDTO> getAllArtists(){
        List<Artist> artistList = artistService.getAllArtists();
        return toList(artistList.stream().map(this::convertToDto).collect(Collectors.toList()));
    }


    /**
     * return artist by id
     * @param id userRole id
     * @return artistDTO if success
     */
    @GetMapping("/getArtist/{id}")
    public ArtistDTO getArtistById(@PathVariable("id")String id){
        return convertToDto(artistService.getArtist(id));
    }

    /**
     * return list of artists userRole id
     * @return List of ids
     */
    @GetMapping (value = {"/artistids"})
    public ArrayList<String> getArtistIds(){
    	ArrayList<String> ids = new ArrayList<String>();
    	List<Artist> artists =artistService.getAllArtists();
    	for (Artist art: artists) {
    		ids.add(art.getUserRoleId());
    	}
    	return ids;
    }


    /**
     * create a new artist
     * @param id userRole id
     * @param userid
     * @param credit
     * @return
     */
    @PostMapping(value = {"/createArtist/{id}", "/createArtist/{id}/"})
    public ArtistDTO createArtist(@PathVariable("id") String id, @RequestParam("user") String userid, @RequestParam("credit") double credit){
    	//userroleService
    	ArtGallerySystemUser user = userService.getUser(userid);
        Artist artist = artistService.createArtist(user,id,credit);
        return convertToDto(artist);
    }

    /**
     * delete an existing artist using id
     * @param id
     */
    @DeleteMapping("/deleteArtist/{id}")
    public void deleteArtist(@PathVariable("id") String id){
        artistService.deleteArtist(id);
    }

    /**
     * create a new artist
     * @param id userRole id
     * @param credit new credit
     * @return updated artistDTO
     */
    @PutMapping("/updateCredit/{id}")
    public ArtistDTO updateArtistCredit(@PathVariable("id") String id, @RequestParam("credit") double credit){
        return convertToDto(artistService.updateArtistCredit(id,credit));
    }



    /**
     * convert artPiece to artPieceDTO
     * @param artPiece
     * @return
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
        // BeanUtils.copyProperties(artPiece,artPieceDTO);
        return artPieceDTO;
    }


    /**
     * convert artist to artistDTO
     * @param artist
     * @return
     */
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
}
