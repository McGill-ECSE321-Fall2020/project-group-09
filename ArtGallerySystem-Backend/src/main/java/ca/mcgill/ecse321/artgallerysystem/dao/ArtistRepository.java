package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;

@Repository
public interface ArtistRepository extends CrudRepository <Artist, String>{
public Artist findArtistByUserRoleId(String userRoleId);
}

