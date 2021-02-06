package com.inventory.dev.service.impl;

import com.inventory.dev.dao.CategoryDAO;
import com.inventory.dev.dao.ProductInfoDAO;
import com.inventory.dev.entity.CategoryEntity;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.ProductInfoEntity;
import com.inventory.dev.service.ProductService;
import com.inventory.dev.util.ConfigLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductServiceImpl implements ProductService {
    private static final Logger log = Logger.getLogger(ProductServiceImpl.class);
    @Autowired
    private CategoryDAO<CategoryEntity> categoryDAO;
    @Autowired
    private ProductInfoDAO<ProductInfoEntity> productInfoDAO;

    @Override
    public void saveCategory(CategoryEntity category) throws Exception {
        log.info("Insert category " + category.toString());
        category.setActiveFlag(1);
        category.setCreatedDate(new Date());
        category.setUpdatedDate(new Date());
        categoryDAO.save(category);
    }

    @Override
    public void updateCategory(CategoryEntity category) throws Exception {
        log.info("Update category " + category.toString());
        category.setUpdatedDate(new Date());
        categoryDAO.update(category);
    }

    @Override
    public void deleteCategory(CategoryEntity category) throws Exception {
        category.setActiveFlag(0);
        category.setUpdatedDate(new Date());
        log.info("Delete category " + category.toString());
        categoryDAO.update(category);
    }

    @Override
    public List<CategoryEntity> findCategory(String property, Object value) {
        log.info("=====Find by property category start====");
        log.info("property =" + property + " value" + value.toString());
        return categoryDAO.findByProperty(property, value);
    }

    @Override
    public List<CategoryEntity> getAllCategory(CategoryEntity category, Paging paging) {
        log.info("show all category");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if (category != null) {
            if (category.getId() != null && category.getId() != 0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", category.getId());
            }
            if (category.getCode() != null && !StringUtils.isEmpty(category.getCode())) {
                queryStr.append(" and model.code=:code");
                mapParams.put("code", category.getCode());
            }
            if (category.getName() != null && !StringUtils.isEmpty(category.getName())) {
                queryStr.append(" and model.name like :name");
                mapParams.put("name", "%" + category.getName() + "%");
            }
        }
        return categoryDAO.findAll(queryStr.toString(), mapParams, paging);
    }

    @Override
    public CategoryEntity findByIdCategory(int id) {
        log.info("find category by id =" + id);
        return categoryDAO.findById(CategoryEntity.class, id);
    }

    // PRODUCT INFO

    @Override
    public void saveProductInfo(ProductInfoEntity productInfo) throws Exception {
        log.info("Insert productInfo " + productInfo.toString());
        productInfo.setActiveFlag(1);
        productInfo.setCreatedDate(new Date());
        productInfo.setUpdatedDate(new Date());
//        String fileName = System.currentTimeMillis()+"_"+productInfo.getMultipartFile().getOriginalFilename();
//        processUploadFile(productInfo.getMultipartFile(),fileName);
//        productInfo.setImgUrl("/upload/"+fileName);
        productInfoDAO.save(productInfo);
    }

    @Override
    public void updateProductInfo(ProductInfoEntity productInfo) throws Exception {
        log.info("Update productInfo " + productInfo.toString());


//        if(!productInfo.getMultipartFile().getOriginalFilename().isEmpty()) {
//
//            String fileName =  System.currentTimeMillis()+"_"+productInfo.getMultipartFile().getOriginalFilename();
//            processUploadFile(productInfo.getMultipartFile(),fileName);
//            productInfo.setImgUrl("/upload/"+fileName);
//        }
        productInfo.setUpdatedDate(new Date());
        productInfoDAO.update(productInfo);
    }

    @Override
    public void deleteProductInfo(ProductInfoEntity productInfo) throws Exception {
        productInfo.setActiveFlag(0);
        productInfo.setUpdatedDate(new Date());
        log.info("Delete productInfo " + productInfo.toString());
        productInfoDAO.update(productInfo);
    }

    @Override
    public List<ProductInfoEntity> findProductInfo(String property, Object value) {
        log.info("=====Find by property productInfo start====");
        log.info("property =" + property + " value" + value.toString());
        return productInfoDAO.findByProperty(property, value);
    }

    @Override
    public List<ProductInfoEntity> getAllProductInfo(ProductInfoEntity productInfo, Paging paging) {
        log.info("show all productInfo");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if (productInfo != null) {
            if (productInfo.getId() != null && productInfo.getId() != 0) {
                queryStr.append(" and model.id=:id");
                mapParams.put("id", productInfo.getId());
            }
            if (productInfo.getCode() != null && !StringUtils.isEmpty(productInfo.getCode())) {
                queryStr.append(" and model.code=:code");
                mapParams.put("code", productInfo.getCode());
            }
            if (productInfo.getName() != null && !StringUtils.isEmpty(productInfo.getName())) {
                queryStr.append(" and model.name like :name");
                mapParams.put("name", "%" + productInfo.getName() + "%");
            }
        }
        return productInfoDAO.findAll(queryStr.toString(), mapParams, paging);
    }

    @Override
    public ProductInfoEntity findByIdProductInfo(int id) {
        log.info("find productInfo by id =" + id);
        return productInfoDAO.findById(ProductInfoEntity.class, id);
    }

    @Override
    public void processUploadFile(MultipartFile multipartFile, String fileName) throws IllegalStateException, IOException {
        if (!multipartFile.getOriginalFilename().isEmpty()) {
            File dir = new File(ConfigLoader.getInstance().getValue("upload.location"));
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(ConfigLoader.getInstance().getValue("upload.location"), fileName);
            multipartFile.transferTo(file);
        }
    }
}
