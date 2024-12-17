package bg.softuni.springDataJsonProcessing.services;

import bg.softuni.springDataJsonProcessing.dtos.CategoryDto;
import bg.softuni.springDataJsonProcessing.dtos.CategoryStatisticsDto;
import bg.softuni.springDataJsonProcessing.models.Category;
import bg.softuni.springDataJsonProcessing.repositories.CategoryRepository;
import bg.softuni.springDataJsonProcessing.repositories.ProductRepository;
import bg.softuni.springDataJsonProcessing.services.interfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addAll(CategoryDto[] categoryDtos) {
        List<Category> list = Arrays.stream(categoryDtos).map(dto -> modelMapper.map(dto, Category.class)).toList();

        categoryRepository.saveAll(list);
    }

    @Override
    public List<CategoryStatisticsDto> getCategoriesByProductCount() {
        return categoryRepository.findAllOrderByProductsSizeDesc().stream().map(c -> {
            CategoryStatisticsDto dto = modelMapper.map(c, CategoryStatisticsDto.class);
            dto.setProductsCount(categoryRepository.countProductsByCategoryId(c.getId()));
            dto.setAveragePrice(categoryRepository.averageProductsPriceByCategoryId(c.getId()));
            dto.setTotalRevenue(categoryRepository.totalProductsRevenueByCategoryId(c.getId()));
            return dto;
        }).toList();
    }

}
