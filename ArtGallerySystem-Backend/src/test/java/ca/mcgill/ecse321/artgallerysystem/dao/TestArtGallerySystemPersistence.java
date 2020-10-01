package ca.mcgill.ecse321.artgallerysystem.dao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

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
	}
	@Test
	public void testPersistAndLoadUser() {
		String name = "TestUser";
		// First example for object save/load
		User user = new User();
		// First example for attribute save/load
		user.setName(name);
		userRepository.save(user);

		user = null;

		user = userRepository.findUserByName(name);
		assertNotNull(user);
		assertEquals(name, user.getName());
	}
	

}
