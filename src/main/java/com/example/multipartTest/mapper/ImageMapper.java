package com.example.multipartTest.mapper;

import com.example.multipartTest.models.Image;
import com.example.multipartTest.response.ImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // to made impl class (Component)
public interface ImageMapper {
    @Mapping(source = "filePath" , target = "url")
    ImageResponse toImageResponse(Image image);
}
