package bg.softuni.springDataJsonProcessing.services.interfaces;

import bg.softuni.springDataJsonProcessing.dtos.ProductDto;
import bg.softuni.springDataJsonProcessing.dtos.ProductWithSellerFullNameDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void addAll(ProductDto[] productDtos);

    List<ProductWithSellerFullNameDto> getProductsInRange(BigDecimal from, BigDecimal to);
}
