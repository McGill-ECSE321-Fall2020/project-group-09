package ca.mcgill.ecse321.artgallerysystem.dao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

//import jdk.nashorn.internal.runtime.logging.DebugLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.artgallerysystem.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestArtGallerySystemPersistence {
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private ArtPieceRepository artpieceRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private DeliveryRepository deliveryRepository;
	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ArtGallerySystemUserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	private ParcelDeliveryRepository parcelDeliveryRepository;
	@Autowired
	private InStorePickUpRepository inStorePickUpRepository;
	

	/**
	 * clears the database before and after every run
	 */
	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		purchaseRepository.deleteAll();
		paymentRepository.deleteAll();
		artpieceRepository.deleteAll();
		deliveryRepository.deleteAll();
		
		
		userRepository.deleteAll();
		userRoleRepository.deleteAll();
		artGallerySystemRepository.deleteAll();
		inStorePickUpRepository.deleteAll();
		parcelDeliveryRepository.deleteAll();
		artistRepository.deleteAll();
		customerRepository.deleteAll();
		addressRepository.deleteAll();
		
	}
	public ArtPiece createArtPiece() {
		ArtGallerySystem sys = new ArtGallerySystem();
		ArtGallerySystemUser u = new ArtGallerySystemUser();
		u.setName("userTest");
		u.setArtGallerySystem(sys);
		userRepository.save(u);
		Artist artist = new Artist();
		artist.setArtGallerySystemUser(u);
		artist.setUserRoleId("id1");
		artist.setCredit(0.0);
		artistRepository.save(artist);
		Set<Artist> arts = new HashSet<Artist>();
		arts.add(artist);
		sys.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys);
		ArtPiece test = new ArtPiece();
		test.setArtPieceId("id");
		test.setAuthor("author");
		test.setDescription("des");
		test.setPrice(10.0);
		test.setDate(Date.valueOf("2020-01-01"));
		test.setArtGallerySystem(sys);
		test.setArtist(arts);
		test.setArtPieceStatus(ArtPieceStatus.Available);
		test.setName("name");
		artpieceRepository.save(test);
		return test;
	}
	@Test
	public void testPersistAndLoadArtGallerySystem() {
	   ArtGallerySystem artGallerySystem = new ArtGallerySystem();
	   String sid = "TestSys";
	   artGallerySystem.setArtGallerySystemId(sid);
	   artGallerySystemRepository.save(artGallerySystem);
	   artGallerySystem = null;
	   
	   
	   //Test Load
	   artGallerySystem = artGallerySystemRepository.findArtGallerySystemByArtGallerySystemId(sid);
	   assertNotNull(artGallerySystem,"failed adding user to repository");
	   assertEquals(sid, artGallerySystem.getArtGallerySystemId());
	   
	   //Test Delete
	   artGallerySystemRepository.deleteById(sid);
	   assertEquals(null,artGallerySystemRepository.findArtGallerySystemByArtGallerySystemId(sid));
	}
/*
	@Test
    public void testPersistAndLoadArtGallerySystem() {
        //ArtGallerySystem details
		String name = "TestUser";
		ArtGallerySystemUser user = new ArtGallerySystemUser();
		user.setName(name);
		//user.setArtGallerySystem(sys);
        //User artGallerySystem = createUser();
        
       // Artist artist = new Artist();
       // artistRepository.save(artist);
        
        
        ArtGallerySystem artGallerysystem = new ArtGallerySystem();
        artGallerysystem.setIsAccepted(isAccepted);
        //artGallerysystem.setArtist(ar);
        artGallerysystem.setUser(user);
        
        
        artGallerySystemRepository.save(artGallerySystem);
        
        //asserts if everything can be retrieved from database
        artGallerysystem = artGallerySystemRepository.findArtGallerySystemByUserNameAndAdvertisement(system.getUserName(), ad);
        assertNotNull(artGallerysystem);
        assertEquals(artGallerysystem.getUserName(), artGallerysystem.getUser().getUserName());
        assertEquals(ar.getArtistId(), artGallerySystem.getArtist().getArtistId());
        assertEquals(isAccepted, artGallerysystem.isIsAccepted());
    }
	//Test System
	*/


	//Test User
	@Test
	public void testPersistAndLoadUser() {
		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys);
		String name = "TestUser";
		ArtGallerySystemUser user = new ArtGallerySystemUser();
		user.setName(name);
		user.setArtGallerySystem(sys);
		userRepository.save(user);
		user = null;

		//Test Load
		user = userRepository.findArtGallerySystemUserByName(name);
		assertNotNull(user,"failed adding user to repository");
		assertEquals(name, user.getName());

		//Test Delete
		userRepository.deleteById(name);
		assertEquals(null,userRepository.findArtGallerySystemUserByName(name));
	}

	//Test Address
	@Test
	public void testPersistAndLoadAddress(){
		   ArtGallerySystem sys = new ArtGallerySystem();
		   sys.setArtGallerySystemId("test");
		   artGallerySystemRepository.save(sys);
		   String location = "TestAddress";
		   Address address = new Address();
		   address.setAddressId(location);
		   address.setArtGallerySystem(sys);
		   addressRepository.save(address);
		   address = null;

		   //Test Load
		   address = addressRepository.findAddressByAddressId(location);
		   assertNotNull(address,"failed adding address to repository");
		   assertEquals(location,address.getAddressId());

		   //Test Delete
		   addressRepository.deleteById(location);
		   assertEquals(null,addressRepository.findAddressByAddressId(location));
		
	}

	//Test Artist
	@Test
	public void testPersistAndLoadArtisit() {
		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys);
		ArtGallerySystemUser u = new ArtGallerySystemUser();
		u.setName("userTest");
		u.setArtGallerySystem(sys);
		userRepository.save(u);
		Artist artist = new Artist();
		artist.setArtGallerySystemUser(u);
		artist.setUserRoleId("id1");
		artist.setCredit(0.0);
		artist.setArtPiece(null);
		artistRepository.save(artist);
		//Test Load
		artist = artistRepository.findArtistByUserRoleId("id1");
		assertNotNull(artist,"failed adding address to repository");
		assertEquals("id1",artist.getUserRoleId());
		//Test Delete
		artistRepository.deleteById("id1");
		assertEquals(null,artistRepository.findArtistByUserRoleId("id1"));
		
		
	}

	//Test Customer
	@Test
	public void testPersistAndLoadCustomer(){
		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys);
		ArtGallerySystemUser u = new ArtGallerySystemUser();
		u.setName("userTest");
		u.setArtGallerySystem(sys);
		userRepository.save(u);
		Customer customer = new Customer();
		customer.setArtGallerySystemUser(u);
		customer.setUserRoleId("id1");
		customer.setBalance(0.0);
		customer.setPurchase(null);
		customerRepository.save(customer);
		//Test Load
		customer = customerRepository.findCustomerByUserRoleId("id1");
		assertNotNull(customer,"failed adding address to repository");
	    assertEquals("id1",customer.getUserRoleId());
		//Test Delete
		customerRepository.deleteById("id1");
		assertEquals(null,customerRepository.findCustomerByUserRoleId("id1"));

	}
	@Test
	public void testPersistAndLoadArtPiece() {
		ArtGallerySystem sys2 = new ArtGallerySystem();
		sys2.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys2);
		ArtPiece test = new ArtPiece();
		test.setArtGallerySystem(sys2);
		test.setArtPieceId("123");
		test.setArtPieceStatus(ArtPieceStatus.Available);
		test.setAuthor("Angelina");
		test.setDate(Date.valueOf("2020-10-08"));
		test.setDescription("new");
		test.setName("Angelina's work");
		test.setPrice(0);
		artpieceRepository.save(test);
		//Test Load
		test = artpieceRepository.findArtPieceByArtPieceId("123");
		assertNotNull(test, "failed adding art piece to repo.");
		assertEquals("123",test.getArtPieceId());
		//Test Delete
		artpieceRepository.deleteById("123");
		assertEquals(null,artpieceRepository.findArtPieceByArtPieceId("123"));
		
	}
	//Test Artpiece
	/*@Test May contains some useful methods, but it is not working
	public void testPersistAndLoadArtPiece(){
		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys);
		String oid = "TestOrder1";
		Purchase purchase = new Purchase();
		purchase.setOrderId(oid);
		purchase.setArtGallerySystem(sys);
		ArtGallerySystemUser u = new ArtGallerySystemUser();
		u.setName("userTest1");
		u.setArtGallerySystem(sys);
		userRepository.save(u);
		//UserRole userole = new UserRole();
		//userole.setArtGallerySystemUser(u);
		//userole.setUserRoleId("id2");
		//userRoleRepository.save(userole);
		Customer customer = new Customer();
		ArtGallerySystemUser u1 = new ArtGallerySystemUser();
		u1.setName("userTest2");
		u1.setArtGallerySystem(sys);
		userRepository.save(u1);
		customer.setArtGallerySystemUser(u1);
		customer.setUserRoleId("id4");
		customer.setBalance(0.0);
		customerRepository.save(customer);
		purchase.setDate(Date.valueOf("2020-01-01"));
		purchase.setOrderStatus(OrderStatus.Successful);
		purchase.setCustomer(customer);
		Artist artist = new Artist();
		artist.setArtGallerySystemUser(u);
		artist.setUserRoleId("id3");
		artist.setCredit(0.0);
		artistRepository.save(artist);
		Set<Artist> arts = new HashSet<Artist>();
		arts.add(artist);
		ArtPiece test = new ArtPiece();
		test.setArtPieceId("456");
		test.setAuthor("author");
		test.setDescription("des");
		test.setPrice(10.0);
		test.setDate(Date.valueOf("2020-01-01"));
		test.setArtGallerySystem(sys);
		test.setArtist(arts);
		test.setArtPieceStatus(ArtPieceStatus.Available);
		test.setName("name");
		test.setPurchase(purchase);
		purchase.setArtPiece(test);
		purchaseRepository.save(purchase);
		artpieceRepository.save(test);
		
		
		//test.setArtist(arts);
		
		//artpieceRepository.save(test);
		
		
		
		test = null;

		//Test Load
		test = artpieceRepository.findArtPieceByArtPieceId("456");
		assertNotNull(test,"failed adding art piece to repository");
		assertEquals("456",test.getArtPieceId());

		//Test Delete
		artpieceRepository.deleteById("456");
		assertEquals(null,artpieceRepository.findArtPieceByArtPieceId("456"));
	}*/

	//Test Purchase
	@Test
	public void testPersistAndLoadPurchase(){
		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys);
		String oid = "TestOrder";
		Purchase purchase = new Purchase();
		purchase.setOrderId(oid);
		purchase.setArtGallerySystem(sys);
		ArtGallerySystemUser u = new ArtGallerySystemUser();
		u.setName("userTest");
		u.setArtGallerySystem(sys);
		userRepository.save(u);
		Artist artist = new Artist();
		artist.setArtGallerySystemUser(u);
		artist.setUserRoleId("id1");
		artist.setCredit(0.0);
		artistRepository.save(artist);
		Set<Artist> arts = new HashSet<Artist>();
		arts.add(artist);
		sys.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys);
		ArtPiece test = new ArtPiece();
		test.setArtPieceId("id");
		test.setAuthor("author");
		test.setDescription("des");
		test.setPrice(10.0);
		test.setDate(Date.valueOf("2020-01-01"));
		test.setArtGallerySystem(sys);
		test.setArtist(arts);
		test.setArtPieceStatus(ArtPieceStatus.Available);
		test.setName("name");
		artpieceRepository.save(test);
		purchase.setArtPiece(test);
		Customer customer = new Customer();
		ArtGallerySystemUser u1 = new ArtGallerySystemUser();
		u1.setName("userTest");
		u1.setArtGallerySystem(sys);
		userRepository.save(u1);
		customer.setArtGallerySystemUser(u1);
		customer.setUserRoleId("id2");
		customer.setBalance(0.0);
		customerRepository.save(customer);
		purchase.setDate(Date.valueOf("2020-01-01"));
		purchase.setOrderStatus(OrderStatus.Successful);
		purchase.setCustomer(customer);
		purchaseRepository.save(purchase);
		purchase = null;

		//Test Load
		purchase = purchaseRepository.findPurchaseByOrderId(oid);
		assertNotNull(purchase,"failed adding order to repository");
		assertEquals(oid,purchase.getOrderId());

		//Test Delete
		purchaseRepository.deleteById(oid);
		assertEquals(null,purchaseRepository.findPurchaseByOrderId(oid));
	}

	//Test Payment
	@Test
	public void testPersistAndLoadPayment(){
		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys);
		String oid = "TestOrder1";
		Purchase purchase = new Purchase();
		purchase.setOrderId(oid);
		purchase.setArtGallerySystem(sys);
		ArtGallerySystemUser u = new ArtGallerySystemUser();
		u.setName("userTest");
		u.setArtGallerySystem(sys);
		userRepository.save(u);
		Artist artist = new Artist();
		artist.setArtGallerySystemUser(u);
		artist.setUserRoleId("id1");
		artist.setCredit(0.0);
		artistRepository.save(artist);
		Set<Artist> arts = new HashSet<Artist>();
		arts.add(artist);
		ArtPiece test = new ArtPiece();
		test.setArtPieceId("id");
		test.setAuthor("author");
		test.setDescription("des");
		test.setPrice(10.0);
		test.setDate(Date.valueOf("2020-01-01"));
		test.setArtGallerySystem(sys);
		test.setArtist(arts);
		test.setArtPieceStatus(ArtPieceStatus.Available);
		test.setName("name");
		artpieceRepository.save(test);
		purchase.setArtPiece(test);
		Customer customer = new Customer();
		ArtGallerySystemUser u1 = new ArtGallerySystemUser();
		u1.setName("userTest");
		u1.setArtGallerySystem(sys);
		userRepository.save(u1);
		customer.setArtGallerySystemUser(u1);
		customer.setUserRoleId("id2");
		customer.setBalance(0.0);
		customerRepository.save(customer);
		purchase.setDate(Date.valueOf("2020-01-01"));
		purchase.setOrderStatus(OrderStatus.Successful);
		purchase.setCustomer(customer);
		/*Useful for delivery, just ignore
		 * String location = "TestAddress";
		Address address = new Address();
		address.setAddressId(location);
		address.setArtGallerySystem(sys);
		addressRepository.save(address);
		String pdid = "TestParcel";
		ParcelDelivery parcelDelivery = new ParcelDelivery();
		parcelDelivery.setDeliveryId(pdid);
		parcelDelivery.setCarrier("n");
		parcelDelivery.setDeliveryAddress(address);
		parcelDelivery.setParcelDeliveryStatus(ParcelDeliveryStatus.Delivered);
		parcelDelivery.setTrackingNumber("123");
		purchase.setDelivery(parcelDelivery);
		parcelDelivery.setPurchase(purchase);
		parcelDeliveryRepository.save(parcelDelivery);
		purchase.setDelivery(parcelDelivery);*/
		String pid = "TestPayment";
		Payment payment = new Payment();
		payment.setPaymentId(pid);
		purchaseRepository.save(purchase);
		payment.setPurchase(purchase);
		paymentRepository.save(payment);
		payment = null;

		//Test Load
		payment = paymentRepository.findPaymentByPaymentId(pid);
		assertNotNull(payment,"failed adding payment to repository");
		assertEquals(pid,payment.getPaymentId());

		//Test Delete
		paymentRepository.deleteById(pid);
		assertEquals(null,paymentRepository.findPaymentByPaymentId(pid));
	}


	//Test Delivery
	/*@Test Can be removed, just ignore, might help
	public void testPersistAndLoadDelivery(){

		String pdid = "TestParcel";
		String isid = "TestPickUp";

		Delivery parcelDelivery = new ParcelDelivery();
		Delivery inStorePickUp = new InStorePickUp();


		parcelDelivery.setDeliveryId(pdid);
		inStorePickUp.setDeliveryId(isid);
		deliveryRepository.save(parcelDelivery);
		deliveryRepository.save(inStorePickUp);
		parcelDelivery = null;
		inStorePickUp = null;

		parcelDelivery = deliveryRepository.findDeliveryByDeliveryId(pdid);
		inStorePickUp = deliveryRepository.findDeliveryByDeliveryId(isid);
		assertNotNull(parcelDelivery,"failed adding parcel delivery to repository");
		assertEquals(pdid,parcelDelivery.getDeliveryId());
		assertNotNull(inStorePickUp,"failed adding instore pick up to repository");
		assertEquals(isid,inStorePickUp.getDeliveryId());
	}*/
	@Test
	public void testPersistAndLoadParcelDelivery(){//bug: Null pointer exception when purchase connects to delivery
		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys); // save sys before purchase.setArtGallerySystem
		String oid = "TestOrder";
		Purchase purchase = new Purchase();
		purchase.setOrderId(oid);
		purchase.setArtGallerySystem(sys);
		ArtGallerySystemUser u = new ArtGallerySystemUser();
		u.setName("userTest");
		u.setArtGallerySystem(sys);
		userRepository.save(u);
		Artist artist = new Artist();
		artist.setArtGallerySystemUser(u);
		artist.setUserRoleId("id1");
		artist.setCredit(0.0);
		artistRepository.save(artist);
		Set<Artist> arts = new HashSet<Artist>();
		arts.add(artist);
/*		sys.setArtGallerySystemId("test");*/
/*		artGallerySystemRepository.save(sys);*/
		ArtPiece test = new ArtPiece();
		test.setArtPieceId("id");
		test.setAuthor("author");
		test.setDescription("des");
		test.setPrice(10.0);
		test.setDate(Date.valueOf("2020-01-01"));
		test.setArtGallerySystem(sys);
		test.setArtist(arts);
		test.setArtPieceStatus(ArtPieceStatus.Available);
		test.setName("name");
		artpieceRepository.save(test);
		purchase.setArtPiece(test);
		Customer customer = new Customer();
		ArtGallerySystemUser u1 = new ArtGallerySystemUser();
		u1.setName("userTest");
		u1.setArtGallerySystem(sys);
		userRepository.save(u1);
		customer.setArtGallerySystemUser(u1);
		customer.setUserRoleId("id2");
		customer.setBalance(0.0);
		customerRepository.save(customer);
		purchase.setDate(Date.valueOf("2020-01-01"));
		purchase.setOrderStatus(OrderStatus.Successful);
		purchase.setCustomer(customer);
		   String location = "TestAddress1";
		   Address address = new Address();
		   address.setAddressId(location);
		   address.setCity("montreal");
		   address.setCountry("canada");
		   address.setName("no");
		   address.setPhoneNumber("124");
		   address.setPostalCode("H");
		   address.setProvince("qc");
		   address.setStreetAddress("qwer");
		   address.setArtGallerySystem(sys);
		   addressRepository.save(address);
		String pdid = "TestParcel";
		ParcelDelivery parcelDelivery = new ParcelDelivery();
		parcelDelivery.setDeliveryId(pdid);
		parcelDelivery.setCarrier("n");
		parcelDelivery.setDeliveryAddress(address);
		parcelDelivery.setParcelDeliveryStatus(ParcelDeliveryStatus.Delivered);
		parcelDelivery.setTrackingNumber("123");
		// parcelDelivery.setDeliveryAddress(address); // repeated set (575)
		purchaseRepository.save(purchase); // save parent
		parcelDelivery.setPurchase(purchase); // child.setParent
		parcelDeliveryRepository.save(parcelDelivery); // save child -> null value in column "store_address_address_id"??? Failing row contains (ParcelDelivery, TestParcel, null, null, n, 2, 123, TestOrder, null, TestAddress1).
			// temporarily resolved by dropping not-null in database
		purchase.setDelivery(parcelDelivery); // parent.saveChild
		String pid = "TestPayment";
		Payment payment = new Payment();
		payment.setPaymentId(pid);
		payment.setPaymentMethod(PaymentMethod.Balance);
		payment.setIsSuccessful(true);
		payment.setPurchase(purchase);
		paymentRepository.save(payment);
		Set<Payment> payments = new HashSet<Payment>();
		payments.add(payment);
		purchase.setPayment(payments);
		test.setPurchase(purchase);
		Set<Purchase> purchases = new HashSet<Purchase>();
		purchases.add(purchase);
		customer.setPurchase(purchases);
		paymentRepository.save(payment);
		purchaseRepository.save(purchase);
		parcelDeliveryRepository.save(parcelDelivery);
		parcelDelivery = null;
		parcelDelivery = parcelDeliveryRepository.findParcelDeliveryByDeliveryId(pdid);
		assertNotNull(parcelDelivery,"failed adding parcel delivery to repository");
		assertEquals(pdid,parcelDelivery.getDeliveryId());
		
	}
	@Test
	public void testPersistAndLoadInStorePickUp(){

		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("test");
		artGallerySystemRepository.save(sys); // save sys before purchase.setArtGallerySystem
		String oid = "TestOrder";
		Purchase purchase = new Purchase();
		purchase.setOrderId(oid);
		purchase.setArtGallerySystem(sys);
		ArtGallerySystemUser u = new ArtGallerySystemUser();
		u.setName("userTest");
		u.setArtGallerySystem(sys);
		userRepository.save(u);
		Artist artist = new Artist();
		artist.setArtGallerySystemUser(u);
		artist.setUserRoleId("id1");
		artist.setCredit(0.0);
		artistRepository.save(artist);
		Set<Artist> arts = new HashSet<Artist>();
		arts.add(artist);
		/*		sys.setArtGallerySystemId("test");*/
		/*		artGallerySystemRepository.save(sys);*/
		ArtPiece test = new ArtPiece();
		test.setArtPieceId("id");
		test.setAuthor("author");
		test.setDescription("des");
		test.setPrice(10.0);
		test.setDate(Date.valueOf("2020-01-01"));
		test.setArtGallerySystem(sys);
		test.setArtist(arts);
		test.setArtPieceStatus(ArtPieceStatus.Available);
		test.setName("name");
		artpieceRepository.save(test);
		purchase.setArtPiece(test);
		Customer customer = new Customer();
		ArtGallerySystemUser u1 = new ArtGallerySystemUser();
		u1.setName("userTest");
		u1.setArtGallerySystem(sys);
		userRepository.save(u1);
		customer.setArtGallerySystemUser(u1);
		customer.setUserRoleId("id2");
		customer.setBalance(0.0);
		customerRepository.save(customer);
		purchase.setDate(Date.valueOf("2020-01-01"));
		purchase.setOrderStatus(OrderStatus.Successful);
		purchase.setCustomer(customer);
		String location = "TestAddress1";
		Address address = new Address();
		address.setAddressId(location);
		address.setCity("montreal");
		address.setCountry("canada");
		address.setName("no");
		address.setPhoneNumber("124");
		address.setPostalCode("H");
		address.setProvince("qc");
		address.setStreetAddress("qwer");
		address.setArtGallerySystem(sys);
		addressRepository.save(address);
		String pdid = "TestParcel";
		InStorePickUp inStorePickUp = new InStorePickUp();
		inStorePickUp.setDeliveryId(pdid);
		inStorePickUp.setInStorePickUpStatus(InStorePickUpStatus.PickedUp);
		inStorePickUp.setStoreAddress(address);
		inStorePickUp.setPickUpReferenceNumber("number");
		//inStorePickUp.setPurchase(null); // should be not-null
		purchaseRepository.save(purchase); // save parent
		inStorePickUp.setPurchase(purchase); // child.setParent
		inStorePickUpRepository.save(inStorePickUp); // save child -> null value in column "store_address_address_id"??? Failing row contains (ParcelDelivery, TestParcel, null, null, n, 2, 123, TestOrder, null, TestAddress1).
		// temporarily resolved by dropping not-null in database
		purchase.setDelivery(inStorePickUp); // parent.saveChild
		String pid = "TestPayment";
		Payment payment = new Payment();
		payment.setPaymentId(pid);
		payment.setPaymentMethod(PaymentMethod.Balance);
		payment.setIsSuccessful(true);
		payment.setPurchase(purchase);
		paymentRepository.save(payment);
		Set<Payment> payments = new HashSet<Payment>();
		payments.add(payment);
		purchase.setPayment(payments);
		test.setPurchase(purchase);
		Set<Purchase> purchases = new HashSet<Purchase>();
		purchases.add(purchase);
		customer.setPurchase(purchases);
		paymentRepository.save(payment);
		purchaseRepository.save(purchase);
		inStorePickUpRepository.save(inStorePickUp);
		inStorePickUp = null;
		inStorePickUp = inStorePickUpRepository.findInStorePickUpByDeliveryId(pdid);
		assertNotNull(inStorePickUp,"failed adding pick up to repository");
		assertEquals(pdid,inStorePickUp.getDeliveryId());
	}

}
