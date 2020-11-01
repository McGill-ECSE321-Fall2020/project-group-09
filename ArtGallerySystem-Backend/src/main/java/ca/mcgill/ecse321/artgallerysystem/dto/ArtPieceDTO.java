package ca.mcgill.ecse321.artgallerysystem.dto;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;

public class ArtPieceDTO {

    private String artPieceId;

    public void setArtPieceId(String value) {
        this.artPieceId = value;
    }

    public String getArtPieceId() {
        return this.artPieceId;
    }
    private String name;

    public void setName(String value) {
        this.name = value;
    }
    public String getName() {
        return this.name;
    }
    private String description;

    public void setDescription(String value) {
        this.description = value;
    }
    public String getDescription() {
        return this.description;
    }
    private String author;

    public void setAuthor(String value) {
        this.author = value;
    }
    public String getAuthor() {
        return this.author;
    }
    private double price;

    public void setPrice(double value) {
        this.price = value;
    }
    public double getPrice() {
        return this.price;
    }
    private Date date;

    public void setDate(Date value) {
        this.date = value;
    }
    public Date getDate() {
        return this.date;
    }

    private ArtPieceStatus artPieceStatus;

    public void setArtPieceStatus(ArtPieceStatus value) {
        this.artPieceStatus = value;
    }
    public ArtPieceStatus getArtPieceStatus() {
        return this.artPieceStatus;
    }
    private Set<ArtistDTO> artist;


    public Set<ArtistDTO> getArtist() {
        return this.artist;
    }

    public void setArtist(Set<ArtistDTO> artists) {
        this.artist = artists;
    }

    private PurchaseDTO purchase;


    public PurchaseDTO getPurchase() {
        return this.purchase;
    }

    public void setPurchase(PurchaseDTO purchase) {
        this.purchase = purchase;
    }

    private ArtGallerySystemDTO artGallerySystem;


    public ArtGallerySystemDTO getArtGallerySystem() {
        return this.artGallerySystem;
    }

    public void setArtGallerySystem(ArtGallerySystemDTO artGallerySystem) {
        this.artGallerySystem = artGallerySystem;
    }
}

