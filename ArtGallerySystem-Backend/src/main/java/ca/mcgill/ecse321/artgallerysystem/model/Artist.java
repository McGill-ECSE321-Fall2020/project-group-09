package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Id;
import java.util.Set;
import java.util.HashSet;

public class Artist extends UserRole {
   private double credit;
   
   public void setCredit(double value) {
      this.credit = value;
   }
   
   public double getCredit() {
      return this.credit;
   }
   
   private String artistId;

public void setArtistId(String value) {
    this.artistId = value;
}
@Id
public String getArtistId() {
    return this.artistId;
}
   /**
    * <pre>
    *           1..*     0..*
    * Artist ------------------------- ArtPiece
    *           artist        &gt;       artPiece
    * </pre>
    */
   private Set<ArtPiece> artPiece;
   
   public Set<ArtPiece> getArtPiece() {
      if (this.artPiece == null) {
         this.artPiece = new HashSet<ArtPiece>();
      }
      return this.artPiece;
   }
   
   }