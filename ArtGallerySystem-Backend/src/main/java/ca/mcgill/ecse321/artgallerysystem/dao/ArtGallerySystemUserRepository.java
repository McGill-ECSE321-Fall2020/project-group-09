package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;

public interface ArtGallerySystemUserRepository extends CrudRepository <ArtGallerySystemUser, String>  {
public ArtGallerySystemUser findUserByName(String name);

}
