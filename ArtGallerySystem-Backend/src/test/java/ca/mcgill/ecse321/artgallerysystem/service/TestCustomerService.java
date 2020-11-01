package ca.mcgill.ecse321.artgallerysystem.service;



import ca.mcgill.ecse321.artgallerysystem.dao.CustomerRepository;

import ca.mcgill.ecse321.artgallerysystem.model.*;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtPieceException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.CustomerException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {
    @Mock
    private CustomerRepository customerRepository;
    private static final String CID = "Test CID";
    private static final double BALANCE = 80.00;
    private Set<Purchase> PURCHASE;
    private Set<Address> ADDRESS = createAddress();


    @InjectMocks
    private CustomerService customerService;
    @BeforeEach
    public void setMockOutput() {
        MockitoAnnotations.initMocks(this);
        lenient().when(customerRepository.findCustomerByUserRoleId(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(CID)) {
                Customer customer = new Customer();
                Set<Address> addresses = createAddress();
                customer.setAddress(addresses);
                customer.setUserRoleId(CID);
                customer.setBalance(BALANCE);
                customerRepository.save(customer);
                return customer;
            } else {
                return null;
            }
        });

        lenient().when(customerRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
            List<Customer> customers = new ArrayList<Customer>();
            Customer customer = new Customer();
            customer.setUserRoleId(CID);
            customer.setBalance(BALANCE);
            customerRepository.save(customer);
            customers.add(customer);

            return customers;

        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);

    }

    @Test
    public void testCreateCustomer(){
        Customer customer = new Customer();
        ArtGallerySystemUser user = new ArtGallerySystemUser();
        try {
            customer = customerService.createCustomer(user,CID,BALANCE,PURCHASE,ADDRESS);
        }catch (CustomerException e){
            fail();
        }
        assertNotNull(customer);
    }

    @Test void testCreateCustomerWithoutId(){
        String error = null;
        Customer customer = new Customer();
        ArtGallerySystemUser user = new ArtGallerySystemUser();
        try{
            customer = customerService.createCustomer(user,null,BALANCE,PURCHASE,ADDRESS);
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test void testCreateCustomerWithNegativeBalance(){
        String error = null;
        Customer customer = new Customer();
        ArtGallerySystemUser user = new ArtGallerySystemUser();
        try{
            customer = customerService.createCustomer(user,CID,-80,PURCHASE,ADDRESS);
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("no neg balance",error);
    }

    @Test
    public void testDelete(){
        try{
            customerService.deleteCustomer(CID);
        }catch(CustomerException e){
            fail();
        }
    }

    @Test
    public void testDeleteNotExist(){
        String error = null;
        try{
            customerService.deleteCustomer("CID2");
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("not exist customer",error);
    }

    @Test
    public void testDeleteByNullId(){
        String error = null;
        try{
            customerService.deleteCustomer(null);
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testDeleteByEmptyId(){
        String error = null;
        try{
            customerService.deleteCustomer("");
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testGetAll(){
        int size = customerService.getAllCustomers().size();
        assertEquals(size,1);
    }

    @Test
    public void testGetCustomer(){
        try{
            customerService.getCustomer(CID);
        }catch(CustomerException e){
            fail();
        }
    }

    @Test
    public void testGetCustomerNotFind(){
        String error = null;
        try{
            customerService.getCustomer("AID2");
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("not exist customer",error);
    }

    @Test
    public void testGetCustomerEmptyId(){
        String error = null;
        try{
            customerService.getCustomer("");
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testGetCustomerNullId(){
        String error = null;
        try{
            customerService.getCustomer(null);
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateCustomerBalance(){
        try{
            customerService.updateCustomerBalance(CID,50);
        }catch (CustomerException e){
            fail();
        }
    }

    @Test
    public void testUpdateCustomerSameBalance(){
        String error = null;
        try{
            customerService.updateCustomerBalance(CID,BALANCE);
        }catch (CustomerException e){
            error = e.getMessage();
        }
        assertEquals("same balance",error);
    }

    @Test
    public void testUpdateBalanceByEmptyId(){
        String error = null;
        try{
            customerService.updateCustomerBalance("",BALANCE);
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateBalanceByNullId(){
        String error = null;
        try{
            customerService.updateCustomerBalance(null,BALANCE);
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateBalanceByNotExistId(){
        String error = null;
        try{
            customerService.updateCustomerBalance("NEWCUSTOMER",BALANCE);
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("not exist customer",error);
    }

    @Test
    public void testUpdateNegativeBalance(){
        String error = null;
        try{
            customerService.updateCustomerBalance(CID,-10);
        }catch(CustomerException e){
            error = e.getMessage();
        }
        assertEquals("neg balance",error);
    }

    @Test
    public void testUpdateCustomerAddress(){
        try{
            customerService.updateCustomerAddress(CID,ADDRESS);
        } catch (CustomerException e){
            fail();
        }

    }

    @Test
    public void testUpdateAddressByEmptyId(){
        String error = null;
        try{
            customerService.updateCustomerAddress("",ADDRESS);
        } catch (CustomerException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateAddressByNullId(){
        String error = null;
        try{
            customerService.updateCustomerAddress(null,ADDRESS);
        } catch (CustomerException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateAddressByNotExistId(){
        String error = null;
        try{
            customerService.updateCustomerAddress("NEWC",ADDRESS);
        } catch (CustomerException e){
            error = e.getMessage();
        }
        assertEquals("not exist customer",error);
    }

    public Set<Address> createAddress(){

        Address address = new Address();
        address.setAddressId("aid");
        address.setCountry("CA");
        address.setCity("MTL");
        address.setProvince("QC");
        address.setName("");
        address.setStreetAddress("Sherbrooke");
        address.setPostalCode("H2X");
        address.setPhoneNumber("11111");
        Set<Address> addresses = new HashSet<Address>();
        addresses.add(address);
        return addresses;

    }

}
