package ca.mcgill.ecse321.artgallerysystem.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.UserRoleRepository;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.UserRole;
import ca.mcgill.ecse321.artgallerysystem.service.exception.UserRoleException;
/**
 * this class contains useful business methods of userRole to manipulate data in backend, used in controller
 * @author Angelina Duan
 */
@Service
public class UserRoleService {
	@Autowired
	ArtGallerySystemUserRepository artGallerySystemUserRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	/**
	 * create a new user role
	 * @param userRoleId
	 * @param userID
	 * @return
	 */
	@Transactional
	public UserRole createUserRole(String userRoleId, String userID) {
		if(userRoleId==null) {
			throw new UserRoleException("Please enter the userRole id");
		}
		UserRole userRole = new UserRole();
		userRole.setUserRoleId(userRoleId);
		ArtGallerySystemUser user = artGallerySystemUserRepository.findArtGallerySystemUserByName(userID);
		userRole.setArtGallerySystemUser(user);
		userRoleRepository.save(userRole);
		return userRole;
	}
	/**
	 * get an existing user role by id
	 * @param id
	 * @return user role if success
	 */
	@Transactional
	public UserRole getUserRole(String id) {
		if(id==null||id=="") {
			throw new UserRoleException("provide id please");
		}
		UserRole userRole=userRoleRepository.findUserRoleByUserRoleId(id);
		if(userRole==null) {
			throw new UserRoleException("userrole not found");
		}else {
			return userRole;
		}
	}
	/**
	 * get all user roles from userRoleRepository
	 * @return list of user roles
	 */
	@Transactional
	public List<UserRole> getAllUserRoles(){
		return toList(userRoleRepository.findAll());
	}
	/**
	 * delete an existing user role by id
	 * @param id
	 * @return
	 */
	@Transactional
	public boolean deleteUserRole(String id) {
		boolean deleted = false;
		UserRole userRole = userRoleRepository.findUserRoleByUserRoleId(id);
		if(userRole!=null) {
			userRoleRepository.delete(userRole);
			deleted=true;
		}else {
			throw new UserRoleException("user role not exist");
		}
		return deleted;
	}
	private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
