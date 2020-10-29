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
import ca.mcgill.ecse321.artgallerysystem.dao.ArtPieceRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PaymentRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.PurchaseRepository;
import ca.mcgill.ecse321.artgallerysystem.dto.AddressDTO;
import ca.mcgill.ecse321.artgallerysystem.dto.ArtGallerySystemDTO;
import ca.mcgill.ecse321.artgallerysystem.model.Address;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.model.Customer;
import ca.mcgill.ecse321.artgallerysystem.model.Payment;
import ca.mcgill.ecse321.artgallerysystem.model.PaymentMethod;
import ca.mcgill.ecse321.artgallerysystem.model.Purchase;
import ca.mcgill.ecse321.artgallerysystem.service.exception.AddressException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtPieceException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.CustomerException;
import ca.mcgill.ecse321.artgallerysystem.service.exception.PaymentException;
@Service
public class ArtPieceService {
	@Autowired
	ArtGallerySystemRepository artGallerySystemRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
	@Autowired
	ArtPieceRepository artpieceRepository;
	@Transactional
	public ArtPiece createArtPiece(String id, String name, String des, String author, double price, Date date, ArtPieceStatus status, Set<Artist> artist) {
		if (id == null||id == "") {
			throw new ArtPieceException ("invalid id");
		}
		if (name == null||name == "") {
			throw new ArtPieceException ("please provide name");
		}
		if (author == null || author == "") {
			throw new ArtPieceException ("provide author");
		}
		if (price<0) {
			throw new ArtPieceException ("invalid price");
		}
		if (date == null) {
			throw new ArtPieceException ("provide date");
		}
		if (artist == null) {
			throw new ArtPieceException ("provide artist");
		}
		if (status == null) {
			throw new ArtPieceException ("provide status");
		}
		ArtPiece artpiece = new ArtPiece();
		artpiece.setArtPieceId(id);
		artpiece.setArtPieceStatus(status);
		artpiece.setPrice(price);
		artpiece.setAuthor(author);
		artpiece.setDate(date);
		artpiece.setDescription(des);
		artpiece.setName(name);
		artpiece.setArtist(artist);
		artpieceRepository.save(artpiece);
		return artpiece;
		
	}
	@Transactional 
	public ArtPiece getArtPiece(String id) {
		if(id == null||id == "") {
			throw new ArtPieceException ("provide valid id");
		}
		ArtPiece artpiece = artpieceRepository.findArtPieceByArtPieceId(id);
		if (artpiece == null) {
			throw new ArtPieceException ("not exist artpiece");
		}
		return artpiece;
	}
	@Transactional
	public List<ArtPiece> getAllArtPieces(){
		return toList(artpieceRepository.findAll());
		
	}
	@Transactional
	public ArtPiece deleteArtPiece (String id) {
		if (id == null||id == "") {
			throw new ArtPieceException ("provide vaild id");
		}
		ArtPiece artpiece = artpieceRepository.findArtPieceByArtPieceId(id);
		if (artpiece == null) {
			throw new ArtPieceException ("not exist artpiece");
		}
		if (artpiece.getPurchase()!=null) {
			throw new ArtPieceException ("not able to delete");
		}
		ArtPiece art = null;
		artpieceRepository.deleteById(id);
		return art;
	}
	@Transactional
	public ArtPiece updateArtPieceDate (String id, Date newdate) {
		if (id == null||id == "") {
			throw new ArtPieceException ("provide vaild id");
		}
		ArtPiece artpiece = artpieceRepository.findArtPieceByArtPieceId(id);
		if (artpiece == null) {
			throw new ArtPieceException ("not exist artpiece");
		}
		if(artpiece.getDate()==newdate) {
			throw new ArtPieceException ("same date");
		}
		artpiece.setDate(newdate);
		artpieceRepository.save(artpiece);
		return artpiece;
	}
	@Transactional
	public ArtPiece updateArtPieceDescription (String id, String des) {
		if (id == null||id == "") {
			throw new ArtPieceException ("provide vaild id");
		}
		ArtPiece artpiece = artpieceRepository.findArtPieceByArtPieceId(id);
		if (artpiece == null) {
			throw new ArtPieceException ("not exist artpiece");
		}
		
		artpiece.setDescription(des);
		artpieceRepository.save(artpiece);
		return artpiece;
	}
	@Transactional
	public ArtPiece updateArtPiecePrice (String id, double price) {
		if (id == null||id == "") {
			throw new ArtPieceException ("provide vaild id");
		}
		ArtPiece artpiece = artpieceRepository.findArtPieceByArtPieceId(id);
		if (artpiece == null) {
			throw new ArtPieceException ("not exist artpiece");
		}
		if (artpiece.getPrice()== price) {
			throw new ArtPieceException ("same price");
		}
		artpiece.setPrice(price);
		artpieceRepository.save(artpiece);
		return artpiece;
	}
	@Transactional
	public ArtPiece updateArtPieceName (String id, String name) {
		if (id == null||id == "") {
			throw new ArtPieceException ("provide vaild id");
		}
		ArtPiece artpiece = artpieceRepository.findArtPieceByArtPieceId(id);
		if (artpiece == null) {
			throw new ArtPieceException ("not exist artpiece");
		}
		if (artpiece.getName()== name) {
			throw new ArtPieceException ("same name");
		}
		artpiece.setName(name);
		artpieceRepository.save(artpiece);
		return artpiece;
	}
	private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}
