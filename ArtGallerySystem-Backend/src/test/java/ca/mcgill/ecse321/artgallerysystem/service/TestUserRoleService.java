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

import ca.mcgill.ecse321.artgallerysystem.dao.UserRoleRepository;
import ca.mcgill.ecse321.artgallerysystem.model.UserRole;

import ca.mcgill.ecse321.artgallerysystem.service.exception.UserRoleException;
@ExtendWith(MockitoExtension.class)
public class TestUserRoleService {
	@Mock
	private UserRoleRepository userRoleRepository;
	private static final String ID = "123";
	private static final String ID_2 = "456";
	@InjectMocks
	private UserRoleService userRoleService;
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		lenient().when(userRoleRepository.findUserRoleByUserRoleId(anyString())).thenAnswer((InvocationOnMock invocation)->{
			if(invocation.getArgument(0).equals(ID)) {
				UserRole userRole = new UserRole();
				userRole.setUserRoleId(ID);
				userRoleRepository.save(userRole);
				return userRole;
			}else {
				return null;
			}
		});
		lenient().when(userRoleRepository.findAll()).thenAnswer((InvocationOnMock invocation)->{
			List <UserRole> userRoles = new ArrayList<UserRole>();
			UserRole userRole = new UserRole();
			userRole.setUserRoleId(ID);
			userRoleRepository.save(userRole);
			userRoles.add(userRole);
			return userRoles;
		});
			Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation)->{
				return invocation.getArgument(0);
			};
			lenient().when(userRoleRepository.save(any(UserRole.class))).thenAnswer(returnParameterAsAnswer);
	}
	@Test
	public void testCreateUserRole() {
		UserRole userRole=null;
		String id="789";
		try {
			userRole=userRoleService.createUserRole(id);
		} catch (UserRoleException e) {
			fail();
		}
		assertNotNull(userRole);
	}
	@Test
	public void testCreateUserRoleWithNotCompleteId() {
		UserRole userRole=null;
		String error =null;
		try {
			userRole=userRoleService.createUserRole(null);
		} catch (UserRoleException e) {
			error=e.getMessage();
		}
		assertNull(userRole);
		assertEquals("Please enter the userRole id", error);
	}
	@Test
	public void testDeleteUserRole() {
		try {
			userRoleService.deleteUserRole(ID);
		} catch (UserRoleException e) {
			fail();
		}
	}
	@Test
	public void testDeleteUserRoleNotExist() {
		String error=null;
		try {
			userRoleService.deleteUserRole(ID_2);
		} catch (UserRoleException e) {
			error=e.getMessage();
		}
		assertEquals("user role not exist", error);
	}
	@Test
	public void testGetAllUserRoles() {
		int size = userRoleService.getAllUserRoles().size();
		assertEquals(size, 1);
	}
	@Test
	public void testGetUserRole() {
		try {
			userRoleService.getUserRole(ID);
		} catch (UserRoleException e) {
			fail();
		}
	}
	@Test
	public void testGetUserRoleNotFound() {
		String error=null;
		try {
			userRoleService.getUserRole(ID_2);
		} catch (UserRoleException e) {
			error=e.getMessage();
		}
		assertEquals("userrole not found", error);
	}
	@Test
	public void testGetUserRoleEmptyId() {
		String error=null;
		try {
			userRoleService.getUserRole("");
		} catch (UserRoleException e) {
			error=e.getMessage();
		}
		assertEquals("provide id please", error);
	}
	@Test
	public void testGetUserRoleNullId() {
		String error = null;
		try {
			userRoleService.getUserRole(null);
		} catch (UserRoleException e) {
			error=e.getMessage();
		}
		assertEquals("provide id please", error);
	}
}
