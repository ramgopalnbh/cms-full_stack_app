package com.nbh.cafe.service.serviceImpl;

import com.nbh.cafe.constants.CafeConstant;
import com.nbh.cafe.dto.CategoryRequest;
import com.nbh.cafe.model.Category;
import com.nbh.cafe.repository.CategoryRepository;
import com.nbh.cafe.service.AdminService;
import com.nbh.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> addCategory(CategoryRequest request) {
        try{
            Optional<Category> existedCategory = categoryRepository.findByName(request.getName());
            if(existedCategory.isPresent()){
                log.info("Category name already exists!!");
                return CafeUtils.getResponseEntity("Category name already exists!!", HttpStatus.BAD_REQUEST);
            }else {
                Category category = new Category();
                category.setName(request.getName());
                category.setDescription(request.getDescription());
                category.setImage(request.getImage().getBytes());
                categoryRepository.save(category);
                log.info("Category added successfully.");
                return CafeUtils.getResponseEntity("Category added successfully.",HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
