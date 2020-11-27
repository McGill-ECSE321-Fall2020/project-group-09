package ca.mcgill.ecse321.artgallerysystem.controller;


import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemUserDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.CustomerDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.model.UserRole;
import ca.mcgill.ecse321.artgallerysystem.service.AddressService;
import ca.mcgill.ecse321.artgallerysystem.service.ArtGallerySystemUserService;
import ca.mcgill.ecse321.artgallerysystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * this class contains the controller methods to call perform database operations using business methods
 *
 */
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    ArtGallerySystemUserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    AddressRepository addressRepository;

    /**
     * @author Zheyan Tu
     * return all customers in the database
     * @return list of customerDTo
     */
    @GetMapping("/customerList")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = customerService.getAllCustomers();
        return toList(customerList.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    /**
     * @author Zheyan Tu
     * Find customer by userRole ID
     * @param id userRoleId
     * @return customerDTO
     */
    @GetMapping("/getCustomer/{id}")
    private CustomerDTO getCustomerById(@PathVariable("id") String id){
        return convertToDto(customerService.getCustomer(id));
    }
    
    /**
     * Added Nov 11
     * get customer by userId
     * @author Zhekai Jiang
     * @param userName userName
     * @return customerDTO
     */
    @GetMapping(value = {"/user/{name}", "/user/{name}/"})
    private CustomerDTO getCustomerByUserName(@PathVariable("name") String userName) {
    	ArtGallerySystemUser user = userService.getUser(userName);
    	for(UserRole role : user.getUserRole()) {
    		if(role instanceof Customer) {
    			return convertToDto((Customer) role);
    		}
    	}
    	throw new IllegalArgumentException("User " + userName + " does not have a customer role.");
    }

    /**
     * get addresses of a customer
     * @param id customer userRole id
     * @return List of addressDTO
     */
    @GetMapping("/getCustomerAddress/{id}")
    private List<AddressDTO> getCustomerAddress(@PathVariable("id") String id){
    	Set<Address> addresses = customerService.getCustomer(id).getAddress();
    	List<Address> add = new ArrayList<Address>();
    	for (Address addess: addresses) {
    		add.add(addess);
    	}
        return toList(add.stream().map(this::convertToDto).collect(Collectors.toList()));
       
    }

    /**
     * @author Zheyan Tu
     * Delete a customer by userRole id
     * @param id customer userRole id
     */
    @DeleteMapping("/deleteCustomer/{id}")
    private void deleteCustomerById(@PathVariable("id") String id){
        customerService.deleteCustomer(id);
    }

    /**
     * create a new customer
     * @author Zheyan Tu
     * @param id customer userRole id
     * @param userid userid
     * @param balance double Balance
     * @return customerDTO
     */
    @PostMapping(value = {"/createCustomer/{id}", "/createCustomer/{id}/"})
    public CustomerDTO createCustomer(@PathVariable("id") String id, @RequestParam("user") String userid, @RequestParam("balance") double balance){
    	ArtGallerySystemUser user = userService.getUser(userid);
        Customer customer = customerService.createCustomer(user, id, balance, new HashSet<Purchase>(), new HashSet<Address>());
        return convertToDto(customer);
    }

    /**
     * update customer balance
     * @author Zheyan Tu
     * @param id customer userRole id
     * @param balance new Balance
     * @return updated CustomerDTO
     */
    @PutMapping("/updateBalance/{id}")
    private CustomerDTO updateCustomerBalance(@PathVariable("id") String id, @RequestParam("balance") double balance){
        return convertToDto(customerService.updateCustomerBalance(id,balance));
    }

    /**
     * add an address into customer's address list
     * @param id customer userRole id
     * @param addressid new address id
     * @return updated CustomerDTO
     */
    @PutMapping("/addAddress/{id}")
    private CustomerDTO addCustomerAddress(@PathVariable("id") String id, @RequestParam("address") String addressid){
    	Set<Address> adds = customerService.getCustomer(id).getAddress();
    	Address e = addressRepository.findAddressByAddressId(addressid);
    	adds.add(e);
        return convertToDto(customerService.updateCustomerAddress(id,adds));
    }
    
    /**
     * Added Nov 11
     * Delete an address in customer's address list
     * @author Zhekai Jiang
     * @param id customer userRole id
     * @param addressId addressId
     * @return updated customerDTO
     */
    @PutMapping(value = {"/deleteAddress/{id}", "/deleteAddress/{id}/"})
    private CustomerDTO deleteCustomerAddress(@PathVariable("id") String id, @RequestParam("addressid") String addressId){
    	Address address = addressService.getAddressById(addressId);
    	Customer customer = customerService.deleteCustomerAddress(id, address);
    	return convertToDto(customer);
    }

    /**
     * convert customer to customerDTO
     * @param customer
     * @return
     */
    public CustomerDTO convertToDto(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setAddress(null);
        customerDTO.setArtGallerySystemUser(convertToDto(customer.getArtGallerySystemUser()));
        customerDTO.setBalance(customer.getBalance());
        Set<AddressDTO> adds = new HashSet<AddressDTO>();
        for (Address a: customer.getAddress()) {
        	adds.add(convertToDto(a));
        }
        customerDTO.setAddress(adds);
        customerDTO.setPurchase(null);
        customerDTO.setUserRoleId(customer.getUserRoleId());
        //BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }

    /**
     * convert user to userDTO
     * @param user
     * @return
     */
    public ArtGallerySystemUserDTO convertToDto(ArtGallerySystemUser user) {
    	ArtGallerySystemUserDTO userDTO = new ArtGallerySystemUserDTO();
    	userDTO.setName(user.getName());
    	userDTO.setEmail(user.getEmail());
    	userDTO.setPassword(user.getPassword());
    	userDTO.setAvatar(user.getAvatar());
    	//userDTO.setArtGallerySystem(user.getArtGallerySystem());
    	return userDTO;
    }

    /**
     * convert address to addressDTO
     * @param address
     * @return
     */
    public AddressDTO convertToDto(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setName(address.getName());
        addressDTO.setPhoneNumber(address.getPhoneNumber());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setProvince(address.getProvince());
        addressDTO.setStreetAddress(address.getStreetAddress());
        addressDTO.setArtGallerySystem(address.getArtGallerySystem());
        return addressDTO;
    }

    /**
     * useful helper method
     * @param <T>
     * @param iterable
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
