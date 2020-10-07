package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;

@Repository
public interface ArtGallerySystemRepository extends CrudRepository <ArtGallerySystem, String>   {
	public ArtGallerySystem findArtGallerySystemByArtGallerySystemId(String id);
}
