package ca.mcgill.ecse321.artgallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.artgallerysystem.model.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository <UserRole, String>   {
	public UserRole findUserRoleByUserRoleId(String userRoleId);
}
