package ca.mcgill.ecse321.artgallerysystem.service;


import ca.mcgill.ecse321.artgallerysystem.dao.ArtistRepository;
import ca.mcgill.ecse321.artgallerysystem.model.ArtGallerySystemUser;
import ca.mcgill.ecse321.artgallerysystem.model.Artist;

import ca.mcgill.ecse321.artgallerysystem.service.exception.ArtistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestArtistService {
    @Mock
    private ArtistRepository artistRepository;
    private static final String AID = "Test AID";
    private static final double CREDIT = 80.00;


    @InjectMocks
    private ArtistService artistService;
    @BeforeEach
    public void setMockOutput(){
        MockitoAnnotations.initMocks(this);
        lenient().when(artistRepository.findArtistByUserRoleId(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(AID)) {
                Artist artist = new Artist();
                artist.setCredit(CREDIT);
                artist.setUserRoleId(AID);
                artistRepository.save(artist);
                return artist;
            } else {
                return null;
            }
        });
        lenient().when(artistRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
            List<Artist> artists = new ArrayList<>();
            Artist artist = new Artist();
            artist.setCredit(CREDIT);
            artist.setUserRoleId(AID);
            artistRepository.save(artist);
            artists.add(artist);
            return artists;
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(artistRepository.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);
    }


    @Test
    public void testCreateArtist(){
        Artist artist = new Artist();
        ArtGallerySystemUser user = new ArtGallerySystemUser();
        try{
            artist = artistService.createArtist(user,AID,CREDIT);
        }catch(ArtistException e){
            fail();
        }
        assertNotNull(artist);
    }

    @Test
    public void testCreateArtistWithoutAID(){
        String error = null;
        Artist artist = new Artist();
        ArtGallerySystemUser user = new ArtGallerySystemUser();
        try{
            artist = artistService.createArtist(user,null,CREDIT);
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("invalid id",error);
    }

    @Test
    public void testCreateArtistWithNegativeCredit(){
        String error = null;
        Artist artist = new Artist();
        ArtGallerySystemUser user = new ArtGallerySystemUser();
        try{
            artist = artistService.createArtist(user,AID,-80);
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("negative credit",error);
    }

    @Test
    public void testDelete(){
        try{
            artistService.deleteArtist(AID);
        }catch(ArtistException e){
            fail();
        }
    }

    @Test
    public void testDeleteNotExist(){
        String error = null;
        try{
            artistService.deleteArtist("AID2");
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("not exist artist",error);
    }

    @Test
    public void testDeleteByEmptyId(){
        String error = null;
        try{
            artistService.deleteArtist("");
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("invalid id",error);
    }

    @Test
    public void testDeleteByNullId(){
        String error = null;
        try{
            artistService.deleteArtist(null);
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("invalid id",error);
    }

    @Test
    public void testGetAll(){
        int size = artistService.getAllArtists().size();
        assertEquals(size,1);
    }

    @Test
    public void testGetArtist(){
        try{
            artistService.getArtist(AID);
        }catch(ArtistException e){
            fail();
        }
    }

    @Test
    public void testGetArtistNotFind(){
        String error = null;
        try{
            artistService.getArtist("AID2");
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("not exist artist",error);
    }

    @Test
    public void testGetArtistEmptyId(){
        String error = null;
        try{
            artistService.getArtist("");
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("invalid id",error);
    }

    @Test
    public void testGetArtistNullId(){
        String error = null;
        try{
            artistService.getArtist(null);
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("invalid id",error);
    }

    @Test
    public void testUpdateCredit(){
        String error = null;
        try{
            artistService.updateArtistCredit(AID,70);
        }catch(ArtistException e){
            fail();
        }
    }

    @Test
    public void testUpdateSameCredit(){
        String error = null;
        try{
            artistService.updateArtistCredit(AID,CREDIT);
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("same credit",error);
    }

    @Test
    public void testUpdateNegativeCredit(){
        String error = null;
        try{
            artistService.updateArtistCredit(AID,-80);
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("negative credit",error);
    }

    @Test
    public void testUpdateCreditByEmptyId(){
        String error = null;
        try{
            artistService.updateArtistCredit("",CREDIT);
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("invalid id",error);
    }

    @Test
    public void testUpdateCreditByNullId(){
        String error = null;
        try{
            artistService.updateArtistCredit(null,CREDIT);
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("invalid id",error);
    }

    @Test void testUpdateByNotExistId(){
        String error = null;
        try{
            artistService.updateArtistCredit("NEWARTIST",CREDIT);
        }catch(ArtistException e){
            error = e.getMessage();
        }
        assertEquals("not exist artist",error);
    }
}
