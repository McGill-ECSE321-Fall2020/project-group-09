package ca.mcgill.ecse321.artgallerysystem.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ArtGallerySystemException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ArtGallerySystemException (String msg) {
        super(msg);
    }
}
