package ca.mcgill.ecse321.artgallerysystem.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.model.UserRole;
import ca.mcgill.ecse321.artgallerysystem.service.exception.CustomerException;

/**
 * this class contains useful business methods to manipulate data in backend, used in controller
 * @author Zheyan Tu
 *
 */
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

    /**
     * create a new customer
     * @param user user
     * @param id customer userRole id
     * @param balance balance
     * @param purchases purchases Set
     * @param addresses address Set
     * @return
     */
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

    /**
     * get customer by customer userRole id
     * @param id customer userRole id
     * @return customer
     */
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

    /**
     * get all customers in database
     * @return List of customer
     */
    @Transactional
    public List<Customer> getAllCustomers(){
        return toList(customerRepository.findAll());

    }
    
    /**
     * Get the customer role of a user.
     * An IllegalArgumentException will be thrown if the user name is empty or the user does not exist.
     * Added Nov 10.
     * @author Zhekai Jiang
     * @param userName The name of the user.
     * @return The Customer (role) instance of the user.
     */
    @Transactional
    public Customer getCustomerByUserName(String userName) {
    	if(userName == null || userName.length() == 0) {
			throw new IllegalArgumentException("Username cannot be empty!");
		}
    	
		ArtGallerySystemUser user = userRepository.findArtGallerySystemUserByName(userName);
		if(user == null) { 
			throw new IllegalArgumentException("User with username " + userName + " does not exist.");
		}
		
		for(UserRole role : user.getUserRole()) {
			if(role instanceof Customer) {
				return (Customer) role;
			}
		}
		
		throw new IllegalArgumentException("User " + userName + " does not have a customer role.");
    }

    /**
     * delete customer by customer userRole id
     * @param id customer userRole id
     * @return
     */
    @Transactional
    public Customer deleteCustomer(String id) {
        if (id == null||id == "") {
            throw new CustomerException ("provide valid id");
        }
        Customer customer = customerRepository.findCustomerByUserRoleId(id);
        if (customer == null) {
            throw new CustomerException ("not exist customer");
        }
        // ArtGallerySystemUser user = customer.getArtGallerySystemUser();
        Customer cus = null;
        //userRepository.delete(user);
        customerRepository.deleteById(id);
        return cus;

    }

    /**
     * update customer balance
     * @param id customer userRole id
     * @param balance new balance
     * @return updated customer
     */
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

    /**
     * update customer address set
     * @param id customer userRole id
     * @param addresses address set
     * @return updated customer
     */
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
    
    /**
     * Delete an address from the address set of a customer.
     * Note that the address is NOT deleted completely from the database, as it may still be referenced by some delivery.
     * An IllegalArgumentException will be thrown if the id or address is null or empty or the customer does not exist.
     * Added Nov 11.
     * @author Zhekai Jiang
     * @param id The id of the customer role. Note that it is not the user name or address id.
     * @param address The address to be deleted.
     * @return The updated Customer instance.
     */
    @Transactional
    public Customer deleteCustomerAddress(String id, Address address) {
    	if(id == null || id.length() == 0) {
    		throw new IllegalArgumentException("Customer id cannot be empty!");
    	}
    	if(address == null) {
    		throw new IllegalArgumentException("Address cannot be empty!");
    	}
    	Customer customer = getCustomer(id);
    	if(customer.getAddress() != null) {
    		customer.getAddress().remove(address);
    	}
    	customerRepository.save(customer);
    	return customer;
    }

    /**
     * helper method
     * @param iterable
     * @param <T>
     * @return
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}

