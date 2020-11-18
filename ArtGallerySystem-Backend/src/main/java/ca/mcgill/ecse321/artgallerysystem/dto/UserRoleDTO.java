package ca.mcgill.ecse321.artgallerysystem.dto;

public class UserRoleDTO {
	private ArtGallerySystemUserDTO artGallerySystemUser;


	public ArtGallerySystemUserDTO getArtGallerySystemUser() {
	   return this.artGallerySystemUser;
	}

	public void setArtGallerySystemUser(ArtGallerySystemUserDTO artGallerySystemUser) {
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
