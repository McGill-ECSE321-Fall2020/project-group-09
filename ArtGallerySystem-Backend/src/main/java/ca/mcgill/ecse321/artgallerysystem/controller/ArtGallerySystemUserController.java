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

@CrossOrigin(origins ="*")
@RestController
public class ArtGallerySystemUserController {
@Autowired
private ArtGallerySystemUserService userService;

@GetMapping(value = {"/users","/users/"})
public List<ArtGallerySystemUserDTO> getAllUsers(){
	List<ArtGallerySystemUser> users=userService.getAllUsers();
	return toList(users.stream().map(this::convertToDto).collect(Collectors.toList()));
}
@PostMapping(value = {"/user","/user/"})
public ArtGallerySystemUserDTO createUser(@RequestParam("name")String name,@RequestParam("email")String email,@RequestParam("password")String password,@RequestParam("avatar")String avatar){
	ArtGallerySystemUser user=userService.createUser(name, email, password, avatar);
	return convertToDto(user);
}
@GetMapping(value = {"/users/{name}","/users/{name}/"})
public ArtGallerySystemUserDTO getUserByName(@PathVariable("name")String name) {
	return convertToDto(userService.getUser(name));
}
@DeleteMapping(value= {"/users/{name}","/users/{name}/"})
public void deleteUser(@PathVariable("name") String name) {
	userService.deleteArtGallerySystemUser(name);
}
@PutMapping(value= {"/user/updateName/{name}","/user/updateName/{name}/"})
public ArtGallerySystemUserDTO updateUserName(@PathVariable("name")String name,@RequestParam("name")String newName) {
	return convertToDto(userService.updateArtGallerySystemUserName(name, newName));
}
@PutMapping(value= {"/user/updateEmail/{name}","/user/updateEmail/{name}/"})
public ArtGallerySystemUserDTO updateUserEmail(@PathVariable("name")String name,@RequestParam("email")String newEmail) {
	return convertToDto(userService.updateArtGallerySystemUserEmail(name, newEmail));
}
@PutMapping(value= {"/user/updatePassword/{name}","/user/updatePassword/{name}/"})
public ArtGallerySystemUserDTO updateUserPassword(@PathVariable("name")String name,@RequestParam("password")String newPassword) {
	return convertToDto(userService.updateArtGallerySystemUserPassword(name, newPassword));
}
@PutMapping(value= {"/user/updateAvatar/{name}","/user/updateAvatar/{name}/"})
public ArtGallerySystemUserDTO updateUserAvatar(@PathVariable("name")String name,@RequestParam("avatar")String newAvatar) {
	return convertToDto(userService.updateArtGallerySystemUserAvatar(name, newAvatar));
}
public ArtGallerySystemUserDTO convertToDto(ArtGallerySystemUser user) {
	ArtGallerySystemUserDTO userDTO = new ArtGallerySystemUserDTO();
	userDTO.setName(user.getName());
	userDTO.setEmail(user.getEmail());
	userDTO.setPassword(user.getPassword());
	userDTO.setAvatar(user.getAvatar());
	//userDTO.setArtGallerySystem(user.getArtGallerySystem());
	return userDTO;
}
private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
}
