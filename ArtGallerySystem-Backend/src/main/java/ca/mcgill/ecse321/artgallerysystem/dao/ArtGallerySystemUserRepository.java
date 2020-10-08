package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;

@Repository
public interface ArtGallerySystemUserRepository extends CrudRepository <ArtGallerySystemUser, String>  {
public ArtGallerySystemUser findUserByName(String name);

}
