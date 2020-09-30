package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
public class User{
   private String name;

public void setName(String value) {
    this.name = value;
}
@Id
public String getName() {
    return this.name;
}
private String email;

public void setEmail(String value) {
    this.email = value;
}
public String getEmail() {
    return this.email;
}
private String password;

public void setPassword(String value) {
    this.password = value;
}
public String getPassword() {
    return this.password;
}
private String avatar;

public void setAvatar(String value) {
    this.avatar = value;
}
public String getAvatar() {
    return this.avatar;
}
   private Set<UserRole> userRole;
   
   @OneToMany(mappedBy="user" , cascade={CascadeType.ALL})
   public Set<UserRole> getUserRole() {
      return this.userRole;
   }
   
   public void setUserRole(Set<UserRole> userRoles) {
      this.userRole = userRoles;
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
