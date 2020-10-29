package ca.mcgill.ecse321.artgallerysystem.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserRoleException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserRoleException (String msg) {
        super(msg);
    }
}
