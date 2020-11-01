package ca.mcgill.ecse321.artgallerysystem.dto;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.UserRole;

public class ArtGallerySystemUserDTO {
	private String name;
	   
	   public void setName(String value) {
	this.name = value;
	    }
	
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

	
	public Set<UserRole> getUserRole() {
	   return this.userRole;
	}

	public void setUserRole(Set<UserRole> userRoles) {
	   this.userRole = userRoles;
	}

	private ArtGallerySystem artGallerySystem;

	
	public ArtGallerySystem getArtGallerySystem() {
	   return this.artGallerySystem;
	}

	public void setArtGallerySystem(ArtGallerySystem artGallerySystem) {
	   this.artGallerySystem = artGallerySystem;
	}

}
