package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;

public interface ArtistRepository extends CrudRepository <Artist, String>  {
public Artist findArtistById(String id);

}
