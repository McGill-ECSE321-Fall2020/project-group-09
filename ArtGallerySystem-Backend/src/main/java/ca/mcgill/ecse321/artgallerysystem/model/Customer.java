package ca.mcgill.ecse321.artgallerysystem.model;

import java.util.Set;
import java.util.HashSet;

public class Customer extends UserRole {
private String customerId;

public void setCustomerId(String value) {
   this.customerId = value;
}

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

/**
 * <pre>
 *           1..1     0..*
 * Customer ------------------------- Order
 *           customer        &gt;       order
 * </pre>
 */
private Set<Order> order;

public Set<Order> getOrder() {
   if (this.order == null) {
this.order = new HashSet<Order>();
   }
   return this.order;
}

/**
 * <pre>
 *           0..1     0..*
 * Customer ------------------------- Address
 *           customer        &lt;       address
 * </pre>
 */
private Set<Address> address;

public Set<Address> getAddress() {
   if (this.address == null) {
this.address = new HashSet<Address>();
   }
   return this.address;
}

}
