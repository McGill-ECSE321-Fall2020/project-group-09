package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class ArtGallerySystem{
private Set<ArtGallerySystemUser> artGallerySystemUser;

@OneToMany(mappedBy="artGallerySystem", cascade={CascadeType.ALL})
public Set<ArtGallerySystemUser> getArtGallerySystemUser() {
   return this.artGallerySystemUser;
}

public void setArtGallerySystemUser(Set<ArtGallerySystemUser> artGallerySystemUsers) {
   this.artGallerySystemUser = artGallerySystemUsers;
}

private Set<ArtPiece> artPiece;

@OneToMany(mappedBy="artGallerySystem", cascade={CascadeType.ALL})
public Set<ArtPiece> getArtPiece() {
   return this.artPiece;
}

public void setArtPiece(Set<ArtPiece> artPieces) {
   this.artPiece = artPieces;
}

private Set<Purchase> purchase;

@OneToMany(mappedBy="artGallerySystem", cascade={CascadeType.ALL})
public Set<Purchase> getPurchase() {
   return this.purchase;
}

public void setPurchase(Set<Purchase> purchases) {
   this.purchase = purchases;
}

private Set<Address> address;

@OneToMany(mappedBy="artGallerySystem", cascade={CascadeType.ALL})
public Set<Address> getAddress() {
   return this.address;
}

public void setAddress(Set<Address> addresss) {
   this.address = addresss;
}

private String artGallerySystemId;

public void setArtGallerySystemId(String value) {
this.artGallerySystemId = value;
    }
@Id
public String getArtGallerySystemId() {
return this.artGallerySystemId;
    }
private double income;

public void setIncome(double value) {
this.income = value;
    }
public double getIncome() {
return this.income;
       }
   }
