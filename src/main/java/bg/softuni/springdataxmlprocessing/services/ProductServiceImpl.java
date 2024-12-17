package bg.softuni.springDataJsonProcessing.services;

import bg.softuni.springDataJsonProcessing.dtos.ProductDto;
import bg.softuni.springDataJsonProcessing.dtos.ProductWithSellerFullNameDto;
import bg.softuni.springDataJsonProcessing.models.Category;
import bg.softuni.springDataJsonProcessing.models.Product;
import bg.softuni.springDataJsonProcessing.models.User;
import bg.softuni.springDataJsonProcessing.repositories.CategoryRepository;
import bg.softuni.springDataJsonProcessing.repositories.ProductRepository;
import bg.softuni.springDataJsonProcessing.repositories.UserRepository;
import bg.softuni.springDataJsonProcessing.services.interfaces.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

@Service
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void addAll(ProductDto[] productDtos) {
        List<Product> list = Arrays.stream(productDtos).map(p -> {
            Product product = modelMapper.map(p, Product.class);
            product.setBuyer(getRandomBuyer());
            product.setSeller(getRandomSeller());
            product.setCategories(getRandomCategories().stream().toList());
            return product;
        }).toList();

        productRepository.saveAll(list);
    }

    @Override
    public List<ProductWithSellerFullNameDto> getProductsInRange(BigDecimal from, BigDecimal to) {
        List<ProductWithSellerFullNameDto> dtos = productRepository
                .findAllByPriceBetween(from, to).stream()
                .map((element) -> modelMapper.map(element, ProductWithSellerFullNameDto.class))
                .toList();

        if (dtos.isEmpty()) {
            throw new IllegalArgumentException("No products in this price range were found!");
        }

        return dtos;
    }

    private Set<Category> getRandomCategories() {
        long numberOfCategories = ThreadLocalRandom.current().nextLong(categoryRepository.count());

        if (numberOfCategories == 0) {
            numberOfCategories++;
        }

        Set<Category> categories = new HashSet<>();

        LongStream.
                rangeClosed(1L, numberOfCategories).
                forEach(n -> categories.add(categoryRepository.findById(ThreadLocalRandom.current()
                                .nextLong(categoryRepository.count()) +1)
                        .orElse(null)));

        return categories;
    }

    private User getRandomSeller() {
        long id = ThreadLocalRandom.current().nextLong(userRepository.count()) + 1;

        Optional<User> byId = userRepository.findById(id);
        while (byId.isEmpty()) {
            byId = userRepository.findById(id);
        }

        return byId.get();
    }

    private User getRandomBuyer() {
        int num = ThreadLocalRandom.current().nextInt(100);
        long id = ThreadLocalRandom.current().nextLong(userRepository.count()) + 1;

        if (isPrime(num)) {
            return null;
        }

        return userRepository.findById(id).orElse(null);
    }

    private boolean isPrime(int num) {
        if (num <= 1)
            return false;

        for (int i = 2; i <= num / 2; i++)
            if (num % i == 0)
                return false;

        return true;
    }
}
