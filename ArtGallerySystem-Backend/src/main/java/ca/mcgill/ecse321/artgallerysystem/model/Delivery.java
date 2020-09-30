package ca.mcgill.ecse321.artgallerysystem.model;

import javax.validation.constraints.Past;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public abstract class Delivery{
   private String deliveryId;

public void setDeliveryId(String value) {
    this.deliveryId = value;
}
@Past
public String getDeliveryId() {
    return this.deliveryId;
}
   private Order order;
   
   @OneToOne(optional=false)
   public Order getOrder() {
      return this.order;
   }
   
   public void setOrder(Order order) {
      this.order = order;
   }
   
   }
