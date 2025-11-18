package com.example.multipartTest.controller;


import com.example.multipartTest.mapper.ImageMapper;
import com.example.multipartTest.response.ApiResponse;
import com.example.multipartTest.response.ImageResponse;
import com.example.multipartTest.services.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageMapper imageMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id){
        ImageResponse imageResponse = imageMapper.toImageResponse(imageService.getImageById(id));

        return ResponseEntity.ok(new ApiResponse("Success",imageResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long id){
        imageService.deleteImage(id);

        return ResponseEntity.ok(new ApiResponse("Image Deleted Successful",null));
    }

    @PutMapping(value = "/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long id ,@RequestPart MultipartFile file){
        ImageResponse imageResponse = imageMapper.toImageResponse(imageService.updateImage(id,file));

        return ResponseEntity.ok(new ApiResponse("Image Updated Successful",imageResponse));
    }


}
