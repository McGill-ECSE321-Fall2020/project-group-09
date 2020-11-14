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
    
    /**
     * Added Nov 10
     * @author Zhekai Jiang
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
    
    /**
     * Added Nov 11
     * @author Zhekai Jiang
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


    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}

