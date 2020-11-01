package ca.mcgill.ecse321.artgallerysystem.service;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtGallerySystemUserRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtPieceRepository;
import ca.mcgill.ecse321.artgallerysystem.dao.ArtistRepository;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystem;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.artgallerysystem.model.ArtPieceStatus;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;
import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtPieceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import static org.mockito.ArgumentMatchers.any;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TestArtPieceService {
    @Mock
    private ArtPieceRepository artPieceRepository;
    private ArtistRepository artistRepository;
    private ArtGallerySystemUserRepository userRepository;
    private ArtGallerySystemRepository artGallerySystemRepository;
    private static final String ap_id = "test ap";
    private static final String NAME = "name";
    private static final String DES = "description";
    private static final double PRICE = 10;
    private static final String AUTHOR = "author";
    private static final Date DATE = java.sql.Date.valueOf("2020-12-12");
    private static final ArtPieceStatus STATUS = ArtPieceStatus.Available;
    private Set<Artist> ARTISTS = createArtist();
    private Set<Artist> EARTISTS;

    @InjectMocks
    private ArtPieceService artPieceService;

    @BeforeEach
    public void setMockOutput() {
        MockitoAnnotations.initMocks(this);
        lenient().when(artPieceRepository.findArtPieceByArtPieceId(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ap_id)){
                ArtPiece artPiece = new ArtPiece();
                artPiece.setArtPieceId(ap_id);
                artPiece.setDate(DATE);
                artPiece.setAuthor(AUTHOR);
                artPiece.setDescription(DES);
                artPiece.setPrice(PRICE);
                artPiece.setName(NAME);
                artPiece.setArtPieceStatus(STATUS);
               // ARTISTS = createArtist();
                artPiece.setArtist(ARTISTS);

                artPieceRepository.save(artPiece);
                return artPiece;
            }else{
                return null;
            }
        });
        lenient().when(artPieceRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<ArtPiece> artPieces = new ArrayList<ArtPiece>();
            ArtPiece artPiece = new ArtPiece();
            artPiece.setArtPieceId(ap_id);
            artPiece.setDate(DATE);
            artPiece.setAuthor(AUTHOR);
            artPiece.setDescription(DES);
            artPiece.setPrice(PRICE);
            artPiece.setName(NAME);
            artPiece.setArtPieceStatus(STATUS);
            //ARTISTS = createArtist();
            artPiece.setArtist(ARTISTS);
            artPieceRepository.save(artPiece);
            artPieces.add(artPiece);
            return artPieces;
     });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(artPieceRepository.save(any(ArtPiece.class))).thenAnswer(returnParameterAsAnswer);

    }

    @Test
    public void testCreateArtPieceWithoutId(){
        String error = null;
        ArtPiece artPiece = new ArtPiece();
        try{
            artPiece = artPieceService.createArtPiece(null,NAME,DES,AUTHOR,PRICE,DATE,STATUS,ARTISTS);
        }catch(ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("invalid id",error);
    }

    @Test
    public void testCreateArtPieceWithoutName(){
        String error = null;
        ArtPiece artPiece = new ArtPiece();
        try{
            artPiece = artPieceService.createArtPiece(ap_id,null,DES,AUTHOR,PRICE,DATE,STATUS,ARTISTS);
        }catch(ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("please provide name",error);
    }

    @Test
    public void testCreateArtPieceWithNegativePrice(){
        String error = null;
        ArtPiece artPiece = new ArtPiece();
        try{
            artPiece = artPieceService.createArtPiece(ap_id,NAME,DES,AUTHOR,-10,DATE,STATUS,ARTISTS);
        }catch(ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("invalid price",error);
    }

    @Test
    public void testCreateArtPieceWithoutDate(){
        String error = null;
        ArtPiece artPiece = new ArtPiece();
        try{
            artPiece = artPieceService.createArtPiece(ap_id,NAME,DES,AUTHOR,PRICE,null,STATUS,ARTISTS);
        }catch(ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide date",error);
    }

    @Test
    public void testCreateArtPieceWithoutAuthor(){
        String error = null;
        ArtPiece artPiece = new ArtPiece();
        try{
            artPiece = artPieceService.createArtPiece(ap_id,NAME,DES,null,PRICE,DATE,STATUS,ARTISTS);
        }catch(ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide author",error);
    }

    @Test
    public void testCreateArtPieceWithoutArtist(){
        String error = null;
        ArtPiece artPiece = new ArtPiece();
        try{
            artPiece = artPieceService.createArtPiece(ap_id,NAME,DES,AUTHOR,PRICE,DATE,STATUS,null);
        }catch(ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide artist",error);
    }

    @Test
    public void testCreateArtPieceWithoutStatus(){
        String error = null;
        ArtPiece artPiece = new ArtPiece();
        try{
            artPiece = artPieceService.createArtPiece(ap_id,NAME,DES,AUTHOR,PRICE,DATE,null,ARTISTS);
        }catch(ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide status",error);
    }

    @Test
    public void testCreateArtPiece(){
        ArtPiece artPiece = new ArtPiece();
        try{
            artPiece = artPieceService.createArtPiece(ap_id,NAME,DES,AUTHOR,PRICE,DATE,STATUS,ARTISTS);
        }catch(ArtPieceException e){
            fail();
        }
        assertNotNull(artPiece);
    }

    @Test
    public void testDelete(){
        try{
            artPieceService.deleteArtPiece(ap_id);
        }catch (ArtPieceException e){
            fail();
        }
    }

    @Test
    public void testDeleteNotExist(){
        String error = null;
        try{
            artPieceService.deleteArtPiece("apid2");
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("not exist artpiece",error);
    }

    @Test
    public void testDeleteByEmptyId(){
        String error = null;
        try{
            artPieceService.deleteArtPiece("");
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testDeleteByNullId(){
        String error = null;
        try{
            artPieceService.deleteArtPiece(null);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testGetAll(){
        int size = artPieceService.getAllArtPieces().size();
        assertEquals(size,1);
    }

    @Test
    public void testGetArtPiece(){
        try{
            artPieceService.getArtPiece(ap_id);
        } catch (ArtPieceException e){
            fail();
        }
    }

    @Test
    public void testGetArtPieceNotFind(){
        String error = null;
        try{
            artPieceService.getArtPiece("apid2");
        }catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("not exist artpiece", error);
    }

    @Test
    public void testGetByEmptyId(){
        String error = null;
        try{
            artPieceService.getArtPiece("");
        }catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id", error);
    }

    @Test
    public void testGetByNullId(){
        String error = null;
        try{
            artPieceService.getArtPiece(null);
        }catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id", error);
    }

    @Test
    public void testUpdateName(){
        try{
            artPieceService.updateArtPieceName(ap_id,"Test02");
        }catch (ArtPieceException e){
            fail();
        }
    }

    @Test
    public void testUpdateSameName(){
        String error = null;
        try{
            artPieceService.updateArtPieceName(ap_id,NAME);
        }catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("same name",error);
    }

    @Test
    public void testUpdateNameByEmptyId(){
        String error = null;
        try{
            artPieceService.updateArtPieceName("",NAME);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateNameByNullId(){
        String error = null;
        try{
            artPieceService.updateArtPieceName(null,NAME);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateNameByNotExistId(){
        String error = null;
        try{
            artPieceService.updateArtPieceName("NEWAP",NAME);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("not exist artpiece",error);
    }

    @Test
    public void testUpdateStatus(){
        try{
            artPieceService.updateArtPieceStatus(ap_id,"Sold");
        }catch (ArtPieceException e){
            fail();
        }
    }

    @Test
    public void testUpdateStatusByEmptyId(){
        String error = null;
        try{
            artPieceService.updateArtPieceAuthor("","Sold");
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateStatusByNullId(){
        String error = null;
        try{
            artPieceService.updateArtPieceAuthor(null,"Sold");
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateSameStatus(){
        String error = null;
        try{
            artPieceService.updateArtPieceStatus(ap_id,"Available");
        }catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("same status",error);
    }

    @Test
    public void testUpdateStatusByNotExistId(){
        String error = null;
        try{
            artPieceService.updateArtPieceStatus("NEWAP",valueOf(STATUS.Sold));
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("not exist artpiece",error);
    }

    @Test
    public void testUpdateDes(){
        try{
            artPieceService.updateArtPieceDescription(ap_id,"NEWDES");
        }catch (ArtPieceException e){
            fail();
        }
    }

    @Test
    public void testUpdateDesByEmptyId(){
        String error = null;
        try{
            artPieceService.updateArtPieceDescription("",DES);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateDesByNullId(){
        String error = null;
        try{
            artPieceService.updateArtPieceDescription(null,DES);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateDesByNotExistId(){
        String error = null;
        try{
            artPieceService.updateArtPieceDescription("NEWAP",DES);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("not exist artpiece",error);
    }

    @Test
    public void testUpdateAuthor(){
        try{
            artPieceService.updateArtPieceAuthor(ap_id,"NEWAUTHOR");
        }catch (ArtPieceException e){
            fail();
        }
    }

    @Test
    public void testUpdateAuthorByNotExistId(){
        String error = null;
        try{
            artPieceService.updateArtPieceAuthor("NEWAP",AUTHOR);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("not exist artpiece",error);
    }

    @Test
    public void testUpdateAuthorByEmptyId(){
        String error = null;
        try{
            artPieceService.updateArtPieceAuthor("",AUTHOR);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateAuthorByNullId(){
        String error = null;
        try{
            artPieceService.updateArtPieceAuthor(null,AUTHOR);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateSameAuthor(){
        String error = null;
        try{
            artPieceService.updateArtPieceAuthor(ap_id,AUTHOR);
        }catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("same author",error);
    }

    @Test
    public void testUpdatePrice(){
        try{
            artPieceService.updateArtPiecePrice(ap_id,99);
        }catch (ArtPieceException e){
            fail();
        }
    }

    @Test
    public void testUpdatePriceByEmptyId(){
        String error = null;
        try{
            artPieceService.updateArtPiecePrice("",PRICE);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdatePriceByNullId(){
        String error = null;
        try{
            artPieceService.updateArtPiecePrice(null,PRICE);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdatePriceByNotExistId(){
        String error = null;
        try{
            artPieceService.updateArtPiecePrice("NEWAP",PRICE);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("not exist artpiece",error);
    }

    @Test
    public void testUpdateDate(){
        try{
            artPieceService.updateArtPieceDate(ap_id,java.sql.Date.valueOf("2023-10-12"));
        } catch (ArtPieceException e){
            fail();
        }
    }

    @Test
    public void testUpdateDateByEmptyId(){
        String error = null;
        try{
            artPieceService.updateArtPieceDate("",java.sql.Date.valueOf("2023-10-12"));
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateDateByNullId(){
        String error = null;
        try{
            artPieceService.updateArtPieceDate(null,java.sql.Date.valueOf("2023-10-12"));
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("provide valid id",error);
    }

    @Test
    public void testUpdateDateByNotExistId(){
        String error = null;
        try{
            artPieceService.updateArtPieceDate("NEWAP",java.sql.Date.valueOf("2023-10-12"));
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("not exist artpiece",error);
    }

    @Test
    public void testUpdateDateBySameDate(){
        String error = null;
        try{
            artPieceService.updateArtPieceDate(ap_id,DATE);
        } catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("same date",error);
    }

    @Test
    public void testUpdateSamePrice(){
        String error = null;
        try{
            artPieceService.updateArtPiecePrice(ap_id,PRICE);
        }catch (ArtPieceException e){
            error = e.getMessage();
        }
        assertEquals("same price",error);
    }

    public Set<Artist> createArtist(){
    	
		ArtGallerySystemUser u = new ArtGallerySystemUser();
		u.setName("userTest");
		Artist artist = new Artist();
		artist.setArtGallerySystemUser(u);
		artist.setUserRoleId("id1");
		artist.setCredit(0.0);
		Set<Artist> arts = new HashSet<Artist>();
		arts.add(artist);
		return arts;
    }
}
