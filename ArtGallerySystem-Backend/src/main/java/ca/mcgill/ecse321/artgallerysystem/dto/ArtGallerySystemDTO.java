package ca.mcgill.ecse321.artgallerysystem.dto;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

public class ArtGallerySystemDTO {
	private Set<ArtGallerySystemUserDTO> artGallerySystemUser;

	
	public Set<ArtGallerySystemUserDTO> getArtGallerySystemUser() {
	   return this.artGallerySystemUser;
	}

	public void setArtGallerySystemUser(Set<ArtGallerySystemUserDTO> artGallerySystemUsers) {
	   this.artGallerySystemUser = artGallerySystemUsers;
	}

	private Set<ArtPieceDTO> artPiece;

	
	public Set<ArtPieceDTO> getArtPiece() {
	   return this.artPiece;
	}

	public void setArtPiece(Set<ArtPieceDTO> artPieces) {
	   this.artPiece = artPieces;
	}

	private Set<PurchaseDTO> purchase;

	
	public Set<PurchaseDTO> getPurchase() {
	   return this.purchase;
	}

	public void setPurchase(Set<PurchaseDTO> purchases) {
	   this.purchase = purchases;
	}

	private Set<AddressDTO> address;

	
	public Set<AddressDTO> getAddress() {
	   return this.address;
	}

	public void setAddress(Set<AddressDTO> addresss) {
	   this.address = addresss;
	}

	private String artGallerySystemId;

	public void setArtGallerySystemId(String value) {
	this.artGallerySystemId = value;
	    }
	
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
