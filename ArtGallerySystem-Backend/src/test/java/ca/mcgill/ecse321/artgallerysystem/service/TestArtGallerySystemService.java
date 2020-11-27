package ca.mcgill.ecse321.artgallerysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtPieceRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

/**
 * Test suite for the system service.
 * @author Zhekai Jiang
 */
@ExtendWith(MockitoExtension.class)
public class TestArtGallerySystemService {
	
	@Mock
	private ArtGallerySystemRepository artGallerySystemDao;
	@Mock
	private ArtGallerySystemUserRepository userDao;
	@Mock
	private ArtPieceRepository artPieceDao;
	@Mock
	private PurchaseRepository purchaseDao;
	@Mock
	private AddressRepository addressDao;
	
	@InjectMocks
	private ArtGallerySystemService artGallerySystemService;
	
	private static final String ARTGALLERYSYSTEM_KEY = "TestArtGallerySystem";
	private static final String USER_KEY = "TestUser";
	private static final String ARTPIECE_KEY = "TestArtPiece";
	private static final String PURCHASE_KEY = "TestPurchase";
	private static final String ADDRESS_KEY = "TestAddress";
		
	@BeforeEach
	public void setMockOutput() {
		lenient().when(artGallerySystemDao.findById(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(ARTGALLERYSYSTEM_KEY)) {
				ArtGallerySystem system = new ArtGallerySystem();
				system.setArtGallerySystemId(ARTGALLERYSYSTEM_KEY);
				system.setIncome(0);
				system.setArtGallerySystemUser(new HashSet<ArtGallerySystemUser>());
				system.setArtPiece(new HashSet<ArtPiece>());
				system.setPurchase(new HashSet<Purchase>());
				system.setAddress(new HashSet<Address>());
				return Optional.of(system);
			} else {
				return Optional.empty();
			}
		});
		
		lenient().when(userDao.findArtGallerySystemUserByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(USER_KEY)) {
				ArtGallerySystemUser user = new ArtGallerySystemUser();
				user.setName(USER_KEY);
				return user;
			} else {
				return null;
			}
		});
		
		lenient().when(artPieceDao.findArtPieceByArtPieceId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(ARTPIECE_KEY)) {
				ArtPiece artPiece = new ArtPiece();
				artPiece.setArtPieceId(ARTPIECE_KEY);
				return artPiece;
			} else {
				return null;
			}
		});
		
		lenient().when(purchaseDao.findPurchaseByOrderId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PURCHASE_KEY)) {
				Purchase purchase = new Purchase();
				purchase.setOrderId(PURCHASE_KEY);
				return purchase;
			} else {
				return null;
			}
		});
		
		lenient().when(addressDao.findAddressByAddressId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(ADDRESS_KEY)) {
				Address address = new Address();
				address.setAddressId(ADDRESS_KEY);
				return address;
			} else {
				return null;
			}
		});
	}
	
	@Test
	public void testGetSystemById() {
		String id = ARTGALLERYSYSTEM_KEY;
		ArtGallerySystem system = null;
		try {
			system = artGallerySystemService.getSystemById(id);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(system);
		assertEquals(id, system.getArtGallerySystemId());
	}
	
	@Test
	public void testGetSystemByIdNull() {
		String id = null;
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = artGallerySystemService.getSystemById(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(system);
		assertEquals("System id cannot be empty!", error);
	}
	
	@Test
	public void testGetSystemByIdEmpty() {
		String id = "";
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = artGallerySystemService.getSystemById(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(system);
		assertEquals("System id cannot be empty!", error);
	}
	
	@Test
	public void testGetSystemByIdNonExistent() {
		String id = "?";
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = artGallerySystemService.getSystemById(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(system);
		assertEquals("System with id " + id + " does not exist.", error);
	}
	
	@Test
	public void testCreateSystem() {
		String id = ARTGALLERYSYSTEM_KEY;
		ArtGallerySystem system = null;
		try {
			system = artGallerySystemService.createSystem(id);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(system);
		assertEquals(id, system.getArtGallerySystemId());
		assertEquals(0, system.getIncome());
		assertNotNull(system.getAddress());
		assertEquals(0, system.getAddress().size());
		assertNotNull(system.getArtGallerySystemUser());
		assertEquals(0, system.getArtGallerySystemUser().size());
		assertNotNull(system.getArtPiece());
		assertEquals(0, system.getArtPiece().size());
		assertNotNull(system.getPurchase());
		assertEquals(0, system.getPurchase().size());
	}
	
	@Test
	public void testCreateSystemIdNull() {
		String id = null;
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = artGallerySystemService.createSystem(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(system);
		assertEquals("System id cannot be empty!", error);
	}
	
	@Test
	public void testCreateSystemIdEmpty() {
		String id = "";
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = artGallerySystemService.createSystem(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(system);
		assertEquals("System id cannot be empty!", error);
	}
	
	@Test
	public void testDeleteSystem() {
		String id = ARTGALLERYSYSTEM_KEY;
		ArtGallerySystem system = null;
		try {
			system = artGallerySystemService.deleteSystem(id);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(system);
		assertEquals(id, system.getArtGallerySystemId());
	}
	
	@Test
	public void testDeleteSystemNull() {
		String id = null;
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = artGallerySystemService.deleteSystem(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(system);
		assertEquals("System id cannot be empty!", error);
	}
	
	@Test
	public void testDeleteSystemEmpty() {
		String id = "";
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = artGallerySystemService.deleteSystem(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(system);
		assertEquals("System id cannot be empty!", error);
	}
	
	@Test
	public void testDeleteSystemNonExistent() {
		String id = "?";
		ArtGallerySystem system = null;
		String error = "";
		try {
			system = artGallerySystemService.deleteSystem(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(system);
		assertEquals("System with id " + id + " does not exist.", error);
	}
	
	@Test
	public void testSetIncome() {
		String id = ARTGALLERYSYSTEM_KEY;
		ArtGallerySystem system = null;
		double income = 1.5;
		try {
			system = artGallerySystemService.setIncome(id, income);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(system);
		assertEquals(id, system.getArtGallerySystemId());
		assertEquals(income, system.getIncome());
	}
	
	@Test
	public void testSetIncomeNull() {
		String id = null;
		ArtGallerySystem system = null;
		double income = 1.5;
		String error = "";
		try {
			system = artGallerySystemService.setIncome(id, income);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals("System id cannot be empty!", error);
		assertNull(system);
	}
	
	@Test
	public void testIncreaseIncome() {
		String id = ARTGALLERYSYSTEM_KEY;
		ArtGallerySystem system = null;
		double initialIncome = -1;
		double incomeIncrement = 1.5;
		try {
			initialIncome = artGallerySystemService.getSystemById(id).getIncome();
			system = artGallerySystemService.increaseIncome(id, incomeIncrement);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(system);
		assertNotEquals(-1, initialIncome);
		assertEquals(id, system.getArtGallerySystemId());
		assertEquals(initialIncome + incomeIncrement, system.getIncome());
	}
	
	@Test
	public void testIncreaseIncomeNull() {
		String id = null;
		ArtGallerySystem system = null;
		double incomeIncrement = 1.5;
		String error = "";
		try {
			system = artGallerySystemService.increaseIncome(id, incomeIncrement);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals("System id cannot be empty!", error);
		assertNull(system);
	}
	
	@Test
	public void testAddUser() {
		String id = ARTGALLERYSYSTEM_KEY;
		ArtGallerySystem system = null;
		String userName = USER_KEY;
		ArtGallerySystemUser user = null;
		try {
			user = userDao.findArtGallerySystemUserByName(userName);
			system = artGallerySystemService.addUser(id, user);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(user);
		assertNotNull(system);
		assertEquals(id, system.getArtGallerySystemId());
		assertEquals(userName, user.getName());
		assert(system.getArtGallerySystemUser().contains(user));
	}
	
	@Test
	public void testAddUserNull() {
		String id = null;
		ArtGallerySystem system = null;
		String userName = null;
		ArtGallerySystemUser user = null;
		String error = "";
		try {
			user = userDao.findArtGallerySystemUserByName(userName);
			system = artGallerySystemService.addUser(id, user);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(user);
		assertNull(system);
		assertEquals("System id cannot be empty! User cannot be empty!", error);
	}
	
	@Test
	public void testAddArtPiece() {
		String id = ARTGALLERYSYSTEM_KEY;
		ArtGallerySystem system = null;
		String artPieceId = ARTPIECE_KEY;
		ArtPiece artPiece = null;
		try {
			artPiece = artPieceDao.findArtPieceByArtPieceId(artPieceId);
			system = artGallerySystemService.addArtPiece(id, artPiece);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(artPiece);
		assertNotNull(system);
		assertEquals(id, system.getArtGallerySystemId());
		assertEquals(artPieceId, artPiece.getArtPieceId());
		assert(system.getArtPiece().contains(artPiece));
	}
	
	@Test
	public void testAddArtPieceNull() {
		String id = null;
		ArtGallerySystem system = null;
		String artPieceId = null;
		ArtPiece artPiece = null;
		String error = "";
		try {
			artPiece = artPieceDao.findArtPieceByArtPieceId(artPieceId);
			system = artGallerySystemService.addArtPiece(id, artPiece);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(artPiece);
		assertNull(system);
		assertEquals("System id cannot be empty! Art piece cannot be empty!", error);
	}
	
	@Test
	public void testAddPurchase() {
		String id = ARTGALLERYSYSTEM_KEY;
		ArtGallerySystem system = null;
		String purchaseId = PURCHASE_KEY;
		Purchase purchase = null;
		try {
			purchase = purchaseDao.findPurchaseByOrderId(purchaseId);
			system = artGallerySystemService.addPurchase(id, purchase);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(purchase);
		assertNotNull(system);
		assertEquals(id, system.getArtGallerySystemId());
		assertEquals(purchaseId, purchase.getOrderId());
		assert(system.getPurchase().contains(purchase));
	}
	
	@Test
	public void testAddPurchaseNull() {
		String id = null;
		ArtGallerySystem system = null;
		String purchaseId = null;
		Purchase purchase = null;
		String error = "";
		try {
			purchase = purchaseDao.findPurchaseByOrderId(purchaseId);
			system = artGallerySystemService.addPurchase(id, purchase);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(purchase);
		assertNull(system);
		assertEquals("System id cannot be empty! Purchase cannot be empty!", error);
	}
	
	@Test
	public void testAddAddress() {
		String id = ARTGALLERYSYSTEM_KEY;
		ArtGallerySystem system = null;
		String addressId = ADDRESS_KEY;
		Address address = null;
		try {
			address = addressDao.findAddressByAddressId(addressId);
			system = artGallerySystemService.addAddress(id, address);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(address);
		assertNotNull(system);
		assertEquals(id, system.getArtGallerySystemId());
		assertEquals(addressId, address.getAddressId());
		assert(system.getAddress().contains(address));
	}
	
	@Test
	public void testAddAddressNull() {
		String id = null;
		ArtGallerySystem system = null;
		String addressId = null;
		Address address = null;
		String error = "";
		try {
			address = addressDao.findAddressByAddressId(addressId);
			system = artGallerySystemService.addAddress(id, address);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertNull(address);
		assertNull(system);
		assertEquals("System id cannot be empty! Address cannot be empty!", error);
	}
}
