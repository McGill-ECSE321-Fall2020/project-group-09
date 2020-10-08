package ca.mcgill.ecse321.artgallerysystem.dao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import jdk.nashorn.internal.runtime.logging.DebugLogger;
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

	/**
	 * clears the database before and after every run
	 */
	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		addressRepository.deleteAll();
		artpieceRepository.deleteAll();
		deliveryRepository.deleteAll();
		purchaseRepository.deleteAll();
		paymentRepository.deleteAll();
		userRepository.deleteAll();
		userRoleRepository.deleteAll();
		artGallerySystemRepository.deleteAll();
	}


	//Test System
	@Test
	public void testPersistAndLoadSystem(){
		//System details

	}


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
		assertEquals(null,userRepository.findById(name));
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
		userRepository.deleteById(location);
		assertEquals(null,userRepository.findById(location));
	}

	//Test Artist
	@Test
	public void testPersistAndLoadArtisit() {
		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("sysTest");
		ArtGallerySystemUser u = new ArtGallerySystemUser();
		u.setName("userTest");
	}

	//Test Customer
	@Test
	public void testPersistAndLoadCustomer(){

	}


	//Test Artpiece
	@Test
	public void testPersistAndLoadArtPiece(){
		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("sysTest");
		Purchase purchase = new Purchase();
		purchase.setOrderId("purTest");
		purchase.setArtGallerySystem(sys);
		artGallerySystemRepository.save(sys);
		String apid = "TestArtPiece";
		ArtPiece artPiece = new ArtPiece();
		artPiece.setArtPieceId(apid);
		artPiece.setArtGallerySystem(sys);
		artPiece.setPurchase(purchase);
		artpieceRepository.save(artPiece);
		artPiece = null;

		//Test Load
		artPiece = artpieceRepository.findArtPieceByArtPieceId(apid);
		assertNotNull(artPiece,"failed adding art piece to repository");
		assertEquals(apid,artPiece.getArtPieceId());

		//Test Delete
		artpieceRepository.deleteById(apid);
		assertEquals(null,artpieceRepository.findById(apid));
	}

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
		purchaseRepository.save(purchase);
		purchase = null;

		//Test Load
		purchase = purchaseRepository.findPurchaseByOrderId(oid);
		assertNotNull(purchase,"failed adding order to repository");
		assertEquals(oid,purchase.getOrderId());

		//Test Delete
		purchaseRepository.deleteById(oid);
		assertEquals(null,purchaseRepository.findById(oid));
	}

	//Test Payment
	@Test
	public void testPersistAndLoadPayment(){
		ArtGallerySystem sys = new ArtGallerySystem();
		sys.setArtGallerySystemId("sysTest");
		artGallerySystemRepository.save(sys);
		Purchase purchase = new Purchase();
		purchase.setOrderId("purTest");
		purchase.setArtGallerySystem(sys);
		String pid = "TestPayment";
		Payment payment = new Payment();
		payment.setPaymentId(pid);
		payment.setPurchase(purchase);
		paymentRepository.save(payment);
		payment = null;

		//Test Load
		payment = paymentRepository.findPaymentByPaymentId(pid);
		assertNotNull(payment,"failed adding payment to repository");
		assertEquals(pid,payment.getPaymentId());

		//Test Delete
		paymentRepository.deleteById(pid);
		assertEquals(null,paymentRepository.findById(pid));
	}


	//Test Delivery
	@Test
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

		parcelDelivery = deliveryRepository.findDeliveryById(pdid);
		inStorePickUp = deliveryRepository.findDeliveryById(isid);
		assertNotNull(parcelDelivery,"failed adding parcel delivery to repository");
		assertEquals(pdid,parcelDelivery.getDeliveryId());
		assertNotNull(inStorePickUp,"failed adding instore pick up to repository");
		assertEquals(isid,inStorePickUp.getDeliveryId());
	}

}
