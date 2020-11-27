package ca.mcgill.ecse321.artgallerysystem.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.artgallerysystem.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;

import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtPieceException;

import static java.lang.String.valueOf;

/**
 * this class contains useful business methods to manipulate data in backend, used in controller
 * @author Zheyan Tu
 *
 */
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
    @Autowired
    ArtistRepository artistRepository;

    /**
     * create a new art piece
     * @param id artPiece id
     * @param name artPiece name
     * @param des artPiece image URL
     * @param author artPiece author
     * @param price artPiece price
     * @param date artPiece date
     * @param status artPiece status
     * @param artist artPiece artist List
     * @return
     */
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

    /**
     * get artPiece by artPiece id
     * @param id artPiece id
     * @return artPiece
     */
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

    /**
     * get all artPieces in database
     * @return List of artPiece
     */
    @Transactional
    public List<ArtPiece> getAllArtPieces(){
        return toList(artpieceRepository.findAll());

    }

    /**
     * get artist by artist userRole id
     * @param id artist userRole id
     * @return List of artPiece
     */
    @Transactional
    public List<ArtPiece> getArtPiecesByArtist(String id){
       Artist artist =  artistRepository.findArtistByUserRoleId(id);
       List<ArtPiece> artistArtPieces = new ArrayList<ArtPiece>();
       for (int i = 0; i < toList(artpieceRepository.findAll()).size(); i++){
           if (toList(artpieceRepository.findAll()).get(i).getArtist().contains(artist)){
               artistArtPieces.add(toList(artpieceRepository.findAll()).get(i));
           }
       }
       return artistArtPieces;
    }

    /**
     * delete artPiece by artPiece id
     * @param id artPiece id
     * @return
     */
    @Transactional
    public ArtPiece deleteArtPiece (String id) {
        if (id == null||id == "") {
            throw new ArtPieceException ("provide valid id");
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

    /**
     * update artPiece date
     * @param id artPiece id
     * @param newdate new date
     * @return updated artPiece
     */
    @Transactional
    public ArtPiece updateArtPieceDate (String id, Date newdate) {
        if (id == null||id == "") {
            throw new ArtPieceException ("provide valid id");
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

    /**
     * update artPiece des
     * @param id artPiece id
     * @param des new artPiece URL
     * @return updated artPiece
     */
    @Transactional
    public ArtPiece updateArtPieceDescription (String id, String des) {
        if (id == null||id == "") {
            throw new ArtPieceException ("provide valid id");
        }
        ArtPiece artpiece = artpieceRepository.findArtPieceByArtPieceId(id);
        if (artpiece == null) {
            throw new ArtPieceException ("not exist artpiece");
        }

        artpiece.setDescription(des);
        artpieceRepository.save(artpiece);
        return artpiece;
    }

    /**
     * update artPiece price
     * @param id artPiece id
     * @param price new price
     * @return updated artPiece
     */
    @Transactional
    public ArtPiece updateArtPiecePrice (String id, double price) {
        if (id == null||id == "") {
            throw new ArtPieceException ("provide valid id");
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

    /**
     * update artPiece name
     * @param id artPiece id
     * @param name new name
     * @return updated artPiece
     */
    @Transactional
    public ArtPiece updateArtPieceName (String id, String name) {
        if (id == null||id == "") {
            throw new ArtPieceException ("provide valid id");
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

    /**
     * update artPiece status
     * @param id artPiece id
     * @param status new status
     * @return updated artPiece
     */
    @Transactional
    public ArtPiece updateArtPieceStatus (String id, String status){
        if (id == null||id == "") {
            throw new ArtPieceException ("provide valid id");
        }
        ArtPiece artPiece = artpieceRepository.findArtPieceByArtPieceId(id);
        if (artPiece == null) {
            throw new ArtPieceException ("not exist artpiece");
        }
        if (valueOf(artPiece.getArtPieceStatus()) == status){
            throw new ArtPieceException ("same status");
        }

        if (status == valueOf(ArtPieceStatus.Available)){
            artPiece.setArtPieceStatus(ArtPieceStatus.Available);
        }else if (status == valueOf(ArtPieceStatus.Sold)){
            artPiece.setArtPieceStatus(ArtPieceStatus.Sold);
        }
        artpieceRepository.save(artPiece);
        return artPiece;
    }

    /**
     * update artPiece author
     * @param id artPiece id
     * @param author new author
     * @return updated artPiece
     */
    @Transactional
    public ArtPiece updateArtPieceAuthor (String id, String author){
        if (id == null||id == "") {
            throw new ArtPieceException ("provide valid id");
        }
        ArtPiece artPiece = artpieceRepository.findArtPieceByArtPieceId(id);
        if (artPiece == null) {
            throw new ArtPieceException ("not exist artpiece");
        }
        if (artPiece.getAuthor()== author){
            throw new ArtPieceException ("same author");
        }
        artPiece.setAuthor(author);
        artpieceRepository.save(artPiece);
        return artPiece;
    }

    /**
     * Associate an artist to an art piece.
     * Note that this method MUST be called separately after creating an art piece.
     * Otherwise the art piece cannot be accessed by the artist.
     * An IllegalArgumentException will be thrown if the id or artist is null or empty or the art piece does not exist.
     * Added Nov 10.
     * @author Zhekai Jiang 
     * @param id The id of the art piece.
     * @param artist The artist to be associated with the art piece.
     * @return The updated ArtPiece instance.
     */
    @Transactional
    public ArtPiece addArtist(String id, Artist artist) {
    	if(id == null || id.length() == 0) {
    		throw new IllegalArgumentException("Art piece id cannot be empty!");
    	}
    	if(artist == null) {
    		throw new IllegalArgumentException("Artist cannot be empty!");
    	}
    	ArtPiece artPiece = artpieceRepository.findArtPieceByArtPieceId(id);
    	if(artPiece == null) {
    		throw new IllegalArgumentException("Art piece with id " + id + " does not exist.");
    	}
    	if(artPiece.getArtist() == null) {
    		artPiece.setArtist(new HashSet<Artist>());
    	}
    	artPiece.getArtist().add(artist);
    	return artPiece;
    }

    /**
     * helper method
     * @param iterable
     * @param <T>
     * @return
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}
