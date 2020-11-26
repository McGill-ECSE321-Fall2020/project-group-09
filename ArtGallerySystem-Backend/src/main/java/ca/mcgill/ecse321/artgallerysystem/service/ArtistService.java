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

@Service
public class ArtistService {

    @Autowired
    ArtGallerySystemUserRepository artGallerySystemUserRepository;
    @Autowired
    ArtistRepository artistRepository;

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

    @Transactional
    public List<Artist> getAllArtists(){
        return toList(artistRepository.findAll());
    }


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



    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}