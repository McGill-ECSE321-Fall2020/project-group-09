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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemUserDTO;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.service.ArtGallerySystemUserService;
/**
 * this class contains the controller methods to call perform database operations using business methods of artGallerySystemUser
 * @author Angelina Duan
 */
@CrossOrigin(origins ="*")
@RestController
public class ArtGallerySystemUserController {
@Autowired
private ArtGallerySystemUserService userService;
/**
 * return all users in the database
 * @return list of ArtGallerySystemUserDTO
 */
@GetMapping(value = {"/users","/users/"})
public List<ArtGallerySystemUserDTO> getAllUsers(){
	List<ArtGallerySystemUser> users=userService.getAllUsers();
	return toList(users.stream().map(this::convertToDto).collect(Collectors.toList()));
}
/**
 * return all user id in the database
 * @return array list of string which are id
 */
@GetMapping (value = {"/userids", "userids/"})
public ArrayList<String> getAllUserIds(){
	ArrayList<String> ids = new ArrayList<String>();
	List<ArtGallerySystemUser> users=userService.getAllUsers();
	for (ArtGallerySystemUser user: users) {
		ids.add(user.getName());
	}
	return ids;
}
/**
 * create a new user
 * @param name
 * @param email
 * @param password
 * @param avatar
 * @return 
 */
@PostMapping(value = {"/user","/user/"})
public ArtGallerySystemUserDTO createUser(@RequestParam("name")String name,@RequestParam("email")String email,@RequestParam("password")String password,@RequestParam("avatar")String avatar){
	ArtGallerySystemUser user=userService.createUser(name, email, password, avatar);
	return convertToDto(user);
}
/**
 * return user by name
 * @param name
 * @return ArtGallerySystemUserDTO if success
 */
@GetMapping(value = {"/users/{name}","/users/{name}/"})
public ArtGallerySystemUserDTO getUserByName(@PathVariable("name")String name) {
	return convertToDto(userService.getUser(name));
}
/**
 * delete an existing user by name
 * @param name
 */
@DeleteMapping(value= {"/users/{name}","/users/{name}/"})
public void deleteUser(@PathVariable("name") String name) {
	userService.deleteArtGallerySystemUser(name);
}
/**
 * update email of existing user with a new email
 * @param name
 * @param newEmail
 * @return updated ArtGallerySystemUserDTO
 */
@PutMapping(value= {"/user/updateEmail/{name}","/user/updateEmail/{name}/"})
public ArtGallerySystemUserDTO updateUserEmail(@PathVariable("name")String name,@RequestParam("email")String newEmail) {
	return convertToDto(userService.updateArtGallerySystemUserEmail(name, newEmail));
}
/**
 * update password of existing user with a new password
 * @param name
 * @param newPassword
 * @return updated ArtGallerySystemUserDTO
 */
@PutMapping(value= {"/user/updatePassword/{name}","/user/updatePassword/{name}/"})
public ArtGallerySystemUserDTO updateUserPassword(@PathVariable("name")String name,@RequestParam("password")String newPassword) {
	return convertToDto(userService.updateArtGallerySystemUserPassword(name, newPassword));
}
/**
 * update avatar of existing user with a new avatar
 * @param name
 * @param newAvatar
 * @return updated ArtGallerySystemUserDTO
 */
@PutMapping(value= {"/user/updateAvatar/{name}","/user/updateAvatar/{name}/"})
public ArtGallerySystemUserDTO updateUserAvatar(@PathVariable("name")String name,@RequestParam("avatar")String newAvatar) {
	return convertToDto(userService.updateArtGallerySystemUserAvatar(name, newAvatar));
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
