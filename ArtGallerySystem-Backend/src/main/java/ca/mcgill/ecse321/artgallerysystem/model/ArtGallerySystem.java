package ca.mcgill.ecse321.artgallerysystem.model;

import java.util.Set;
import java.util.HashSet;

public class ArtGallerySystem {
/**
 * <pre>
 *           1..1     0..*
 * ArtGallerySystem ------------------------- User
 *           artGallerySystem        &gt;       user
 * </pre>
 */
private Set<User> user;

public Set<User> getUser() {
   if (this.user == null) {
this.user = new HashSet<User>();
   }
   return this.user;
}

/**
 * <pre>
 *           1..1     0..*
 * ArtGallerySystem ------------------------- ArtPiece
 *           artGallerySystem        &gt;       artPiece
 * </pre>
 */
private Set<ArtPiece> artPiece;

public Set<ArtPiece> getArtPiece() {
   if (this.artPiece == null) {
this.artPiece = new HashSet<ArtPiece>();
   }
   return this.artPiece;
}

/**
 * <pre>
 *           1..1     0..*
 * ArtGallerySystem ------------------------- Order
 *           artGallerySystem        &gt;       order
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
 *           1..1     0..*
 * ArtGallerySystem ------------------------- Address
 *           artGallerySystem        &gt;       address
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
