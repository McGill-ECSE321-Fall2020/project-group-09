package ca.mcgill.ecse321.artgallerysystem.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.UserRoleRepository;
import ca.mcgill.ecse321.artgallerysystem.model.UserRole;
import ca.mcgill.ecse321.artgallerysystem.service.exception.UserRoleException;
/**
 * @author Angelina Duan
 * @version initial
 */
@Service
public class UserRoleService {
	@Autowired
	ArtGallerySystemUserRepository artGallerySystemUserRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	@Transactional
	public UserRole createUserRole(String userRoleId) {
		if(userRoleId==null) {
			throw new UserRoleException("Please enter the userRole id");
		}
		UserRole userRole = new UserRole();
		userRole.setUserRoleId(userRoleId);
		userRoleRepository.save(userRole);
		return userRole;
	}
	@Transactional
	public UserRole getUserRole(String id) {
		if(id==null) {
			throw new UserRoleException("userrole id invalid");
		}
		UserRole userRole=userRoleRepository.findUserRoleByUserRoleId(id);
		if(userRole==null) {
			throw new UserRoleException("userrole id not found");
		}else {
			return userRole;
		}
	}
	@Transactional
	public List<UserRole> getAllUserRoles(){
		return toList(userRoleRepository.findAll());
	}
	@Transactional
	public boolean deleteUserRole(String id) {
		if(id==null) {
			throw new UserRoleException("userRole id invalid");
		}
		boolean deleted = false;
		UserRole userRole = userRoleRepository.findUserRoleByUserRoleId(id);
		if(userRole!=null) {
			userRoleRepository.delete(userRole);
			deleted=true;
		}else {
			throw new UserRoleException("user role must be valid");
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
