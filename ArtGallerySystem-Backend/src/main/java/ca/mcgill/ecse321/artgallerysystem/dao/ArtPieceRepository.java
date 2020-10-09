package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;

@Repository
public interface ArtPieceRepository extends CrudRepository <ArtPiece, String>  {
public ArtPiece findArtPieceByArtPieceId(String artPieceId);
public List<ArtPiece> finfArtPieceByUserRoleId(String artistId);
}
