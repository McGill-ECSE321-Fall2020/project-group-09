package ca.mcgill.ecse321.artgallerysystem.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
/*import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemUserDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;*/
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtGallerySystemUserException;
/**
 * @author Angelina Duan
 * @version initial
 */
@Service
public class ArtGallerySystemUserService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	ArtGallerySystemUserRepository artGallerySystemUserRepository;
	@Transactional
	public ArtGallerySystemUser createUser(String name, String email, String password, String avatar, ArtGallerySystem system) {
		if(name==null) {
			throw new ArtGallerySystemUserException("Please give a name for the user");
			
		}
		if(email==null) {
			throw new ArtGallerySystemUserException("Please provide user's email");
		}
		if(password==null) {
			throw new ArtGallerySystemUserException("Please set the password");
		}
		if(avatar==null) {
			throw new ArtGallerySystemUserException("Please give the avatar");
		}
		ArtGallerySystemUser user = new ArtGallerySystemUser();
		user.setArtGallerySystem(system);
		user.setAvatar(avatar);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		Set<ArtGallerySystemUser> sysUser=system.getArtGallerySystemUser();
		sysUser.add(user);
		system.setArtGallerySystemUser(sysUser);
		artGallerySystemRepository.save(system);
		artGallerySystemUserRepository.save(user);
		return user;
	}
	@Transactional
	public ArtGallerySystemUser getUser(String name) {
		ArtGallerySystemUser user=artGallerySystemUserRepository.findArtGallerySystemUserByName(name);
		return user;
	}
	@Transactional
	public List<ArtGallerySystemUser> getAllUsers(){
		return toList(artGallerySystemUserRepository.findAll());
	}
	@Transactional
	public boolean deleteArtGallerySystemUser(String name) {
		if(name==null) {
			throw new ArtGallerySystemUserException("name invalid");
		}
		boolean deleted=false;
		ArtGallerySystemUser user=artGallerySystemUserRepository.findArtGallerySystemUserByName(name);
		if(user!=null) {
			artGallerySystemUserRepository.delete(user);
			deleted=true;
		}else {
			throw new ArtGallerySystemUserException("user must be valid");
		}
		return deleted;
	}
	@Transactional
	public ArtGallerySystemUser updateArtGallerySystemUserName(String name, String newusername) {
		if(name==null) {
			throw new ArtGallerySystemUserException("name of user cannot be empty");
		}
		if(newusername==null) {
			throw new ArtGallerySystemUserException("new user name cannot be empty");
		}
		ArtGallerySystemUser user= artGallerySystemUserRepository.findArtGallerySystemUserByName(name);
		if(user==null) {
			throw new ArtGallerySystemUserException("user does not exist");
		}else {
			if(user.getName().equals(newusername)) {
				throw new ArtGallerySystemUserException("new user name is the same as the old one");
			}
			user.setName(newusername);
			artGallerySystemUserRepository.save(user);
			return user;
		}
	}
	private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
