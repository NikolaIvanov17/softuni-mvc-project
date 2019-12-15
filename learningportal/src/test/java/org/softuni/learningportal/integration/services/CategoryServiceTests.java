package org.softuni.learningportal.integration.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.learningportal.domain.entities.Category;
import org.softuni.learningportal.domain.models.service.CategoryServiceModel;
import org.softuni.learningportal.repository.CategoryRepository;
import org.softuni.learningportal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTests {

    @Autowired
    private CategoryService service;

    @MockBean
    private CategoryRepository mockCategoryRepository;



    @Test(expected = IllegalArgumentException.class)
    public void createWithInvalidValues_ThrowError() {
        service.addCategory(null);
        verify(mockCategoryRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void categoryService_findCategoryByIdWithInvalidValue_ThrowError() {
        service.findCategoryById(null);
        verify(mockCategoryRepository)
                .save(any());
    }

    //    @Test
//    public void createWithValidValues_ReturnCorrect() {
//       CategoryServiceModel category = new CategoryServiceModel();
//        category.setName("Best Category");
//        when(mockCategoryRepository.save(any()))
//                .thenReturn(new Category());
//
//        service.addCategory(category);
//        verify(mockCategoryRepository)
//                .save(any());
//    }

    @Test(expected = Exception.class)
    public void categoryService_deleteCategoryWithInvalidValue_ThrowError() {
        service.deleteCategory(null);
        verify(mockCategoryRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void categoryService_editCategorykWithInvalidValue_ThrowError() {
        CategoryServiceModel category = new CategoryServiceModel();
        service.editCategory(null, category);
        verify(mockCategoryRepository)
                .save(any());
    }
}
