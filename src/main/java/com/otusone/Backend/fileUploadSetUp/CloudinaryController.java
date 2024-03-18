package com.otusone.Backend.fileUploadSetUp;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@RestController
//@RequestMapping("/cloudinary")
//@CrossOrigin(origins = "http://localhost:4200")
@Service
public class CloudinaryController {
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImageService imageService;

    public ResponseEntity<List<Image>> list(){
        List<Image> list = imageService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    public String upload(MultipartFile multipartFile) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return "Image not valid";
        }
        Map result = cloudinaryService.upload(multipartFile);
        Image image = new Image((String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id"));
        return imageService.save(image);
    }
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        Optional<Image> imageOptional = imageService.getOne(id);
        if (imageOptional.isEmpty()) {
            return new ResponseEntity<>("Image Not exist", HttpStatus.NOT_FOUND);
        }
        Image image = imageOptional.get();
        String cloudinaryImageId = image.getImageId();
        try {
            cloudinaryService.delete(cloudinaryImageId);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete image from Cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        imageService.delete(id);
        return new ResponseEntity<>("Image added !", HttpStatus.OK);
    }

}
