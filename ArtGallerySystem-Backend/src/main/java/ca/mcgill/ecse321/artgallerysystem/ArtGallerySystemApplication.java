package ca.mcgill.ecse321.artgallerysystem;


@RestController
@SpringBootApplication
public class ArtGallerySystemApplication{
public static void main(String[] args) {
SpringApplication.run(ArtGallerySystemApplication.class, args);
     }

@RequestMapping("/")
public String greeting ()
{
return "Hello world!";
     }
}