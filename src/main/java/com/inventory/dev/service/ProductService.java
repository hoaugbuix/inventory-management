package com.inventory.dev.service;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInfoEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {
    void saveProductInfo(ProductInfoEntity productInfo) throws Exception;

    void updateProductInfo(ProductInfoEntity productInfo) throws Exception;

    void deleteProductInfo(ProductInfoEntity productInfo) throws Exception;

    List<ProductInfoEntity> findProductInfo(String property, Object value);

    List<ProductInfoEntity> getAllProductInfo(ProductInfoEntity productInfo, Paging paging);

    ProductInfoEntity findByIdProductInfo(int id);

    void processUploadFile(MultipartFile multipartFile, String fileName) throws IllegalStateException, IOException;
}
