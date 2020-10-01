package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

@Entity
public class ArtPiece{
private String artPieceId;
   
   public void setArtPieceId(String value) {
this.artPieceId = value;
    }
@Id
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
private ArtPieceStatus artpieceStatus;

public void setArtpieceStatus(ArtPieceStatus value) {
this.artpieceStatus = value;
    }
public ArtPieceStatus getArtpieceStatus() {
return this.artpieceStatus;
    }
private Set<Artist> artist;

@ManyToMany
public Set<Artist> getArtist() {
   return this.artist;
}

public void setArtist(Set<Artist> artists) {
   this.artist = artists;
}

private Order order;

@OneToOne(mappedBy="artPiece")
public Order getOrder() {
   return this.order;
}

public void setOrder(Order order) {
   this.order = order;
}

private ArtGallerySystem artGallerySystem;

@ManyToOne(optional=false)
public ArtGallerySystem getArtGallerySystem() {
   return this.artGallerySystem;
}

public void setArtGallerySystem(ArtGallerySystem artGallerySystem) {
   this.artGallerySystem = artGallerySystem;
}

}
