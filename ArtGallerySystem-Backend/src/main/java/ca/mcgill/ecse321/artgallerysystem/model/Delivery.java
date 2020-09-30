package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Id;

public abstract class Delivery {
   private String deliveryId;

public void setDeliveryId(String value) {
    this.deliveryId = value;
}
@Id
public String getDeliveryId() {
    return this.deliveryId;
}
   /**
    * <pre>
    *           0..1     1..1
    * Delivery ------------------------- Order
    *           delivery        &lt;       order
    * </pre>
    */
   private Order order;
   
   public void setOrder(Order value) {
      this.order = value;
   }
   
   public Order getOrder() {
      return this.order;
   }
   
   }
