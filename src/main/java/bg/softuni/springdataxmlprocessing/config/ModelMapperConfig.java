package bg.softuni.springdataxmlprocessing.config;

import bg.softuni.springdataxmlprocessing.dtos.product.ProductDto;
import bg.softuni.springdataxmlprocessing.dtos.product.ProductExportWrapperDto;
import bg.softuni.springdataxmlprocessing.dtos.product.ProductWithNamePriceBuyerDto;
import bg.softuni.springdataxmlprocessing.dtos.product.SoldProductWrapperDto;
import bg.softuni.springdataxmlprocessing.dtos.user.UserWithSoldProductDto;
import bg.softuni.springdataxmlprocessing.models.Product;
import bg.softuni.springdataxmlprocessing.models.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfig {
    private ModelMapper modelMapper;

    @Bean
    public ModelMapper getInstance() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            configure();
        }

        return modelMapper;
    }

    private void configure() {

    }

}
