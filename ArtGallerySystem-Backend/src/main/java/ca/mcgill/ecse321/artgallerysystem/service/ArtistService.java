package ca.mcgill.ecse321.artgallerysystem.service;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtistRepository;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.model.UserRole;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * this class contains useful business methods to manipulate data in backend, used in controller
 * @author Zheyan Tu
 *
 */
@Service
public class ArtistService {

    @Autowired
    ArtGallerySystemUserRepository artGallerySystemUserRepository;
    @Autowired
    ArtistRepository artistRepository;

    /**
     * get Artist by artist userRole id
     * @param id artist userRole id
     * @return artist
     */
    @Transactional
    public Artist getArtist(String id){
        if (id == null || id == ""){
            throw new ArtistException("invalid id");
        }
        Artist artist = artistRepository.findArtistByUserRoleId(id);
        if (artist == null){
            throw new ArtistException("not exist artist");
        }
        return artist;
    }
    
    /**
     * get artist bu artist user id
     * @param userName userID
     * @return artist
     * Added Nov 10
     * @author Zhekai Jiang
     */
    @Transactional
    public Artist getArtistByUserName(String userName) {
    	if(userName == null || userName.length() == 0) {
			throw new IllegalArgumentException("Username cannot be empty!");
		}
    	
		ArtGallerySystemUser user = artGallerySystemUserRepository.findArtGallerySystemUserByName(userName);
		if(user == null) { 
			throw new IllegalArgumentException("User with username " + userName + " does not exist.");
		}
		
		for(UserRole role : user.getUserRole()) {
			if(role instanceof Artist) {
				return (Artist) role;
			}
		}
		
		throw new IllegalArgumentException("User " + userName + " does not have a artist role.");
    }

    /**
     * returns list of artists stored in database
     * @return list of artist
     */
    @Transactional
    public List<Artist> getAllArtists(){
        return toList(artistRepository.findAll());
    }

    /**
     * create a new artist
     * @param user
     * @param id artist userRole id
     * @param credit credit
     * @return
     */
    @Transactional
    public Artist createArtist(ArtGallerySystemUser user, String id, double credit ){
        if (id == null || id == ""){
            throw new ArtistException("invalid id");
        }
        if (credit < 0){
            throw new ArtistException("negative credit");
        }
        Artist artist = new Artist();
        artist.setCredit(credit);
        artist.setUserRoleId(id);
        artist.setArtGallerySystemUser(user);
        artistRepository.save(artist);
        return artist;
    }

    /**
     * delete an artist by artist userRole id from database
     * @param id artist userRole id
     * @return
     */
    @Transactional
    public Artist deleteArtist(String id){
        if (id == null || id == ""){
            throw new ArtistException("invalid id");
        }
        Artist artist = artistRepository.findArtistByUserRoleId(id);
        if (artist == null){
            throw new ArtistException("not exist artist");
        }
        Artist artist1 = null;
        artistRepository.deleteById(id);
        return artist1;
    }

    /**
     * update artist credit
     * @param id artist userRole id
     * @param credit new credit
     * @return updated artist
     */
    @Transactional
    public Artist updateArtistCredit(String id, double credit){
        if (id == null || id == ""){
            throw new ArtistException("invalid id");
        }
        Artist artist = artistRepository.findArtistByUserRoleId(id);
        if (artist == null){
            throw new ArtistException("not exist artist");
        }
        if (credit<0){
            throw new ArtistException("negative credit");
        }
        if (artist.getCredit() == credit){
            throw new ArtistException("same credit");
        }
        artist.setCredit(credit);
        artistRepository.save(artist);
        return artist;
    }


    /**
     * helper method
     * @param iterable
     * @param <T>
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
