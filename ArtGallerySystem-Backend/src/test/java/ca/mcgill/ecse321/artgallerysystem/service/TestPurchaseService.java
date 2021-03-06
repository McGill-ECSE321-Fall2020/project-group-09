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
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtPieceRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.DeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.InStorePickUp;
import ca.mcgill.ecse321.artgallerysystem.model.OrderStatus;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

/**
 * Test suite for the purchase service.
 * @author Zhekai Jiang
 */
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
	@Mock
	private DeliveryRepository deliveryDao;
	
	@InjectMocks
	private PurchaseService purchaseService;
	
	private static final String PURCHASE_KEY = "TestPurchase";
	private static final String CUSTOMER_KEY = "TestCustomer";
	private static final String ARTPIECE_KEY = "TestArtPiece";
	private static final String PAYMENT_KEY = "TestPayment";
	private static final String PARCELDELIVERY_KEY = "TestParcelDelivery";
	private static final String INSTOREPICKUP_KEY = "TestInStorePickUp";
	
	private boolean isOnePurchaseInRepository; // To test get all
	
	@BeforeEach
	public void setMockOutput() {
		
		isOnePurchaseInRepository = false;

		lenient().when(purchaseDao.findPurchaseByOrderId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PURCHASE_KEY)) {
				Purchase purchase = new Purchase();
				purchase.setOrderId(PURCHASE_KEY);
				return purchase;
			} else {
				return null;
			}
		});
		
		lenient().when(purchaseDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			if(isOnePurchaseInRepository) {
				Purchase purchase = new Purchase();
				purchase.setOrderId(PURCHASE_KEY);
				List<Purchase> allPurchases = new ArrayList<Purchase>();
				allPurchases.add(purchase);
				return allPurchases;
			} else {
				return null;
			}
			
		});
		
		lenient().when(purchaseDao.findByCustomer(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(((Customer)(invocation.getArgument(0))).getUserRoleId().equals(CUSTOMER_KEY)) {
				Customer customer = new Customer();
				customer.setUserRoleId(CUSTOMER_KEY);
				Purchase purchase = new Purchase();
				purchase.setOrderId(PURCHASE_KEY);
				purchase.setCustomer(customer);
				List<Purchase> allPurchases = new ArrayList<Purchase>();
				allPurchases.add(purchase);
				return allPurchases;
			} else {
				return null;
			}
		});
		
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
		
		lenient().when(paymentDao.findPaymentByPaymentId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PAYMENT_KEY)) {
				Payment payment = new Payment();
				payment.setPaymentId(PAYMENT_KEY);
				return payment;
			} else {
				return null;
			}
		});
		
		lenient().when(deliveryDao.findDeliveryByDeliveryId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PARCELDELIVERY_KEY)) {
				Delivery delivery = new ParcelDelivery();
				delivery.setDeliveryId(PARCELDELIVERY_KEY);
				return delivery;
			} else if(invocation.getArgument(0).equals(INSTOREPICKUP_KEY)) {
				Delivery delivery = new InStorePickUp();
				delivery.setDeliveryId(INSTOREPICKUP_KEY);
				return delivery;
			} else {
				return null;
			}
		});
	}
	
	@Test
	public void testCreatePurchase() {		
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
	public void testCreatePurchaseEmptyId() {
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
		assertEquals("Id cannot be empty!", error);
	}
	
	@Test
	public void testCreatePurchaseNull() {
		String id = null;
		Date date = null;
		OrderStatus status = null;
		Customer customer = null;
		ArtPiece artPiece = null;
		
		Purchase purchase = null;
		String error = null;
		try {
			purchase = purchaseService.createPurchase(id, date, status, artPiece, customer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(purchase);
		assertEquals("Id cannot be empty! Date cannot be empty! Status cannot be empty! Art piece cannot be empty! Customer cannot be empty!", error);
	}
	
	
	@Test
	public void testGetPurchase() {
		String id = PURCHASE_KEY;
		Purchase purchase = null;
		
		try {
			purchase = purchaseService.getPurchase(id);
		} catch(Exception e) {
			fail();
		}
		
		assertEquals(id, purchase.getOrderId());
	}
	
	@Test
	public void testGetPurchaseNullId() {
		String id = null;
		Purchase purchase = null;
		
		String error = null;
		try {
			purchase = purchaseService.getPurchase(id);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Id cannot be empty!", error);
		assertNull(purchase);
	}
	
	@Test
	public void testGetPurchaseEmptyId() {
		String id = "";
		Purchase purchase = null;
		
		String error = null;
		try {
			purchase = purchaseService.getPurchase(id);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Id cannot be empty!", error);
		assertNull(purchase);
	}
	
	@Test
	public void testGetPurchaseNonExistentId() {
		String id = "?";
		Purchase purchase = null;
		
		String error = null;
		try {
			purchase = purchaseService.getPurchase(id);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Purchase with id " + id + " does not exist.", error);
		assertNull(purchase);
	}
	
	
	@Test
	public void testGetAllPurchases() {
		isOnePurchaseInRepository = true;
		List<Purchase> purchases = null;
		try {
			purchases = purchaseService.getAllPurchases();
		} catch(Exception e) {
			fail();
		}
		
		assertNotNull(purchases);
		assertEquals(1, purchases.size());
		assertEquals(PURCHASE_KEY, purchases.get(0).getOrderId());
	}
	
	@Test
	public void testGetAllPurchasesEmpty() {
		List<Purchase> purchases = null;
		try {
			purchases = purchaseService.getAllPurchases();
		} catch(Exception e) {
			fail();
		}
		
		assertEquals(0, purchases.size());
	}
	
	@Test
	public void testDeletePurchase() {
		String id = PURCHASE_KEY;
		Purchase purchase = null;
		
		try {
			purchase = purchaseService.deletePurchase(id);
		} catch(Exception e) {
			fail();
		}
		
		assertEquals(id, purchase.getOrderId());
	}
	
	@Test
	public void testDeletePurchaseNullId() {
		String id = null;
		Purchase purchase = null;
		
		String error = null;
		try {
			purchase = purchaseService.deletePurchase(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Id cannot be empty!", error);
		assertNull(purchase);
	}
	
	@Test
	public void testDeletePurchaseEmptyId() {
		String id = "";
		Purchase purchase = null;
		
		String error = null;
		try {
			purchase = purchaseService.deletePurchase(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Id cannot be empty!", error);
		assertNull(purchase);
	}
	
	@Test
	public void testDeletePurchaseNonExistentId() {
		String id = "?";
		Purchase purchase = null;
		
		String error = null;
		try {
			purchase = purchaseService.deletePurchase(id);
		} catch(Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Purchase with id " + id + " does not exist.", error);
		assertNull(purchase);
	}
	
	
	@Test
	public void testGetPurchasesMadeByCustomer() {
		List<Purchase> purchases = null;
		String customerUserRoleId = CUSTOMER_KEY;
		
		try {
			Customer customer = customerDao.findCustomerByUserRoleId(CUSTOMER_KEY);
			purchases = purchaseService.getPurchasesMadeByCustomer(customer);
		} catch(Exception e) {
			fail();
		}
		
		assertNotNull(purchases);
		assertEquals(purchases.size(), 1);
		assertEquals(purchases.get(0).getCustomer().getUserRoleId(), customerUserRoleId);
	}
	
	@Test
	public void testGetPurchaseMadeByCustomerNull() {
		List<Purchase> purchases = null;
		Customer customer = null;
		String error = "";
		try {
			purchases = purchaseService.getPurchasesMadeByCustomer(customer);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals("Customer cannot be empty!", error);
		assertNull(purchases);
	}
	
	@Test
	public void testUpdatePurchaseStatus() {
		Purchase purchase = null;
		String id = PURCHASE_KEY;
		OrderStatus status = OrderStatus.Successful;
		try {
			purchase = purchaseService.updatePurchaseStatus(id, status);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(purchase);
		assertEquals(id, purchase.getOrderId());
		assertEquals(status, purchase.getOrderStatus());
	}
	
	@Test
	public void testUpdatePurchaseStatusNull() {
		Purchase purchase = null;
		String id = null;
		OrderStatus status = null;
		String error = null;
		try {
			purchase = purchaseService.updatePurchaseStatus(id, status);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(purchase);
		assertNotNull(error);
		assertEquals("Id cannot be empty! Status cannot be empty!", error);
	}
	
	@Test
	public void testSetParcelDelivery() {
		String id = PURCHASE_KEY;
		Purchase purchase = null;
		String deliveryId = PARCELDELIVERY_KEY;
		Delivery delivery = null;
		try {
			delivery = deliveryDao.findDeliveryByDeliveryId(deliveryId);
			purchase = purchaseService.setDelivery(id, delivery);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(delivery);
		assert(delivery instanceof ParcelDelivery);
		assertEquals(deliveryId, delivery.getDeliveryId());
		assertNotNull(purchase);
		assertEquals(id, purchase.getOrderId());
		assertNotNull(purchase.getDelivery());
		assertEquals(delivery, purchase.getDelivery());
	}
	
	@Test
	public void testSetInStorePickUp() {
		String id = PURCHASE_KEY;
		Purchase purchase = null;
		String deliveryId = INSTOREPICKUP_KEY;
		Delivery delivery = null;
		try {
			delivery = deliveryDao.findDeliveryByDeliveryId(deliveryId);
			purchase = purchaseService.setDelivery(id, delivery);
		} catch(Exception e) {
			fail();
		}
		assertNotNull(delivery);
		assert(delivery instanceof InStorePickUp);
		assertEquals(deliveryId, delivery.getDeliveryId());
		assertNotNull(purchase);
		assertEquals(id, purchase.getOrderId());
		assertNotNull(purchase.getDelivery());
		assertEquals(delivery, purchase.getDelivery());
	}
	
	@Test
	public void testSetDeliveryNull() {
		String id = null;
		Purchase purchase = null;
		Delivery delivery = null;
		String error = "";
		try {
			purchase = purchaseService.setDelivery(id, delivery);
		} catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals("Id cannot be empty! Delivery cannot be empty!", error);
		assertNull(purchase);
	}
	
	@Test
	public void testAddPayment() {
		String id = PURCHASE_KEY;
		String paymentId = PAYMENT_KEY;
		Payment payment = null;
		Purchase purchase = null;
		try {
			payment = paymentDao.findPaymentByPaymentId(paymentId);
			purchase = purchaseService.addPayment(id, payment);
		} catch (Exception e) {
			fail();
		}
		assertNotNull(payment);
		assertEquals(paymentId, payment.getPaymentId());
		assert(purchase.getPayment().contains(payment));
	}
	
	@Test
	public void testAddPaymentNull() {
		String id = null;
		Payment payment = null;
		String error = null;
		Purchase purchase = null;
		try {
			purchase = purchaseService.addPayment(id, payment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(error);
		assertEquals("Id cannot be empty! Payment cannot be empty!", error);
		assertNull(purchase);
	}
	
}
