package com.otusone.Backend.fileUploadSetUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    public List<Image> list(){
        return imageRepository.findByOrderById();
    }
    public Optional<Image> getOne(int id){
        return imageRepository.findById(id);
    }
    public String save(Image image){

        imageRepository.save(image);
        return image.getImageUrl();
    }
    public void delete(int id){
        imageRepository.deleteById(id);
    }
    public boolean exists(int id){
        return imageRepository.existsById(id);
    }
}