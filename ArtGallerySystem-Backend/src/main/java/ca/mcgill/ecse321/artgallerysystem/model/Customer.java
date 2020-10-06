package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Customer extends UserRole{
private double balance;
   
   public void setBalance(double value) {
this.balance = value;
    }
public double getBalance() {
return this.balance;
    }
private Set<Purchase> purchase;

@OneToMany(mappedBy="customer")
public Set<Purchase> getPurchase() {
   return this.purchase;
}

public void setPurchase(Set<Purchase> purchases) {
   this.purchase = purchases;
}

private Set<Address> address;

@OneToMany
public Set<Address> getAddress() {
   return this.address;
}

public void setAddress(Set<Address> addresss) {
   this.address = addresss;
}

}
