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
import ca.mcgill.ecse321.artgallerysystem.dao.ArtPieceRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.DeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ParcelDeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDeliveryStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtPieceException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ParcelDeliveryException;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;;

@ExtendWith(MockitoExtension.class)
public class TestParcelDeliveryService {
	
	@Mock
	private ParcelDeliveryRepository parcelDeliveryRepository;
	private AddressRepository addressRepository;
	private DeliveryRepository deliveryRepository;
	private static final String TRACKINGNUMBER = "00213";
	private static final String CARRIER = "000";
	private static final Address DELIVERADDRESS 
	private static final ParcelDeliveryStatus STATUS = ParcelDeliveryStatus.Delivered;
	private static final String TRACKINGNUMBER_N = "Test TrackingNumber 2";
	private ParcelDelivery parcelDelivery;
	private Delivery delivery;
	private List<ParcelDelivery> allParcelDeliveries;

	@InjectMocks
	private ParcelDeliveryService parcelDeliveryService;
	@BeforeEach
	public void setMockOutput() {
		 MockitoAnnotations.initMocks(this);
	        lenient().when(parcelDeliveryRepository.findById(anyString())).thenAnswer((InvocationOnMock inovation) -> {
	            if (inovation.getArgument(0).equals(TRACKINGNUMBER)){
	            	ParcelDelivery parcelDelivery = new ParcelDelivery();
	            	parcelDelivery.setTrackingNumber(TRACKINGNUMBER);
	            	parcelDelivery.setCarrier(CARRIER);
	            	parcelDelivery.setDeliveryAddress(DELIVERADDRESS);
	            	parcelDelivery.setParcelDeliveryStatus(STATUS);
	                
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
			parcelDelivery = parcelDeliveryService.createParcelDelivery("00213", "000", STATUS, DELIVERADDRESS);
		} catch (ParcelDeliveryException e) {
			fail();
		}
		assertNotNull(parcelDelivery);
	}
	@Test
	public void testCreateParcelDeliveryWithoutTrackingNumber() {
		String error = null;
		ParcelDelivery parcelDelivery = new ParcelDelivery();
        try{
        	parcelDelivery = parcelDeliveryService.createParcelDelivery(null,CARRIER,STATUS,DELIVERADDRESS);
        }catch(ParcelDeliveryException e){
            error = e.getMessage();
        }
        assertEquals("invalid trackingnumber",error);
    }
	@Test
	public void testCreateParcelDeliveryWithoutCarrier() {
		String error = null;
		ParcelDelivery parcelDelivery = new ParcelDelivery();
        try{
        	parcelDelivery = parcelDeliveryService.createParcelDelivery(TRACKINGNUMBER,null,STATUS,DELIVERADDRESS);
        }catch(ParcelDeliveryException e){
            error = e.getMessage();
        }
        assertEquals("please enter valid carrier",error);
    }
	@Test
	public void testCreateParcelDeliveryWithoutStatus() {
		String error = null;
		ParcelDelivery parcelDelivery = new ParcelDelivery();
        try{
        	parcelDelivery = parcelDeliveryService.createParcelDelivery(TRACKINGNUMBER,CARRIER,null,DELIVERADDRESS);
        }catch(ParcelDeliveryException e){
            error = e.getMessage();
        }
        assertEquals("provide status",error);
    }
	@Test
	public void testCreateParcelDeliveryWithoutDeliveryAddress() {
		String error = null;
		ParcelDelivery parcelDelivery = new ParcelDelivery();
        try{
        	parcelDelivery = parcelDeliveryService.createParcelDelivery(TRACKINGNUMBER,CARRIER,STATUS,null);
        }catch(ParcelDeliveryException e){
            error = e.getMessage();
        }
        assertEquals("please provide valid delivery address",error);
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
	        assertEquals("not exist parcelDelivery",error);
	    }
	 
	 @Test
	    public void testDeleteByEmptyTrackingNumber(){
	        String error = null;
	        try{
	        	parcelDeliveryService.deleteParcelDelivery("");
	        } catch (ParcelDeliveryException e){
	            error = e.getMessage();
	        }
	        assertEquals("provide valid tracking number",error);
	    }
	 
	 @Test
	    public void testDeleteByNullTrackingNumber(){
	        String error = null;
	        try{
	        	parcelDeliveryService.deleteParcelDelivery("null");
	        } catch (ParcelDeliveryException e){
	            error = e.getMessage();
	        }
	        assertEquals("provide valid tracking number",error);
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
	        assertEquals("provide valid trackingnumber", error);
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
	        assertEquals("provide valid trackingnumber", error);
	    }
	 
	 @Test
	    public void testUpdateParcelDelivery(){
	        String error = null;
	        try{
	            parcelDeliveryService.updateparcelDelivery(TRACKINGNUMBER, STATUS.Delivered);
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
			} catch (AddressException e) {
				error = e.getMessage();
			}
			assertEquals("TrackingNumber not exist", error);
		}
	 
	 @Test
		public void testUpdateParcelDeliveryWithSameTrackingNumber() {
			ParcelDelivery parcelDelivery = null;
			String error = null;
			String newTrackingnNUMBER = TRACKINGNUMBER;
			try {
				parcelDelivery = parcelDeliveryService.updateparcelDelivery(TRACKINGNUMBER, STATUS);
			} catch (ParcelDeliveryException e) {
				error = e.getMessage();
			}
			assertEquals("TrackingNumber is the same", error);
		}
	 @Test
		public void testUpdateParcelDeliveryWithEmptyTrackingNumber(){
			String error = null;
			try {
				parcelDelivery = parcelDeliveryService.updateparcelDelivery("", STATUS);
			}catch (ParcelDeliveryException e) {
				error = e.getMessage();
			}
			assertEquals("please provide a not null trackingNumber", error);
		}
	 @Test
		public void testUpdateParcelDeliveryWithNullTrackingNumber(){
			String error = null;
			try {
				parcelDelivery = parcelDeliveryService.updateparcelDelivery(null, STATUS);
			}catch (ParcelDeliveryException e) {
				error = e.getMessage();
			}
			assertEquals("please provide a not null trackingNumber", error);
		}
	 
}

