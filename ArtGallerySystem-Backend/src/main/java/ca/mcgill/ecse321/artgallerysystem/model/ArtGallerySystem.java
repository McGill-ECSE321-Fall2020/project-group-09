package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class ArtGallerySystem{
   private Set<User> user;
   
   @OneToMany(mappedBy="artGallerySystem" , cascade={CascadeType.ALL})
   public Set<User> getUser() {
      return this.user;
   }
   
   public void setUser(Set<User> users) {
      this.user = users;
   }
   
   private Set<ArtPiece> artPiece;
   
   @OneToMany(mappedBy="artGallerySystem" , cascade={CascadeType.ALL})
   public Set<ArtPiece> getArtPiece() {
      return this.artPiece;
   }
   
   public void setArtPiece(Set<ArtPiece> artPieces) {
      this.artPiece = artPieces;
   }
   
   private Set<Order> order;
   
   @OneToMany(mappedBy="artGallerySystem" , cascade={CascadeType.ALL})
   public Set<Order> getOrder() {
      return this.order;
   }
   
   public void setOrder(Set<Order> orders) {
      this.order = orders;
   }
   
   private Set<Address> address;
   
   @OneToMany(mappedBy="artGallerySystem" , cascade={CascadeType.ALL})
   public Set<Address> getAddress() {
      return this.address;
   }
   
   public void setAddress(Set<Address> addresss) {
      this.address = addresss;
   }
   private double income;

   public void setIncome(String value) {
      this.income = value;
   }
   public String getIncome() {
      return this.income;
   }
   }
