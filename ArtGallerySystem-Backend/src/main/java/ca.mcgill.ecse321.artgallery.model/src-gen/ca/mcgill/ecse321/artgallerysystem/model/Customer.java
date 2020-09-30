package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Customer extends UserRole{
   private String customerId;

public void setCustomerId(String value) {
    this.customerId = value;
}
@Id
public String getCustomerId() {
    return this.customerId;
}
private double balance;

public void setBalance(double value) {
    this.balance = value;
}
public double getBalance() {
    return this.balance;
}
   private Set<Order> order;
   
   @OneToMany(mappedBy="customer" )
   public Set<Order> getOrder() {
      return this.order;
   }
   
   public void setOrder(Set<Order> orders) {
      this.order = orders;
   }
   
   private Set<Address> address;
   
   @OneToMany(mappedBy="customer" )
   public Set<Address> getAddress() {
      return this.address;
   }
   
   public void setAddress(Set<Address> addresss) {
      this.address = addresss;
   }
   
   }
