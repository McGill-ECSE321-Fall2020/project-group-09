package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.artgallerysystem.model.User;

public interface UserRepository extends CrudRepository <User, String>  {
public User findUserByName(String name);

}
