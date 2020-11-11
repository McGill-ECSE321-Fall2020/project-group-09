package ca.mcgill.ecse321.artgallerysystem.controller;


import ca.mcgill.ecse321.artgallerysystem.dto.ArtistDTO;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.service.ArtGallerySystemUserService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtistService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    

    @GetMapping("/artistList")
    public List<ArtistDTO> getAllArtists(){
        List<Artist> artistList = artistService.getAllArtists();
        return toList(artistList.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/getArtist/{id}")
    public ArtistDTO getArtistById(@PathVariable("id")String id){
        return convertToDto(artistService.getArtist(id));
    }

    @PostMapping(value = {"/createArtist/{id}", "/createArtist/{id}/"})
    public ArtistDTO createArtist(@PathVariable("id") String id, @RequestParam("user") String userid, @RequestParam("credit") double credit){
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


    public ArtistDTO convertToDto(Artist artist){
        ArtistDTO artistDTO = new ArtistDTO();
        BeanUtils.copyProperties(artist,artistDTO);
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
