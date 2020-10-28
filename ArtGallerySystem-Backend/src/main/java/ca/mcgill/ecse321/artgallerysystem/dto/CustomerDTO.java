package ca.mcgill.ecse321.artgallerysystem.dto;

import java.util.Set;

import javax.persistence.OneToMany;

import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

public class CustomerDTO extends UserRoleDTO{
    private double balance;

    public void setBalance(double value) {
        this.balance = value;
    }
    public double getBalance() {
        return this.balance;
    }
    private Set<PurchaseDTO> purchase;


    public Set<PurchaseDTO> getPurchase() {
        return this.purchase;
    }

    public void setPurchase(Set<PurchaseDTO> purchases) {
        this.purchase = purchases;
    }

    private Set<AddressDTO> address;


    public Set<AddressDTO> getAddress() {
        return this.address;
    }

    public void setAddress(Set<AddressDTO> addresss) {
        this.address = addresss;
    }

}