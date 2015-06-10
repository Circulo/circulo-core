package com.circulo.model.repository;

import com.circulo.model.*;
import com.circulo.util.DateFormat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by azim on 6/9/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class ProductRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    public void testCreate() {
        Product product = createProduct();

        categoryRepository.save(product.getCategory());
        supplierRepository.save(product.getSupplier());
        productRepository.save(product);

        Product productFound = mongoTemplate.findById(product.getId(), Product.class);

        Assert.assertNotNull(productFound);
        Assert.assertEquals(product.getId(), productFound.getId());
        Assert.assertEquals(product.getName(), productFound.getName());
        Assert.assertEquals(product.getBrand(), productFound.getBrand());

        Assert.assertEquals(product.getCategory().getId(), productFound.getCategory().getId());
        Assert.assertEquals(product.getCategory().getName(), productFound.getCategory().getName());

        Assert.assertEquals(product.getDescription(), productFound.getDescription());
        Assert.assertEquals(product.getStatus(), productFound.getStatus());

        Assert.assertEquals(product.getSupplier().getId(), productFound.getSupplier().getId());
        Assert.assertEquals(product.getSupplier().getName(), productFound.getSupplier().getName());
        Assert.assertEquals(product.getSupplier().getAddress().getCountry(), productFound.getSupplier().getAddress().getCountry());
        Assert.assertEquals(product.getSupplier().getAddress().getState(), productFound.getSupplier().getAddress().getState());
        Assert.assertEquals(product.getSupplier().getAddress().getCity(), productFound.getSupplier().getAddress().getCity());
        Assert.assertEquals(product.getSupplier().getAddress().getZipCode(), productFound.getSupplier().getAddress().getZipCode());
        Assert.assertEquals(product.getSupplier().getAddress().getPostalAddress(), productFound.getSupplier().getAddress().getPostalAddress());

        Assert.assertEquals(product.getTags(), productFound.getTags());
        Assert.assertEquals(3, productFound.getVariations().size());
        Assert.assertEquals(3, productFound.getTags().size());

        for (String tag : productFound.getTags()) {
            Assert.assertTrue(Arrays.asList("Test Tag1", "Test Tag2", "Test Tag3").contains(tag));
        }

        // Tests to make sure supplier is saved and can be retrieved correctly
        Supplier supplierFound = mongoTemplate.findById(product.getSupplier().getId(), Supplier.class);
        Assert.assertNotNull(supplierFound);
        Assert.assertEquals(product.getSupplier().getId(), supplierFound.getId());
        Assert.assertEquals(product.getSupplier().getName(), supplierFound.getName());
        Assert.assertEquals(product.getSupplier().getAddress().getCountry(), supplierFound.getAddress().getCountry());
        Assert.assertEquals(product.getSupplier().getAddress().getState(), supplierFound.getAddress().getState());
        Assert.assertEquals(product.getSupplier().getAddress().getCity(), supplierFound.getAddress().getCity());
        Assert.assertEquals(product.getSupplier().getAddress().getZipCode(), supplierFound.getAddress().getZipCode());
        Assert.assertEquals(product.getSupplier().getAddress().getPostalAddress(), supplierFound.getAddress().getPostalAddress());

        // Tests to make sure category is saved and can be retrieved correctly
        Category categoryFound = mongoTemplate.findById(product.getCategory().getId(), Category.class);
        Assert.assertNotNull(categoryFound);
        Assert.assertEquals(product.getCategory().getId(), categoryFound.getId());
        Assert.assertEquals(product.getCategory().getName(), categoryFound.getName());
    }

    private Product createProduct() {
        Product product = new Product();

        product.setId(UUID.randomUUID().toString());
        product.setName("Test Product " + product.getId());
        product.setBrand("Test Brand " + product.getId());
        product.setCategory(createCategory());
        product.setDescription("Test Description " + product.getId());
        product.setStatus(ProductStatus.ACTIVE);
        product.setSupplier(createSupplier());
        product.setTags(Arrays.asList("Test Tag1", "Test Tag2", "Test Tag3"));
        product.setVariations(createVariations());

        return product;
    }

    private List<Variation> createVariations() {
        List<Variation> variations = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Variation variation = new Variation();
            variation.setSku("Test SKU " + UUID.randomUUID().toString());
            variation.setName("Test Variation Name " + variation.getSku());
            variation.setDescription("Test Varation Description" + variation.getSku());
            variations.add(variation);
        }

        return variations;
    }

    private Category createCategory() {
        Category category = new Category();

        category.setId(UUID.randomUUID().toString());
        category.setName("Test Category " + category.getId());

        return category;
    }

    private Supplier createSupplier() {
        Supplier supplier = new Supplier();

        supplier.setId(UUID.randomUUID().toString());
        supplier.setName("Test Supplier " + supplier.getId());
        supplier.setAddress(createAddress());

        return supplier;
    }

    private Address createAddress() {
        Address address = new Address();

        address.setCity("San Francisco");
        address.setCountry("USA");
        address.setState("CA");
        address.setZipCode("11111");
        address.setPostalAddress("Test Postal Address");

        return address;
    }
}
