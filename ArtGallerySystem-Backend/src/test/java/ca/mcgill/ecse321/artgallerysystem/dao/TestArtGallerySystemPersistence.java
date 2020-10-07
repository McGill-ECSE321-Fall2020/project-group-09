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
	//@Autowired
	//private ArtistRepository artistRepository;
	@Autowired
	private ArtPieceRepository artpieceRepository;
	//@Autowired
	//private CustomerRepository customerRepository;
	@Autowired
	private DeliveryRepository deliveryRepository;
	@Autowired
	private PurchaseRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ArtGallerySystemUserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private ArtGallerySystemRepository artGallerySystemManagerRepository;
	@AfterEach
	public void clearDatabase() {
		addressRepository.deleteAll();
		artpieceRepository.deleteAll();
		deliveryRepository.deleteAll();
		orderRepository.deleteAll();
		paymentRepository.deleteAll();
		userRepository.deleteAll();
		userRoleRepository.deleteAll();
		artGallerySystemManagerRepository.deleteAll();
	}


	//Test System
	@Test
	public void testPersistAndLoadSystem(){

	}




	//Test User
	@Test
	public void testPersistAndLoadUser() {
		String name = "TestUser";
		// First example for object save/load
		ArtGallerySystemUser user = new ArtGallerySystemUser();
		// First example for attribute save/load
		user.setName(name);
		userRepository.save(user);
		user = null;

		user = userRepository.findArtGallerySystemUserByName(name);
		assertNotNull(user,"failed adding user to repository");
		assertEquals(name, user.getName());

	}


	//Test Address
	@Test
	public void testPersistAndLoadAddress(){
		String location = "TestAddress";
		Address address = new Address();
		address.setAddressId(location);
		addressRepository.save(address);
		address = null;

		address = addressRepository.findAddressByAddressId(location);
		assertNotNull(address,"failed adding address to repository");
		assertEquals(location,address.getAddressId());
	}

	//Test Artist
	@Test
	public void testPersistAndLoadArtisit() {
		String aid = "TestArtist";
		Artist artist = new Artist();
		artist.setUserRoleId(aid);
		userRoleRepository.save(artist);
		artist = null;

		artist = (Artist) userRoleRepository.findUserRoleByUserRoleId(aid);
		assertNotNull(artist,"failed adding address to repository");
		assertEquals(aid,artist.getUserRoleId());
	}

	//Test Customer
	@Test
	public void testPersistAndLoadCustomer(){
		String cid = "TestCustomer";
		Customer customer = new Customer();
		customer.setUserRoleId(cid);
		userRoleRepository.save(customer);
		customer = null;

		customer = (Customer) userRoleRepository.findUserRoleByUserRoleId(cid);
		assertNotNull(customer,"failed adding customer to repository");
		assertEquals(cid,customer.getUserRoleId());
	}

	//Test Artpiece
	@Test
	public void testPersistAndLoadArtPiece(){
		String apid = "TestArtPiece";
		ArtPiece artPiece = new ArtPiece();
		artPiece. setArtPieceId(apid);
		artpieceRepository.save(artPiece);
		artPiece = null;

		artPiece = artpieceRepository.findArtPieceByArtPieceId(apid);
		assertNotNull(artPiece,"failed adding artPiece to repository");
		assertEquals(apid,artPiece.getArtPieceId());
	}

	//Test Order
	@Test
	public void testPersistAndLoadOrder(){
		String oid = "TestOrder";
		Purchase order = new Purchase();
		order.setOrderId(oid);
		orderRepository.save(order);
		order = null;

		order = orderRepository.findPurchaseByOrderId(oid);
		assertNotNull(order,"failed adding order to repository");
		assertEquals(oid,order.getOrderId());
	}

	//TestPayment
	@Test
	public void testPersistAndLoadPayment(){
		String pid = "TestPayment";
		Payment payment = new Payment();
		payment.setPaymentId(pid);
		paymentRepository.save(payment);
		payment = null;

		payment = paymentRepository.findPaymentByPaymentId(pid);
		assertNotNull(payment,"failed adding payment to repository");
		assertEquals(pid,payment.getPaymentId());
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

		parcelDelivery = deliveryRepository.findDeliveryByDeliveryId(pdid);
		inStorePickUp = deliveryRepository.findDeliveryByDeliveryId(isid);
		assertNotNull(parcelDelivery,"failed adding parcel delivery to repository");
		assertEquals(pdid,parcelDelivery.getDeliveryId());
		assertNotNull(inStorePickUp,"failed adding instore pick up to repository");
		assertEquals(isid,inStorePickUp.getDeliveryId());
	}

}
