package ca.mcgill.ecse321.artgallerysystem.dto;

import java.util.Set;

public class ArtistDTO {

    private Set<ArtPieceDTO> artPiece;


    public Set<ArtPieceDTO> getArtPiece() {
        return this.artPiece;
    }

    public void setArtPiece(Set<ArtPieceDTO> artPieces) {
        this.artPiece = artPieces;
    }

    private double credit;

    public void setCredit(double value) {
        this.credit = value;
    }
    public double getCredit() {
        return this.credit;
    }
}

