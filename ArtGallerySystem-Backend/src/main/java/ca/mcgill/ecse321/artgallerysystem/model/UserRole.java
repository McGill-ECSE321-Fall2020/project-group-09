package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class UserRole{
private ArtGallerySystemUser artGallerySystemUser;

@ManyToOne(optional=false)
public ArtGallerySystemUser getArtGallerySystemUser() {
   return this.artGallerySystemUser;
}

public void setArtGallerySystemUser(ArtGallerySystemUser artGallerySystemUser) {
   this.artGallerySystemUser = artGallerySystemUser;
}

private String userRoleId;

public void setUserRoleId(String value) {
this.userRoleId = value;
    }
@Id
public String getUserRoleId() {
return this.userRoleId;
       }
   }
