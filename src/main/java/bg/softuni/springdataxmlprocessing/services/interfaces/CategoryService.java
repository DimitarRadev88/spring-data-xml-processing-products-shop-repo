package bg.softuni.springDataJsonProcessing.services.interfaces;

import bg.softuni.springDataJsonProcessing.dtos.CategoryDto;
import bg.softuni.springDataJsonProcessing.dtos.CategoryStatisticsDto;
import bg.softuni.springDataJsonProcessing.models.Category;

import java.util.List;

public interface CategoryService {
    void addAll(CategoryDto[] categoryDtos);

    List<CategoryStatisticsDto> getCategoriesByProductCount();

}
