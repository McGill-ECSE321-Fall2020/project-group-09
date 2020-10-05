package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Artist extends UserRole{
private Set<ArtPiece> artPiece;

@ManyToMany(mappedBy="artist")
public Set<ArtPiece> getArtPiece() {
   return this.artPiece;
}

public void setArtPiece(Set<ArtPiece> artPieces) {
   this.artPiece = artPieces;
}

private double credit;

public void setCredit(double value) {
this.credit = value;
    }
public double getCredit() {
return this.credit;
       }
   }
