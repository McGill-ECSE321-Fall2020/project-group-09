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
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private ArtPieceRepository artpieceRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private DeliveryRepository deliveryRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ArtGallerySystemRepository artGallerySystemRepository;
	@AfterEach
	public void clearDatabase() {
		addressRepository.deleteAll();
		artistRepository.deleteAll();
		artpieceRepository.deleteAll();
		customerRepository.deleteAll();
		deliveryRepository.deleteAll();
		orderRepository.deleteAll();
		paymentRepository.deleteAll();
		userRepository.deleteAll();
		artGallerySystemRepository.deleteAll();
	}


	//Test System
	@Test
	public void testPersistAndLoadSystem(){

	}




	//Test User
	@Test
	public void testPersistAndLoadUser() {
		String name = "TestUser";
		User user = new User();
		user.setName(name);
		userRepository.save(user);
		user = null;

		user = userRepository.findUserByName(name);
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

		address = addressRepository.findAddressById(location);
		assertNotNull(address,"failed adding address to repository");
		assertEquals(location,address.getAddressId());
	}

	//Test Artist
	@Test
	public void testPersistAndLoadArtisit() {
		String aid = "TestArtist";
		Artist artist = new Artist();
		artist.setArtistId(aid);
		artistRepository.save(artist);
		artist = null;

		artist = artistRepository.findArtistById(aid);
		assertNotNull(artist,"failed adding address to repository");
		assertEquals(aid,artist.getArtistId());
	}

	//Test Customer
	@Test
	public void testPersistAndLoadCustomer(){
		String cid = "TestCustomer";
		Customer customer = new Customer();
		customer.setCustomerId(cid);
		customerRepository.save(customer);
		customer = null;

		customer = customerRepository.findCustomerById(cid);
		assertNotNull(customer,"failed adding customer to repository");
		assertEquals(cid,customer.getCustomerId());
	}

	//Test Artpiece
	@Test
	public void testPersistAndLoadArtPiece(){
		String apid = "TestArtPiece";
		ArtPiece artPiece = new ArtPiece();
		artPiece. setArtPieceId(apid);
		artpieceRepository.save(artPiece);
		artPiece = null;

		artPiece = artpieceRepository.findArtPieceById(apid);
		assertNotNull(artPiece,"failed adding artPiece to repository");
		assertEquals(apid,artPiece.getArtPieceId());
	}

	//Test Order
	@Test
	public void testPersistAndLoadOrder(){
		String oid = "TestOrder";
		Order order = new Order();
		order.setOrderId(oid);
		orderRepository.save(order);
		order = null;

		order = orderRepository.findOrderById(oid);
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

		payment = paymentRepository.findPaymentById(pid);
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

		parcelDelivery = deliveryRepository.findDeliveryById(pdid);
		inStorePickUp = deliveryRepository.findDeliveryById(isid);
		assertNotNull(parcelDelivery,"failed adding parcel delivery to repository");
		assertEquals(pdid,parcelDelivery.getDeliveryId());
		assertNotNull(inStorePickUp,"failed adding instore pick up to repository");
		assertEquals(isid,inStorePickUp.getDeliveryId());
	}

}
