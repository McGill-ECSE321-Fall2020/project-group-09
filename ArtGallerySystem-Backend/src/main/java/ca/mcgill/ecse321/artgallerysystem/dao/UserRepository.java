package ca.mcgill.ecse321.artgallerysystem.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.artgallerysystem.model.User;

public interface UserRepository extends CrudRepository<User, String>{

	User findUserByName(String name);

}
