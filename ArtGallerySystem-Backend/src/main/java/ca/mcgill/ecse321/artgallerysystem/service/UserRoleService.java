package ca.mcgill.ecse321.artgallerysystem.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.UserRoleRepository;
/*import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemUserDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;*/
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
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
	public UserRole createUserRole(String userRoleId, ArtGallerySystemUser user) {
		if(userRoleId==null) {
			throw new UserRoleException("Please enter the userRole id");
		}
		if(user==null) {
			throw new UserRoleException("Please set a valid user");
		}
		UserRole userRole = new UserRole();
		userRole.setArtGallerySystemUser(user);
		userRole.setUserRoleId(userRoleId);
		Set<UserRole> sysUserRole = user.getUserRole();
		sysUserRole.add(userRole);
		user.setUserRole(sysUserRole);
		artGallerySystemUserRepository.save(user);
		userRoleRepository.save(userRole);
		return userRole;
	}
	@Transactional
	public UserRole getUserRole(String id) {
		UserRole userRole=userRoleRepository.findUserRoleByUserRoleId(id);
		return userRole;
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
