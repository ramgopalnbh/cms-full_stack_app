package com.nbh.cafe.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryRequest {
    private Integer id;
    private String name;
    private String description;
    private MultipartFile image;
    private byte[] returnedImage;
}
