package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Id;
import java.util.Set;
import java.util.HashSet;

public class User {
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
   /**
    * <pre>
    *           1..1     0..2
    * User ------------------------- UserRole
    *           user        &gt;       userRole
    * </pre>
    */
   private Set<UserRole> userRole;
   
   public Set<UserRole> getUserRole() {
      if (this.userRole == null) {
         this.userRole = new HashSet<UserRole>();
      }
      return this.userRole;
   }
   
   /**
    * <pre>
    *           0..*     1..1
    * User ------------------------- ArtGallerySystem
    *           user        &lt;       artGallerySystem
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
