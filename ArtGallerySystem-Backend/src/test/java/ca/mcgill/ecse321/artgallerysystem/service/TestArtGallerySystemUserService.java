package ca.mcgill.ecse321.artgallerysystem.service;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;

import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtGallerySystemUserException;
@ExtendWith(MockitoExtension.class)
public class TestArtGallerySystemUserService {
	@Mock
	private ArtGallerySystemUserRepository userRepository;
	private static final String NAME = "Angelina";
	private static final String EMAIL = "123@gmail.com";
	private static final String PASSWORD = "ECSE321";
	private static final String AVATAR = "Angelinacoder";
	private static final String NAME_2 = "Linpei";
	
	@InjectMocks
	private ArtGallerySystemUserService userService;
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		lenient().when(userRepository.findArtGallerySystemUserByName(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(NAME)) {
				ArtGallerySystemUser user = new ArtGallerySystemUser();
				user.setName(NAME);
				user.setEmail(EMAIL);
				user.setPassword(PASSWORD);
				user.setAvatar(AVATAR);
				userRepository.save(user);
				return user;
			}else {
				return null;
			}
		});
		lenient().when(userRepository.findAll()).thenAnswer((InvocationOnMock invocation)->{
			List <ArtGallerySystemUser> users = new ArrayList<ArtGallerySystemUser>();
			ArtGallerySystemUser user = new ArtGallerySystemUser();
			user.setName(NAME);
			user.setEmail(EMAIL);
			user.setPassword(PASSWORD);
			user.setAvatar(AVATAR);
			userRepository.save(user);
			users.add(user);
			return users;
		});
			Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
				return invocation.getArgument(0);
			};
			lenient().when(userRepository.save(any(ArtGallerySystemUser.class))).thenAnswer(returnParameterAsAnswer);
	}
	@Test
	public void testCreateArtGallerySystemUser() {
		String name = "Angelina";
		ArtGallerySystemUser user = null;
		try {
			user = userService.createUser(name, "123@gmail.com", "ECSE321", "Angelinacoder");
		} catch (ArtGallerySystemUserException e) {
			fail();
		}
		assertNotNull(user);
	}
	@Test
	public void testCreateArtGallerySystemUserWithNotCompleteName() {
		ArtGallerySystemUser user = null;
		String error = null;
		try {
			user = userService.createUser(null, "123@gmail.com", "ECSE321", "Angelinacoder");
		} catch (ArtGallerySystemUserException e) {
			error = e.getMessage();
		}
		assertNull(user);
		assertEquals("Please give a name for the user", error);
	}
	@Test
	public void testCreateArtGallerySystemUserWithNotCompleteEmail() {
		String name = "Angelina";
		ArtGallerySystemUser user = null;
		String error = null;
		try {
			user = userService.createUser(name, "", "ECSE321", "Angelinacoder");
		} catch (ArtGallerySystemUserException e) {
			error = e.getMessage();
		}
		assertNull(user);
		assertEquals("Please provide user's email", error);
	}
	@Test
	public void testCreateArtGallerySystemUserWithNotCompletePassword() {
		String name = "Angelina";
		ArtGallerySystemUser user = null;
		String error = null;
		try {
			user = userService.createUser(name, "123@gmail.com", "", "Angelinacoder");
		} catch (ArtGallerySystemUserException e) {
			error = e.getMessage();
		}
		assertNull(user);
		assertEquals("Please set the password", error);
	}
	@Test
	public void testCreateArtGallerySystemUserWithNotCompleteAvatar() {
		String name = "Angelina";
		ArtGallerySystemUser user = null;
		String error = null;
		try {
			user = userService.createUser(name, "123@gmail.com", "ECSE321", "");
		} catch (ArtGallerySystemUserException e) {
			error = e.getMessage();
		}
		assertNull(user);
		assertEquals("Please give the avatar", error);
	}
	@Test
	public void testDeleteArtGallerySystemUser() {
		try {
			userService.deleteArtGallerySystemUser(NAME);
		} catch (ArtGallerySystemUserException e) {
			fail();
		}
	}
	@Test
	public void testDeleteArtGallerySystemUserNotExist() {
		String error = null;
		try {
			userService.deleteArtGallerySystemUser(NAME_2);
		} catch (ArtGallerySystemUserException e) {
			error = e.getMessage();
		}
		assertEquals("user not exist", error);
	}
	@Test
	public void testGetAllUsers() {
		int size = userService.getAllUsers().size();
		assertEquals(size, 1);
	}
	@Test
	public void testGetUser() {
		try {
			userService.getUser(NAME);
		} catch (ArtGallerySystemUserException e) {
			fail();
		}
	}
	@Test
	public void testGetUserNotFound() {
		String error = null;
		try {
			userService.getUser(NAME_2);
		} catch (ArtGallerySystemUserException e) {
			error = e.getMessage();
		}
		assertEquals("user not found", error);
	}
	@Test
	public void testGetUserEmptyName() {
		String error =null;
		try {
			userService.getUser("");
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("provide name please",error);
	}
	@Test
	public void testGetUserNullName() {
		String error= null;
		try {
			userService.getUser(null);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("provide name please", error);
	}
	@Test
	public void testUpdateUser() {
		try {
			String newName = "Duan";
			userService.updateArtGallerySystemUserName(NAME, newName);
		} catch (ArtGallerySystemUserException e) {
			fail();
		}
	}
	@Test
	public void testUpdateUserWithNotExistUser() {
		String error = null;
		String newName = "Pei";
		try {
			userService.updateArtGallerySystemUserName(NAME_2, newName);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("user does not exist", error);
	}
	@Test
	public void testUpdateUserWithSameName() {
		String error=null;
		try {
			userService.updateArtGallerySystemUserName(NAME, NAME);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new user name is the same as the old one", error);
	}
	@Test
	public void testUpdateUserWithEmptyName() {
		String error = null;
		try {
			userService.updateArtGallerySystemUserName(NAME, "");
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new user name cannot be empty or null", error);
	}
	@Test
	public void testUpdateUserWithNullName() {
		String error = null;
		try {
			userService.updateArtGallerySystemUserName(NAME, null);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new user name cannot be empty or null", error);
	}
	@Test
	public void testUpdateUserEmail() {
		try {
			String newemail = "lin@123.com";
			userService.updateArtGallerySystemUserEmail(NAME, newemail);
		} catch (ArtGallerySystemUserException e) {
			fail();
		}
	}
	@Test
	public void testUpdateUserEmailWithNotExistUser() {
		String error = null;
		String newEmail = "lin@456.com";
		try {
			userService.updateArtGallerySystemUserEmail(NAME_2, newEmail);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("user does not exist", error);
	}
	@Test
	public void testUpdateUserEmailWithSameEmail() {
		String error=null;
		try {
			userService.updateArtGallerySystemUserEmail(NAME, EMAIL);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new email is the same as the old one", error);
	}
	@Test
	public void testUpdateUserWithEmptyEmail() {
		String error = null;
		try {
			userService.updateArtGallerySystemUserEmail(NAME, "");
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new email cannot be empty or null", error);
	}
	@Test
	public void testUpdateUserWithNullEmail() {
		String error = null;
		try {
			userService.updateArtGallerySystemUserEmail(NAME, null);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new email cannot be empty or null", error);
	}
	@Test
	public void testUpdateUserPassword() {
		try {
			String newPassword = "789";
			userService.updateArtGallerySystemUserPassword(NAME, newPassword);
		} catch (ArtGallerySystemUserException e) {
			fail();
		}
	}
	@Test
	public void testUpdateUserPasswordWithNotExistUser() {
		String error = null;
		String newPassword = "789";
		try {
			userService.updateArtGallerySystemUserPassword(NAME_2, newPassword);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("user does not exist", error);
	}
	@Test
	public void testUpdateUserWithSamePassword() {
		String error=null;
		try {
			userService.updateArtGallerySystemUserPassword(NAME, PASSWORD);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new password is the same as the old one", error);
	}
	@Test
	public void testUpdateUserWithEmptyPassword() {
		String error = null;
		try {
			userService.updateArtGallerySystemUserPassword(NAME, "");
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new password cannot be empty or null", error);
	}
	@Test
	public void testUpdateUserWithNullPassword() {
		String error = null;
		try {
			userService.updateArtGallerySystemUserPassword(NAME, null);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new password cannot be empty or null", error);
	}
	@Test
	public void testUpdateUserAvatar() {
		try {
			String newAvatar = "AA";
			userService.updateArtGallerySystemUserAvatar(NAME, newAvatar);
		} catch (ArtGallerySystemUserException e) {
			fail();
		}
	}
	@Test
	public void testUpdateUserAvatarWithNotExistUser() {
		String error = null;
		String newAvatar = "AA";
		try {
			userService.updateArtGallerySystemUserAvatar(NAME_2, newAvatar);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("user does not exist", error);
	}
	@Test
	public void testUpdateUserWithSameAvatar() {
		String error=null;
		try {
			userService.updateArtGallerySystemUserAvatar(NAME, AVATAR);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new avatar is the same as the old one", error);
	}
	@Test
	public void testUpdateUserWithEmptyAvatar() {
		String error = null;
		try {
			userService.updateArtGallerySystemUserAvatar(NAME, "");
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new avatar cannot be empty or null", error);
	}
	@Test
	public void testUpdateUserWithNullAvatar() {
		String error = null;
		try {
			userService.updateArtGallerySystemUserAvatar(NAME, null);
		} catch (ArtGallerySystemUserException e) {
			error=e.getMessage();
		}
		assertEquals("new avatar cannot be empty or null", error);
	}
}
