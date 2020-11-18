package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public abstract class Delivery{
private String deliveryId;
   
   public void setDeliveryId(String value) {
this.deliveryId = value;
    }
@Id
public String getDeliveryId() {
return this.deliveryId;
    }
private Purchase purchase;

@OneToOne(optional=false)
public Purchase getPurchase() {
   return this.purchase;
}

public void setPurchase(Purchase purchase) {
   this.purchase = purchase;
}

}
