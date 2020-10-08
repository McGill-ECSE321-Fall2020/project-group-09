package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;

public interface ArtGallerySystemRepository extends CrudRepository <ArtGallerySystem, String>   {
	public ArtGallerySystem findArtGallerySystemById(String artGallerySystemId);
}
