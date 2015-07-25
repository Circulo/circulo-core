package com.circulo.model.repository;

import com.circulo.enums.ProductStatus;
import com.circulo.model.Category;
import com.circulo.model.Product;
import com.circulo.model.Variation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.circulo.util.TestUtil.createProduct;

/**
 * Created by azim on 6/9/15.
 */
//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class ProductRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryTest.class);

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
        Product product = createDocuments();

        Query query = new Query(Criteria.where("_id").is(product.getId()));
        List<Product> products = mongoTemplate.find(query, Product.class);

        Assert.assertEquals(1, products.size());

        Product productFound = products.get(0);

        checkProductData(product, productFound);
    }

    private void checkProductData(Product product, Product productFound) {
        Assert.assertNotNull(productFound);
        Assert.assertEquals(product.getId(), productFound.getId());
        Assert.assertEquals(product.getName(), productFound.getName());
        Assert.assertEquals(product.getBrand(), productFound.getBrand());

        Assert.assertEquals(product.getCategory().getId(), productFound.getCategory().getId());
        Assert.assertEquals(product.getCategory().getName(), productFound.getCategory().getName());

        Assert.assertEquals(product.getDescription(), productFound.getDescription());
        Assert.assertEquals(product.getStatus(), productFound.getStatus());

        Assert.assertEquals(product.getTags(), productFound.getTags());
        Assert.assertEquals(product.getVariations().size(), productFound.getVariations().size());
        Assert.assertEquals(product.getTags().size(), productFound.getTags().size());

        for (String tag : productFound.getTags()) {
            Assert.assertTrue(Arrays.asList("Test Tag1", "Test Tag2", "Test Tag3").contains(tag));
        }

        // Tests to make sure category is saved and can be retrieved correctly
        Category categoryFound = categoryRepository.findOne(product.getCategory().getId());
        Assert.assertNotNull(categoryFound);
        Assert.assertEquals(product.getCategory().getId(), categoryFound.getId());
        Assert.assertEquals(product.getCategory().getName(), categoryFound.getName());
    }

    @Test
    public void testFind() {
        Product product = createDocuments();
        Product productFound = productRepository.findOne(product.getId());
        checkProductData(product, productFound);
    }

    private Product createDocuments() {
        Product product = createProduct();
        categoryRepository.save(product.getCategory());
        productRepository.save(product);
        return product;
    }

    @Test
    public void testUpdate() {
        Product product = createDocuments();
        Product productFound = productRepository.findOne(product.getId());
        checkProductData(product, productFound);

        Variation variation = new Variation();
        variation.setSku("Test SKU " + UUID.randomUUID().toString());
        variation.setName("Test Variation Name " + variation.getSku());
        variation.setNotes("Test Varation Description" + variation.getSku());
        List<Variation> variations = product.getVariations();
        variations.add(variation);
        product.setVariations(variations);

        product.setName("Updated" + product.getName());
        product.setDescription("Updated" + product.getDescription());
        product.setStatus(ProductStatus.INACTIVE);
        productRepository.save(product);

        productFound = productRepository.findOne(product.getId());
        checkProductData(product, productFound);
    }

    @Test
    public void testDelete() {
        Product product = createDocuments();
        Product productFound = productRepository.findOne(product.getId());
        checkProductData(product, productFound);

        productRepository.delete(product);
        productFound = productRepository.findOne(product.getId());
        Assert.assertNull(productFound);
    }

    @Test
    public void testFindByCategory() {
        Product product = createDocuments();
        List<Product> productsFound = productRepository.findByCategory(product.getCategory());
        Assert.assertEquals(1, productsFound.size());

        Product productFound = productsFound.get(0);
        checkProductData(product, productFound);
    }

    @Test
    public void testFindByBrand() {
        Product product = createDocuments();
        List<Product> productsFound = productRepository.findByBrand(product.getBrand());
        Assert.assertEquals(1, productsFound.size());

        Product productFound = productsFound.get(0);
        checkProductData(product, productFound);
    }

}