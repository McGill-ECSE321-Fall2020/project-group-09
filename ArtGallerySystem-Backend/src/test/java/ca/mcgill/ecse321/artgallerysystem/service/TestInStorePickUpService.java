package ca.mcgill.ecse321.artgallerysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.DeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.InStorePickUpRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUpStatus;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.InStorePickUpException;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUp;;

@ExtendWith(MockitoExtension.class)
public class TestInStorePickUpService {
	
	@Mock
	private InStorePickUpRepository inStorePickUpRepository;
	@Mock
	private AddressRepository addressRepository;
	@Mock
	private DeliveryRepository deliveryRepository;
	@Mock
	private PurchaseRepository purchaseRepository;
	private static final String DELIVERYID = "111";
	private static final String PICKUPREFERENCENUMBER = "00123";
	private Address STOREADDRESS = createAddress();
	private Purchase purchase = createPurchase();
	private static final InStorePickUpStatus STATUS = InStorePickUpStatus.PickedUp;
	private static final String PICKUPREFERENCENUMBER_N = "Test PickUpReferenceNumber 2";
	private InStorePickUp inStorePickUp;

	@InjectMocks
	private InStorePickUpService inStorePickUpService;
	
	@BeforeEach
	public void setMockOutput() {
		 MockitoAnnotations.initMocks(this);
	        lenient().when(inStorePickUpRepository.findInStorePickUpByDeliveryId((anyString()))).thenAnswer((InvocationOnMock invocation) -> {
	            if (invocation.getArgument(0).equals(PICKUPREFERENCENUMBER)){
	            	InStorePickUp inStorePickUp = new InStorePickUp();
	            	inStorePickUp.setPickUpReferenceNumber(PICKUPREFERENCENUMBER);
	            	inStorePickUp.setStoreAddress(STOREADDRESS);
	            	inStorePickUp.setInStorePickUpStatus(STATUS);
	                inStorePickUp.setPurchase(purchase);
	            	inStorePickUpRepository.save(inStorePickUp);
	                return inStorePickUp;
	            }else{
	                return null;
	            }
	        });
	        
	        lenient().when(inStorePickUpRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
	            List<InStorePickUp> inStorePickUps = new ArrayList<InStorePickUp>();
	            InStorePickUp inStorePickUp = new InStorePickUp();
            	inStorePickUp.setPickUpReferenceNumber(PICKUPREFERENCENUMBER);
            	inStorePickUp.setStoreAddress(STOREADDRESS);
            	inStorePickUp.setInStorePickUpStatus(STATUS);
            	inStorePickUp.setPurchase(purchase);
            	inStorePickUpRepository.save(inStorePickUp);
            	inStorePickUps.add(inStorePickUp);
	            return inStorePickUps;
	     });

	        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
	            return invocation.getArgument(0);
	        };
	        lenient().when(inStorePickUpRepository.save(any(InStorePickUp.class))).thenAnswer(returnParameterAsAnswer);

	    }
	@Test
	public void testCreateInStorePickUp() {

		InStorePickUp inStorePickUp = null;
		try {
			inStorePickUp = inStorePickUpService.createInStorePickUp(DELIVERYID,PICKUPREFERENCENUMBER, STATUS, STOREADDRESS,purchase);
		} catch (InStorePickUpException e) {
			fail();
		}
		assertNotNull(inStorePickUp);
	}
	@Test
	public void testCreateInStorePickUpWithoutPickUpReferenceNumber() {
		String error = null;
		InStorePickUp inStorePickUp = null;
        try{
        	inStorePickUp = inStorePickUpService.createInStorePickUp(DELIVERYID,null,STATUS,STOREADDRESS,purchase);
        }catch(InStorePickUpException e){
            error = e.getMessage();
        }
        assertEquals("Please provide valid pickUpReferenceNumber.",error);
        assertNull(inStorePickUp);
    }
	
	@Test
	public void testCreateInStorePickUpWithoutStatus() {
		String error = null;
		InStorePickUp inStorePickUp = null;
        try{
        	inStorePickUp = inStorePickUpService.createInStorePickUp(DELIVERYID,PICKUPREFERENCENUMBER,null,STOREADDRESS,purchase);
        }catch(InStorePickUpException e){
            error = e.getMessage();
        }
        assertEquals("Status can not be empty! ",error);
        assertNull(inStorePickUp);
    }
	
	@Test
	public void testCreateInStorePickUpWithoutDeliveryid() {
		String error = null;
		InStorePickUp inStorePickUp = null;
        try{
        	inStorePickUp = inStorePickUpService.createInStorePickUp(null,PICKUPREFERENCENUMBER,STATUS,STOREADDRESS,purchase);
        }catch(InStorePickUpException e){
            error = e.getMessage();
        }
        assertEquals("Please provide valid id.",error);
        assertNull(inStorePickUp);
    }

	@Test
	public void testCreateInStorePickUpWithoutStoreAddress() {
		String error = null;
		InStorePickUp inStorePickUp = null;
        try{
        	inStorePickUp = inStorePickUpService.createInStorePickUp(DELIVERYID,PICKUPREFERENCENUMBER,STATUS,null,purchase);
        }catch(InStorePickUpException e){
            error = e.getMessage();
        }
        assertEquals("Please provide valid StoreAddress.",error);
        assertNull(inStorePickUp);
    }
	@Test
	public void testCreateInStorePickUpWithoutPURID() {
		String error = null;
		InStorePickUp inStorePickUp = null;
        try{
        	inStorePickUp = inStorePickUpService.createInStorePickUp(DELIVERYID,PICKUPREFERENCENUMBER,STATUS,STOREADDRESS,null);
        }catch(InStorePickUpException e){
            error = e.getMessage();
        }
        assertEquals("Purchaseid can not be empty!",error);
        assertNull(inStorePickUp);
    }
	
	@Test
    public void testDelete(){
        try{
        inStorePickUpService.deleteInStorePickUp(PICKUPREFERENCENUMBER);
        }catch (InStorePickUpException e){
            fail();
        }
    }
	
	 @Test
	    public void testDeleteNotExist(){
	        String error = null;
	        try{
	        	inStorePickUpService.deleteInStorePickUp(PICKUPREFERENCENUMBER_N);
	        } catch (InStorePickUpException e){
	            error = e.getMessage();
	        }
	        assertEquals("provide valid pickupreferencenumber",error);
	    }
	 
	 @Test
	    public void testDeleteByEmptyPickUpReferenceNumber(){
	        String error = null;
	        try{
	        	inStorePickUpService.deleteInStorePickUp("");
	        } catch (InStorePickUpException e){
	            error = e.getMessage();
	        }
	        assertEquals("provide valid non-empty pickupreferencenumber",error);
	    }
	 
	 @Test
	    public void testDeleteByNullPickUpReferenceNumber(){
	        String error = null;
	        try{
	        	inStorePickUpService.deleteInStorePickUp("null");
	        } catch (InStorePickUpException e){
	            error = e.getMessage();
	        }
	        assertEquals("provide valid pickupreferencenumber",error);
	    }
	 
	 @Test
	    public void testGetInStorePickUp(){
	        try{
	        	inStorePickUpService.getInStorePickUp(PICKUPREFERENCENUMBER);
	        } catch (InStorePickUpException e){
	            fail();
	        }
	    }
	 @Test
	    public void testGetAllInStorePickUp(){
				int size = inStorePickUpService.getAllInStorePickUps().size();
				assertEquals(size, 1);
	    }
	 @Test
	    public void testGetInStorePickUpNotFind(){
		 String error = null; 
		 try{
	        	inStorePickUpService.getInStorePickUp(PICKUPREFERENCENUMBER_N);
	        } catch (InStorePickUpException e){
	        	error = e.getMessage();
	        }
	        assertEquals("InStorePickUp with id Test PickUpReferenceNumber 2 does not exist.",error);
	    }
	 @Test
	    public void testGetInStorePickUpByEmptyPickUpReferenceNumber(){
	        String error = null;
	        try{
	            inStorePickUpService.getInStorePickUp("");
	        }catch (InStorePickUpException e){
	            error = e.getMessage();
	        }
	        assertEquals("Please provide valid pickUpReferenceNumber.", error);
	    }
	 
	 @Test
	    public void testGetInStorePickUpByNullPickUpReferenceNumber(){
	        String error = null;
	        try{
	            inStorePickUpService.getInStorePickUp(null);
	        }catch (InStorePickUpException e){
	            error = e.getMessage();
	        }
	        assertEquals("Please provide valid pickUpReferenceNumber.", error);
	    }
	 
	 @Test
	    public void testUpdateInStorePickUp(){
	      
	       try{
	            inStorePickUpService.updateinStorePickUp(PICKUPREFERENCENUMBER, InStorePickUpStatus.Pending);
	        }catch (InStorePickUpException e){
	            fail();
	        }
	    }
	 
	 @Test
		public void testUpdateInStorePickUpWithNotExistPickUpReferenceNumber() {
		    String error = null;
			try {
				inStorePickUpService.updateinStorePickUp(PICKUPREFERENCENUMBER_N, STATUS);
			} catch (InStorePickUpException e) {
				error = e.getMessage();
			}
			assertEquals("not exist inStorePickUp", error);
		}
	 
	 @Test
		public void testUpdateInStorePickUpWithSameStatus() {
			String error = null;
			try {
				inStorePickUp = inStorePickUpService.updateinStorePickUp(PICKUPREFERENCENUMBER, STATUS);
			} catch (InStorePickUpException e) {
				error = e.getMessage();
			}
			assertEquals("same status", error);
			assertNull(inStorePickUp);
		}
	 @Test
		public void testUpdateInStorePickUpWithNullStatus() {
			String error = null;
			try {
				inStorePickUp = inStorePickUpService.updateinStorePickUp(PICKUPREFERENCENUMBER, null);
			} catch (InStorePickUpException e) {
				error = e.getMessage();
			}
			assertEquals("Status cannot be empty!", error);
			assertNull(inStorePickUp);
		}
	 
	 @Test
		public void testUpdateInStorePickUpWithEmptyPickUpReferenceNumber(){
			String error = null;
			try {
				inStorePickUp = inStorePickUpService.updateinStorePickUp("", STATUS);
			}catch (InStorePickUpException e) {
				error = e.getMessage();
			}
			assertEquals("provide valid pickUpReferenceNumber", error);
			assertNull(inStorePickUp);
		}
	 @Test
		public void testUpdateInStorePickUpStatusWithNullPickUpReferenceNumber(){
			String error = null;
			try {
				inStorePickUp = inStorePickUpService.updateinStorePickUp(null, STATUS);
			}catch (InStorePickUpException e) {
				error = e.getMessage();
			}
			assertEquals("provide valid pickUpReferenceNumber", error);
			assertNull(inStorePickUp);
		}

		public Address createAddress(){
			Address address = new Address();
			address.setAddressId("addressid");
			address.setCountry("CA");
			address.setCity("MTL");
			address.setProvince("QC");
			address.setName("xxx");
			address.setStreetAddress("Sherbrooke");
			address.setPostalCode("H3A");
			address.setPhoneNumber("222");
			return address;
		}
		public Purchase createPurchase() {
			
			String oid = "TestOrder1";
			Purchase purchase = new Purchase();
			purchase.setOrderId(oid);
			//purchase.setArtGallerySystem(sys);
			ArtGallerySystemUser u = new ArtGallerySystemUser();
			u.setName("userTest");
			//u.setArtGallerySystem(sys);
			//userRepository.save(u);
			Artist artist = new Artist();
			artist.setArtGallerySystemUser(u);
			artist.setUserRoleId("id1");
			artist.setCredit(0.0);
			//artistRepository.save(artist);
			Set<Artist> arts = new HashSet<Artist>();
			arts.add(artist);
			ArtPiece test = new ArtPiece();
			test.setArtPieceId("id");
			test.setAuthor("author");
			test.setDescription("des");
			test.setPrice(10.0);
			test.setDate(Date.valueOf("2020-01-01"));
			//test.setArtGallerySystem(sys);
			test.setArtist(arts);
			test.setArtPieceStatus(ArtPieceStatus.Available);
			test.setName("name");
			//artPieceRepository.save(test);
			purchase.setArtPiece(test);
			Customer customer = new Customer();
			ArtGallerySystemUser u1 = new ArtGallerySystemUser();
			u1.setName("userTest");
			//u1.setArtGallerySystem(sys);
			//userRepository.save(u1);
			customer.setArtGallerySystemUser(u1);
			customer.setUserRoleId("id2");
			customer.setBalance(0.0);
			//customerRepository.save(customer);
			purchase.setDate(Date.valueOf("2020-01-01"));
			purchase.setOrderStatus(OrderStatus.Successful);
			purchase.setCustomer(customer);
			return purchase;
		}
}


