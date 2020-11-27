package ca.mcgill.ecse321.artgallerysystem.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtGallerySystemUserException;
/**
 * this class contains useful business methods of user to manipulate data in backend, used in controller
 * @author Angelina Duan
 */
@Service
public class ArtGallerySystemUserService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	ArtGallerySystemUserRepository artGallerySystemUserRepository;
	/**
	 * create a new user
	 * @param name
	 * @param email
	 * @param password
	 * @param avatar
	 * @return
	 */
	@Transactional
	public ArtGallerySystemUser createUser(String name, String email, String password, String avatar) {
		if(name==null||name=="") {
			throw new ArtGallerySystemUserException("Please give a name for the user");
			
		}
		if(email==null||email=="") {
			throw new ArtGallerySystemUserException("Please provide user's email");
		}
		if(password==null||password=="") {
			throw new ArtGallerySystemUserException("Please set the password");
		}
		if(avatar==null||avatar=="") {
			throw new ArtGallerySystemUserException("Please give the avatar");
		}
		ArtGallerySystemUser user = new ArtGallerySystemUser();
		user.setAvatar(avatar);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		artGallerySystemUserRepository.save(user);
		return user;
	}
	/**
	 * get an existing user by name
	 * @param name
	 * @return ArtGallerySystemUser
	 */
	@Transactional
	public ArtGallerySystemUser getUser(String name) {
		if(name==null||name=="") {
			throw new ArtGallerySystemUserException("provide name please");
		}
		ArtGallerySystemUser user=artGallerySystemUserRepository.findArtGallerySystemUserByName(name);
		if(user==null) {
			throw new ArtGallerySystemUserException("user not found");
		}else {
			return user;
		}
	}
	/**
	 * get all users from artGallerySystemUserRepository
	 * @return list of users
	 */
	@Transactional
	public List<ArtGallerySystemUser> getAllUsers(){
		return toList(artGallerySystemUserRepository.findAll());
	}
	/**
	 * delete an existing user by name
	 * @param name
	 * @return
	 */
	@Transactional
	public boolean deleteArtGallerySystemUser(String name) {
		boolean deleted=false;
		ArtGallerySystemUser user=artGallerySystemUserRepository.findArtGallerySystemUserByName(name);
		if(user!=null) {
			artGallerySystemUserRepository.delete(user);
			deleted=true;
		}else {
			throw new ArtGallerySystemUserException("user not exist");
		}
		return deleted;
	}
	/**
	 * update user email with new email
	 * @param name
	 * @param newemail
	 * @return updated ArtGallerySystemUser
	 */
	@Transactional
	public ArtGallerySystemUser updateArtGallerySystemUserEmail(String name, String newemail) {
		ArtGallerySystemUser user= artGallerySystemUserRepository.findArtGallerySystemUserByName(name);
		if(newemail==null||newemail=="") {
			throw new ArtGallerySystemUserException("new email cannot be empty or null");
		}
		if(user==null) {
			throw new ArtGallerySystemUserException("user does not exist");
		}else {
			if(user.getEmail().equals(newemail)) {
				throw new ArtGallerySystemUserException("new email is the same as the old one");
			}
			user.setEmail(newemail);
			artGallerySystemUserRepository.save(user);
			return user;
		}
	}
	/**
	 * update user password with new password
	 * @param name
	 * @param newpassword
	 * @return updated ArtGallerySystemUser
	 */
	@Transactional
	public ArtGallerySystemUser updateArtGallerySystemUserPassword(String name, String newpassword) {
		ArtGallerySystemUser user= artGallerySystemUserRepository.findArtGallerySystemUserByName(name);
		if(newpassword==null||newpassword=="") {
			throw new ArtGallerySystemUserException("new password cannot be empty or null");
		}
		if(user==null) {
			throw new ArtGallerySystemUserException("user does not exist");
		}else {
			if(user.getPassword().equals(newpassword)) {
				throw new ArtGallerySystemUserException("new password is the same as the old one");
			}
			user.setPassword(newpassword);
			artGallerySystemUserRepository.save(user);
			return user;
		}
	}
	/**
	 * update user avatar with new avatar
	 * @param name
	 * @param newavatar
	 * @return updated ArtGallerySystemUser
	 */
	@Transactional
	public ArtGallerySystemUser updateArtGallerySystemUserAvatar(String name, String newavatar) {
		ArtGallerySystemUser user= artGallerySystemUserRepository.findArtGallerySystemUserByName(name);
		if(newavatar==null||newavatar=="") {
			throw new ArtGallerySystemUserException("new avatar cannot be empty or null");
		}
		if(user==null) {
			throw new ArtGallerySystemUserException("user does not exist");
		}else {
			if(user.getAvatar().equals(newavatar)) {
				throw new ArtGallerySystemUserException("new avatar is the same as the old one");
			}
			user.setAvatar(newavatar);;
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
