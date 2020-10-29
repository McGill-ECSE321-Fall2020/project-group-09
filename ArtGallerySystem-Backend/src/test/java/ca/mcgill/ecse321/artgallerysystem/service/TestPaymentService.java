package ca.mcgill.ecse321.artgallerysystem.service;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtPieceRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtistRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.PaymentException;
@ExtendWith(MockitoExtension.class)
public class TestPaymentService {
	@Mock
	private PaymentRepository paymentRepository;
	private PurchaseRepository purchaseRepository;
	private ArtGallerySystemRepository artGallerySystemRepository;
	private ArtGallerySystemUserRepository userRepository;
	private ArtistRepository artistRepository;
	private ArtPieceRepository artPieceRepository;
	private CustomerRepository customerRepository;
	
	private static final String PAYMENT_ID = "Test payment";
	private static final PaymentMethod METHOD = PaymentMethod.Balance;
	private static final String PAYMENT_ID_2 = "Test payment 2";
	private static final String PAYMENT_ID_3 = "Test payment 3";
	
	
	
	
	
	
	@InjectMocks
	private PaymentService service;
	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
	    lenient().when(paymentRepository.findPaymentByPaymentId(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(PAYMENT_ID)) {
	        	Purchase purchase = createPurchase();
	    		//String pid = "TestPayment";
	    		Payment payment = new Payment();
	    		payment.setPaymentId(PAYMENT_ID);
	    		payment.setPaymentMethod(METHOD);
	    		payment.setIsSuccessful(false);
	        	payment.setPurchase(purchase);
	    		//purchaseRepository.save(purchase);
	    		//payment.setPurchase(purchase);
	    		paymentRepository.save(payment);
	        	
	            return payment;
	        } 
	        else if (invocation.getArgument(0).equals(PAYMENT_ID_3)){
	        	Purchase purchase = createPurchase();
	    		//String pid = "TestPayment";
	    		Payment payment = new Payment();
	    		payment.setPaymentId(PAYMENT_ID_3);
	    		payment.setPaymentMethod(METHOD);
	    		payment.setIsSuccessful(true);
	        	payment.setPurchase(purchase);
	    		//purchaseRepository.save(purchase);
	    		//payment.setPurchase(purchase);
	    		paymentRepository.save(payment);
	    		return payment;
	        }
	        else {
	            return null;
	        }
	    });
	    lenient().when(paymentRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
	        	List<Payment> payments = new ArrayList<Payment>();
	        	Purchase purchase = createPurchase();
	    		//String pid = "TestPayment";
	    		Payment payment = new Payment();
	    		payment.setPaymentId(PAYMENT_ID);
	    		payment.setPaymentMethod(METHOD);
	    		payment.setIsSuccessful(true);
	        	payment.setPurchase(purchase);
	    		//purchaseRepository.save(purchase);
	    		//payment.setPurchase(purchase);
	    		paymentRepository.save(payment);
	    		payments.add(payment);
	        	
	            return payments;
	        
	    });
	    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(paymentRepository.save(any(Payment.class))).thenAnswer(returnParameterAsAnswer);
		//lenient().when(eventDao.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
		//lenient().when(registrationDao.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
	}
	@Test
	public void testCreatePayment() {
		String error = null;
		//assertEquals(0, service.getAllPayments().size());
		Payment payment = null;
		Purchase purchase = createPurchase();
		try {
			payment = service.createPayment(PAYMENT_ID, METHOD, purchase, true);
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, null);
	}
	@Test
	public void testCreatePaymentWithoutMethod() {
		String error = null;
		Payment payment = null;
		Purchase purchase = createPurchase();
		try {
			payment = service.createPayment(PAYMENT_ID, null, purchase, true);
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals("provide valid method", error);
	}
	@Test
	public void testCreatePaymentWithoutPurchase() {
		String error = null;
		Payment payment = null;
		Purchase purchase = null;
		try {
			payment = service.createPayment(PAYMENT_ID, METHOD, purchase, true);
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals("provide valid purchase", error);
	}
	@Test
	public void testCreatePaymentWithoutId() {
		String error = null;
		Payment payment = null;
		Purchase purchase = createPurchase();
		try {
			payment = service.createPayment("", METHOD, purchase, true);
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals("provide valid id", error);
	}
	@Test
	public void testGetPayment() {
		try {
			service.getPayment(PAYMENT_ID);
		} catch (PaymentException e) {
			fail();
		}
	}
	@Test
	public void testGetPaymentWithNotExistID() {
		String error = null;
		try {
			service.getPayment(PAYMENT_ID_2);
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "not exist payment");
	}
	@Test
	public void testGetPaymentWithEmptyID() {
		String error = null;
		try {
			service.getPayment("");
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "provide vaild id");
	}
	@Test
	public void testGetPaymentWithNullID() {
		String error = null;
		try {
			service.getPayment(null);
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "provide vaild id");
	}
	@Test
	public void testGetAllPayments() {
		int size = service.getAllPayments().size();
		assertEquals(size, 1);
	}
	@Test
	public void testDeletePayments() {
		try {
			service.deletePayment(PAYMENT_ID);
		} catch (PaymentException e) {
			fail();
		}
	}
	@Test
	public void testUnsuccessfulDeletePayments() {
		String error = null;
		try {
			service.deletePayment(PAYMENT_ID_3);
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals("unable to delete", error);
	}
	@Test
	public void testDeletePaymentsWithEmptyID() {
		String error = null;
		try {
			service.deletePayment("");
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "provide vaild id");
	}
	@Test
	public void testDeletePaymentsWithNullID() {
		String error = null;
		try {
			service.deletePayment(null);
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "provide vaild id");
	}
	@Test
	public void testDeletePaymentsWithNotExistingPayment() {
		String error = null;
		try {
			service.deletePayment(PAYMENT_ID_2);
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "not exist payment");
	}
	@Test
	public void testUpdatePayment() {
		PaymentMethod METHOD2 = PaymentMethod.CreditCard;
		try {
			service.updatePaymentMethod(PAYMENT_ID, METHOD2);
		}catch (PaymentException e) {
			fail();
		}
	}
	@Test
	public void testUpdatePaymentWithEmptyID() {
		String error = null;
		PaymentMethod METHOD2 = PaymentMethod.CreditCard;
		try {
			service.updatePaymentMethod("", METHOD2);
		}catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "provide vaild id");
	}
	@Test
	public void testUpdatePaymentWithNullID() {
		String error = null;
		PaymentMethod METHOD2 = PaymentMethod.CreditCard;
		try {
			service.updatePaymentMethod(null, METHOD2);
		}catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "provide vaild id");
	}
	@Test
	public void testUpdatePaymentWithSameMethod() {
		String error = null;
		//PaymentMethod METHOD2 = PaymentMethod.CreditCard;
		try {
			service.updatePaymentMethod(PAYMENT_ID, METHOD);
		}catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "give a new method");
	}
	@Test
	public void testUpdatePaymentWithNotExistingPayment() {
		String error = null;
		PaymentMethod METHOD2 = PaymentMethod.CreditCard;
		try {
			service.updatePaymentMethod(PAYMENT_ID_2, METHOD2);
		}catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "not exist payment");
	}
	/*@Test
	public void testCreatePaymentWithExistingId() {
		String error = null;
		Payment payment = null;
		Purchase purchase = createPurchase();
		try {
			payment = service.createPayment(PAYMENT_ID, METHOD, purchase);
		} catch (PaymentException e) {
			fail();
		}
		try {
			payment = service.createPayment(PAYMENT_ID, METHOD, purchase);
		} catch (PaymentException e) {
			error = e.getMessage();
		}
		assertEquals("payment already exist", error);
	}*/
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
