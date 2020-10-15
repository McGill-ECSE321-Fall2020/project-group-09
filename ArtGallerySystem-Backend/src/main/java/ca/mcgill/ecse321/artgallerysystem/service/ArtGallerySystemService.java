package ca.mcgill.ecse321.artgallerysystem.service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.dao.AddressRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtGallerySystemException;
@Service
public class ArtGallerySystemService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Transactional
	public ArtGallerySystem getSystemById(String id) {
		Optional<ArtGallerySystem> system = artGallerySystemRepository.findById(id);
		if(system.isPresent()) {
			return system.get();
		}else {
			throw new ArtGallerySystemException ("System Not Found");
		}
		
	}

}
