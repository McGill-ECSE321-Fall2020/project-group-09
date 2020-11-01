package ca.mcgill.ecse321.artgallerysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import ca.mcgill.ecse321.artgallerysystem.dao.ParcelDeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDeliveryStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ParcelDeliveryException;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;;

@ExtendWith(MockitoExtension.class)
public class TestParcelDeliveryService {
	
	@Mock
	private ParcelDeliveryRepository parcelDeliveryRepository;
	@Mock
	private AddressRepository addressRepository;
	@Mock
	private DeliveryRepository deliveryRepository;
	private static final String DELIVERYID = "001";
	private static final String TRACKINGNUMBER = "00213";
	private static final String CARRIER = "000";
	private static final String PURID = "321";
	private Address DELIVERADDRESS = createAddress();
	private static final ParcelDeliveryStatus STATUS = ParcelDeliveryStatus.Delivered;
	private static final String TRACKINGNUMBER_N = "Test TrackingNumber 2";
	private ParcelDelivery parcelDelivery;
	private Delivery delivery;
	private Purchase purchase = createPurchase();
	private List<ParcelDelivery> allParcelDeliveries;

	@InjectMocks
	private ParcelDeliveryService parcelDeliveryService;
	@BeforeEach
	public void setMockOutput() {
		 MockitoAnnotations.initMocks(this);
	        lenient().when(parcelDeliveryRepository.findParcelDeliveryByDeliveryId((anyString()))).thenAnswer((InvocationOnMock inovation) -> {
	            if (inovation.getArgument(0).equals(TRACKINGNUMBER)){
	            	ParcelDelivery parcelDelivery = new ParcelDelivery();
	            	parcelDelivery.setTrackingNumber(TRACKINGNUMBER);
	            	parcelDelivery.setCarrier(CARRIER);
	            	parcelDelivery.setDeliveryAddress(DELIVERADDRESS);
	            	parcelDelivery.setParcelDeliveryStatus(STATUS);
	                parcelDelivery.setDeliveryId(TRACKINGNUMBER);
	                parcelDelivery.setPurchase(purchase);
	          
	            	parcelDeliveryRepository.save(parcelDelivery);
	                return parcelDelivery;
	            }else{
	                return null;
	            }
	        });
	        lenient().when(parcelDeliveryRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
	            List<ParcelDelivery> parcelDeliveris = new ArrayList<ParcelDelivery>();
	            ParcelDelivery parcelDelivery = new ParcelDelivery();
            	parcelDelivery.setTrackingNumber(TRACKINGNUMBER);
            	parcelDelivery.setCarrier(CARRIER);
            	parcelDelivery.setDeliveryAddress(DELIVERADDRESS);
            	parcelDelivery.setParcelDeliveryStatus(STATUS);
                
            	parcelDeliveryRepository.save(parcelDelivery);
            	parcelDeliveris.add(parcelDelivery);
	            return parcelDeliveris;
	     });

	        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
	            return invocation.getArgument(0);
	        };
	        lenient().when(parcelDeliveryRepository.save(any(ParcelDelivery.class))).thenAnswer(returnParameterAsAnswer);

	    }
	@Test
	public void testCreateParcelDelivery() {

		ParcelDelivery parcelDelivery = null;
		try {
			parcelDelivery = parcelDeliveryService.createParcelDelivery("001","00213","000", STATUS, DELIVERADDRESS,purchase);
		} catch (ParcelDeliveryException e) {
			fail();
		}
		assertNotNull(parcelDelivery);
	}
	
	@Test
	public void testCreateParcelDeliveryWithoutDeliveryid() {
		String error = null;
		ParcelDelivery parcelDelivery = new ParcelDelivery();
        try{
        	parcelDelivery = parcelDeliveryService.createParcelDelivery("",TRACKINGNUMBER,CARRIER,STATUS,DELIVERADDRESS,purchase);
        }catch(ParcelDeliveryException e){
            error = e.getMessage();
        }
        assertEquals("Please provide valid deliveryid.",error);
    }
	
	@Test
	public void testCreateParcelDeliveryWithoutTrackingNumber() {
		String error = null;
		ParcelDelivery parcelDelivery = new ParcelDelivery();
        try{
        	parcelDelivery = parcelDeliveryService.createParcelDelivery("001",null,CARRIER,STATUS,DELIVERADDRESS,purchase);
        }catch(ParcelDeliveryException e){
            error = e.getMessage();
        }
        assertEquals("Please provide valid trackingNumber.",error);
    }
	@Test
	public void testCreateParcelDeliveryWithoutCarrier() {
		String error = null;
		ParcelDelivery parcelDelivery = new ParcelDelivery();
        try{
        	parcelDelivery = parcelDeliveryService.createParcelDelivery("001",TRACKINGNUMBER,null,STATUS,DELIVERADDRESS,purchase);
        }catch(ParcelDeliveryException e){
            error = e.getMessage();
        }
        assertEquals("Carrier can not be empty! ",error);
    }
	@Test
	public void testCreateParcelDeliveryWithoutStatus() {
		String error = null;
		ParcelDelivery parcelDelivery = new ParcelDelivery();
        try{
        	parcelDelivery = parcelDeliveryService.createParcelDelivery("001",TRACKINGNUMBER,CARRIER,null,DELIVERADDRESS,purchase);
        }catch(ParcelDeliveryException e){
            error = e.getMessage();
        }
        assertEquals("Status can not be empty! ",error);
    }
	@Test
	public void testCreateParcelDeliveryWithoutDeliveryAddress() {
		String error = null;
		ParcelDelivery parcelDelivery = new ParcelDelivery();
        try{
        	parcelDelivery = parcelDeliveryService.createParcelDelivery(DELIVERYID,TRACKINGNUMBER,CARRIER,STATUS,null,purchase);
        }catch(ParcelDeliveryException e){
            error = e.getMessage();
        }
        assertEquals("Please provide valid Address.",error);
    }
	@Test
	public void testCreateParcelDeliveryWithoutPURID() {
		String error = null;
		ParcelDelivery parcelDelivery = new ParcelDelivery();
        try{
        	parcelDelivery = parcelDeliveryService.createParcelDelivery(DELIVERYID,TRACKINGNUMBER,CARRIER,STATUS,DELIVERADDRESS,null);
        }catch(ParcelDeliveryException e){
            error = e.getMessage();
        }
        assertEquals("Purchaseid can not be empty!",error);
    }
	
	@Test
    public void testDelete(){
        try{
        parcelDeliveryService.deleteParcelDelivery(TRACKINGNUMBER);
        }catch (ParcelDeliveryException e){
            fail();
        }
    }
	
	 @Test
	    public void testDeleteNotExist(){
	        String error = null;
	        try{
	        	parcelDeliveryService.deleteParcelDelivery(TRACKINGNUMBER_N);
	        } catch (ParcelDeliveryException e){
	            error = e.getMessage();
	        }
	        assertEquals("Order not exist.",error);
	    }
	 
	 @Test
	    public void testDeleteByEmptyTrackingNumber(){
	        String error = null;
	        try{
	        	parcelDeliveryService.deleteParcelDelivery("");
	        } catch (ParcelDeliveryException e){
	            error = e.getMessage();
	        }
	        assertEquals("Order not exist.",error);
	    }
	 
	 @Test
	    public void testDeleteByNullTrackingNumber(){
	        String error = null;
	        try{
	        	parcelDeliveryService.deleteParcelDelivery("null");
	        } catch (ParcelDeliveryException e){
	            error = e.getMessage();
	        }
	        assertEquals("Order not exist.",error);
	    }
	 
	 @Test
	    public void testGetParcelDelivery(){
	        try{
	        	parcelDeliveryService.getParcelDelivery(TRACKINGNUMBER);
	        } catch (ParcelDeliveryException e){
	            fail();
	        }
	    }
	 @Test
	    public void testGetAllParcelDelivery(){
				int size = parcelDeliveryService.getAllParcelDeliveris().size();
				assertEquals(size, 1);
	    }
	 @Test
	    public void testGetParcelDeliveryNotFind(){
		 String error = null; 
		 try{
	        	parcelDeliveryService.getParcelDelivery(TRACKINGNUMBER_N);
	        } catch (ParcelDeliveryException e){
	        	error = e.getMessage();
	        }
	        assertEquals("not exist parcelDelivery",error);
	    }
	 @Test
	    public void testGetParcelDeliveryByEmptyTrackingNumber(){
	        String error = null;
	        try{
	            parcelDeliveryService.getParcelDelivery("");
	        }catch (ParcelDeliveryException e){
	            error = e.getMessage();
	        }
	        assertEquals("Please provide vaild trackingNumber.", error);
	    }
	 
	 @Test
	    public void testGetParcelDeliveryByNullTrackingNumber(){
	        String error = null;
	        String trackingNumber = null;
	        try{
	            parcelDeliveryService.getParcelDelivery(null);
	        }catch (ParcelDeliveryException e){
	            error = e.getMessage();
	        }
	        assertEquals("Please provide vaild trackingNumber.", error);
	    }
	 
	 @Test
	    public void testUpdateParcelDelivery(){
	        String error = null;
	        try{
	            parcelDeliveryService.updateparcelDelivery(TRACKINGNUMBER,ParcelDeliveryStatus.Shipped);
	        }catch (ParcelDeliveryException e){
	            fail();
	        }
	    }
	 
	 @Test
		public void testUpdateParcelDeliveryWithNotExistTrackingNumber() {
		 ParcelDelivery parcelDelivery = null;
			String error = null;
			try {
				parcelDelivery = parcelDeliveryService.updateparcelDelivery(TRACKINGNUMBER_N, STATUS);
			} catch (ParcelDeliveryException e) {
				error = e.getMessage();
			}
			assertEquals("not exist delivery", error);
		}
	 
	 @Test
		public void testUpdateParcelDeliveryWithSameStatus() {
			ParcelDelivery parcelDelivery = null;
			String error = null;
			String newTrackingnNUMBER = TRACKINGNUMBER;
			try {
				parcelDelivery = parcelDeliveryService.updateparcelDelivery(TRACKINGNUMBER, STATUS);
			} catch (ParcelDeliveryException e) {
				error = e.getMessage();
			}
			assertEquals("same status", error);
		}
	 
	 @Test
		public void testUpdateInStorePickUpWithNullStatus() {
			String error = null;
			try {
				parcelDelivery = parcelDeliveryService.updateparcelDelivery(TRACKINGNUMBER, null);
			} catch (ParcelDeliveryException e) {
				error = e.getMessage();
			}
			assertEquals("Status cannot be empty!", error);
		}
	 @Test
		public void testUpdateParcelDeliveryWithEmptyTrackingNumber(){
			String error = null;
			try {
				parcelDelivery = parcelDeliveryService.updateparcelDelivery("", STATUS);
			}catch (ParcelDeliveryException e) {
				error = e.getMessage();
			}
			assertEquals("provide vaild trackingNumber", error);
		}
	 @Test
		public void testUpdateParcelDeliveryWithNullTrackingNumber(){
			String error = null;
			try {
				parcelDelivery = parcelDeliveryService.updateparcelDelivery(null, STATUS);
			}catch (ParcelDeliveryException e) {
				error = e.getMessage();
			}
			assertEquals("provide vaild trackingNumber", error);
		}
	 public Address createAddress(){
			Address address = new Address();
			address.setAddressId("addressid");
			address.setCountry("CA");
			address.setCity("MTL");
			address.setProvince("QC");
			address.setName("xxx");
			address.setStreetAddress("Sherbrooke");
			address.setPostalCode("H8G");
			address.setPhoneNumber("666");
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

