package ca.mcgill.ecse321.artgallerysystem.model;

import java.sql.Date;
import java.util.Set;
import java.util.HashSet;

public class ArtPiece {
private String artPieceId;

public void setArtPieceId(String value) {
   this.artPieceId = value;
}

public String getArtPieceId() {
   return this.artPieceId;
}

private String name;

public void setName(String value) {
   this.name = value;
}

public String getName() {
   return this.name;
}

private String description;

public void setDescription(String value) {
   this.description = value;
}

public String getDescription() {
   return this.description;
}

private String author;

public void setAuthor(String value) {
   this.author = value;
}

public String getAuthor() {
   return this.author;
}

private double price;

public void setPrice(double value) {
   this.price = value;
}

public double getPrice() {
   return this.price;
}

private Date date;

public void setDate(Date value) {
   this.date = value;
}

public Date getDate() {
   return this.date;
}

private ArtPieceStatus artPieceStatus;

public void setArtPieceStatus(ArtPieceStatus value) {
   this.artPieceStatus = value;
}

public ArtPieceStatus getArtPieceStatus() {
   return this.artPieceStatus;
}

/**
 * <pre>
 *           0..*     1..*
 * ArtPiece ------------------------- Artist
 *           artPiece        &lt;       artist
 * </pre>
 */
private Set<Artist> artist;

public Set<Artist> getArtist() {
   if (this.artist == null) {
this.artist = new HashSet<Artist>();
   }
   return this.artist;
}

/**
 * <pre>
 *           1..1     0..1
 * ArtPiece ------------------------- Order
 *           artPiece        &gt;       order
 * </pre>
 */
private Order order;

public void setOrder(Order value) {
   this.order = value;
}

public Order getOrder() {
   return this.order;
}

/**
 * <pre>
 *           0..*     1..1
 * ArtPiece ------------------------- ArtGallerySystem
 *           artPiece        &lt;       artGallerySystem
 * </pre>
 */
private ArtGallerySystem artGallerySystem;

public void setArtGallerySystem(ArtGallerySystem value) {
   this.artGallerySystem = value;
}

public ArtGallerySystem getArtGallerySystem() {
   return this.artGallerySystem;
}

}
