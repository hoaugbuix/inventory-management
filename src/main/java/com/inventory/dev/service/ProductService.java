package com.inventory.dev.service;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInfoEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {


    // PRODUCT INFO
    public void saveProductInfo(ProductInfoEntity productInfo) throws Exception;

    public void updateProductInfo(ProductInfoEntity productInfo) throws Exception;

    public void deleteProductInfo(ProductInfoEntity productInfo) throws Exception;

    public List<ProductInfoEntity> findProductInfo(String property, Object value);

    public List<ProductInfoEntity> getAllProductInfo(ProductInfoEntity productInfo, Paging paging);

    public ProductInfoEntity findByIdProductInfo(int id);

    void processUploadFile(MultipartFile multipartFile, String fileName) throws IllegalStateException, IOException;
}
