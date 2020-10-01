package ca.mcgill.ecse321.artgallerysystem.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;

public interface ArtPieceRepository extends CrudRepository<ArtPiece, String>{

	ArtPiece findArtPieceById(String id);

}