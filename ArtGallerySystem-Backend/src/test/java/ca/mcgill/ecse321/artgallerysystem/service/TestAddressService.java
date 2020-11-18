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

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
@ExtendWith(MockitoExtension.class)
public class TestAddressService {
	@Mock
	private AddressRepository addressRepository;
	@Mock
	private ArtGallerySystemRepository artGallerySystemRepository;
	private static final String ADDRESS_ID = "Test address";
	private static final String CITY = "mtl";
	private static final String PROVINCE = "QC";
	private static final String COUNTRY = "Canada";
	private static final String NUMBER = "514";
	private static final String NAME = "address1";
	private static final String POSTCODE = "H";
	private static final String ADDRESS = "apt";
	private static final String ADDRESS_ID_2 = "Test address 2";
	
	
	
	
	@InjectMocks
	private AddressService service;
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
	    lenient().when(addressRepository.findAddressByAddressId(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(ADDRESS_ID)) {
	        //	ArtGallerySystem sys = new ArtGallerySystem();
	 		 //  sys.setArtGallerySystemId("test");
	 		  // artGallerySystemRepository.save(sys);
	            Address address = new Address();
	            address.setAddressId(ADDRESS_ID);
	            address.setCity(CITY);
	            address.setCountry(COUNTRY);
	            address.setName(NAME);
	            address.setPhoneNumber(NUMBER);
	            address.setPostalCode(POSTCODE);
	            address.setProvince(PROVINCE);
	            address.setStreetAddress(ADDRESS);
	          //  address.setArtGallerySystem(sys);
	            addressRepository.save(address);
	            return address;
	        } else {
	            return null;
	        }
	    });
	    lenient().when(addressRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
        	List<Address> addresses = new ArrayList<Address>();
        	Address address = new Address();
            address.setAddressId(ADDRESS_ID);
            address.setCity(CITY);
            address.setCountry(COUNTRY);
            address.setName(NAME);
            address.setPhoneNumber(NUMBER);
            address.setPostalCode(POSTCODE);
            address.setProvince(PROVINCE);
            address.setStreetAddress(ADDRESS);
          //  address.setArtGallerySystem(sys);
            addressRepository.save(address);
    		addresses.add(address);
        	
            return addresses;
        
    });
	    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(addressRepository.save(any(Address.class))).thenAnswer(returnParameterAsAnswer);
		//lenient().when(eventDao.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
		//lenient().when(registrationDao.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
	}
	@Test
	public void testCreateAddress() {
		//assertEquals(0, service.getAllAddresses().size());
		String name = "address";
		String id = "address1";
		Address address = null;
		try {
			address = service.createAddress(id, "mtl", "Canada", "H", "QC", "random", "514", name);
		} catch (AddressException e) {
			fail();
		}
		assertNotNull(address);
	}
	@Test
	public void testCreateAddressWithNotCompleteAddress() {
		String name = "address";
		String id = "address1";
		Address address = null;
		String error = null;
		try {
			address = service.createAddress(id, "", "", "H", "QC", "random", "514", name);
		} catch (AddressException e) {
			error = e.getMessage();
		}
		assertNull(address);
		assertEquals("not complete address", error);
	}
	@Test
	public void testCreateAddressWithNotCompleteNumber() {
		String name = "address";
		Address address = null;
		String id = "address1";
		String error = null;
		try {
			address = service.createAddress(id, "mtl", "Canada", "H", "QC", "random", "", name);
		} catch (AddressException e) {
			error = e.getMessage();
		}
		assertNull(address);
		assertEquals("phone number cannot be null", error);
	}
	@Test
	public void testCreateAddressWithNotCompleteName() {
		Address address = null;
		String id = "address1";
		String error = null;
		try {
			address = service.createAddress(id, "mtl", "Canada", "H", "QC", "random", "514", null);
		} catch (AddressException e) {
			error = e.getMessage();
		}
		assertNull(address);
		assertEquals("please give a name for the address", error);
	}
	@Test
	public void testCreateAddressWithNotCompleteId() {
		String name = "address";
		Address address = null;
		String error = null;
		try {
			address = service.createAddress(null, "mtl", "Canada", "H", "QC", "random", "514", name);
		} catch (AddressException e) {
			error = e.getMessage();
		}
		assertNull(address);
		assertEquals("please give an id for the address", error);
	}
	@Test
	public void testDeleteAddress() {
		//assertTrue(service.deleteAddress(ADDRESS_ID));
		//AddressDTO = 
		try {
			//service.getAddressById(ADDRESS_ID);
			service.deleteAddress(ADDRESS_ID);
		} catch (AddressException e) {
			fail();
		}
		//assertEquals("address not exist", error);
	}
	@Test
	public void testDeleteAddressNotExist() {
		String error = null;
		//assertTrue(service.deleteAddress(ADDRESS_ID));
		//AddressDTO = 
		try {
			//service.getAddressById(ADDRESS_ID);
			service.deleteAddress(ADDRESS_ID_2);
		} catch (AddressException e) {
			error = e.getMessage();
		}
		assertEquals("address not exist", error);
	}
	@Test
	public void testGetAllAddresses() {
		int size = service.getAllAddresses().size();
		assertEquals(size, 1);
	}
	@Test
	public void testGetAddress() {
		//String error = null;
		//assertTrue(service.deleteAddress(ADDRESS_ID));
		try {
			service.getAddressById(ADDRESS_ID);
			//service.deleteAddress(ADDRESS_ID);
		} catch (AddressException e) {
			fail();
		}
		//assertEquals("address not exist", error);
	}
	@Test
	public void testGetAddressNotFind() {
		String error = null;
		//assertTrue(service.deleteAddress(ADDRESS_ID));
		try {
			service.getAddressById(ADDRESS_ID_2);
			//service.deleteAddress(ADDRESS_ID);
		} catch (AddressException e) {
			error = e.getMessage();
		}
		assertEquals("Address Not Found", error);
	}
	@Test
	public void testGetAddressEmptyId() {
		String error = null;
		//assertTrue(service.deleteAddress(ADDRESS_ID));
		try {
			service.getAddressById("");
			//service.deleteAddress(ADDRESS_ID);
		} catch (AddressException e) {
			error = e.getMessage();
		}
		assertEquals("provide id please", error);
	}
	@Test
	public void testGetAddressNullId() {
		String error = null;
		String id = null;
		//assertTrue(service.deleteAddress(ADDRESS_ID));
		try {
			service.getAddressById(id);
			//service.deleteAddress(ADDRESS_ID);
		} catch (AddressException e) {
			error = e.getMessage();
		}
		assertEquals("provide id please", error);
	}
	@Test
	public void testUpdateAddress() {
		//String error = null;
		try {
			String name = "newName";
			service.updateAddress(ADDRESS_ID, name);
		} catch (AddressException e) {
			fail();
			}
		//assertEquals("address not exist", error);
		
	}
	@Test
	public void testUpdateAddressWithNotExistAddress() {
		Address address = null;
		String error = null;
		String newaddress = "new";
		try {
			address = service.updateAddress(ADDRESS_ID_2, newaddress);
		} catch (AddressException e) {
			error = e.getMessage();
		}
		assertEquals("address not exist", error);
		assertNull(address);
		
	}
	@Test
	public void testUpdateAddressWithSameNewAddress() {
		Address address = null;
		String error = null;
		String newaddress = ADDRESS;
		try {
			address = service.updateAddress(ADDRESS_ID, newaddress);
		} catch (AddressException e) {
			error = e.getMessage();
		}
		assertEquals("address is the same", error);
		assertNull(address);
		
	}
	@Test
	public void testUpdateAddressWithEmptyName(){
		String error = null;
		try {
			service.updateAddress(ADDRESS_ID, "");
		}catch (AddressException e) {
			error = e.getMessage();
		}
		assertEquals("please provide a not null address", error);
	}
	@Test
	public void testUpdateAddressWithNullName(){
		String error = null;
		String newadd = null;
		try {
			service.updateAddress(ADDRESS_ID, newadd);
		}catch (AddressException e) {
			error = e.getMessage();
		}
		assertEquals("please provide a not null address", error);
	}

}
