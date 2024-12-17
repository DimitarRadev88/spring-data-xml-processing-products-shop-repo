package bg.softuni.springDataJsonProcessing.config;

import bg.softuni.springDataJsonProcessing.dtos.ProductWithSellerFullNameDto;
import bg.softuni.springDataJsonProcessing.models.Product;
import bg.softuni.springDataJsonProcessing.models.User;
import bg.softuni.springDataJsonProcessing.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class ApplicationBeanConfiguration {

    private Gson gson;
    private ModelMapper modelMapper;
    private BufferedReader bufferedReader;

    public ApplicationBeanConfiguration(UserRepository userRepository) {

    }

    @Bean
    public BufferedReader getBufferedReader() {
        if (bufferedReader == null) {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

        return bufferedReader;
    }

    @Bean
    public Gson getGsonInstance() {
        if (gson == null) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .setPrettyPrinting()
                    .create();
        }

        return gson;
    }

    @Bean
    public ModelMapper getModelMapperInstance() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            addMappings();
        }

        return modelMapper;
    }

    private void addMappings() {
        productToProductWithSellerFullNameDto();
    }

    private void productToProductWithSellerFullNameDto() {
        TypeMap<Product, ProductWithSellerFullNameDto> typeMap =
                modelMapper.createTypeMap(Product.class, ProductWithSellerFullNameDto.class);

        Converter<User, String> converter = c -> c.getSource() == null ?
                null :
                c.getSource().getFirstName() + " " + c.getSource().getLastName();

        typeMap.addMappings(mapper -> mapper
                .using(converter)
                .map(Product::getSeller, ProductWithSellerFullNameDto::setSeller));
    }


}
