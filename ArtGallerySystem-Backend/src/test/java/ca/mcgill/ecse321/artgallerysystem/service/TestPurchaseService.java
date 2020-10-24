package ca.mcgill.ecse321.artgallerysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtPieceRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

@ExtendWith(MockitoExtension.class)
public class TestPurchaseService {
	
	@Mock
	private PurchaseRepository purchaseDao;
	@Mock
	private CustomerRepository customerDao;
	@Mock
	private ArtPieceRepository artPieceDao;
	@Mock
	private PaymentRepository paymentDao;
	
	@InjectMocks
	private PurchaseService purchaseService;
	
	private static final String PURCHASE_KEY = "TestPurchase";
	private static final String CUSTOMER_KEY = "TestCustomer";
	private static final String ARTPIECE_KEY = "TestArtPiece";
	private static final String PAYMENT_KEY = "TestPayment";
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(customerDao.findCustomerByUserRoleId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
				Customer customer = new Customer();
				customer.setUserRoleId(CUSTOMER_KEY);
				return customer;
			} else {
				return null;
			}
		});
		
		lenient().when(artPieceDao.findArtPieceByArtPieceId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ARTPIECE_KEY)) {
				ArtPiece artPiece = new ArtPiece();
				artPiece.setArtPieceId(ARTPIECE_KEY);
				return artPiece;
			} else {
				return null;
			}
		});
		
		lenient().when(purchaseDao.findPurchaseByOrderId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PURCHASE_KEY)) {
				Purchase purchase = new Purchase();
				purchase.setOrderId(PURCHASE_KEY);
				return purchase;
			} else {
				return null;
			}
		});
		
		lenient().when(paymentDao.findPaymentByPaymentId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PAYMENT_KEY)) {
				Payment payment = new Payment();
				payment.setPaymentId(PAYMENT_KEY);
				return payment;
			} else {
				return null;
			}
		});
	}
	
	@Test
	public void testCreatePurchase() {
		assertEquals(0, purchaseService.getAllPurchases().size());
		
		String id = "1";
		Date date = new Date(1);
		Customer customer = customerDao.findCustomerByUserRoleId(CUSTOMER_KEY);
		ArtPiece artPiece = artPieceDao.findArtPieceByArtPieceId(ARTPIECE_KEY);
		
		Purchase purchase = null;
		try {
			purchase = purchaseService.createPurchase(id, date, OrderStatus.Pending, artPiece, customer);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(purchase);
		assertEquals(id, purchase.getOrderId());
	}
	
	@Test
	public void testCreatePurchaseIdEmpty() {
		assertEquals(0, purchaseService.getAllPurchases().size());
		
		String id = "";
		Date date = new Date(1);
		Customer customer = customerDao.findCustomerByUserRoleId(CUSTOMER_KEY);
		ArtPiece artPiece = artPieceDao.findArtPieceByArtPieceId(ARTPIECE_KEY);
		
		Purchase purchase = null;
		String error = null;
		try {
			purchase = purchaseService.createPurchase(id, date, OrderStatus.Pending, artPiece, customer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(purchase);
		assertEquals(error, "Order ID cannot be empty!");
	}
	
	@Test
	public void testCreatePurchaseNull() {
		assertEquals(0, purchaseService.getAllPurchases().size());
		
		String id = null;
		Date date = null;
		Customer customer = null;
		ArtPiece artPiece = null;
		
		Purchase purchase = null;
		String error = null;
		try {
			purchase = purchaseService.createPurchase(id, date, OrderStatus.Pending, artPiece, customer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(purchase);
		assertEquals(error, "Order ID cannot be empty! Date cannot be empty! Customer cannot be empty! Art piece cannot be empty!");
	}
	
	@Test
	public void testAddPayment() {
		Purchase purchase = purchaseDao.findPurchaseByOrderId(PURCHASE_KEY);
		Payment payment = new Payment();
		try {
			purchase = purchaseService.addPayment(purchase, payment);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assert(purchase.getPayment().contains(payment));
	}
	
}
