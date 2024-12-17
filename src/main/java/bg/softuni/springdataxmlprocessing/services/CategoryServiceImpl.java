package bg.softuni.springdataxmlprocessing.services;

import bg.softuni.springdataxmlprocessing.dtos.category.CategoriesWrapperDto;
import bg.softuni.springdataxmlprocessing.dtos.category.CategoryExportWrapperDto;
import bg.softuni.springdataxmlprocessing.dtos.category.CategoryWithProductCountAveragePriceTotalRevenueDto;
import bg.softuni.springdataxmlprocessing.models.Category;
import bg.softuni.springdataxmlprocessing.repositories.CategoryRepository;
import bg.softuni.springdataxmlprocessing.repositories.ProductRepository;
import bg.softuni.springdataxmlprocessing.services.interfaces.CategoryService;
import bg.softuni.springdataxmlprocessing.utils.Parser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORIES_XML_FILE_PATH = "src/main/resources/input/xml-files/categories.xml";
    private static final String CATEGORIES_BY_PRODUCTS_XML_FILE_PATH = "src/main/resources/output/xml-files/categories-by-products.xml";
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final Parser parser;

    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository, Parser parser) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.parser = parser;
    }

    @Override
    public void importCategories() throws JAXBException, FileNotFoundException {
        CategoriesWrapperDto categoriesWrapperDto = parser.fromFile(CATEGORIES_XML_FILE_PATH, CategoriesWrapperDto.class);

        List<Category> list = categoriesWrapperDto.getCategories()
                .stream()
                .map(dto -> modelMapper.map(dto, Category.class))
                .toList();

        categoryRepository.saveAll(list);
    }

    @Override
    public void exportCategoriesWithAverageProductPriceAndTotalRevenueSortedByProductsSize() throws JAXBException {
        List<CategoryWithProductCountAveragePriceTotalRevenueDto> list = categoryRepository.findAllOrderByProductsCountDesc()
                .stream()
                .map(c -> getCategoryWithProductCountAveragePriceTotalRevenueDto(c))
                .toList();

        CategoryExportWrapperDto wrapperDto = new CategoryExportWrapperDto();

        wrapperDto.setCategories(list);

        parser.toFile(CATEGORIES_BY_PRODUCTS_XML_FILE_PATH, wrapperDto);
    }

    private CategoryWithProductCountAveragePriceTotalRevenueDto getCategoryWithProductCountAveragePriceTotalRevenueDto(Category c) {
        CategoryWithProductCountAveragePriceTotalRevenueDto dto = modelMapper.map(c, CategoryWithProductCountAveragePriceTotalRevenueDto.class);
        dto.setProductsCount(categoryRepository.countProductIdByCategoryId(c.getId()));
        dto.setAveragePrice(categoryRepository.averageProductPriceByCategoryId(c.getId()));
        dto.setTotalRevenue(categoryRepository.sumProductPriceByCategoryId(c.getId()));
        return dto;
    }

}
