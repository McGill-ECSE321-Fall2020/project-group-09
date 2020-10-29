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

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtPieceRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.DeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ParcelDeliveryRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Delivery;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDeliveryStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.model.ParcelDelivery;;

@ExtendWith(MockitoExtension.class)
public class TestParcelDeliveryService {
	
	@Mock
	private ParcelDeliveryRepository parcelDeliveryDao;
	@Mock
	private AddressRepository addressDao;
	@Mock
	private DeliveryRepository deliveryDao;
	
	@InjectMocks
	private ParcelDeliveryService parcelDeliveryService;
	private static final String PARCELDELIVERY_KEY = "TestParcelDelivery";
	private static final String ADDRESS_KEY = "TestAddress";
	private static final String DELIVERY_KEY = "TestDelivery";
	
	private ParcelDelivery parcelDelivery;
	private Address address;
	private Delivery delivery;
	private List<ParcelDelivery> allParcelDeliveries;
	

	@BeforeEach
	public void setMockOutput() {
		parcelDelivery = new ParcelDelivery();
	}
}
