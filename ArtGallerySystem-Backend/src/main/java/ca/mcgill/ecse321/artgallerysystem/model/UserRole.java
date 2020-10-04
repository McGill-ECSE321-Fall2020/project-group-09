package ca.mcgill.ecse321.artgallerysystem.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class UserRole{
private User user;

@ManyToOne(optional=false)
public User getUser() {
   return this.user;
}

public void setUser(User user) {
   this.user = user;
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
