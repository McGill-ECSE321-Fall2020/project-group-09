package ca.mcgill.ecse321.artgallerysystem.dto;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;

public class UserRoleDTO {
	private ArtGallerySystemUser artGallerySystemUser;


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

	public String getUserRoleId() {
	return this.userRoleId;
	       }
}
