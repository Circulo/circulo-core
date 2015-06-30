package com.circulo.model.repository;

import com.circulo.model.Category;
import org.junit.*;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

/**
 * Created by azim on 6/28/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class CategoryRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreate() {
        // create a new category
        Category category = createCategory();
        // check whether that category is created by fetching the cateogry from mongodb using the _id property.
        Query query = new Query(Criteria.where("_id").is(category.getId()));
        List<Category> categories = mongoTemplate.find(query, Category.class);
        // We should only get 1 category.
        Assert.assertEquals(1, categories.size());
        Category categoryFound = categories.get(0);
        // Check properties of category fetched with the category we created earlier.
        checkCategoryData(category, categoryFound);
    }

    private void checkCategoryData(Category category, Category categoryFound) {
        Assert.assertEquals(category.getId(), categoryFound.getId());
        Assert.assertEquals(category.getName(), categoryFound.getName());
    }

    private Category createCategory() {
        Category category = new Category();

        category.setId(UUID.randomUUID().toString());
        category.setName("Test Category " + category.getId());

        categoryRepository.save(category);
        return category;
    }

    @Test
    public void testFind() {
        createFindAndCheck();
    }

    private Category createFindAndCheck() {
        // create a new category
        Category category = createCategory();
        // Fetch the category using repository's find method
        Category categoryFound = categoryRepository.findOne(category.getId());
        // Check properties of category fetched with the category we created earlier.
        checkCategoryData(category, categoryFound);
        return category;
    }

    @Test
    public void testUpdate() {
        // create a new category, fetch it and check whether it's created correctly or not.
        Category category = createFindAndCheck();
        // update name.
        category.setName("Updated" + category.getName());
        // save the update
        categoryRepository.save(category);
        // fetch again and check whether the update persists or not.
        Category categoryFound = categoryRepository.findOne(category.getId());
        checkCategoryData(category, categoryFound);
    }

    @Test
    public void testDelete() {
        // create a new category, fetch it and check whether it's created correctly or not.
        Category category = createFindAndCheck();
        // delete the category
        categoryRepository.delete(category);
        // try to fetch now.
        Category categoryFound = categoryRepository.findOne(category.getId());
        Assert.assertNull(categoryFound);
    }
}
