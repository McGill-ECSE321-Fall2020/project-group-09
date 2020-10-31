package ca.mcgill.ecse321.artgallerysystem.controller;


import ca.mcgill.ecse321.artgallerysystem.dto.ArtistDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.CustomerDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.service.ArtGallerySystemUserService;
import ca.mcgill.ecse321.artgallerysystem.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    ArtGallerySystemUserService userService;

    @GetMapping("/customerList")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = customerService.getAllCustomers();
        return toList(customerList.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/getCustomer/{id}")
    private CustomerDTO getCustomerById(@PathVariable("id") String id){
        return convertToDto(customerService.getCustomer(id));
    }

    @DeleteMapping("/deleteCustomer/{id}")
    private void deleteCustomerById(@PathVariable("id") String id){
        customerService.deleteCustomer(id);
    }
    @PostMapping(value = {"/createCustomer/{id}", "/createCustomer/{id}/"})
    public CustomerDTO createCustomer(@PathVariable("id") String id, @RequestParam("user") String userid, @RequestParam("balance") double balance){
    	ArtGallerySystemUser user = userService.getUser(userid);
        Customer customer = customerService.createCustomer(user, id, balance, null, null);
        return convertToDto(customer);
    }
    
    @PutMapping("/updateBalance/{id}")
    private CustomerDTO updateCustomerBalance(@PathVariable("id") String id, @RequestParam("balance") double balance){
        return convertToDto(customerService.updateCustomerBalance(id,balance));
    }

    @PutMapping("/updateAddress/{id}")
    private CustomerDTO updateCustomerAddress(@PathVariable("id") String id, @RequestParam("address") Set<Address> address){
        return convertToDto(customerService.updateCustomerAddress(id,address));
    }


    public CustomerDTO convertToDto(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }


    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
