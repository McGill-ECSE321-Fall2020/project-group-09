package ca.mcgill.ecse321.artgallerysystem.controller;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemUserDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.UserRoleDTO;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.UserRole;
import ca.mcgill.ecse321.artgallerysystem.service.UserRoleService;

@CrossOrigin(origins ="*")
@RestController
/**
 * this class contains the controller methods to call perform database operations using business methods of UserRole
 * @author Angelina Duan
 */
public class UserRoleController {
@Autowired
private UserRoleService userRoleService;
/**
 * return all userRoles in the database
 * @return list of UserRoleDTO
 */
@GetMapping(value = {"/userRoles","/userRoles/"})
public List<UserRoleDTO> getAllUserRoles(){
	List<UserRole> userRoles = userRoleService.getAllUserRoles();
	return toList(userRoles.stream().map(this::convertToDto).collect(Collectors.toList()));
}
/**
 * create a new user role
 * @param id
 * @param uid (user id)
 * @return 
 */
@PostMapping(value = {"/userRole","/userRole/"})
public UserRoleDTO createUserRole(@RequestParam("id")String id, @RequestParam("userid") String uid) {
	UserRole userRole=userRoleService.createUserRole(id, uid);
	return convertToDto(userRole);
}
/**
 * return user role by id
 * @param id
 * @return UserRoleDTO if success
 */
@GetMapping(value = {"/userRoles/{id}","/userRoles/{id}"})
public UserRoleDTO getUserRoleById(@PathVariable("id")String id) {
	return convertToDto(userRoleService.getUserRole(id));
}
/**
 * delete an existing user role using id
 * @param id
 */
@DeleteMapping(value = {"/userRoles/{id}","/userRoles/{id}"})
public void deleteUserRole(@PathVariable("id")String id) {
	userRoleService.deleteUserRole(id);
}
/**
 * convert userRole to userRoleDTO
 * @param userRole
 * @return userRoleDTO
 */
public UserRoleDTO convertToDto(UserRole userRole) {
	UserRoleDTO userRoleDTO = new UserRoleDTO();
	userRoleDTO.setUserRoleId(userRole.getUserRoleId());
	userRoleDTO.setArtGallerySystemUser(convertToDto(userRole.getArtGallerySystemUser()));
	return userRoleDTO;
}
/**
 * convert ArtGallerySystemUser to ArtGallerySystemUserDTO
 * @param user
 * @return userDTO
 */
public ArtGallerySystemUserDTO convertToDto(ArtGallerySystemUser user) {
	ArtGallerySystemUserDTO userDTO = new ArtGallerySystemUserDTO();
	userDTO.setName(user.getName());
	userDTO.setEmail(user.getEmail());
	userDTO.setPassword(user.getPassword());
	userDTO.setAvatar(user.getAvatar());
	return userDTO;
}
/**
 * useful helper method
 * @param <T>
 * @param iterable
 * @return resultList
 */
private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
}
