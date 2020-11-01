package ca.mcgill.ecse321.artgallerysystem.service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
/*<<<<<<< HEAD
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.CustomerException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.PaymentException;
@Service
public class CustomerService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	ArtGallerySystemUserRepository userRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
	@Transactional
	public Customer createCustomer(ArtGallerySystemUser user, String id, double balance, Set<Purchase> purchases, Set<Address> addresses) {
		if (id == null||id == "") {
			throw new CustomerException ("provide valid id");
		}
		if (balance<0) {
			throw new CustomerException ("no neg balance");
		}
		Customer customer = new Customer ();
		customer.setAddress(addresses);
		customer.setBalance(balance);
		customer.setPurchase(purchases);
		customer.setUserRoleId(id);
		customer.setArtGallerySystemUser(user);
		customerRepository.save(customer);
		return customer;
	}
	@Transactional 
	public Customer getCustomer(String id) {
		if(id == null||id == "") {
			throw new CustomerException ("provide valid id");
		}
		Customer customer = customerRepository.findCustomerByUserRoleId(id);
		if (customer == null) {
			throw new CustomerException ("not exist customer");
		}
		return customer;
	}
	@Transactional
	public List<Customer> getAllCustomers(){
		return toList(customerRepository.findAll());
		
	}
	@Transactional
	public Customer deleteCustomer(String id) {
		if (id == null||id == "") {
			throw new CustomerException ("provide vaild id");
		}
		Customer customer = customerRepository.findCustomerByUserRoleId(id);
		if (customer == null) {
			throw new CustomerException ("not exist customer");
		}
		ArtGallerySystemUser user = customer.getArtGallerySystemUser();
		Customer cus = null;
		userRepository.delete(user);
		customerRepository.deleteById(id);
		return cus;
		
	}
	@Transactional
	public Customer updateCustomerBalance (String id, double balance) {
		if (id == null||id == "") {
			throw new CustomerException ("provide vaild id");
		}
		Customer customer = customerRepository.findCustomerByUserRoleId(id);
		if (customer == null) {
			throw new CustomerException ("not exist customer");
		}
		if (balance<0) {
			throw new CustomerException ("neg balance");
		}
		if (customer.getBalance()== balance) {
			throw new CustomerException ("same balance");
		}
		customer.setBalance(balance);
		customerRepository.save(customer);
		return customer;
	}
	@Transactional
	public Customer updateCustomerAddress (String id, Set<Address> addresses) {
		if (id == null||id == "") {
			throw new CustomerException ("provide vaild id");
		}
		Customer customer = customerRepository.findCustomerByUserRoleId(id);
		if (customer == null) {
			throw new CustomerException ("not exist customer");
		}
		customer.setAddress(addresses);
		customerRepository.save(customer);
		return customer;
	}
	
	
	private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}
=======*/
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

import ca.mcgill.ecse321.artgallerysystem.service.exception.CustomerException;

@Service
public class CustomerService {
    @Autowired
    ArtGallerySystemRepository artGallerySystemRepository;
    @Autowired
    ArtGallerySystemUserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Transactional
    public Customer createCustomer(ArtGallerySystemUser user, String id, double balance, Set<Purchase> purchases, Set<Address> addresses) {
        if (id == null||id == "") {
            throw new CustomerException ("provide valid id");
        }
        if (balance<0) {
            throw new CustomerException ("no neg balance");
        }
        Customer customer = new Customer ();
        customer.setAddress(addresses);
        customer.setBalance(balance);
        customer.setPurchase(purchases);
        customer.setUserRoleId(id);
        customer.setArtGallerySystemUser(user);
        customerRepository.save(customer);
        return customer;
    }
    @Transactional
    public Customer getCustomer(String id) {
        if(id == null||id == "") {
            throw new CustomerException ("provide valid id");
        }
        Customer customer = customerRepository.findCustomerByUserRoleId(id);
        if (customer == null) {
            throw new CustomerException ("not exist customer");
        }
        return customer;
    }
    @Transactional
    public List<Customer> getAllCustomers(){
        return toList(customerRepository.findAll());

    }
    @Transactional
    public Customer deleteCustomer(String id) {
        if (id == null||id == "") {
            throw new CustomerException ("provide valid id");
        }
        Customer customer = customerRepository.findCustomerByUserRoleId(id);
        if (customer == null) {
            throw new CustomerException ("not exist customer");
        }
        ArtGallerySystemUser user = customer.getArtGallerySystemUser();
        Customer cus = null;
        //userRepository.delete(user);
        customerRepository.deleteById(id);
        return cus;

    }
    @Transactional
    public Customer updateCustomerBalance (String id, double balance) {
        if (id == null||id == "") {
            throw new CustomerException ("provide valid id");
        }
        Customer customer = customerRepository.findCustomerByUserRoleId(id);
        if (customer == null) {
            throw new CustomerException ("not exist customer");
        }
        if (balance<0) {
            throw new CustomerException ("neg balance");
        }
        if (customer.getBalance()== balance) {
            throw new CustomerException ("same balance");
        }
        customer.setBalance(balance);
        customerRepository.save(customer);
        return customer;
    }
    @Transactional
    public Customer updateCustomerAddress (String id, Set<Address> addresses) {
        if (id == null||id == "") {
            throw new CustomerException ("provide valid id");
        }
        Customer customer = customerRepository.findCustomerByUserRoleId(id);
        if (customer == null) {
            throw new CustomerException ("not exist customer");
        }
        customer.setAddress(addresses);
        customerRepository.save(customer);
        return customer;
    }


    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}

